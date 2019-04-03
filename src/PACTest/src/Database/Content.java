package Database;


/**
 *
 * @author Jacob Oleson
 * 
 * @update 4/02/2019
 * 
 * Sample content model
 */
public class Content {
    private int contentID;
    private String contentTypeName;
    private String creatorName;
    private String genreName;
    private String seriesName;
    private String publisherName;
    private String contentName;
    private String contentDescription;
    private String uploadDate;
    private int pageCount;
    private String duration;
    private String isbn;
    private boolean explicit; 
    private String location;
    private String url;
    private boolean wantToSync;
    private String originalFilePath;

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    public void setOriginalFilePath(String originalFilePath) {
        this.originalFilePath = originalFilePath;
    }

    public Content() {
    }

    public Content(int contentID, String contentTypeName, 
            String creatorName, String genreName, 
            String seriesName, String publisherName, String contentName, String contentDescription, String uploadDate, int pageCount, String duration, String isbn, boolean explicit, String location, String url, boolean wantToSync, String originalFilePath) {
        this.contentID = contentID;
        this.contentTypeName = contentTypeName;
        this.creatorName = creatorName;
        this.genreName = genreName;
        this.seriesName = seriesName;
        this.publisherName = publisherName;
        this.contentName = contentName;
        this.contentDescription = contentDescription;
        this.uploadDate = uploadDate;
        this.pageCount = pageCount;
        this.duration = duration;
        this.isbn = isbn;
        this.explicit = explicit;
        this.location = location;
        this.url = url;
        this.wantToSync = wantToSync;
        this.originalFilePath = originalFilePath;
    }

    public Content(String contentTypeName, String creatorName, String genreName, String seriesName, String publisherName, String contentName, String contentDescription, String uploadDate, int pageCount, String duration, String isbn, boolean explicit, String location, String url, boolean wantToSync, String originalFilePath) {
        this.contentTypeName = contentTypeName;
        this.creatorName = creatorName;
        this.genreName = genreName;
        this.seriesName = seriesName;
        this.publisherName = publisherName;
        this.contentName = contentName;
        this.contentDescription = contentDescription;
        this.uploadDate = uploadDate;
        this.pageCount = pageCount;
        this.duration = duration;
        this.isbn = isbn;
        this.explicit = explicit;
        this.location = location;
        this.url = url;
        this.wantToSync = wantToSync;
        this.originalFilePath = originalFilePath;
    }

    
    
    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }


    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }


    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }


    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getContentName() {
        return contentName;
    }


    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getWantToSync() {
        return wantToSync;
    }

    public void setWantToSync(boolean wantToSync) {
        this.wantToSync = wantToSync;
    }

    @Override
    public String toString() {
        return "Content{" + "contentID=" + contentID +  ", contentTypeName=" + contentTypeName + ", creatorName=" + creatorName + ", genreName=" + genreName + ", seriesName=" + seriesName + ", publisherName=" + publisherName + ", contentName=" + contentName + ", contentDescription=" + contentDescription + ", uploadDate=" + uploadDate + ", pageCount=" + pageCount + ", duration=" + duration + ", isbn=" + isbn + ", explicit=" + explicit + ", location=" + location + ", url=" + url + ", wantToSync=" + wantToSync + ", originalFilePath=" + originalFilePath + '}';
    }

   
}    