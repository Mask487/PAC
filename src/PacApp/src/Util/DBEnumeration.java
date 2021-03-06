package Util;

/**
 *
 * @author Jacob Oleson
 * 
 * @update 3/14/2019
 */
public class DBEnumeration {
    
    //Table Names
    public static final String CONTENT = "Content";
    public static final String CONTENTTYPE = "ContentType";
    public static final String GENRE = "Genre";
    public static final String CREATOR = "Creator";
    public static final String PCLOOKUP = "PlaylistContentLookup";
    public static final String PLAYLIST = "Playlists";
    public static final String PUBLISHER = "Publisher";
    public static final String SERIES = "Series";
    
    //Sentinel Value
    public static final int SENTINEL = -99;
    
    //Default value for records not given
    public static final String UNKNOWN = "UNKNOWN";
    public static final String UNLISTED = "UNLISTED";
    
    //Count variable given in SQL Statement
    public static final String COUNT = "total";
    
    //Project Directory
    public static final String PROJECTDIRECTORY = "C:/PAC/ContentFiles/";
}
