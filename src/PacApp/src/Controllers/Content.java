package Controllers;
import Database.*;
import java.util.Set;

/**
 *
 * @author Jacob Oleson
 * 
 * An effort to make the view's job easier to query the models. 
 */
public class Content {
    
    private ContentDAO dao = new ContentDAO();
    
    
    /**
     * Called from view to retrieve all content held in DB.
     * @return 
     */
    public Set getAllContent() {
        return dao.getAllContent();
    }
    
    
    public Set getContentByType(String contentType) {
        return dao.getAllContentByType(contentType);
    }
    
    
    
    public boolean addContent(Database.Content content) {
        return dao.insertContent(content);
    }
    
    
}
