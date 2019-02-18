import be.derycke.pieter.com.COMException;
import jmtp.*;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


class Transfer implements TransferObject
{
    private PortableDevice pD = null;

    public void initialize(int i)
    {
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
        if (!pod)
        {
            createFolder("podcasts", pD);
        }
        if (!ebooks)
        {
            createFolder("ebooks", pD);
        }
        if (!pacfiles)
        {
            createFolder("pacfiles", pD);
        }
        pD.close();
    }

    public String getPhoneModel()
    {
        String out = "";
        return out = pD.getModel();
    }

    public int getPhoneBattery()
    {
        int out;
        return out = pD.getPowerLevel();
    }

    public String getPhoneName()
    {
        String out;
        return out = pD.getFriendlyName();
    }

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

    public void addPodcast(File file)
    {
        if (doesFolderExist("podcasts", pD))
        {
            setTargetFolder("podcasts", pD);
        }else{
            createFolder("Podcasts", pD);
            setTargetFolder("podcasts", pD);
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

    public void ptoPC(PortableDeviceObject pDO, PortableDevice pD, String file)
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
        BigInteger bigint = new BigInteger("123456789");
        try
        {
            targetFolder.addAudioObject(file, "", "", bigint);
        } catch (Exception e)
        {
            System.out.println(e);
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
        for (PortableDeviceObject obj1 : pD.getRootObjects())
        {
            if (obj1 instanceof PortableDeviceStorageObject)
            {
                PortableDeviceStorageObject store = (PortableDeviceStorageObject) obj1;
                for (PortableDeviceObject obj2 : store.getChildObjects())
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
