//standard java imports included with netbeans
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//json library specific imports
import org.json.JSONException;
import org.json.JSONObject;

public class BookSearch{
    
    //start getters for the book information
    String getTitle(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String title = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("title").toString();
        return title;
    }
    String getSubtitle(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String subTitle = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("subtitle").toString();
        return subTitle;
    }
    String getAuthors(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String authors = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("authors").toString();
        return authors;
    }
    String getPublisher(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String publisher = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("publisher").toString();
        return publisher;
    }
    String getPublishYear(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String publishYear = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("publishedDate").toString();
        return publishYear;
    }
    String getPageCount(String isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String pageCount = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("pageCount").toString();
        return pageCount;
    }
    //end getters
    
    //read character by character into one string
    private static String readJSON(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
    //get the json from the url and read it into the string builder above and 
    //then convert into a json object
    public static JSONObject readJSONFromUrl(String url) throws IOException, 
            JSONException {
        try (InputStream inStream = new URL(url).openStream()) {
          BufferedReader rd = new BufferedReader(new InputStreamReader(inStream,
                  Charset.forName("UTF-8")));
          String jsonText = readJSON(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
          
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
    }
    //USE THIS METHOD TO LOOKUP BOOKS
    //accepts a list of isbn numbers, returns a list of books with their info
    List<Book> BookLookUp(List<String> isbn) throws IOException, JSONException{
        
        List<Book> bookList = new ArrayList<>();
        
        //takes each isbn and uses the getters above to get their info from
        //the json file for the book, uses that info to create a book object
        //that is added to the list
        for(String i : isbn){
            Book book = new Book(getTitle(i), getSubtitle(i), getAuthors(i), 
                    getPublisher(i), getPublishYear(i), getPageCount(i), i);
            bookList.add(book);
        }
        
        return bookList;
    }
    
}