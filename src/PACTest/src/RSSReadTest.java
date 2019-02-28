import java.util.Scanner;


public class RSSReadTest {
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a RSS Feed URL: ");
        String n = reader.next();
        
        RSSReader.DownloadPodcast(n);
    }
}