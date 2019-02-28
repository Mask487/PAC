package Database;

import java.sql.*;
import java.util.List;

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
        
//        String contentType = "EBook";
//        String syncStatusType = "Pixel";
//        String firstName = "Jacob";
//        String middleName = "Tyler";
//        String lastName = "Oleson";
//        String genreName = "TestAgain";
//        String publisherName = "Test";
//        String seriesName = "Test"; 
//        String contentName = "Test";
//        String contentDescription = "No";
//        //yyyy-mm-dd
//        String uploadDate = "2019-02-27";
//        int pageCount = 1; 
//        //hh:mm:ss
//        String duration = "24:00:00";
//        String isbn = "UNKNOWN";
//        boolean explicit = true; 
//        String location = "/ProjectDirectory/Content/Test/Test";
//        test.addContent(contentType, syncStatusType, firstName, middleName, lastName, genreName, publisherName, seriesName, contentName, contentDescription, uploadDate, pageCount, duration, isbn, explicit, location);
//        
        String unknown = "UNKNOWN";
        test.addContentType(unknown);
        test.addCreator("jacob", unknown, unknown);
        test.addGenre(unknown);
        test.addPublisher(unknown);
        test.addSeries(unknown);
        test.addSyncStatus(unknown);
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
        
    }
    
    public void addCreator(String firstName, String middleName, String lastName) {
    
    }
    
    public static void printContents(List<String[]> result) {
                
        for(int i = 0; i < result.size(); i++) {
            String[] strings = result.get(i);
            for(int j = 0; j < strings.length; j++) {
                System.out.println(strings[j] + " ");
            }
            System.out.println();
        }
    }
}
