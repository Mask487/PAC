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
        String foo = "ThisIsTest";
        String contentType = "AudioBook";
        String creatorName = "Ayn Rand";
        String genreName = "Philosophical Fiction";
        String publisherName = "Merills";
        String seriesName = null; 
        String contentName = "The Fountainhead";
        String contentDescription = null;
        //yyyy-mm-dd
        String uploadDate = "2019-04-01";
        int pageCount = 0; 
        //hh:mm:ss
        String duration = "20:33:12";
        String isbn = null;
        boolean explicit = true; 
        String location = null;
        String url = "https://test.test";
        boolean wantToSync = true;
        
        //test.addPlaylist("Test play");
        test.addContent(contentType, creatorName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location, url, wantToSync);
        //test.addToPlaylist(contentName, contentType, creatorName, "Test play");
        //test.deleteContent(contentName, contentType, creatorName);
        //test.deleteContent(contentName, contentType, creatorName);
        //DBPrint.printContents(test.getAllContent());
//        DBPrint.printContents(test.getContentByType(contentType));
//        
//        String test3 = "C:/Test/test1.mp3";
//        MetaDataReader mdr = new MetaDataReader();
//        mdr.MetaDataReader(test3);
//        System.out.println(mdr.getTitle());
//        System.out.println(mdr.getCreator());
        //DBPrint.printContents(test.getAllGenres());
        
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
