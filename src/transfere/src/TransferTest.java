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
        t.initialize(0);
        System.out.println("Phone Name: " + t.getPhoneName() + "\nPhone Model: " + t.getPhoneModel() + "\nBattery Level: " + t.getPhoneBattery());
        //t.addEbook(files);
        //t.getFolder("podcasts", file1);
        //t.backup("Backup");
        t.setAdbPath("C:\\Users\\quinc\\AppData\\Local\\Android\\Sdk\\platform-tools\\adb.exe");
        t.checkConnection();
        System.out.println(t.getIp());

    }
}
