import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceObject;

import java.io.File;

public interface TransferObject
{
    void initialize(int i);
    void pctoP(PortableDeviceFolderObject targetFolder, File file);
    void ptoPC(PortableDeviceObject pDO, PortableDevice pD, String file);
    boolean doesFolderExist(String folderName, PortableDevice pD);
    void addPodcast(File file);
    void addEBook(File file);
    String getPhoneModel();
    int getPhoneBattery();
    String getPhoneName();




}
