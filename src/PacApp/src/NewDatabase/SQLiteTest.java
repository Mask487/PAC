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
        String contentType = "pacapp.Podcast";
        String creatorName = "Jacob";
        String genreName = "pacapp.Test";
        String publisherName = "pacapp.Test";
        String seriesName = "test";
        String contentName = "pacapp.Test on new file system";
        String contentDescription = foo;
        //yyyy-mm-dd
        String uploadDate = "2019-04-07";
        int pageCount = 0;
        //hh:mm:ss
        String duration = "01:33:12";
        String isbn = null;
        boolean explicit = false;
        String location = "C:/pacapp.Test/ultimatetest.mp3";
        String url = null;
        boolean wantToSync = false;
        String filePath = "";
        
        ContentDAO dao = new ContentDAO();
        PlaylistDAO pdao = new PlaylistDAO();
        pdao.addPlaylist("test");
        Set set = dao.getAllContent();
        Iterator iter = set.iterator();
        Content content = (Content) iter.next();
        
        Set playlists = pdao.getAllPlaylists();
        Iterator piter = playlists.iterator();
        
        Playlist playlist = (Playlist) piter.next();
        
        pdao.insertContentIntoPlaylist(content, playlist);
        
        Set playlists2 = pdao.getAllPlaylists();
        Set set2 = dao.getAllContent();
        Iterator iter2 = set2.iterator();
        while(iter2.hasNext()) {
            System.out.println(iter2.next().toString());
        }
        

    }
}    
