import Database.SQLTranslator;
import java.util.ArrayList;
import java.util.List;
public class Stats{
    SQLTranslator db = new Database.SQLTranslator();
    
    void BookStats(){
        List<String[]> genres = new ArrayList<>();
        List<String[]> authors = new ArrayList<>();
        List<String[]> publishers = new ArrayList<>();
        List<String[]> series = new ArrayList<>();
        
        genres = db.getAllGenres();
        authors = db.getAllCreators();
        publishers = db.getAllPublishers();
        series = db.getAllSeries();
        
        for(String[] s : genres){
            System.out.println(s);
        }
    }
    void PodcastStats(){
        
    }
    void MusicStats(){}
    void VideoStats(){}
}