import NewDatabase.SQLTranslator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test{
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Stats stats = new Stats();
        Recommend rec = new Recommend();
        List<Book> bookList = new ArrayList<>();
        
        bookList = rec.RecommendBook("series");
        
        System.out.println(bookList);
        
        for (Book b : bookList){
            System.out.println(b.getTitle());
            System.out.println(b.getAuthors());
            System.out.println("");
        }
        //stats.BookStats();
        
        /*File file = new File("C:/Users/cothe/Desktop/test.jpeg");
        String url = "https://books.google.com/books/content?id=MsJluAAACAAJ&printsec="
                + "frontcover&img=1&zoom=1&imgtk=AFLRE705SJJz394ZrV67jOCZFL0URfz"
                + "wqcFR9YN1iXljkRwBH7R2hdDbuMsim1y8wJ55DmcdKXTjgf8aV2cGhOgmaFH"
                + "aYaqXvqE4O3paODdE15OWDW5of0laoKKjRqaGQHr4Cpsz9FXO&source=gbs_api";
        URL feedUrl = new URL(url);
        ReadableByteChannel readableByteChannel = Channels
                        .newChannel(feedUrl.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 
                        0, Long.MAX_VALUE);*/
        
        
        //RSSReader.DownloadPodcast("http://www.hellointernet.fm/podcast?format=rss");
    }
}