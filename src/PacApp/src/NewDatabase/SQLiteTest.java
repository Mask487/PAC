package NewDatabase;
import org.apache.commons.io.FilenameUtils;
import Util.DBPrint;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;
/**
 *
 * @author Jacob Oleson
 * 
 * @update 4/01/19
 * 
 * Simple test that calls the DB interface to add info to the DB file.
 */
public class SQLiteTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        
        SQLTranslator test = new SQLTranslator();
        ResultSet res;
        String foo = "ThisIsTest";
        String contentType = "EBook";
        String creatorName = "Jacob";
        String genreName = "Science Fiction";
        String publisherName = null;
        String seriesName = "Test";
        String contentName = "test3";
        String contentDescription = foo;
        //yyyy-mm-dd
        String uploadDate = "2019-04-05";
        int pageCount = 0; 
        //hh:mm:ss
        String duration = "01:33:12";
        String isbn = null;
        boolean explicit = false; 
        String location = null;
        String url = null;
        boolean wantToSync = false;
        test.addContent(contentType, creatorName, genreName, publisherName, contentName, contentDescription, uploadDate, uploadDate, pageCount, duration, isbn, explicit, location, url, wantToSync, null);


        ContentDAO cdao = new ContentDAO();
        PlaylistDAO pdao = new PlaylistDAO();
        Set ebooks = cdao.getAllContent();
        Iterator eIter = ebooks.iterator();
        while (eIter.hasNext()) {

            System.out.println(eIter.next().toString());
        }

        Set playlists = pdao.getAllPlaylists();
        Iterator pDAOIterator = playlists.iterator();
        
        Playlist playlist1 =  (Playlist) pDAOIterator.next();
        pdao.getContentFromPlaylist(playlist1);
        System.out.println(playlist1.toString());
        
        Set contents = cdao.getAllContent();
        Set contents2 = cdao.searchAllTablesBySearchTermAndType("ant", "EBook");
        Content[] content2;
        
    }
}
