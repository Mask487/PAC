
import Database.SQLTranslator;
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

    //Recommend based on a specific type
    List<Book> RecommendBook(List<String> searchType) throws IOException, SQLException, ClassNotFoundException {

        List<String[]> searchTerms = new ArrayList<>();

        List<Book> bookList = new ArrayList<>();

        for (String s : searchType) {

            String st = s.toLowerCase();

            if (st.equals("genre")) {
                SQLTranslator db = new Database.SQLTranslator();
                searchTerms = db.getAllGenres();
            } else if (st.equals("author")) {
                SQLTranslator db = new Database.SQLTranslator();
                searchTerms = db.getAllCreators();
            } else if (st.equals("series")) {
                SQLTranslator db = new Database.SQLTranslator();
                searchTerms = db.getAllSeries();
            } else if (st.equals("publisher")) {
                SQLTranslator db = new Database.SQLTranslator();
                searchTerms = db.getAllPublishers();
            } else {
                //should eventually return a message popup containing the string
                System.out.println("Please enter valid searchType: Genre, Author/Creator, or Series");
            }

            if (st.equals("genre")) {
                for (String[] tl : searchTerms) {
                    for (String t : tl) {
                        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                        JSONArray jArray = json.getJSONArray("items");

                        for (int i = 0; i > jArray.length(); i++) {
                            String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                            String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                            String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                            String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                            String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                            String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                            String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                    .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                            Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                            bookList.add(book);
                        }
                    }
                }
            } else if (st.equals("author") || st.equals("creator")) {
                for (String[] tl : searchTerms) {
                    for (String t : tl) {
                        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                        JSONArray jArray = json.getJSONArray("items");

                        for (int i = 0; i > jArray.length(); i++) {
                            String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                            String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                            String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                            String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                            String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                            String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                            String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                    .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                            Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                            bookList.add(book);
                        }
                    }
                }
            } else if (st.equals("series")) {
                for (String[] tl : searchTerms) {
                    for (String t : tl) {
                        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                        JSONArray jArray = json.getJSONArray("items");

                        for (int i = 0; i > jArray.length(); i++) {
                            String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                            String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                            String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                            String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                            String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                            String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                            String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                    .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                            Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                            bookList.add(book);
                        }
                    }
                }
            } else if (st.equals("publisher")) {
                for (String[] tl : searchTerms) {
                    for (String t : tl) {
                        JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                        JSONArray jArray = json.getJSONArray("items");

                        for (int i = 0; i > jArray.length(); i++) {
                            String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                            String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                            String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                            String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                            String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                            String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                            String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                    .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                            Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                            bookList.add(book);
                        }
                    }
                }
            } else {

                //should eventually return a message popup containing the string
                System.out.println("Please enter valid searchType: Genre, Author/Creator, or Series");
            }

        }

        return bookList;

    }

    //Recommend based on user statistics
    List<Book> RecommendBook() throws IOException, SQLException, ClassNotFoundException {

        List<String[]> searchTerms = new ArrayList<>();

        List<Book> bookList = new ArrayList<>();

        //String st = searchTerms.toLowerCase();
        
        String st = "";

        if (st.equals("genre")) {
            SQLTranslator db = new Database.SQLTranslator();
            searchTerms = db.getAllGenres();
        } else if (st.equals("author")) {
            SQLTranslator db = new Database.SQLTranslator();
            searchTerms = db.getAllCreators();
        } else if (st.equals("series")) {
            SQLTranslator db = new Database.SQLTranslator();
            searchTerms = db.getAllSeries();
        } else if (st.equals("publisher")) {
            SQLTranslator db = new Database.SQLTranslator();
            searchTerms = db.getAllPublishers();
        } else {
            //should eventually return a message popup containing the string
            System.out.println("Please enter valid searchType: Genre, Author/Creator, or Series");
        }

        if (st.equals("genre")) {
            for (String[] tl : searchTerms) {
                for (String t : tl) {
                    JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                    JSONArray jArray = json.getJSONArray("items");

                    for (int i = 0; i > jArray.length(); i++) {
                        String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                        String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                        String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                        String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                        String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                        String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                        String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                        Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                        bookList.add(book);
                    }
                }
            }
        } else if (st.equals("author") || st.equals("creator")) {
            for (String[] tl : searchTerms) {
                for (String t : tl) {
                    JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                    JSONArray jArray = json.getJSONArray("items");

                    for (int i = 0; i > jArray.length(); i++) {
                        String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                        String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                        String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                        String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                        String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                        String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                        String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                        Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                        bookList.add(book);
                    }
                }
            }
        } else if (st.equals("series")) {
            for (String[] tl : searchTerms) {
                for (String t : tl) {
                    JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                    JSONArray jArray = json.getJSONArray("items");

                    for (int i = 0; i > jArray.length(); i++) {
                        String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                        String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                        String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                        String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                        String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                        String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                        String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                        Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                        bookList.add(book);
                    }
                }
            }
        } else if (st.equals("publisher")) {
            for (String[] tl : searchTerms) {
                for (String t : tl) {
                    JSONObject json = readJSONFromUrl("https://www.googleapis.com/books/v1/" + "volumes?q=subject:" + t);
                    JSONArray jArray = json.getJSONArray("items");

                    for (int i = 0; i > jArray.length(); i++) {
                        String title = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("title").toString();
                        String subTitle = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("subtitle").toString();
                        String authors = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("authors").toString();
                        String publisher = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publisher").toString();
                        String publishYear = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("publishedDate").toString();
                        String pageCount = jArray.getJSONObject(i).getJSONObject("volumeInfo").get("pageCount").toString();
                        String isbn = jArray.getJSONObject(i).getJSONObject("volumeInfo")
                                .getJSONArray("industryIdentifiers").getJSONObject(1).get("identifier").toString();

                        Book book = new Book(title, subTitle, authors, publisher, publishYear, pageCount, isbn);

                        bookList.add(book);
                    }
                }
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
