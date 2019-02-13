import java.io.File;

public class TransferTest
{
    public static void main(String[] args) {
        File file = new File("D:\\Desktop\\stuff");
        Transfer t = new Transfer();
        t.initialize(0);
        System.out.println("Phone Name: " + t.getPhoneName() + "\nPhone Model: " + t.getPhoneModel() + "\nBattery Level: " + t.getPhoneBattery());

    }
}
