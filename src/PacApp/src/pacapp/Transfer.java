package pacapp;

import NewDatabase.Content;
import be.derycke.pieter.com.COMException;
import jmtp.*;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.DatagramSocket;
import java.sql.*;

import NewDatabase.ContentDAO;

class Transfer extends Thread implements pacapp.TransferObject {
    private PortableDevice pD = null;
    private PortableDeviceManager pDM;
    private String ip;
    private String adbPath = null;
    private String mainPath = System.getProperty("user.dir");
    private String backupPath = null;


    //creates folder on the root of the device
    private void createFolder(String folderName, PortableDevice pD) {
        for (PortableDeviceObject obj1 : pD.getRootObjects())
        {
            if (obj1 instanceof PortableDeviceStorageObject)
            {
                PortableDeviceStorageObject store = (PortableDeviceStorageObject) obj1;
                store.createFolderObject(folderName);
            }
        }
    }

    //sets the target folder on the phone
    private PortableDeviceFolderObject setTargetFolder(String folderName, PortableDevice pD) {
        PortableDeviceFolderObject target = null;
        for (PortableDeviceObject obj1 : pD.getRootObjects())//gets root files of phone
        {
            if (obj1 instanceof PortableDeviceStorageObject)//if obj is phone storage or sd storage
            {
                PortableDeviceStorageObject store = (PortableDeviceStorageObject) obj1;
                for (PortableDeviceObject obj2 : store.getChildObjects())//gets child objects of internal or sd
                {
                    if (obj2.getOriginalFileName().equalsIgnoreCase(folderName))
                    {
                        target = (PortableDeviceFolderObject) obj2;
                    }
                }
            }
        }
        return target;
    }

    //checks if file exists on the phone
    private boolean doesFileExist(PortableDeviceFolderObject targetFolder, File file) {
        PortableDeviceObject[] items = targetFolder.getChildObjects();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getOriginalFileName().equalsIgnoreCase(file.getName())){
                //System.out.println(items[i].getOriginalFileName());
                return false;
            }
        }
        return true;
    }

    //checks if folder exists on device
    private boolean doesFolderExist(String folderName, PortableDevice pD) {
        for (PortableDeviceObject obj1 : pD.getRootObjects())
        {
            if (obj1 instanceof PortableDeviceStorageObject)
            {
                PortableDeviceStorageObject store = (PortableDeviceStorageObject) obj1;
                for (PortableDeviceObject obj2 : store.getChildObjects())
                {
                    if (obj2.getOriginalFileName().equalsIgnoreCase(folderName))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //returns string of the contents of a text file
    private String fileToString(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String ret;
        StringBuffer sb = new StringBuffer();
        while((line = br.readLine()) != null){
            sb.append(line);
            sb.append("\n");
        }
        ret = sb.toString();
        br.close();
        return ret;
    }

    //method to recursively search through files on phone
    //called by backup method
    private void recurBackup(PortableDeviceFolderObject object, String tab, File file) {
        tab = tab + "    ";

        for (PortableDeviceObject obj : object.getChildObjects())
        {
            System.out.println(tab + obj.getName());
            if (obj instanceof PortableDeviceFolderObject){
                File tempFile = new File(file.getPath() + "\\" + obj.getName());
                if(!tempFile.isDirectory()){
                    tempFile.mkdir();
                }
                recurBackup((PortableDeviceFolderObject) obj, tab, tempFile);
            }
            ptoPC(obj, file.getPath());
        }
    }

    //recursive method that helps restore bacup to phone
    private void recurRestore(PortableDeviceFolderObject object, File file){
        System.out.println("+++++++++++++++++++ STEPED INTO " + object.getName());
        File[] files = file.listFiles();
        File add = null;
        for (PortableDeviceObject obj : object.getChildObjects()){
            System.out.println(obj.getName());
            if(obj instanceof PortableDeviceFolderObject){//checks if obj is a folder
                System.out.println("====================" + obj.getName());
                for (int i = 0; i < files.length; i++) {
                    System.out.println(files[i].getPath());
                    if(obj.getName().equalsIgnoreCase(files[i].getName()) && files[i].isDirectory()){//if obj is a folder, checks to see if backup has a folder of the same name
                        recurRestore((PortableDeviceFolderObject) obj, files[i]);
                        System.out.println("+++++++++++++++++++ STEPED OUT " + obj.getName());
                    }else if (files[i].isFile()){
                        pctoP(object, files[i]);
                    }
                }
            }else{
                for (int i = 0; i < files.length; i++) {
                    if(files[i].isFile()){//if backup has a file, adds to current target
                        System.out.println(files[i].getPath() + " added to " + object.getName());
                        pctoP(object, files[i]);
                    }
                }
            }
        }
        for (int i = 0; i < files.length; i++) {
            if(files[i].isFile()){
                pctoP(object, files[i]);
            }
        }
    }

    //checks if config file exists on pc
    //if not a config file is created
    public void initializeDesk() throws FileNotFoundException {
        File file = new File(mainPath + "\\PAC_config.cfg");
        if(file.isFile() == false){
            System.out.println("Config path doesnt exist");
            PrintWriter w = new PrintWriter("PAC_Config.cfg");
            w.println("adb_directory = \"\"");
            w.println("backup_directory = \"" + mainPath + "\\Backups\\\"");
            w.println("phone_ip = \"\"");
            w.close();
            file = new File("PAC_Config.cfg");
        }
    }

    //checks phone if folders exist on the phone
    public void initializePhone(int i) {
        PortableDeviceFolderObject pFO = null;
        PortableDeviceManager pDM = new PortableDeviceManager();
        try{
            pD = pDM.getDevices()[i];
            File file = new File("\\PACFILES");
            boolean condition = false;

            //gets ip for wifi transfer
            try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }


            //open phone
            pD.open();

            //checks root of phone for folder
            boolean pod = doesFolderExist("PODCASTS", pD);
            boolean ebooks = doesFolderExist("EBOOKS", pD);
            boolean pacfiles = doesFolderExist("PACFILES", pD);
            boolean audiobooks = doesFolderExist("AUDIOBOOKS", pD);
            boolean music = doesFolderExist("MUSIC", pD);
            boolean videos = doesFolderExist("VIDEOS", pD);
            if (!pod) {
                createFolder("podcasts", pD);
            }
            if (!ebooks) {
                createFolder("ebooks", pD);
            }
            if (!pacfiles) {
                createFolder("pacfiles", pD);
            }
            if (!audiobooks) {
                createFolder("audiobooks", pD);
            }
            if (!music) {
                createFolder("music", pD);
            }
            if (!videos) {
                createFolder("videos", pD);
            }
            pD.close();
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("No Phone Connected");
        }

    }

    //sets path to adb.exe in config file
    public boolean setAdbPath(String path) throws IOException {
        String x = "adb_directory = \"";
        int i = x.length();
        File newPath = new File(path);
        File config = new File(this.mainPath + "\\PAC_Config.cfg");
        if(!newPath.isDirectory()){
            return false;
        }else{
            File newAdb = new File(path + "\\adb.exe");
            if(!newAdb.isFile()){
                return false;
            }else{
                String inString = fileToString(config);
                System.out.println(inString);
                System.out.println(newPath.getAbsolutePath());
                String newline = "adb_directory = \"" + path.replace("\\", "\\\\") + "\"";
                inString = inString.replaceAll("adb_directory = \".*\"", newline);
                System.out.println("\nNEWPATHS\n" + inString);
                FileOutputStream fos = new FileOutputStream(config);
                fos.write(inString.getBytes());
                fos.close();
            }
        }
        this.adbPath = path;
        return true;
    }

    //returns the path to adb file
    public String getAdbPath() throws IOException {
        if(this.adbPath != null){
            //System.out.println("Path exists: " + this.adbPath);
            return this.adbPath;
        }else{
            File file = new File(this.mainPath + "\\PAC_Config.cfg");
            String conString = fileToString(file);
            String[] a = conString.split("adb_directory = \"");
            String[] b = a[1].split("\"");
            String path = b[0];
            //System.out.println("get adb path test: " + path);
            this.adbPath = path;
        }
        return this.adbPath;
    }

    //sets path to backup folder in config file
    public boolean setBackupPath(String path) throws IOException {
        String x = "backup_directory = \"";
        int i = x.length();
        File newPath = new File(path);
        File config = new File(this.mainPath + "\\PAC_Config.cfg");
        if(!newPath.isDirectory()){
            return false;
        }else{
            String inString = fileToString(config);
            String newline = "backup_directory = \"" + path.replace("\\", "\\\\") + "\"";
            inString = inString.replaceAll("backup_directory = \".*\"", newline);
            FileOutputStream fos = new FileOutputStream(config);
            fos.write(inString.getBytes());
            fos.close();
        }
        return true;
    }

    //retruns backup folder path from config file
    public String getBackupPath() throws IOException{
        if (this.backupPath != null) {
            return this.backupPath;
        }else{
            File file = new File(this.mainPath + "\\PAC_Config.cfg");
            String conString = fileToString(file);
            String[] a = conString.split("backup_directory = \"");
            //System.out.println("test " + a[0] + "\ntest " + a[1]);
            String[] b = a[1].split("\"");
            String path = b[0];
            //System.out.println("get backup path test: " + path);
            this.backupPath = path;
        }
        return this.backupPath;
    }

    //sets main path of program
    public boolean setMainPath(String path){
        File file = new File(path);
        if (file.exists() && file.isDirectory()){
            this.mainPath = path;
            return true;
        }
        return false;
    }

    //returns main path of program
    public String getMainPath(){
        return this.mainPath;
    }

    //searches phone using adb to get ip address
    public void getPhoneIp() throws IOException {
        String addr = mainPath + "\\addrs.txt";
        String longIp = "";
        String ip = "";

        //calls command prompt and runs adb.exe
        //checks if phone is connected and copppies ip output to file named addrs.txt
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"" + this.adbPath + "\" " +
                "&& adb devices " +
                "&& adb shell ip route > " + addr + " ");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while(true){
            line = r.readLine();
            if(line == null){ break; }
            //System.out.println(line);
        }
        File file = new File(addr);
        if(!file.isFile()){
            //System.out.println(file.getAbsolutePath());
            file.createNewFile();
            //System.out.println(file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file, false);
        }

        BufferedReader r2 = new BufferedReader(new FileReader(file));
        try{
            StringBuilder sb = new StringBuilder();
            line = r2.readLine();
            //System.out.println(line);
            ip = line.split("/")[0];
            longIp = sb.toString();
        }finally {
            r2.close();
        }
        //System.out.println("IP: " + ip);

        this.ip = ip;
    }

    //returns string of phone ip address
    public String getIp(){
        if (this.ip != null){
            return this.ip;
        }
        return "No ip Specified";
    }

    //returns phone model
    public String getPhoneModel() {
        String out = "";
        try {
            out = pD.getModel();
        }catch(NullPointerException e){
            System.out.println("Cannot get phone model: No Phone Connected");
        }
        return out;
    }

    //returns battery percentage
    public int getPhoneBattery() {
        int out = -1;
        try {
            out = pD.getPowerLevel();
        }catch(NullPointerException e){
            System.out.println("Cannot get battery percentage: No Phone Connected!");
        }
        return out;
    }

    //return name of phone
    public String getPhoneName() {
        String out = "";
        try {
            out = pD.getFriendlyName();
        }catch(NullPointerException e){
            System.out.println("Cannot get phone name: No Phone Connected!");
        }
        return out;
    }

    public ArrayList<FileA> syncQueuery(){
        /*
        Content Type
        1 - Audiobook
        2 - EBook
        3 -
        4 -
        5 -
        6 -
        7 - Podcast
        */

        System.out.println();
        Connection c = null;
        Statement stmt = null;
        ArrayList<FileA> locations = new ArrayList<FileA>();
        String location;
        String contentName;
        String type;
        /*
        try {
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\PACDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened Database");
            stmt = c.createStatement();
            //SELECT c.location, c.ContentName, ct.ContentType FROM Content as c, ContentType as ct WHERE c.ContentTypeID = ct.ContentTypeID, and c.SyncStatusID = FALSE;
            ResultSet rs = stmt.executeQuery("SELECT c.location, c.ContentName, ct.ContentType FROM Content as c, ContentType as ct WHERE c.ContentTypeID = ct.ContentTypeID and c.WantToSync = 1;");
            while(rs.next()){
                if(rs.getString("Location") == null){
                    System.out.println("location is null");
                }else if(rs.getString("Location") != null){
                    System.out.println(rs.getString("Location"));
                    System.out.println(rs.getString("ContentName"));
                    System.out.println(rs.getString("ContentType"));
                    String locationString = rs.getString("Location");
                    String nameString = rs.getString("ContentName");
                    String id = rs.getString("ContentType");
                    FileA fileaccess = new FileA(locationString, nameString, id);
                    locations.add(fileaccess);
                }
            }
            rs.close();
            stmt.close();
            c.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        */
        ContentDAO cd = new ContentDAO();
        Set coll = cd.getAllContentBySyncStatus();
        Iterator collection = coll.iterator();
        while(collection.hasNext()){
            Content con = (Content) collection.next();
            String locationString = con.getLocation();
            String nameString = con.getContentName();
            String typeString = con.getContentTypeName();
            locations.add(new FileA(locationString, nameString, typeString));
        }
        return locations;
    }

    public void sync(){
        //calls syncQueuery method to make a list of items that need to be synced to the phone
        try {
            ArrayList<FileA> queue = syncQueuery();
            addFiles(queue);
        }catch(NullPointerException e){
            System.out.println("Cannot Sync");
        }
    }

    public void addFiles(ArrayList<FileA> files){
        for (int i = 0; i < files.size(); i++) {
            File file = new File(files.get(i).getLocation());
            System.out.println(files.get(i).getType());
            switch(files.get(i).getType().toUpperCase()){
                case "PODCAST":
                    addPodcast(file);
                    break;
                case "EBOOK":
                    addEBook(file);
                    break;
                case "MUSIC":
                    addMusic(file);
                    break;
                case "VIDEO":
                    addVideos(file);
                    break;
                case "AUDIOBOOK":
                    addAudioBook(file);
                    break;
            }
        }

    }

    //add single Podcast
    public void addPodcast(File file) {
        if (doesFolderExist("podcasts", pD))
        {
            pctoP(setTargetFolder("podcasts", pD), file);
        }else{
            createFolder("Podcasts", pD);
            pctoP(setTargetFolder("podcasts", pD), file);
        }

    }

    //add multiple podcasts
    public void addPodcast(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (doesFolderExist("podcasts", pD))
            {
                pctoP(setTargetFolder("podcasts", pD), file);
            }else{
                createFolder("podcasts", pD);
                pctoP(setTargetFolder("ebooks", pD), file);
            }
        }
    }

    //adds single ebooks
    public void addEBook(File file) {
        if (doesFolderExist("eBooks", pD))
        {
            pctoP(setTargetFolder("ebooks", pD), file);
        }else{
            createFolder("eBooks", pD);
            pctoP(setTargetFolder("ebooks", pD), file);
        }
    }

    //adds multiple ebooks
    public void addEbook(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (doesFolderExist("eBooks", pD))
            {
                pctoP(setTargetFolder("ebooks", pD), file);
            }else{
                createFolder("eBooks", pD);
                pctoP(setTargetFolder("ebooks", pD), file);
            }
        }
    }

    //add single audiobooks
    public void addAudioBook(File file){
        if (doesFolderExist("AudioBooks", pD))
        {
            pctoP(setTargetFolder("AudioBooks", pD), file);
        }else{
            createFolder("AudioBooks", pD);
            pctoP(setTargetFolder("AudioBooks", pD), file);
        }
    }

    //add multiple audiobooks
    public void addAudioBook(ArrayList<File> files){
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (doesFolderExist("AudioBooks", pD))
            {
                pctoP(setTargetFolder("AudioBooks", pD), file);
            }else{
                createFolder("AudioBooks", pD);
                pctoP(setTargetFolder("AudioBooks", pD), file);
            }
        }
    }

    //add a single song
    public void addMusic(File file) {
        if (doesFolderExist("music", pD))
        {
            pctoP(setTargetFolder("music", pD), file);
        }else{
            createFolder("music", pD);
            pctoP(setTargetFolder("music", pD), file);
        }
    }

    //add multiple songs
    public void addMusic(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (doesFolderExist("music", pD))
            {
                pctoP(setTargetFolder("music", pD), file);
            }else{
                createFolder("music", pD);
                pctoP(setTargetFolder("music", pD), file);
            }
        }
    }

    //add a single video
    public void addVideos(File file) {
        if (doesFolderExist("videos", pD))
        {
            pctoP(setTargetFolder("videos", pD), file);
        }else{
            createFolder("videos", pD);
            pctoP(setTargetFolder("videos", pD), file);
        }
    }

    //add multiple videos
    public void addVideos(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (doesFolderExist("videos", pD))
            {
                pctoP(setTargetFolder("videos", pD), file);
            }else{
                createFolder("videos", pD);
                pctoP(setTargetFolder("videos", pD), file);
            }
        }
    }

    //returns folder
    public void getFolder(String folder, File file) {
        PortableDeviceObject[] folderFiles = setTargetFolder(folder, pD).getChildObjects();
        for (int i = 0; i < folderFiles.length; i++) {
            System.out.println(folderFiles[i].getOriginalFileName());
            System.out.println(file.getPath());
            ptoPC(folderFiles[i], file.getPath());
        }
    }

    //transfers files from phone to pc
    public void ptoPC(PortableDeviceObject pDO, String file) {
        PortableDeviceToHostImpl32 copy = new PortableDeviceToHostImpl32();
        try
        {
            copy.copyFromPortableDeviceToHost(pDO.getID(), file, pD);
        }catch (COMException e)
        {
            System.out.println(e);
        }

    }

    //transfers files from the pc to phone
    public void pctoP(PortableDeviceFolderObject targetFolder, File file) {
        if (doesFileExist(targetFolder, file) == false) {
            System.out.println(file.getName() + " not added: already exists");
        } else {
            BigInteger bigint = new BigInteger("123456789");
            try {
                targetFolder.addAudioObject(file, "", "", bigint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Checks if a phone ic currently connected
    public boolean checkConnection(){
        try{
            if(pD.getRootObjects() != null){
                return true;
            }
        }catch(NullPointerException e){
            System.out.println("No Phone Connected");
        }
        return false;
    }

    //makes a copy of the phones storage and puts it in backup folder on pc
    public void backup() throws IOException {
        //add backup folder check here
        String path = getBackupPath();
        //make sure phone model is here
        String pModel = getPhoneModel();
        //pModel = pModel.replaceAll("");
        String time = new SimpleDateFormat("yyyy-MM-dd_HHmm").format(Calendar.getInstance().getTime());
        class BackupThread implements Runnable{
            //String path;
            BackupThread(String string) {

                //path = string;
            }
            public void run() {
                System.out.println("Thread starting");
                PortableDeviceFolderObject target = null;
                File file = new File(path + "\\" + time);
                //File file = new File(path + "\\" + pModel + "\\" + time);
                //String test = file.toString();
                if(!file.isDirectory()){
                    file.mkdirs();
                    System.out.println(file.toString());
                }
                try {
                    for (PortableDeviceObject obj1 : pD.getRootObjects()) {
                        System.out.println(obj1.getName() + "\n--------------------");
                        if (obj1 instanceof PortableDeviceStorageObject) {
                            File tempFile = new File(file.getPath() + "\\" + obj1.getName());
                            if (!tempFile.isDirectory()) {
                                tempFile.mkdir();
                            }
                            PortableDeviceStorageObject storage = (PortableDeviceStorageObject) obj1;
                            for (PortableDeviceObject obj2 : storage.getChildObjects()) {
                                System.out.println("    " + obj2.getName());
                                if (obj2 instanceof PortableDeviceFolderObject) {
                                    File tempFile2 = new File(tempFile.getPath() + "\\" + obj2.getName());
                                    if (!tempFile2.isDirectory()) {
                                        tempFile2.mkdir();
                                    }
                                    recurBackup((PortableDeviceFolderObject) obj2, "    ", tempFile2);
                                }
                                ptoPC(obj2, tempFile.getPath());
                            }
                        }
                        System.out.println("");
                    }
                }catch(NullPointerException e){
                    System.out.println("No Phone Connected");
                }
            }
        }
        Thread t = new Thread(new BackupThread(path));
        t.start();

    }

    //restores backup to phone
    public void restore()throws IOException{
        File bfolder = new File(this.getBackupPath());// "*\Backups"
        class RestoreThread implements Runnable{
            RestoreThread(File file){

            }
            public void run(){
                PortableDeviceFolderObject target = null;
                File[] backups = bfolder.listFiles();//list files and folders in "*\Backups"
                File[] phone = null;
                if(backups == null){
                    System.out.println("No Backups Found!");
                }else{
                    Arrays.sort(backups);
                    phone = backups[0].listFiles();
                    if(phone == null){
                        System.out.println("No BAckups Found!");
                    }else{
                        Arrays.sort(phone);
                        File inPhone = new File(phone[0].getAbsolutePath());// "*\Backups\Phone"
                        for (PortableDeviceObject obj : pD.getRootObjects()){//  Device:\
                            if(obj instanceof PortableDeviceStorageObject && obj.getName().equalsIgnoreCase("phone")){
                                PortableDeviceStorageObject storage = (PortableDeviceStorageObject) obj;// Device:\Phone
                                for(PortableDeviceObject obj2 : storage.getChildObjects()){
                                    if(obj2 instanceof PortableDeviceFolderObject){
                                        File[] a = inPhone.listFiles();//lists files and folders in "*\Backups\Phone"
                                        Arrays.sort(a);
                                        target = (PortableDeviceFolderObject) obj2;
                                        System.out.println("==================== COMPARE WITH " + obj2.getName());
                                        for (int i = 0; i < a.length; i++) {
                                            System.out.println(a[i].getPath());
                                            if(obj2.getName().equalsIgnoreCase(a[i].getName()) && a[i].isDirectory()){
                                                recurRestore(target, a[i]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Thread t = new Thread(new RestoreThread(bfolder));
        t.start();
    }

    /*
    public void wifiSetup() throws IOException {
        //not done
    }
    */
}

class FileA{
    String location, filename, type;
    FileA(String location, String type){
        this.location = location;
        this.filename = "Unknown";
        this.type = type;
    }
    FileA(String location, String filename, String type){
        this.location = location;
        this.filename = filename;
        this.type = type;
    }
    public String getLocation(){
        return this.location;
    }
    public String getFilename(){
        return this.filename;
    }
    public String getType(){
        return this.type;
    }
    public void setLocation(String location){
        File file = new File(location);
        if(file.isFile()){
            this.location = location;
        }
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public void setType(String type){
        this.type = type;
    }
}
