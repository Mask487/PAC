/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewDatabase;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author jacob
 */
public class Playlist {
    
    private int playlistID;
    private String playlistName;
    private Set contents = new HashSet();

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
    
    public void addContent(Set contents) {
        this.contents.add(contents);
    }
    
    public Set geContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Playlist{" + "playlistID=" + playlistID + ", playlistName=" + playlistName 
                + ", contents=" + getContents(this.contents) + '}';
    }
    
    
    //Helper method to toString.
    private String getContents(Set contents) {
        String allContent = "";
        Iterator iter = contents.iterator();
        while(iter.hasNext()) {
            allContent = allContent.concat(iter.next().toString());
        }
        
        return allContent;
    }
    
}
