package pacapp;

import be.derycke.pieter.com.COMException;
import jmtp.*;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.net.DatagramSocket;
import java.sql.*;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Transfer extends Thread implements pacapp.TransferObject {
    private PortableDevice pD = null;
    private PortableDeviceManager pDM;
    private String ip;
    private String adbPath = null;
    private String backupPath = null;
    private String mainPath = System.getProperty("user.dir");
    private boolean pIFlag = false;


    public void initializeDesk() throws FileNotFoundException {
        File file = new File(mainPath + "\\PAC_config.cfg");
        if(file.isFile() == false){
            System.out.println("Config path doesnt exist");
            PrintWriter w = new PrintWriter("PAC_Config.cfg");
            w.println("adb_directory = \"\"");
            w.println("backup_directory = \"\"");
            w.println("phone_ip = \"\"");
            w.close();
            file = new File("PAC_Config.cfg");
        }

        //System.out.println(file.getAbsolutePath());

    }

    public ArrayList<String> syncQueuery(){
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
        Connection c = null;
        Statement stmt = null;
        ArrayList<String> locations = null;
        String location;
        String contentName;
        String type;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\PACDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened Database");
            stmt = c.createStatement();
            //SELECT c.location, c.ContentName, ct.ContentType FROM Content as c, ContentType as ct WHERE c.ContentTypeID = ct.ContentTypeID, and c.SyncStatusID = FALSE;
            ResultSet rs = stmt.executeQuery("SELECT c.location, c.ContentName, ct.ContentType FROM Content as c, ContentType as ct WHERE c.ContentTypeID = ct.ContentTypeID;");

            while(rs.next()){
                String test;
                test = rs.getString("Location");
                if(rs.getString("Location") == null){
                    System.out.println("location is null");
                }else if(rs.getString("Location") != null){
                    locations.add(test);
                }

            }
            for(int i = 0; i < locations.size(); i++){
                System.out.println(locations.get(i));
            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


        return locations;
    }


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
            if (!pod) {
                createFolder("podcasts", pD);
            }
            if (!ebooks) {
                createFolder("ebooks", pD);
            }
            if (!pacfiles) {
                createFolder("pacfiles", pD);
            }
            pD.close();
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("No Pnone Connected");
        }

    }



    public boolean setMainPath(String path){
        File file = new File(path);
        if (file.exists() && file.isDirectory()){
            this.mainPath = path;
            return true;
        }
        return false;
    }

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

    //change path files to something more universal
    public void getPhoneIp() throws IOException {
        String addr = mainPath + "\\addrs.txt";
        String longIp = "";
        String ip = "";

        //adbPath
        //System.out.println("cd \"" + this.adbPath + "\" ");
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


    public String getIp(){
        if (this.ip != null){
            return this.ip;
        }
        return "No ip Specified";
    }

    public void wifiSetup() throws IOException {
        //not done
    }


    //returns phone model
    public String getPhoneModel() {
        String out = "";
        return out = pD.getModel();
    }



    //returns battery percentage
    public int getPhoneBattery() {
        int out;
        return out = pD.getPowerLevel();
    }

    //return name of phone
    public String getPhoneName() {
        String out;
        return out = pD.getFriendlyName();
    }

    public void addFiles(File file, char choice){
        switch(choice){
            case 'p':
                addPodcast(file);
                break;
            case 'e':
                addEBook(file);
                break;
            case 'm':
                addMusic(file);
                break;
            case 'v':
                addVideos(file);
                break;
        }
    }

    public void sync(){

    }

    public void addFiles(ArrayList<File> files, char choice){
        switch(choice){
            case 'p':
                addPodcast(files);
                break;
            case 'e':
                addEbook(files);
                break;
            case 'm':
                addMusic(files);
                break;
            case 'v':
                addVideos(files);
                break;
        }
    }

    //add single Podcast
    public void addPodcast(File file)
    {
        if (doesFolderExist("podcasts", pD))
        {
            pctoP(setTargetFolder("podcasts", pD), file);
        }else{
            createFolder("Podcasts", pD);
            pctoP(setTargetFolder("podcasts", pD), file);
        }

    }

    //add multiple podcasts
    public void addPodcast(ArrayList<File> files)
    {
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

    public void addEBook(File file)
    {
        if (doesFolderExist("eBooks", pD))
        {
            pctoP(setTargetFolder("ebooks", pD), file);
        }else{
            createFolder("eBooks", pD);
            pctoP(setTargetFolder("ebooks", pD), file);
        }
    }

    public void addEbook(ArrayList<File> files)
    {
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

    public void addMusic(File file)
    {
        if (doesFolderExist("music", pD))
        {
            pctoP(setTargetFolder("music", pD), file);
        }else{
            createFolder("music", pD);
            pctoP(setTargetFolder("music", pD), file);
        }
    }

    public void addMusic(ArrayList<File> files)
    {
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

    public void addVideos(File file)
    {
        if (doesFolderExist("videos", pD))
        {
            pctoP(setTargetFolder("videos", pD), file);
        }else{
            createFolder("videos", pD);
            pctoP(setTargetFolder("videos", pD), file);
        }
    }

    public void addVideos(ArrayList<File> files)
    {
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

    public void getFolder(String folder, File file)
    {
        PortableDeviceObject[] folderFiles = setTargetFolder(folder, pD).getChildObjects();
        for (int i = 0; i < folderFiles.length; i++) {
            System.out.println(folderFiles[i].getOriginalFileName());
            System.out.println(file.getPath());
            ptoPC(folderFiles[i], file.getPath());
        }
    }

    public void ptoPC(PortableDeviceObject pDO, String file)
    {
        PortableDeviceToHostImpl32 copy = new PortableDeviceToHostImpl32();
        try
        {
            copy.copyFromPortableDeviceToHost(pDO.getID(), file, pD);
        }catch (COMException e)
        {
            System.out.println(e);
        }

    }

    public void pctoP(PortableDeviceFolderObject targetFolder, File file)
    {
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

    public void checkConnection(boolean b){
        class PhoneConnection extends Thread{
            private volatile boolean flag = true;
            private volatile boolean pIFlag = false;
            //private volatile boolean con = false;
            public void stopRunning(){
                flag = false;
            }
            public void run(){
                while(flag){
                    System.out.println("Running...");
                    try{

                        if(pIFlag == false){
                            initializePhone(0);
                            pDM.getDevices()[0].getRootObjects();
                            System.out.println("Phone Initialized");
                            pIFlag = true;
                            //con = true;
                        }else if(pIFlag == true){
                            System.out.println("Phone Connected");
                            pDM.getDevices()[0].getRootObjects();
                        }
                    }catch (NullPointerException e){
                        System.out.println("Not Connected!");
                        pIFlag = false;
                        //con = false;
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Not Connected");
                        pIFlag = false;
                        //con = false;
                    }
                }
                System.out.println("Stopped...");
            }
        }
        PhoneConnection pc = new PhoneConnection();
        pc.start();
        if(b == false){
            pc.stopRunning();
        }
    }

    public void backup(){
        //add backup folder check here
        String path = this.backupPath;
        //make sure phone model is here
        String pModel = getPhoneModel();
        String time = new SimpleDateFormat("yyyy-MM-dd_HHmm").format(Calendar.getInstance().getTime());

        class BackupThread implements Runnable{
            //String path;
            BackupThread(String string) {

                //path = string;
            }
            public void run() {
                System.out.println("Thread starting");
                PortableDeviceFolderObject target = null;
                File file = new File(path + "\\" + pModel + "\\" + time);
                if(!file.isDirectory()){
                    file.mkdir();
                }
                for (PortableDeviceObject obj1 : pD.getRootObjects())
                {
                    System.out.println(obj1.getName() + "\n--------------------");
                    if (obj1 instanceof PortableDeviceStorageObject)
                    {
                        File tempFile = new File(file.getPath() + "\\" + obj1.getName());
                        if (!tempFile.isDirectory()){
                            tempFile.mkdir();
                        }
                        PortableDeviceStorageObject storage = (PortableDeviceStorageObject) obj1;
                        for (PortableDeviceObject obj2 : storage.getChildObjects())
                        {
                            System.out.println("    " + obj2.getName());
                            if (obj2 instanceof PortableDeviceFolderObject)
                            {
                                File tempFile2 = new File(tempFile.getPath() + "\\" + obj2.getName());
                                if(!tempFile2.isDirectory()){
                                    tempFile2.mkdir();
                                }
                                recur((PortableDeviceFolderObject) obj2, "    ", tempFile2);
                            }
                            ptoPC(obj2, tempFile.getPath());
                        }
                    }
                    System.out.println("");
                }
            }
        }

        Thread t = new Thread(new BackupThread(path));
        t.start();

    }

    private void recur(PortableDeviceFolderObject object, String tab, File file)
    {
        tab = tab + "    ";

        for (PortableDeviceObject obj : object.getChildObjects())
        {
            System.out.println(tab + obj.getName());
            if (obj instanceof PortableDeviceFolderObject){
                File tempFile = new File(file.getPath() + "\\" + obj.getName());
                if(!tempFile.isDirectory()){
                    tempFile.mkdir();
                }
                recur((PortableDeviceFolderObject) obj, tab, tempFile);
            }
            ptoPC(obj, file.getPath());
        }
    }

    //when adding files, checks to see if file doesn't already exist
    private boolean doesFileExist(PortableDeviceFolderObject targetFolder, File file)
    {
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
    public boolean doesFolderExist(String folderName, PortableDevice pD)
    {
        //boolean condition = false;
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

    private void createFolder(String folderName, PortableDevice pD)
    {
        //PortableDeviceFolderObject target = ;
        for (PortableDeviceObject obj1 : pD.getRootObjects())
        {
            if (obj1 instanceof PortableDeviceStorageObject)
            {
                PortableDeviceStorageObject store = (PortableDeviceStorageObject) obj1;
                store.createFolderObject(folderName);
            }
        }
    }

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
}
