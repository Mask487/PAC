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
 * @author Jacob Oleson
 * 
 * @update 3/24/2019
 */
public interface DBInterface {
    
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void getConnection() throws SQLException, ClassNotFoundException;
    
    
    public void closeConnection();
    
    
    public boolean addContent(String contentType,String creatorName, 
            String genreName, String publisherName, String seriesName, 
            String contentName, String contentDescription, String uploadDate,
            int pageCount, String duration, String isbn, boolean explicit, 
            String location, String url, boolean wantToSync) throws SQLException, ClassNotFoundException;
    
    
    public boolean addContentType(String contentTypeName);
    
    
    public boolean addCreator(String creatorName);

    
    public boolean addGenre(String genreName);
    
    
    public boolean addPlaylist(String playlistName);
    
    
    public boolean addToPlaylist(String contentName, String contentType, String creatorName, String playlistName);
    
    
    public boolean addPublisher(String publisherName);
 
    
    public boolean addSeries(String seriesName);
    
    
    public boolean addSyncStatus(String syncName);
    
    
    public boolean deleteContent(String contentName, String contentType, String creatorName);
    
    
    public boolean deleteContentType(String contentType);
    
    
    public boolean deleteCreator(String creatorName);
    
    
    public boolean deleteGenre(String genreName);
    
    
    public boolean deletePlaylist(String playlistName);
    
    
    public boolean deleteFromPlaylist(String contentName, String contentType, String creatorName, String playlistName);
    
    
    public boolean deletePublisher(String publisherName);
    
    
    public boolean deleteSeries(String seriesName);
    
    
    public List<String[]> getAllContent();


    public List<String[]> getAllContentTypes();
    
    
    
    public List<String[]> getAllCreators();
    
   
    public List<String[]> getAllGenres();
    
    
    public List<String[]> getAllPublishers();
    
    
    public List<String[]> getAllSeries();
    
 
    public List<String[]> getContentByCreator(String creatorName);
    
    
    public List<String[]> getContentByGenre(String _genreName);


    public List<String[]> getContentByName(String contentName);


    public List<String[]> getContentByPublisher(String publisherName);


    public List<String[]> getContentBySeries(String seriesName);


    public List<String[]> getContentByType(String contentType);

    
    public List<String[]> getContentType(String contentType);
    
    
    public List<String[]> getCreator(String creatorName);
    
    
    public int getCreatorCount(String creatorName);
    
    
    public List<String[]> getGenre(String genreName);
    
    
    public int getGenreCount(String genreName);
    
    
    public List<String[]> getPublisher(String publisherName);
    
    
    public int getPublisherCount(String publisherName);
    
    
    public List<String[]> getSeries(String seriesName);
    
    
    public int getSeriesCount(String seriesName);
    
    
    public boolean updateContent();
    
    
    public boolean updateContentType();
    
    
    public boolean updateCreator();
    
    
    public boolean updateGenre();
    
    
    public boolean updatePlaylist();
    
    
    public boolean updatePublisher();
    
    
    public boolean updateSeries();
    
    
    public boolean updateSyncStatus();
}
