package pacapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TransferTest
{
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Desktop\\stuff\\");
        File file1 = new File("D:\\Desktop\\stuff1");
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
        pacapp.Transfer t = new pacapp.Transfer();
        t.initializeDesk();
        t.initializePhone(0);
        if(t.checkConnection() == true){
            System.out.println("connected!");
        }
        //System.out.println("Phone Name: " + t.getPhoneName() + "\nPhone Model: " + t.getPhoneModel() + "\nBattery Level: " + t.getPhoneBattery());
        //t.addEbook(files);
        //t.getFolder("podcasts", file1);
        t.backup();
        //t.restore();
        //t.setAdbPath("C:\\Users\\quinc\\AppData\\Local\\Android\\Sdk\\platform-tools");
        //t.getAdbPath();
        //t.setBackupPath("D:\\Desktop\\BACKUP");
        //System.out.println("IP: "+ t.getIp());
        //t.getPhoneIp();
        //t.getBackupPath();
        //t.syncQueuery();
        //t.setRoot("Phone");
        ;
    }
}
