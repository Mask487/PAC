public class Book{
    
    String title;
    String authors;
    String publisher;
    String publishYear;
    String pageCount;
    int isbn;
    
    Book(String title, String authors, String publisher, String publishYear, String pageCount, int isbn){
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.pageCount = pageCount;
        this.isbn = isbn;
    }
    
    String getTitle(){
        return title;
    }
    void setTitle(String title){
        this.title = title;
    }
    
    String getAuthors(){
        return authors;
    }
    void setAuthors(String authors){
        this.authors = authors;
    }
    
    String getPublisher(){
        return publisher;
    }
    void setPublisher(String publisher){
        this.publisher = publisher;
    }
    
    String getPublishYear(){
        return publishYear;
    }
    void setPublishYear(String publishYear){
        this.publishYear = publishYear;
    }
    
    String getPageCount(){
        return pageCount;
    }
    void setPageCount(String pageCount){
        this.pageCount = pageCount;
    }
    
    int getISBN(){
        return isbn;
    }
    void setISBN(int isbn){
        this.isbn = isbn;
    }
}