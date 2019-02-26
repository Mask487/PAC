
import java.io.File;
import java.util.List;
import java.net.URL;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class RSSReader {

    static void DownloadPodcast(String url) {
        try {
            URL feedUrl = new URL(url);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            SyndEntry entry = null;

            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                entry = (SyndEntry) i.next();

                DownloadPodcast(entry);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    static void DownloadPodcast(SyndEntry e) {
        try {
            List<SyndEnclosure> tempList = e.getEnclosures();
            URL tempURL = new URL(tempList.get(0).getUrl());
            
            String name = e.getTitle();
            if (name.contains("!")) {
                name = name.replace("!", "");
            }
            if (name.contains("/")) {
                name = name.replace("/", "");
            }
            if (name.contains("\\")) {
                name = name.replace("\\", "");
            }
            if (name.contains("?")) {
                name = name.replace("?", "");
            }
            if (name.contains("%")) {
                name = name.replace("%", "");
            }
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            if (name.contains(":")) {
                name = name.replace(":", "");
            }
            if (name.contains("|")) {
                name = name.replace("|", "");
            }
            if (name.contains("\"")) {
                name = name.replace("\"", "");
            }
            if (name.contains("<")) {
                name = name.replace(">", "");
            }
            if (name.contains(">")) {
                name = name.replace(">", "");
            }
            if (name.contains(".")) {
                name = name.replace(".", "");
            }
            if (name.contains(" ")) {
                name = name.replace(" ", "_");
            }
            File file = new File("C:/Users/cothe/Desktop/" + name + ".mp3");
            
            if (!file.exists()) {
                
                System.out.println(e.getTitle());
                
                ReadableByteChannel readableByteChannel = Channels.newChannel(tempURL.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel fileChannel = fileOutputStream.getChannel();

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } 
            else {
                System.out.println("FILE ALREADY EXISTS. CHECKING REMAINING FILES.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
