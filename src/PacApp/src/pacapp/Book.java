package pacapp;

public class Book{
    
     String title;
     String subTitle;
     String authors;
     String publisher;
     String publishYear;
     String pageCount;
     String isbn;
    
    public Book(String title, String subTitle, String authors, String publisher, 
            String publishYear, String pageCount, String isbn){
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.pageCount = pageCount;
        this.isbn = isbn;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getSubtitle(){
        return subTitle;
    }
    public void setSubTitle(String subTitle){
        this.subTitle = subTitle;
    }
    
    public String getAuthors(){
        return authors;
    }
    public void setAuthors(String authors){
        this.authors = authors;
    }
    
    public String getPublisher(){
        return publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    
    public String getPublishYear(){
        return publishYear;
    }
    public void setPublishYear(String publishYear){
        this.publishYear = publishYear;
    }
    
    public String getPageCount(){
        return pageCount;
    }
    public void setPageCount(String pageCount){
        this.pageCount = pageCount;
    }
    
    public String getISBN(){
        return isbn;
    }
    public void setISBN(String isbn){
        this.isbn = isbn;
    }
}