<<<<<<< HEAD:src/PacApp/src/transfere/src/TransferTest.java
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class TransferTest
//{
//    public static void main(String[] args) throws IOException {
//        File file = new File("D:\\Desktop\\stuff\\");
//        File file1 = new File("D:\\Desktop\\stuff1");
//        ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
//        Transfer t = new Transfer();
//        t.initializeDesk();
//        //t.initializePhone(0);
//        //System.out.println("Phone Name: " + t.getPhoneName() + "\nPhone Model: " + t.getPhoneModel() + "\nBattery Level: " + t.getPhoneBattery());
//        //t.addEbook(files);
//        //t.getFolder("podcasts", file1);
//        //t.backup("Backup");
//        t.setAdbPath("C:\\Users\\quinc\\AppData\\Local\\Android\\Sdk\\platform-tools");
//        t.getAdbPath();
//        t.setBackupPath("D:\\Desktop\\BACKUP");
//        //System.out.println("IP: "+ t.getIp());
//        //t.getPhoneIp();
//
//
//    }
//}
=======
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferTest
{
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Desktop\\stuff\\");
        File file1 = new File("D:\\Desktop\\stuff1");
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
        Transfer t = new Transfer();
        t.initializeDesk();
        //t.initializePhone(0);
        //System.out.println("Phone Name: " + t.getPhoneName() + "\nPhone Model: " + t.getPhoneModel() + "\nBattery Level: " + t.getPhoneBattery());
        //t.addEbook(files);
        //t.getFolder("podcasts", file1);
        //t.backup("Backup");
        //t.setAdbPath("C:\\Users\\quinc\\AppData\\Local\\Android\\Sdk\\platform-tools");
        //t.getAdbPath();
        //t.setBackupPath("D:\\Desktop\\BACKUP");
        //System.out.println("IP: "+ t.getIp());
        //t.getPhoneIp();
        //t.syncQueuery();
        if(t.isPhoneConnected() == true){
            System.out.println("That shit is true babeh!!");
        }else{
            System.out.println("No phone");
        }


    }
}
>>>>>>> 127d738acbdc1c77bdbf921d975ec5e04ed8f02a:src/PacApp/src/pacapp/TransferTest.java
