package NewDatabase;
import org.apache.commons.io.FilenameUtils;
import Util.DBPrint;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;
import pacapp.Book;
/**
 * 
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
        
        Book book = new Book("Test", "Test", "Test", "Test", "04-18-2019", "0", "TestISBN");
        dao.insertBook(book);
        Set set4 = dao.getAllContentByType("Book");
        Set set3 = dao.getAllContent();

    }
}    
