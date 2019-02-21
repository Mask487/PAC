import be.derycke.pieter.com.COMException;
import jmtp.*;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


class Transfer implements TransferObject {
    private PortableDevice pD = null;

    public void initialize(int i) {
        PortableDeviceFolderObject pFO = null;
        PortableDeviceManager pDM = new PortableDeviceManager();
        pD = pDM.getDevices()[i];
        File file = new File("\\PACFILES");
        boolean condition = false;


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

    //add
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

    public void backup(String path){
        PortableDeviceFolderObject target = null;
        File file = new File("D:\\Desktop\\" + path);
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
