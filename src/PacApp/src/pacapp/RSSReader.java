package pacapp;

import java.io.File;
import java.util.List;
import java.net.URL;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import NewDatabase.ContentDAO;

public class RSSReader {
    
    //USE THIS METHOD TO SEARCH FOR AND DOWNLOAD PODCAST
    static void DownloadPodcast(String url) {
        try {
            URL feedUrl = new URL(url);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            SyndEntry entry = null;
            
            //loops through feed for podcasts
            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                entry = (SyndEntry) i.next();

                DownloadPodcast(entry);

            }
        } catch (FeedException | IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    static void DownloadPodcast(SyndEntry e) {
        try {
            List<SyndEnclosure> tempList = e.getEnclosures();
            URL tempURL = new URL(tempList.get(0).getUrl());
            
            //removes invalid character from title
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
            
            //will be changed in the future to permanent location
            //uses above formatted title to create file path
            File file = new File("./ContentFiles/pacappPodcast/Podcast/UNKNOWN/" + name + ".mp3");

            
            //checks if file already exists
            if (!file.exists()) {
                
                System.out.println(e.getTitle());
                
                ReadableByteChannel readableByteChannel = Channels
                        .newChannel(tempURL.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 
                        0, Long.MAX_VALUE);
                ContentDAO cdao = new ContentDAO();
                cdao.insertContent(file.getAbsolutePath(), "pacapp.Podcast");
            } 
            else {
                System.out.println("FILE ALREADY EXISTS. "
                        + "CHECKING REMAINING FILES.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
