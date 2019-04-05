/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Set;

/**
 *
 * @author jacob
 */
public class Playlist {

    private int playlistID;
    private String playlistName;
    private Set contents;

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void addContent(Content content) {
        this.contents.add(content);
    }

    public Set geContents() {
        return contents;
    }


}
