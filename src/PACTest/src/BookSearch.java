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
    String getTitle(int isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String title = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("title").toString();
        return title;
    }
    String getSubtitle(int isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String subTitle = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("subtitle").toString();
        return subTitle;
    }
    String getAuthors(int isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String authors = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("authors").toString();
        return authors;
    }
    String getPublisher(int isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String publisher = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("publisher").toString();
        return publisher;
    }
    String getPublishYear(int isbn) throws IOException{
        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/"
                + "volumes?q=isbn:" + isbn);
        String publishYear = json.getJSONArray("items").getJSONObject(0)
                .getJSONObject("volumeInfo").get("publishedDate").toString();
        return publishYear;
    }
    String getPageCount(int isbn) throws IOException{
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
        }
    }
    //accepts a list of isbn numbers, returns a list of books with their info
    List<Book> BookLookUp(List<Integer> isbn) throws IOException, JSONException{
        
        List<Book> bookList = new ArrayList<>();
        
        //takes each isbn and uses the getters above to get their info from
        //the json file for the book, uses that info to create a book object
        //that is added to the list
        for(int i : isbn){
            Book book = new Book(getTitle(i), getSubtitle(i), getAuthors(i), getPublisher(i), 
                    getPublishYear(i), getPageCount(i), i);
            bookList.add(book);
        }
        
        return bookList;
    }
    
}