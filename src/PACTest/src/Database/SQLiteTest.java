package Database;

import Util.DBDirectories;
import Util.DBEnumeration;
import Util.DBPrint;
import java.io.File;
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
        
        String contentType = "AudioBook";
        String syncStatusType = "Iphone";
        String firstName = "Phiip";
        String middleName = "Ader";
        String lastName = "French";
        String genreName = "Historical Fiction";
        String publisherName = "Test";
        String seriesName = null; 
        String contentName = "Thist";
        String contentDescription = "A test of the new directory system";
        //yyyy-mm-dd
        String uploadDate = "2019-03-22";
        int pageCount = 0; 
        //hh:mm:ss
        String duration = "1:50:32";
        String isbn = "UNKNOWN";
        boolean explicit = true; 
        String location = null;
        String url = "https://test.test";
        test.addContent(contentType, syncStatusType, firstName, middleName, lastName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location, url);
        
        //System.out.println(test.getCreatorCount("J", "Tyler", "Oleson"));
//        test.addToPlaylist("Test", "Podcsaegeasgast", "newPlaylist");
//
//        //DBDirectories.createDirectoy("Series");
//        
//    Path currentRelativePath = Paths.get("");
//    String s = currentRelativePath.toAbsolutePath().toString();
//    String r = DBEnumeration.PROJECTDIRECTORY + "ContentFiles";
//    DBDirectories.createDirectoy(r);
        //System.out.println("Current relative path is: " + s);
    }
}
