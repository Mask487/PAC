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
        //System.out.println(t.getIp());

    }
}
