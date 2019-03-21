package Database;

import Util.DBPrint;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
/**
 *
 * @author Jacob Oleson
 * 
 * @update 2/28/19
 * 
 * Simple test that calls the DB interface to add info to the DB file.
 */
public class SQLiteTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SQLTranslator test = new SQLTranslator();
        ResultSet res;
        
        String contentType = "Podcast";
        String syncStatusType = "Iphone";
        String firstName = "Matthew";
        String middleName = "James";
        String lastName = "Malzahn";
        String genreName = "Fantasy";
        String publisherName = "Test";
        String seriesName = "Test"; 
        String contentName = "Test";
        String contentDescription = "Test of URL";
        //yyyy-mm-dd
        String uploadDate = "2019-02-30";
        int pageCount = 0; 
        //hh:mm:ss
        String duration = "1:50:32";
        String isbn = "UNKNOWN";
        boolean explicit = true; 
        String location = "/ProjectDirectory/Content/Test/Test";
        String url = "test";
        test.addContent(contentType, syncStatusType, firstName, middleName, lastName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location, url);
        
        
        test.addToPlaylist("Test", "Podcsaegeasgast", "newPlaylist");


    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }
}
