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
 * @author Jacob Oleson
 * 
 * @update 4/03/2019
 * 
 * A data access object that communicates with the Translator Interface to the sqlite db
 * to insert, delete, and read from the db. This file only interacts
 * with the content table and its dimension tables.
 * 
 * Playlists will have their own data access object.
 */
public class ContentDAO {
    SQLTranslator sql = new SQLTranslator();
     
    /**
     * Gets content denoted by its name, type, and string.
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public Content getContent(String contentName, String contentType, String creatorName) {
        ResultSet res = sql.getContentTest(contentName, contentType, creatorName);
        return extractDataFromResultSet(res);
    }
   
    
    /**
     * Gets content denoted by its id.
     * @param contentId
     * @return 
     */
    public Content getContentByID(int contentId) {
        ResultSet res = sql.getContentByID(contentId);
        return extractDataFromResultSet(res);
    }
    
    
    /**
     * Returns all held content in db. 
     * @return 
     */
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
    
    
    /**
     * Returns all content in db denoted by a genre name
     * @param genreName
     * @return 
     */
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
     * Gets all content produced by a publisher, denoted by that publishers id.
     * @param publisherID
     * @return 
     */
    public Set getAllContentByPublisher(int publisherID) {
        ResultSet res = sql.getContentByPublisher(publisherID);
        
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
     * Gets all content produced by a publisher, given that publisher's name
     * @param publisherName
     * @return 
     */
    public Set getAllContentByPublisher(String publisherName) {
        ResultSet res = sql.getContentByPublisher(publisherName);
        
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
    
    
    /**
     * Gets all content in db denoted by a series name
     * @param seriesName
     * @return 
     */
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
    

    /**
     * Gets all content of a certain type, denoted by that types id
     * @param contentTypeID
     * @return 
     */
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
    
    
    /**
     * Gets all creator names held in db.
     * @return 
     */
    public List<String[]> getAllCreators() {
        return SQLTranslator.SQLToPrimitives(sql.getAllCreators());
    }
    
    
    /**
     * Gets all genre names held in db.
     * @return 
     */
    public List<String[]> getAllGenres() {
        return SQLTranslator.SQLToPrimitives(sql.getAllGenres());
    }
    
    
    /**
     * Gets all publisher names held in db
     * @return 
     */    
    public List<String[]> getAllPublishers() {
        return SQLTranslator.SQLToPrimitives(sql.getAllPublishers());
    }
    
    
    /**
     * Gets all series names held in DB. 
     * @return 
     */
    public List<String[]> getAllSeries() {
        return SQLTranslator.SQLToPrimitives(sql.getAllSeries());
    }

    
    /**
     * Inserts a piece of content given the content object.
     * @param content
     * @return 
     */
    public boolean insertContent(Content content) {
        
        boolean success = sql.addContent(content.getContentTypeName(), content.getCreatorName(), content.getGenreName(), content.getPublisherName(), content.getSeriesName(), content.getContentName(), content.getContentDescription(), content.getUploadDate(), content.getPageCount(), content.getDuration(), content.getIsbn(), content.isExplicit(), content.getLocation(), content.getUrl(), content.getWantToSync(), content.getOriginalFilePath());
        
        return success;   
    }
    
    
    /**
     * Inserts a piece of content given the current filepath to that content, 
     * and a description of its type (Podcast, AudioBook, Etc.)
     * @param filePath
     * @param contentType
     * @return 
     */
    public boolean insertContent(String filePath, String contentType) {
        boolean success = sql.addContent(filePath, contentType);
        return success;
    }
    
    
    /**
     * Deletes records of content held in db.
     * @param content
     * @return 
     */
    public boolean deleteContent(Content content) {
        boolean success = sql.deleteContent(content);
        
        return success;
    }
    
    
    /**
     * Returns a creator's id from db given their name
     * @param creatorName
     * @return 
     */
    public int getCreatorID(String creatorName) {
        return sql.getCreatorID(creatorName);
    }
    
    
    /**
     * returns a content type id from db given its name
     * @param contentType
     * @return 
     */
    public int getContentTypeID(String contentType) {
        return sql.getContentTypeID(contentType);
    }
    
    
    /**
     * Returns a genre's id from db given its name
     * @param genreName
     * @return 
     */
    public int getGenreID(String genreName) {
        return sql.getGenreID(genreName);
    }
    
    
    /**
     * Returns a publisher's id from db given their name
     * @param publisherName
     * @return 
     */
    public int getPublisherID(String publisherName) {
        return sql.getPublisherID(publisherName);
    }
    
    
    /**
     * Returns a series id from db given its name
     * @param seriesName
     * @return 
     */
    public int getSeriesID(String seriesName) {
        return sql.getSeriesID(seriesName);
    }
    
    
    /**
     * Set the sync status for a piece of content to true
     * @param content
     * @return 
     */
    public boolean setSyncStatus(Content content) {
        return sql.setSyncStatus(content.getContentID());
    }
    
    
    /**
     * Set the sync status for a piece of content to false
     * @param content
     * @return 
     */
    public boolean unsetSyncStatus(Content content) {
        return sql.unsetSyncStatus(content.getContentID());
    }
    
    
    /**
     * Gets all data from the result set and creates the new content object.
     * @param res
     * @return 
     */
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