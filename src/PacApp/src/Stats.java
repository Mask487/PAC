import Database.ContentDAO;
import Database.SQLTranslator;
import java.util.ArrayList;
import java.util.List;
public class Stats{
    SQLTranslator db = new Database.SQLTranslator();
    ContentDAO dao = new Database.ContentDAO();
    String BookStats(String statType){
        List<String[]> genres = new ArrayList<>();
        List<String[]> authors = new ArrayList<>();
        List<String[]> publishers = new ArrayList<>();
        List<String[]> series = new ArrayList<>();
        
        String mostReadGenre = null;
        int mostReadGenreAmt = 0;
        
        String mostReadAuthor = null;
        int mostReadAuthorAmt = 0;
        
        String mostReadPublisher = null;
        int mostReadPublisherAmt = 0;
        
        String mostReadSeries = null;
        int mostReadSeriesAmt = 0;
        
        genres = dao.getAllGenres();
        authors = dao.getAllCreators();
        publishers = dao.getAllPublishers();
        series = dao.getAllSeries();
        
        for(String[] s : genres){
            for(String x : s){
                mostReadGenre = null;
                mostReadGenreAmt = 0;
                int temp;
                temp = db.getGenreCount(x);
                
                if(temp > mostReadGenreAmt){
                    mostReadGenreAmt = temp;
                    mostReadGenre = x;
                }
            }
        }
        for(String[] s : publishers){
            for(String x : s){
                mostReadPublisher = null;
                mostReadPublisherAmt = 0;
                int temp;
                temp = db.getPublisherCount(x);
                
                if(temp > mostReadPublisherAmt){
                    mostReadPublisherAmt = temp;
                    mostReadPublisher = x;
                }
            }
        }
        for(String[] s : authors){
            for(String x : s){
                mostReadAuthor = null;
                mostReadAuthorAmt = 0;
                int temp;
                temp = db.getCreatorCount(x);
                
                if(temp > mostReadAuthorAmt){
                    mostReadAuthorAmt = temp;
                    mostReadAuthor = x;
                }
            }
        }
        for(String[] s : series){
            for(String x : s){
                mostReadSeries = null;
                mostReadSeriesAmt = 0;
                int temp;
                temp = db.getSeriesCount(x);
                
                if(temp > mostReadSeriesAmt){
                    mostReadSeriesAmt = temp;
                    mostReadSeries = x;
                }
            }
        }
        if(statType.equals("genre")){
            return mostReadGenre;
        }
        else if(statType.equals("author")){
            return mostReadAuthor;
        }
        else if(statType.equals("publisher")){
            return mostReadPublisher;
        }
        else if(statType.equals("series")){
            return mostReadSeries;
        }
        else{
            return "";
        }
    }
    void PodcastStats(){
        
    }
    void MusicStats(){}
    void VideoStats(){}
}