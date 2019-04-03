/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
    
    public Set getAllPlaylists() {
        return null;
    }
    
    
    public boolean insertContentIntoPlaylist(Content content, Playlist playlist) {
        
        //return sql.addToPlaylist(content, playlist);
        return false;
    }
    
    public boolean getContentFromPlaylist(Playlist playlist) {
        return false;
    }
}