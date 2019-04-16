package pacapp;

import jmtp.PortableDevice;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//if
//autodetect usb
//syncQueuery thread conflicts with backup
//stop backup/restore button

public interface TransferObject
{
    void initializeDesk() throws FileNotFoundException;
    void initializePhone(int i);
    boolean setAdbPath(String path) throws IOException;
    String getAdbPath() throws IOException;
    boolean setBackupPath(String path) throws IOException;
    String getBackupPath() throws IOException;
    boolean setMainPath(String path);
    String getMainPath();
    void getPhoneIp() throws IOException;
    String getIp();
    String getPhoneModel();
    int getPhoneBattery();
    String getPhoneName();
    ArrayList<FileA> syncQueuery();
    void addFiles(ArrayList<FileA> files);
    void addPodcast(File file);
    void addPodcast(ArrayList<File> files);
    void addEBook(File file);
    void addEbook(ArrayList<File> files);
    void addMusic(File file);
    void addMusic(ArrayList<File> files);
    void addVideos(File file);
    void addVideos(ArrayList<File> files);
    void addAudioBook(File file);
    void addAudioBook(ArrayList<File> files);
    void getFolder(String folder, File file);
    void ptoPC(PortableDeviceObject pDO, String file);
    void pctoP(PortableDeviceFolderObject targetFolder, File file);
    boolean checkConnection();
    void setRunningB();
    void setRunningR();
    void backup() throws IOException;
    void restore() throws IOException;




}
