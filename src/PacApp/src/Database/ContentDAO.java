/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jacob
 */
public class ContentDAO {
    SQLTranslator sql = new SQLTranslator();
    
    public Content getContent(String contentName, String contentType, String creatorName) {
        ResultSet res = sql.getContentTest(contentName, contentType, creatorName);
        return extractDataFromResultSet(res);
    }
   
    
    public Content getContentByID(int contentId) {
        ResultSet res = sql.getContentByID(contentId);
        return extractDataFromResultSet(res);
    }
    
    
    public Set getAllContent() {
        ResultSet res = sql.getAllContent();
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content); 
            }
            
            return contents;
        } 
        catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    /**
     * Get all content denoted by a given creator id.
     * @param creatorID
     * @return 
     */
    public Set getAllContentByCreator(int creatorID) {
        ResultSet res = sql.getContentByCreator(creatorID);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
                
            }
            
            return contents;
        } 
        catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    /**
     * Gets all content given a specific creator name
     * @param creatorName
     * @return 
     */
    public Set getAllContentByCreator(String creatorName) {
        ResultSet res = sql.getContentByCreator(creatorName);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
            }
            
            return contents;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets all content given a specific genre id.
     * @param genreID
     * @return 
     */
    public Set getAllContentByGenre(int genreID) {
        ResultSet res = sql.getContentByGenre(genreID);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
                
            }
            return contents;
        } 
        catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public Set getAllContentByGenre(String genreName) {
        ResultSet res = sql.getContentByGenre(genreName);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
                
            }
            return contents;
        } 
        catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    /**
     * Get all content denoted by a given series id.
     * @param seriesID
     * @return 
     */
    public Set getAllContentBySeries(int seriesID) {
        ResultSet res = sql.getContentBySeries(seriesID);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
                
            }
            return contents;
        } 
        catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public Set getAllContentBySeries(String seriesName) {
        ResultSet res = sql.getContentBySeries(seriesName);
        
        Set contents = new HashSet();
        try{
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
            }
            
            return contents;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    

    public Set getAllContentByType(int contentTypeID) {
        ResultSet res = sql.getContentByType(contentTypeID);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
            }
            
            return contents;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }   
    
    
    /**
     * Gets all content given a specific content type name.
     * @param contentType
     * @return 
     */
    public Set getAllContentByType(String contentType) {
        ResultSet res = sql.getContentByType(contentType);
        
        Set contents = new HashSet();
        
        try {
            while(res.next()) {
                Content content = extractDataFromResultSet(res);
                contents.add(content);
            }
            
            return contents;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    public List<String[]> getAllCreators() {
        return SQLTranslator.SQLToPrimitives(sql.getAllCreators());
    }
    
    public List<String[]> getAllGenres() {
        return SQLTranslator.SQLToPrimitives(sql.getAllGenres());
    }
    
    public List<String[]> getAllSeries() {
        return SQLTranslator.SQLToPrimitives(sql.getAllSeries());
    }
    
    public List<String[]> getAllPublishers() {
        return SQLTranslator.SQLToPrimitives(sql.getAllPublishers());
    }
    
    
    public boolean insertContent(Content content) {
        
        boolean success = sql.addContent(content.getContentTypeName(), content.getCreatorName(), content.getGenreName(), content.getPublisherName(), content.getSeriesName(), content.getContentName(), content.getContentDescription(), content.getUploadDate(), content.getPageCount(), content.getDuration(), content.getIsbn(), content.isExplicit(), content.getLocation(), content.getUrl(), content.getWantToSync(), content.getOriginalFilePath());
        
        return success;   
    }
    
    
    public int getCreatorID(String creatorName) {
        return sql.getCreatorID(creatorName);
    }
    
    
    public int getSeriesID(String seriesName) {
        return sql.getSeriesID(seriesName);
    }
    
    public int getGenreID(String genreName) {
        return sql.getGenreID(genreName);
    }
    
    public int getPublisherID(String publisherName) {
        return sql.getPublisherID(publisherName);
    }
    
    public int getContentTypeID(String contentType) {
        return sql.getContentTypeID(contentType);
    }
    
    
    private Content extractDataFromResultSet(ResultSet res) {
        Content content = new Content();
        try {
            ResultSet res2;
            content.setContentID(res.getInt("ContentID"));
            content.setContentName(res.getString("ContentName"));
            /**Since result set returns the foreign key ids
             * we need to call the translator class to convert
             * those ids into the actual names for the content object
             */
            content.setCreatorName(sql.getCreatorName(res.getInt("CreatorID")));
            content.setContentDescription(res.getString("ContentDescription"));
            content.setContentTypeName(sql.getContentTypeName(res.getInt("ContentTypeID")));
            content.setGenreName(sql.getGenreName(res.getInt("GenreID")));
            content.setSeriesName(sql.getSeriesName(res.getInt("SeriesID")));
            content.setPublisherName(sql.getPublisherName(res.getInt("PublisherID")));
            content.setDuration(res.getString("Duration"));
            content.setExplicit(res.getBoolean("Explicit"));
            content.setIsbn(res.getString("isbn"));
            content.setLocation(res.getString("location"));
            content.setPageCount(res.getInt("PageCount"));
            content.setUploadDate(res.getString("Uploaddate"));
            content.setUrl(res.getString("DownloadURL"));
            content.setWantToSync(res.getBoolean("WantToSync"));
            
            return content; 
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    } 
}
