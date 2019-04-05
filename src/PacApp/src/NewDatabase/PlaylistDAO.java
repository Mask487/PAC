/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jacob Oleson
 * 
 * @update 4/3/2019
 * 
 * This is the model for setting up playlists
 */
public class PlaylistDAO {
    
    SQLTranslator sql = new SQLTranslator();
    
    
    /**
     * Gets all playlist ids and names from db and initializes the objects.
     * This does not get the content associated with each playlist.
     * @return 
     */
    public Set getAllPlaylists() {
        ResultSet res = sql.getPlaylists();
        
        Set playlists = new HashSet();
        
        try {
            while(res.next()) {
                Playlist playlist = extractDataFromPlaylistResultSet(res);
                playlists.add(playlist);
            }
            
            return playlists;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Inserts a new piece of content into a playlist.
     * @param content
     * @param playlist
     * @return 
     */
    public boolean insertContentIntoPlaylist(Content content, Playlist playlist) {
        
        return sql.addToPlaylist(content, playlist);
    }
    
    
    /**
     * Retrieves content in a playlist that is already inside of database.
     * @param playlist
     * @return 
     */
    public Playlist getContentFromPlaylist(Playlist playlist) {
        ResultSet res = sql.getContentFromPlaylist(playlist.getPlaylistID());
        
        try {
            
            while(res.next()) {
                Content content = extractDataFromContentResultSet(res);
                playlist.addContent(content);
                
            }
            
            return playlist;
            
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets all data from the result set and creates the new content object.
     * @param res
     * @return 
     */
    private Content extractDataFromContentResultSet(ResultSet res) {
        Content content = new Content();
        try {
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
    
    
    /**
     * Gets the content associated with just the playlist itself, not the content
     * that is a part of it.
     * @param res
     * @return 
     */
    private Playlist extractDataFromPlaylistResultSet(ResultSet res) {
        Playlist playlist = new Playlist();
        
        try {
            playlist.setPlaylistID(res.getInt("PlaylistID"));
            playlist.setPlaylistName(res.getString("PlaylistName"));
            return playlist;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
}
