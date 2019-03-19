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
        
        String unknown = "UNKNOWN";
        test.addContentType(unknown);
        test.addCreator("jacob", unknown, unknown);
        test.addGenre(unknown);
        test.addPublisher(unknown);
        test.addSeries(unknown);
        test.addSyncStatus(unknown);
        test.getGenreCount("Philosophical Fiction");
        DBPrint.printContents(test.getGenreCount("Philosophical Fon"));
//        List<String[]> result = test.getAllContent();
//
//        printContents(result);
//        
//        List<String[]> result2 = test.getAllCreators();
//        
//        printContents(result2);
        
        
        
        
        
        
//        String firstName = "Jacob";
//        String middleName = "Tyler";
//        String lastName = "Oleson";
        //test.addContentTest(firstName, middleName, lastName);
        //test.addCreator(firstName, middleName, lastName);
//      
//        try {
//            res = test.displayCreators();
//            while(res.next()) {
//                System.out.println(res.getString("FirstName") +  " " + 
//                    res.getString("LastName"));
//            }
//        } 
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
        


    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }
}
