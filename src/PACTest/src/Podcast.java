public class Podcast{
    
    void getTitle(){}
    void getAuthor(){}
    void getDescription(){}
    void getDuration(){}
    void getURL(){}
    void getExplicit(){}
    
    String title;
    String author;
    String description;
    String duration;
    String url;
    Boolean isExplicit;
    
    Podcast(String title, String author, String description, String duration, String url, Boolean isExplicit){
        this.title = title;
        this.author = author;
        this.description = description;
        this.duration = duration;
        this.url = url;
        this.isExplicit = isExplicit;
    }
}