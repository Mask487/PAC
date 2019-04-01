public class Podcast{
    
    String title;
    String author;
    String description;
    String duration;
    String url;
    Boolean isExplicit;
    
    Podcast(String title, String author, String description, String duration, 
            String url, Boolean isExplicit){
        this.title = title;
        this.author = author;
        this.description = description;
        this.duration = duration;
        this.url = url;
        this.isExplicit = isExplicit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(Boolean isExplicit) {
        this.isExplicit = isExplicit;
    }
    
    
}