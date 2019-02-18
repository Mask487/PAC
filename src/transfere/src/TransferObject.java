import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface TransferObject
{
    void initialize(int i);
    void pctoP(PortableDeviceFolderObject targetFolder, File file);
    void ptoPC(PortableDeviceObject pDO, PortableDevice pD, String file);
    boolean doesFolderExist(String folderName, PortableDevice pD);
    void addPodcast(File file);
    void addPodcast(ArrayList<File> files);
    void addEBook(File file);
    void addEbook(ArrayList<File> files);
    String getPhoneModel();
    int getPhoneBattery();
    String getPhoneName();




}
