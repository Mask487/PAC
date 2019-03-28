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
        
        String mostReadGenre = null;
        String mostReadAuthor = null;
        String mostReadPublisher = null;
        String mostReadSeries = null;
        
        genres = db.getAllGenres();
        authors = db.getAllCreators();
        publishers = db.getAllPublishers();
        series = db.getAllSeries();
        
        for(String[] s : genres){
            for(String x : s){
                mostReadGenre = null;
                int mostReadGenreAmt = 0;
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
                int mostReadPublisherAmt = 0;
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
                int mostReadAuthorAmt = 0;
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
                int mostReadSeriesAmt = 0;
                int temp;
                temp = db.getSeriesCount(x);
                
                if(temp > mostReadSeriesAmt){
                    mostReadSeriesAmt = temp;
                    mostReadAuthor = x;
                }
            }
        }
        
        System.out.println(mostReadGenre);
        System.out.println(mostReadAuthor);
        System.out.println(mostReadPublisher);
        System.out.println(mostReadSeries);
    }
    void PodcastStats(){
        
    }
    void MusicStats(){}
    void VideoStats(){}
}