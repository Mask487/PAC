package Database;

import Util.DBPrint;
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
        String syncStatusType = "Andorid";
        String creatorName = "J.R.R Tolkein";
        String genreName = "Fantasy";
        String publisherName = null;
        String seriesName ="The Lord of the Rings"; 
        String contentName = "The Fellowship of the Ring";
        String contentDescription = "Testing";
        //yyyy-mm-dd
        String uploadDate = "2019-03-24";
        int pageCount = 183; 
        //hh:mm:ss
        String duration = null;
        String isbn = null;
        boolean explicit = true; 
        String location = null;
        String url = "https://test.test";
        //test.addContent(contentType, syncStatusType, creatorName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location, url);
        //DBPrint.printContents(test.getAllContent());
        DBPrint.printContents(test.getContentByType(contentType));
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
