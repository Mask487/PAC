package NewDatabase;
import org.apache.commons.io.FilenameUtils;
import Util.DBPrint;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author Jacob Oleson
 * 
 * @update 4/08/19
 * 
 * Simple test that calls the DB interface to add info to the DB file.
 */
public class SQLiteTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        
        SQLTranslator test = new SQLTranslator();
        ResultSet res;
        String foo = "ThisIsTest";
        String contentType = "Podcast";
        String creatorName = "Jacob";
        String genreName = "Test";
        String publisherName = "Test";
        String seriesName = "test"; 
        String contentName = "Test on new file system";
        String contentDescription = foo;
        //yyyy-mm-dd
        String uploadDate = "2019-04-07";
        int pageCount = 0; 
        //hh:mm:ss
        String duration = "01:33:12";
        String isbn = null;
        boolean explicit = false; 
        String location = "C:/Test/ultimatetest.mp3";
        String url = null;
        boolean wantToSync = false;
        String filePath = "";
        //test.addContent(contentType, creatorName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location, url, wantToSync, filePath);
        //test.addContent("C:/Test/test4.epub", contentType);
        
        File file1 = new File("ContentFiles").getAbsoluteFile();
        System.out.println(file1.toString());
        ContentDAO cdao = new ContentDAO();
        PlaylistDAO pdao = new PlaylistDAO();
        //cdao.insertContent("C:/Test/FreePodcast.mp3", "Podcast");
        Set contents0 = cdao.getAllContent();
        Set playlists = pdao.getAllPlaylists();
        Iterator pDAOIterator = playlists.iterator();
        Content contentTest = cdao.getContent(3);
        
        Playlist playlist1 =  (Playlist) pDAOIterator.next();
        pdao.insertContentIntoPlaylist(contentTest, playlist1);
        pdao.getContentFromPlaylist(playlist1);
        System.out.println(playlist1.toString());
        
        Set contents = cdao.getAllContent();
        Iterator iterTest = contents.iterator();
        Content contentTest2 = cdao.getContent(2);
        Set contents2 = cdao.searchAllTablesBySearchTermAndType("ant", "EBook");
        Iterator iter2 = contents2.iterator();
        
        //System.out.println(iter2.next().toString());
        Content test3 = (Content) iter2.next();
        System.out.println(test3.getContentName());
        
        Set content5 = cdao.getAllContent();
        Iterator iter5 = content5.iterator();
        while(iter5.hasNext()) {
            System.out.println(iter5.next().toString());
        }
        
        Set contentsResult = cdao.searchAllTablesBySearchTerm("t");
        Iterator iterResult = contentsResult.iterator();
        
        System.out.println("The results are: ");
        while(iterResult.hasNext()) {
            System.out.println(iterResult.next().toString());
        }
        
        test.addContent("C:/Test/Test.epub", contentType);
    }
    
    
}
