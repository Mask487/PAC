import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
import java.util.List;
import java.net.URL;
import java.util.Scanner;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;


public class RSSReadTest {
    
    public static void main(String[] args) {
        boolean ok = false;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a RSS Feed URL: ");
        String n = reader.next();
        reader.close();
        if (n!=null) {
            try {
                URL feedUrl = new URL(n);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));
                SyndEntry entry = null;
                
                for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
		entry = (SyndEntry) i.next();
		//System.out.println(entry.getTitle());
 		}
                List<SyndEnclosure> tempList = entry.getEnclosures();
                File file = new File("C:/Users/cothe/Desktop/temp.mp3");
                URL tempURL = new URL(tempList.get(0).getUrl());
                ReadableByteChannel readableByteChannel = Channels.newChannel(tempURL.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel fileChannel = fileOutputStream.getChannel();
                
                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                
                

                ok = true;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: "+ex.getMessage());
            }
        }

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }

}