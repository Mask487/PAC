/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jacob
 */
public interface DBInterface {
    
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void getConnection() throws SQLException, ClassNotFoundException;
    
    
    public void closeConnection();
    
    
    public boolean addContent(String contentType, String syncStatusType, 
            String firstName, String middleName, String lastName, 
            String genreName, String publisherName, String seriesName, 
            String contentName, String contentDescription, String uploadDate,
            int pageCount, String duration, String isbn, boolean explicit, 
            String location) throws SQLException, ClassNotFoundException;
    
    
    public boolean addContentType(String contentTypeName) throws SQLException, ClassNotFoundException;
    
    
    public boolean addCreator(String firstName, String middleName, String lastName) throws SQLException, ClassNotFoundException;

    
    public boolean addGenre(String genreName) throws SQLException, ClassNotFoundException;
    
    
    public boolean addPublisher(String publisherName) throws SQLException, ClassNotFoundException;
 
    
    public boolean addSeries(String seriesName) throws SQLException, ClassNotFoundException;
    
    
    public boolean addSyncStatus(String syncName) throws SQLException, ClassNotFoundException;
    
    
    public boolean deleteContent();
    
    
    public boolean deleteContentType();
    
    
    public boolean deleteCreator();
    
    
    public boolean deleteGenre();
    
    
    public boolean deletePublisher();
    
    
    public boolean deleteSeries();
    
    
    public boolean deleteSyncStatus();
    
    
    public List<String[]> getAllContent() throws SQLException, ClassNotFoundException;


    public List<String[]> getAllContentTypes() throws SQLException, ClassNotFoundException;
    
    
    
    public List<String[]> getAllCreators() throws SQLException, ClassNotFoundException;
    
   
    public List<String[]> getAllGenres() throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getAllPublishers() throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getAllSeries() throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getAllSyncStatus() throws SQLException, ClassNotFoundException;
    
 
    public List<String[]> getContentByCreator(String _firstName, String _middleName, String _lastName) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getContentByGenre(String _genreName) throws SQLException, ClassNotFoundException;


    public List<String[]> getContentByName(String contentName) throws SQLException, ClassNotFoundException;


    public List<String[]> getContentByPublisher(String publisherName) throws SQLException, ClassNotFoundException;


    public List<String[]> getContentBySeries(String seriesName) throws SQLException, ClassNotFoundException;


    public List<String[]> getContentByType(String contentType) throws SQLException, ClassNotFoundException;

    
    public List<String[]> getContentType(String contentType) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getCreator(String firstName, String middleName, String lastName) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getGenre(String genreName) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getPublisher(String publisherName) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getSeries(String seriesName) throws SQLException, ClassNotFoundException;
    
    
    public List<String[]> getSyncStatus(String syncStatusDescription) throws SQLException, ClassNotFoundException;
    
    
    public boolean updateContent();
    
    
    public boolean updateContentType();
    
    
    public boolean updateCreator();
    
    
    public boolean updateGenre();
    
    
    public boolean updatePublisher();
    
    
    public boolean updateSeries();
    
    
    public boolean updateSyncStatus();
}
