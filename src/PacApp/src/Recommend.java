
import Database.SQLTranslator;
import Database.ContentDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recommend {

    //Recommend based on user statistics
    List<Book> RecommendBook(String searchType) throws IOException, SQLException, ClassNotFoundException {

        List<String[]> searchTerms = new ArrayList<>();
        Stats stats = new Stats();
        List<Book> bookList = new ArrayList<>();

        //String st = searchTerms.toLowerCase();
        String st = searchType;

        if (st.equals("genre")) {
            ContentDAO dao = new Database.ContentDAO();
            searchTerms = dao.getAllGenres();
        } else if (st.equals("author")) {
            ContentDAO dao = new Database.ContentDAO();
            searchTerms = dao.getAllCreators();
        } else if (st.equals("series")) {
            ContentDAO dao = new Database.ContentDAO();
            searchTerms = dao.getAllSeries();
        } else if (st.equals("publisher")) {
            ContentDAO dao = new Database.ContentDAO();
            searchTerms = dao.getAllPublishers();
        } else {
            //should eventually return a message popup containing the string
            System.out.println("Please enter valid searchType: Genre, Author/Creator, or Series");
        }

        if (st.equals("genre")) {
            String t = stats.BookStats("genre");
            if(t.contains(" ")){
                t = t.replace(" ", "+");
            }
            JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
            JSONArray jArray = json.getJSONArray("items");

            for (int i = 0; i < jArray.length(); i++) {
                String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                String subTitle;
                try{
                    subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                }catch(Exception e){
                    subTitle = "";
                }
                String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                        .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                bookList.add(book);
            }
        } else if (st.equals("author") || st.equals("creator")) {
            String t = stats.BookStats("author");
            if(t.contains(" ")){
                t = t.replace(" ", "+");
            }
            JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=inauthor:" + t);
            JSONArray jArray = json.getJSONArray("items");

            for (int i = 0; i < jArray.length(); i++) {
                String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                String subTitle;
                try{
                    subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                }catch(Exception e){
                    subTitle = "";
                }
                String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                        .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                bookList.add(book);
            }
        } else if (st.equals("series")) {
            String t = stats.BookStats("series");
            if(t.contains(" ")){
                t = t.replace(" ", "+");
            }
            System.out.println(t);
            JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=intitle:" + t);
            JSONArray jArray = json.getJSONArray("items");

            for (int i = 0; i < json.getJSONArray("items").length(); i++) {
                String title = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                String subTitle;
                try{
                    subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                }catch(Exception e){
                    subTitle = "";
                }
                String authors = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                String publisher = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                String publishYear = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                String pageCount = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                String isbn = json.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo")
                        .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                bookList.add(book);
            }
        } else if (st.equals("publisher")) {
            String t = stats.BookStats("publisher");
            if(t.contains(" ")){
                t = t.replace(" ", "+");
            }
            JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=inpublisher:" + t);
            JSONArray jArray = json.getJSONArray("items");

            for (int i = 0; i < jArray.length(); i++) {
                String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                String subTitle;
                try{
                    subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                }catch(Exception e){
                    subTitle = "";
                }
                String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                        .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                bookList.add(book);
            }
        } else {

            //should eventually return a message popup containing the string
            System.out.println("Please enter valid searchType: Genre, Author/Creator, or Series");
        }

        return bookList;

    }

    void RecommendPodcast() {

        //will need to decide if we want to use 3rd party data base
        //if so, choose and implement
        //if not, remove method
    }

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

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
    }
}
