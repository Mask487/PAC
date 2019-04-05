import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//check backup method just in case
//finish clone
//query
//autodetect usb
//syncQueuery thread conflicts wiht backup

public interface TransferObject
{
    void initializePhone(int i);
    void initializeDesk() throws FileNotFoundException;
    void pctoP(PortableDeviceFolderObject targetFolder, File file);
    void ptoPC(PortableDeviceObject pDO, String file);
    void getFolder(String folder, File file);
    void backup();
    void addFiles(File file, char choice);
    void addFiles(ArrayList<File> files, char choice);
    void addPodcast(File file);
    void addPodcast(ArrayList<File> files);
    void addEBook(File file);
    void addEbook(ArrayList<File> files);
    void addMusic(File file);
    void addMusic(ArrayList<File> files);
    void addVideos(File file);
    void addVideos(ArrayList<File> files);
    int getPhoneBattery();
    void getPhoneIp() throws IOException;
    boolean setMainPath(String path);
    boolean setAdbPath(String path) throws IOException;
    boolean doesFolderExist(String folderName, PortableDevice pD);
    void checkConnection(boolean b);
    String getPhoneModel();
    String getPhoneName();
    String getIp();
    String getAdbPath() throws IOException;
    ArrayList<String> syncQueuery();




}