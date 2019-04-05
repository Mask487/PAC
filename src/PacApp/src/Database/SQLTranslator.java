package Database;

import java.sql.*;
import Util.DBDirectories;
import Util.DBEnumeration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import java.nio.file.*;
/**
 * @author Jacob Oleson
 * 
 * @update 4/02/2019
 * 
 * Translator class that actually speaks to the DB File.
 */

//@TODO How does actual content file fit into folder that DB creates for it
//@TODO Create directories for new content being added in.
public class SQLTranslator {
    
    //Establishes connection to db file.
    private static Connection conn;
    MetaDataReader mdr = new MetaDataReader();
    /**
     * The jdbc:sqlite: part is permanent. The part after that specifies
     * the filepath. There needs to be a way to specify the filepath 
     * in relation to where this file is in the project directory.
     * For now just specify where it is on your own machine.
     */
    //private final String dbLocationPath = "jdbc:sqlite:C:\\PAC\\Database\\PACDB.db";
    private final String dbLocationPath = getPathToDB();
    
    /**
     * Adds a new piece of content to DB. Desperately needs to be refactored down.
     * You must give all parameters but if you do not have information to give, just put null.
     * Giving null will allow for info to be stored as null and set the foreign keys
     * to an UNKNOWN value that should be pre-populated in the DB 
     * 
     * Eventually I want to have it so that you just have to pass a file, 
     * and the name of the content type and you'll be good to go.
     * Still need to work on the metadata extraction for that though.
     * @param contentType
     * @param creatorName
     * @param genreName
     * @param publisherName
     * @param seriesName
     * @param contentName
     * @param contentDescription
     * @param uploadDate
     * @param pageCount
     * @param duration
     * @param isbn
     * @param explicit
     * @param location
     * @param url
     * @param wantToSync
     * @param filePath
     * @return true if content added successfully and false otherwise.
     */
    public boolean addContent(String contentType, 
            String creatorName, String genreName, String publisherName, String seriesName, 
            String contentName, String contentDescription, String uploadDate,
            int pageCount, String duration, String isbn, boolean explicit, 
            String location, String url, boolean wantToSync, String filePath) {
        
        try {
            if(conn == null) {
                getConnection();
            }
//            String fileName;
//            
//            if("Podcast".equals(contentType) | "AudioBook".equals(contentType) | "Music".equals(contentType)) {
//                fileName = contentName + ".mp3";
//            }
//            
//            if("EBook".equals(contentType)) {
//                fileName = contentName + ".epub";
//            }
//            
            if(contentType == null) {
                contentType = DBEnumeration.UNKNOWN;
            }
            if(creatorName == null) {
                creatorName = DBEnumeration.UNKNOWN;
            }
            if(genreName == null) {
                genreName = DBEnumeration.UNKNOWN;
            }
            if(publisherName == null) {
                publisherName = DBEnumeration.UNKNOWN;
            }
            if(seriesName == null) {
                seriesName = DBEnumeration.UNKNOWN;
            }
            if(contentName == null) {
                contentName = DBEnumeration.UNKNOWN;
            }
            contentName  = cleanString(contentName);
            if(contentDescription == null) {
                contentDescription = DBEnumeration.UNKNOWN;
            }
            if(uploadDate == null) {
                uploadDate = "2019-04-03";
            }
            if(duration == null) {
                duration = "00:00:00";
            }
            if(isbn == null) {
                isbn = "null";
            }
            if(url == null) {
                url = DBEnumeration.UNKNOWN;
            }
            
            //Upload Date, Page Count, Duration, ISBN and Exlicit can remain UNKNOWN
           
            //Check if attributes of content exist by querying relevant tables
            String queryContentType = "SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                    + " WHERE ContentType = '" + contentType + "'";
            String queryCreator = "SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorName = '" + creatorName + "'";
            String queryGenre = "SELECT GenreID FROM " + DBEnumeration.GENRE 
                    + " WHERE GenreName = '" + genreName + "'";
            String queryPublisher = "SELECT PublisherID FROM " + DBEnumeration.PUBLISHER
                    + " WHERE PublisherName = '" + publisherName + "'";
            String querySeries = "SELECT SeriesID FROM " + DBEnumeration.SERIES
                    + " WHERE SeriesName = '" + seriesName + "'";

           //uploadDate MUST be in format "yyyy-[m]m-[d]d"
            //java.sql.Date date = java.sql.Date.valueOf(uploadDate);

            //Duration string MUST be in formt "hh:mm::ss"
            //java.sql.Time time = java.sql.Time.valueOf(duration);

            //Retrieve id of foreign keys. If they don't exist in DB, insert them as new records.
            // -1 is the sentinel value to denote that something does not exist within the DB.
            int contentTypeID = SQLCheckForeignKeyRecord(queryContentType, DBEnumeration.CONTENTTYPE);
            if(contentTypeID == DBEnumeration.SENTINEL) {
                addContentType(contentType);
                contentTypeID = SQLCheckForeignKeyRecord(queryContentType, DBEnumeration.CONTENTTYPE);
            }

            int creatorID = SQLCheckForeignKeyRecord(queryCreator, DBEnumeration.CREATOR);
            if(creatorID == DBEnumeration.SENTINEL) {
                addCreator(creatorName);
                creatorID = SQLCheckForeignKeyRecord(queryCreator, DBEnumeration.CREATOR);
            }

            int genreID = SQLCheckForeignKeyRecord(queryGenre, DBEnumeration.GENRE);
            if(genreID == DBEnumeration.SENTINEL) {
                addGenre(genreName);
                genreID = SQLCheckForeignKeyRecord(queryGenre, DBEnumeration.GENRE);
            }

            int publisherID = SQLCheckForeignKeyRecord(queryPublisher, DBEnumeration.PUBLISHER);
            if(publisherID == DBEnumeration.SENTINEL) {
                addPublisher(publisherName);
                publisherID = SQLCheckForeignKeyRecord(queryPublisher, DBEnumeration.PUBLISHER);
            }

            int seriesID = SQLCheckForeignKeyRecord(querySeries, DBEnumeration.SERIES);
            if(seriesID == DBEnumeration.SENTINEL) {
                addSeries(seriesName);
                seriesID = SQLCheckForeignKeyRecord(querySeries, DBEnumeration.SERIES);
            }

            //Check if a piece of content already exists as a given type by a given author. If it does, don't add it.
            String queryCount = "SELECT COUNT(*) AS total FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.CONTENTTYPE
                    + " ct on c.ContentTypeID = ct.contentTypeID"
                    + " JOIN " + DBEnumeration.CREATOR 
                    + " cr on c.CreatorID = cr.CreatorID WHERE c.ContentName = '" 
                    + contentName + "' AND ct.contentTypeID = "
                    + "(" + queryContentType 
                    + ") AND cr.CreatorID = (" + queryCreator + ")";
            
            //Checks contents existence, if it exists, don't add
            if(!checkExistence(queryCount)) {
                System.out.println("Cannot add duplicate content");
                return false;
            }
            
            //Content does not seem to already exist, continue with adding
            
            /*
             * Set location path
             * Right now follows pattern of
             * ParentDirecoty/ContentType/GenreName/SeriesName/ContentFile.extension
             */

            //Set the parent directories for a new file
            location = setContentLocation(contentName, contentType, genreName, seriesName);
            
            //Get the content extension (mp3, epub, etc.)
            String ext = getExtension(filePath);
                        
            // the absoulte filepath to the content. This will be put in DB.
            String fileName = location + contentName + "." + ext;
            
            //Query to insert content into db.
            String query = "INSERT INTO " + DBEnumeration.CONTENT 
                    + "(ContentTypeID, CreatorID, GenreID, PublisherID"
                    + ", SeriesID, ContentName, ContentDescription, UploadDate, "
                    + "PageCount, Duration, ISBN, Explicit, Location, DownloadURL, WantToSync)" 
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, contentTypeID);
            prep.setInt(2, creatorID);
            prep.setInt(3, genreID);
            prep.setInt(4, publisherID);
            prep.setInt(5, seriesID);
            prep.setString(6, contentName);
            prep.setString(7, contentDescription);
            prep.setString(8, uploadDate);
            prep.setInt(9, pageCount);
            prep.setString(10, duration);
            prep.setString(11, isbn);
            prep.setBoolean(12, explicit);
            prep.setString(13, fileName);
            prep.setString(14, url);
            prep.setBoolean(15, wantToSync);

            //Check if content already exists. If it doesn't, add it. 

            if(SQLExecute(prep)) {        
                //Set the file into new filepath on device.
                
                //Original filepath
                File file = new File(filePath);
                
                //Make parent directories for new filepath
                DBDirectories.createDirectories(location);
                
                //New filepath for application.
                if(file.renameTo(new File(fileName))) {
                    //file.delete();
                    System.out.println("File moved successfully");
                }
                
                System.out.println("Content added successfully");
                return true;    
            }
            
            else {
                System.out.println("Error in adding content");
                return false;
            }    
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    /**
     * Adds a new content type that the DB can hold.
     * @param contentTypeName
     * @return 
     */
    public boolean addContentType(String contentTypeName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.CONTENTTYPE + " ct WHERE ct.ContentType = '" 
                    + contentTypeName + "'";
            
            if(!checkExistence(checkQuery)) {
                System.out.println("Cannot add duplicate ContentType");
                return false;
            }

            //Insert record into DB
            String insertQuery = "INSERT INTO " + DBEnumeration.CONTENTTYPE 
                    + "(ContentType)"
                    + " VALUES(?);";

            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, contentTypeName);

            if(SQLExecute(prep)) {
                System.out.println("ContentType added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new content type");
                return false;
            }
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
     
     
    /**
     * This method adds a new creator to the creator table. 
     * @param creatorName
     * @return 
     */
    public boolean addCreator(String creatorName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.CREATOR 
                    + " WHERE CreatorName = '" + creatorName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Creator already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.CREATOR 
                    + "(CreatorName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, creatorName);

            if(SQLExecute(prep)) {
                System.out.println("Creator added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new creator");
                return false;
            }
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    /**
     * Adds a new genre to DB.
     * @param genreName 
     * @return  
     */
    public boolean addGenre(String genreName) {
        
        try {         
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.GENRE + " WHERE GenreName = '" 
                    + genreName + "'";
            
            if(!checkExistence(checkQuery)) {
                System.out.println("Cannot add duplicate Genre");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.GENRE + "(GenreName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, genreName);

            if(SQLExecute(prep)) {
                System.out.println("Genre added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new genre");
                return false;
            }
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    /**
     * 
     * @param playlistName
     * @return 
     */
    public boolean addPlaylist(String playlistName) {
        
        try {       
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.PLAYLIST 
                    + " WHERE PlaylistName = '" + playlistName + "'";
            
            if(!checkExistence(checkQuery)) {
                System.out.println("Cannot add duplicate Playlist");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.PLAYLIST 
                    + "(PlaylistName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, playlistName);

            if(SQLExecute(prep)) {
                System.out.println("New playlist added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new playlist");
                return false;
            }
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
                    //Default Value
            return false;
        }
    }
    
    
    /**
     * Adds a new piece of content, of a given type, to a playlist. 
     * @param contentName
     * @param contentType
     * @param creatorName
     * @param playlistName
     * @return 
     */
    public boolean addToPlaylist(String contentName, String contentType, String creatorName, String playlistName) {
        
        try {    
            if(conn == null) {
                getConnection();
            }
            
            //Check if content does not exist
            String query1 = "SELECT COUNT(*) AS " + DBEnumeration.COUNT + " FROM " + DBEnumeration.CONTENT 
                    + " WHERE ContentName = '" + contentName 
                    + "' AND ContentTypeID = (SELECT ContentTypeID FROM " 
                    + DBEnumeration.CONTENTTYPE + " WHERE ContentType = '"
                    + contentType + "') AND CreatorID = (SELECT CreatorID FROM "
                    + DBEnumeration.CREATOR + " WHERE CreatorName = '" + creatorName
                    + "')";
            
            if(checkExistence(query1)) {
                System.out.println("Content does not exist!");
                return false;
            }
            
            //Check if playlist does not exist
            String query2 = "SELECT COUNT(*) AS " + DBEnumeration.COUNT + " FROM " + DBEnumeration.PLAYLIST
                    + " WHERE PlaylistName = '" + playlistName + "'";
            if(checkExistence(query2)) {
                System.out.println("Playlist does not exist!");
                return false;
            }
            
            //Check if content already exists in playlist
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.PCLOOKUP 
                    + " pc WHERE pc.PlaylistID ="
                    + " (SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST 
                    + " WHERE PlaylistName = '" + playlistName + "') AND"
                    + " ContentID = (SELECT ContentID FROM " + DBEnumeration.CONTENT
                    + " WHERE ContentName = '" + contentName + "' AND ContentTypeID ="
                    + " (SELECT ContentTypeID FROM ContentType WHERE ContentType = '" 
                    + contentType + "') AND CreatorID = (SELECT CreatorID FROM "
                    + DBEnumeration.CREATOR + " WHERE CreatorName = '" 
                    + creatorName + "'))";

            if(!checkExistence(checkQuery)) {
                System.out.println("Content already exists in that playlist");
                return false;
            }
            
            else {

                //Insert content into playlist
                String query = "INSERT INTO " + DBEnumeration.PCLOOKUP
                        + " (PlaylistID, ContentID) VALUES (?,?);"; 

                PreparedStatement prep = conn.prepareStatement(query);
                
                String getPlaylistID = "SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST
                        + " WHERE PlaylistName = '" + playlistName + "'";
                String getContentID = "SELECT ContentID FROM " + DBEnumeration.CONTENT
                        + " WHERE ContentName = '" + contentName + "' AND"
                        + " ContentTypeID = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                        + " WHERE ContentType = '" + contentType + "') AND CreatorID ="
                        + " (SELECT CreatorID FROM " + DBEnumeration.CREATOR 
                        + " WHERE CreatorName = '" + creatorName + "')";
                int playlistID = getKey(getPlaylistID);
                int contentID = getKey(getContentID);
               
                prep.setInt(1, playlistID);
                prep.setInt(2, contentID);
                
                if(SQLExecute(prep)) {
                    System.out.println("Content added to playlist successfully");
                    return true;
                }
                else {
                    System.out.println("Error with adding content to playlist");
                    return false;
                } 
            }
        }
        
        catch(SQLException  e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    public boolean addToPlaylist(Content content, Playlist playlist) {
        return addToPlaylist(content.getContentName(), content.getContentTypeName(), content.getCreatorName(), playlist.getPlaylistName());
    }
    
    
    /**
     * Adds a new publisher to DB.
     * @param publisherName 
     * @return  
     */
    public boolean addPublisher(String publisherName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.PUBLISHER 
                    + " WHERE PublisherName = '" + publisherName + "'";
            
            if(!checkExistence(checkQuery)) {
                System.out.println("Cannot add duplicate Publisher");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.PUBLISHER 
                    + "(PublisherName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, publisherName);

            if(SQLExecute(prep)) {
                System.out.println("Publisher added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new publisher");
                return false;
            }
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    /**
     * Adds a new Series to the DB.
     * @param seriesName 
     * @return  
     */
    public boolean addSeries(String seriesName) {
        
        try {
            if(conn == null) {
                getConnection();
            }        

            //Check if record already exists
            String checkQuery = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.SERIES + " WHERE SeriesName = '" 
                    + seriesName + "'";
            
            if(!checkExistence(checkQuery)) {
                System.out.println("Cannot add duplicate Series");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.SERIES 
                    + "(SeriesName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, seriesName);

            if(SQLExecute(prep)) {
                System.out.println("Series added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new series.");
                return false;
            }
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    

    /**
     * Deletes a specific piece of content from the content table.
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public boolean deleteContent(String contentName, String contentType, String creatorName, String filePath) {
        
        //Need to remove content from all playlists first
        deleteFromAllPlaylists(contentName, contentType, creatorName);
        
        String queryContent = DBEnumeration.CONTENT
                + " WHERE ContentName = '" + contentName + "' AND ContentTypeID = "
                + " (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE 
                + " WHERE ContentType = '" + contentType + "') AND"
                + " CreatorID = (SELECT CreatorID FROM " + DBEnumeration.CREATOR
                + " WHERE CreatorName = '" + creatorName + "')";
        
        String query = "DELETE FROM " + queryContent;
        
        if(deleteFromDB(query)) {
            System.out.println("Content deleted successfully");
            
            //Remove parent keys if the deleted content was the last content to have them as a foreign key.
            removeParentKey(DBEnumeration.GENRE, "GenreID");

            removeParentKey(DBEnumeration.PUBLISHER, "PublisherID");

            removeParentKey(DBEnumeration.SERIES, "SeriesID");

            removeParentKey(DBEnumeration.CREATOR, "CreatorID");

            removeParentKey(DBEnumeration.CONTENTTYPE, "ContentTypeID");
            
            
            //Remove file from directory and if directory is empty, delete directory
            try {
                Files.deleteIfExists(Paths.get(filePath));
            }
            
            catch(NoSuchFileException e) {
                System.out.println(e.getMessage());
            }
            
            catch(DirectoryNotEmptyException e) {
                System.out.println(e.getMessage());
            }
            
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
            
            System.out.println("File deleted.");
            
            return true;
        }

        else {
            System.out.println("Error with deleteing content");
            return false;
        }     
    }
    
    
    public boolean deleteContent(Content content) {
        return deleteContent(content.getContentName(), content.getContentTypeName(), content.getCreatorName(), content.getLocation());      
    }
 
    
    /**
     * Deletes a specific content type from the ContentType table.
     * Does it need to delete all content of that type as well? I'm not sure.
     * @param contentType
     * @return 
     */
    public boolean deleteContentType(String contentType) {
        String query = "DELETE FROM " + DBEnumeration.CONTENTTYPE
                + " WHERE ContentType = '" + contentType + "'";
        return deleteFromDB(query);
    }
    
    
    /**
     * Deletes a specific creator from the creator table. Does it need to 
     * delete all content associated with that creator? I'm inclined to yes.
     * @param creatorName
     * @return 
     */
    public boolean deleteCreator(String creatorName) {
        String query = "DELETE FROM " + DBEnumeration.CREATOR
                + " WHERE CreatorName = '" + creatorName + "'";
        return deleteFromDB(query);
    }
    
    
    /**
     * Deletes a genre from the genre table. Does it need to delete all content
     * of a given genre too? I'm inclined to no.
     * @param genreName
     * @return 
     */
    public boolean deleteGenre(String genreName) {
        String query = "DELETE FROM " + DBEnumeration.GENRE
                + " WHERE GenreName = '" + genreName + "'";
        return deleteFromDB(query);
    }
    
    
    /**
     * Deletes a play list from db.
     * @param playlistName
     * @return 
     */
    public boolean deletePlaylist(String playlistName) {
        //Delete all records in lookup table that have that playlist id
        String check = "DELETE FROM " + DBEnumeration.PCLOOKUP 
                + " WHERE PlaylistID = (SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST
                + " WHERE PlaylistName = '" + playlistName + "')";
        
        if(!deleteFromDB(check)) {
            System.out.println("Error with deleting playlist");
            return false;
        }
        
        else {
            String query = "DELETE FROM " + DBEnumeration.PLAYLIST
                    + " WHERE PlaylistName = '" + playlistName + "'";
            if(deleteFromDB(query)) {
                System.out.println("Playlist deleted successfully");
                return true;
            }
            
            else {
                System.out.println("Error with deleting playlist");
                return false;
            }
        }
    }
    
    
    public boolean deletePlaylist(int playlistID) {
        String check = "DELETE FROM " + DBEnumeration.PCLOOKUP
                +  " WHERE PlaylistID = " + playlistID;
        if(!deleteFromDB(check)) {
            System.out.println("Error with deleting playlist");
            return false;
        }
        
        else {
            String query = "DELETE FROM " + DBEnumeration.PLAYLIST
                    + " WHERE PlaylistID = " + playlistID;
            if(deleteFromDB(query)) {
                    System.out.println("Playlist deleted successfully");
                    return true;
            }
            
            else {
                System.out.println("Error with deleting playlist");
                return false;
            }
        }
    }
    
    /**
     * Deletes content from a specific play list
     * @param contentName
     * @param contentType
     * @param creatorName
     * @param playlistName
     * @return 
     */
    public boolean deleteFromPlaylist(String contentName, String contentType, String creatorName, String playlistName) {
        
        String query = "DELETE FROM " + DBEnumeration.PCLOOKUP + " WHERE"
                + " ContentID = (SELECT ContentID FROM " + DBEnumeration.CONTENT
                + " WHERE ContentName = '" + contentName + "' AND ContentTypeID"
                + " = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE 
                + " WHERE ContentType = '" + contentType + "') AND CreatorID ="
                + " (SELECT CreatorID FROM " + DBEnumeration.CREATOR + " WHERE"
                + " CreatorName = '" + creatorName + "')) AND PlaylistID ="
                + " (SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST + " WHERE"
                + " PlaylistName = '" + playlistName + "'";
        if(deleteFromDB(query)) {
            System.out.println("Content deleted from playlist: "  + playlistName);
            return true;
        }
        
        else {
            System.out.println("Error with deleting content from playlist: " + playlistName);
            return false;
        }
    }
    
    
    public boolean deleteFromPlaylist(int contentID, int playlistID) {
        String query  = "DELETE FROM " + DBEnumeration.PCLOOKUP + " WHERE"
                + " ContentID = " + contentID 
                + " AND PlaylistID = " + playlistID;
        
        if(deleteFromDB(query)) {
            System.out.println("Content deleted from playlist successfully");
            return true;
        }
        
        else {
            System.out.println("Error with deleting content from playlist");
            return false;
        }
    }
    
    
    public boolean deleteFromAllPlaylists(int contentID) {
        String query  = "DELETE FROM " + DBEnumeration.PCLOOKUP + " WHERE"
                + " ContentID = " + contentID;
        
        if(deleteFromDB(query)) {
            System.out.println("Content deleted from all playlists successfully");
            return true;
        }
        
        else {
            System.out.println("Error with deleting content from all playlists");
            return false;
        }
        
    }
    
    
    /**
     * Deletes content from all play lists it appears in.
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public boolean deleteFromAllPlaylists(String contentName, String contentType, String creatorName) {
        
        String query = "DELETE FROM " + DBEnumeration.PCLOOKUP + " WHERE"
                + " ContentID = (SELECT ContentID FROM " + DBEnumeration.CONTENT
                + " WHERE ContentName = '" + contentName + "' AND ContentTypeID"
                + " = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE 
                + " WHERE ContentType = '" + contentType + "') AND CreatorID ="
                + " (SELECT CreatorID FROM " + DBEnumeration.CREATOR + " WHERE"
                + " CreatorName = '" + creatorName + "'))";
        if(deleteFromDB(query)) {
            System.out.println("Content deleted from all playlists");
            return true;
        }
        
        else {
            System.out.println("Error with deleting content from all playlists");
            return false;
        }
    }
    
    
    /**
     * Deletes a publisher from the publisher table. Does it need to 
     * delete all content associated with a given publisher too?
     * I'm inclined to no.
     * @param publisherName
     * @return 
     */
    public boolean deletePublisher(String publisherName) {
        String query = "DELETE FROM " + DBEnumeration.PUBLISHER
                + " WHERE PublisherName = '" + publisherName + "'";
        return deleteFromDB(query);
    }
    
    
    /**
     * Delete a series from the series table. Needs to delete all content associated
     * with that series from the content table too. I'm inclined to yes.
     * I think an extra 
     * parameter obtained from user needs to specify this. 
     * @param seriesName
     * @return 
     */
    public boolean deleteSeries(String seriesName) {
        String query = "DELETE FROM " + DBEnumeration.SERIES
                + " WHERE SeriesName = '" + seriesName + "'";
        return deleteFromDB(query);
    }

    
    /**
     * All records requested from DB 
     * are put in a list of String arrays. Could be optimized better
     * but this prevents from other parts of the application being returned
     * SQL table objects. 
     * This at least turns everything back into a primitive data type that you
     * can work with.
     */
    
    
    /**
     * Gets All Content in the DB and displays all information associated with them.
     * Most likely need a Reflection Class to help with outputting.
     * @return 
     */
    public ResultSet getAllContent() {        
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Returns the Content Types the DB currently knows.
     * @return 
     */
    public ResultSet getAllContentTypes() {
        
        try {
            String query = "SELECT ContentType FROM " + DBEnumeration.CONTENTTYPE;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
        
    /**
     * This method displays all current creators held in db. 
     * 
     * @return res returns the set of all creators and the information associated
     * with them that was requested.
     */
    public ResultSet getAllCreators() {
        
        try {
            String query = "SELECT CreatorName FROM " + DBEnumeration.CREATOR;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
        
    
    /**
     * Gets all the genres that the DB knows about. 
     * @return gets all genres that the DB holds.
     */
    public ResultSet getAllGenres() {
        
        try {
            String query = "SELECT GenreName FROM " + DBEnumeration.GENRE;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
        
    
    /**
     * 
     * @return 
     */
    public ResultSet getAllPublishers() {
        
        try {
            String query = "SELECT PublisherName FROM Publisher";
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
        
   
    /**
     * Gets all series that the DB currently holds.
     * @return
     */
    public ResultSet getAllSeries() {
        
        try {
            String query = "SELECT SeriesName FROM " + DBEnumeration.SERIES;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets content given a creator name
     * @param creatorName
     * @return returns all content associated with one creator.
     */
    public ResultSet getContentByCreator(String creatorName) {
          
        try {
            String query = "SELECT ContentName FROM " + DBEnumeration.CONTENT  
                    + " WHERE CreatorID = (SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorName = '" + creatorName + "')";
            return getRecords(query);
        } 
        
        catch(SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * Gets content given a specific creator id.
     * @param creatorID
     * @return 
     */
    public ResultSet getContentByCreator(int creatorID) {
        
    try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT  
                    + " WHERE CreatorID = " + creatorID;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets a singular piece of content specified by its id
     * @param id
     * @return 
     */
    public ResultSet getContentByID(int id) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " WHERE ContentID = " + id;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        
        return null;
     }
    
    
    /**
     * Gets all content by a given genre.
     * @param genreName
     * @return returns all content of the given genre.
     */
    public ResultSet getContentByGenre(String genreName) {        
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT 
                    + " c WHERE c.GenreID = (SELECT GenreID FROM " + DBEnumeration.GENRE
                    + " WHERE GenreName = '" + genreName + "')";
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    public ResultSet getContentByGenre(int genreID) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " WHERE GenreID = " + genreID;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        
        return null;
    }
    
    
    /**
     * Gets a specific content determined by its name.
     * @param contentName
     * @return returns an individual piece of content.
     */
    public ResultSet getContentByName(String contentName) {
        
        try{
            String query = "SELECT * FROM Content c WHERE c.ContentName = '" 
                    + contentName + "'"; 
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * 
     * @param contentName
     * @param creatorName
     * @return 
     */
    public ResultSet getContentByNameAndCreator(String contentName, String creatorName) {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " WHERE CreatorID = (SELECT CreatorID FROM "
                    + DBEnumeration.CREATOR + " WHERE CreatorName = '" 
                    + creatorName + "') AND ContentName = '" + contentName + "'";     
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //DefaultValue
        return null;
    }
    
    
    /**
     * Gets content associated with a publisher.
     * @param publisherName
     * @return returns all content by a given publisher.
     */
    public ResultSet getContentByPublisher(String publisherName) {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " c WHERE c.PublisherID = (SELECT PublisherID FROM " + DBEnumeration.PUBLISHER
                    + " WHERE PublisherName = '" + publisherName + "')";
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    public ResultSet getContentByPublisher(int publisherID) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " WHERE PublisherID = " + publisherID;
            
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets all content in DB that is a part of a specific series. 
     * @param seriesName
     * @return returns all content associated with a given series. 
     */
    public ResultSet getContentBySeries(String seriesName) {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT 
                    + " c WHERE SeriesID = (SELECT SeriesID FROM " + DBEnumeration.SERIES
                    + " WHERE SeriesName = '" + seriesName + "')";
                    
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets all content of a specific series number
     * @param seriesID
     * @return 
     */
    public ResultSet getContentBySeries(int seriesID) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT 
                    + " WHERE SeriesID = " + seriesID;
            
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets all content given a content type name.
     * @param contentType 
     * @return returns all content by a given type.
     */
    public ResultSet getContentByType(String contentType) {
        
        try {
            String query = "SELECT ContentName FROM " + DBEnumeration.CONTENT
                    + " c WHERE c.ContentTypeID = (SELECT ContentTypeID FROM "
                    + DBEnumeration.CONTENTTYPE + " WHERE ContentType = '" 
                    + contentType + "')";
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets all content of a given content type by id
     * @param contentTypeID
     * @return 
     */
    public ResultSet getContentByType(int contentTypeID) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT
                    + " WHERE ContentTypeID = " + contentTypeID;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets a specific content type from the db. 
     * @param contentTypeID
     * @return
     */
    public int getContentTypeID(String contentTypeID) {
        
            String query = "SELECT ContentTypeID FROM ContentType "
                    + "WHERE ContentType = '" + contentTypeID + "'";
            return getKey(query);

    }
    
    
    public String getContentTypeName(int contentTypeID) {
        try {
            String query = "SELECT ContentType FROM  " + DBEnumeration.CONTENTTYPE
                + " WHERE ContentTypeID = " + contentTypeID;
            return getRecords(query).getString(1);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
  }
   
       
    /**
     * Gets the content id of a content given its name, creator, and its type
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public int getContentID(String contentName, String contentType, String creatorName) {
        String query = "SELECT ContentID FROM " + DBEnumeration.CONTENT
                + " WHERE ContentName = '" + contentName + "' AND ContentTypeID = "
                + "(SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE + " WHERE ContentType = '" + contentType 
                + "') AND CreatorID = (SELECT CreatorID FROM " + DBEnumeration.CREATOR + " WHERE CreatorName = '" + creatorName + "')";
        return getKey(query);
    }
       
    
    public int getCreatorID(String creatorName) {
            String query = "SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorName = '" + creatorName + "'";
            return getKey(query);
        
    }
    
    
    public String getCreatorName(int creatorID) {
        try {
            String query = "SELECT CreatorName FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorID = " + creatorID;
            return getRecords(query).getString(1);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }


    /**
     * Gets the count of the number of content created by a specific author
     * @param creatorName
     * @return 
     */
    public int getCreatorCount(String creatorName) {
        
        try {
            String query = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.CREATOR 
                    + " cr on c.CreatorID = cr.CreatorID WHERE cr.CreatorName = '"
                    + creatorName + "'";
            return getCount(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default value
        return 0;
    }
    
    
    /**
     * Retrieves the genre id given a name
     * @param genreName
     * @return 
     */
    public int getGenreID(String genreName) {
        String query = "SELECT GenreID FROM " + DBEnumeration.GENRE
            + " WHERE GenreName = '" + genreName + "'";
        return getKey(query);
    }
    
    
    public String getGenreName(int genreID) {
        try {
            String query = "SELECT GenreName FROM " + DBEnumeration.GENRE 
                + " WHERE GenreID = " + genreID;
            return getRecords(query).getString(1);
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    
    /**
     * Method that returns a count of the number of entries of a certain genre
     * Hopefully useful for the recommendation class
     * @param genreName
     * @return 
     */
    public int getGenreCount(String genreName) {
        
        try {
            String query = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.GENRE 
                    + " g on c.GenreID = g.GenreID WHERE g.GenreName = '"
                    + genreName + "'";
            return getCount(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return 0;
    }
    
    
    /**
     * Returns a playlist from the Playlist-ContentLookup table that actually
     * carries the records of what content is in what playlist
     * @param playlistID
     * @return 
     */
    public ResultSet getPlaylist(int playlistID) {
        try {
            String query = "SELECT * FROM " + DBEnumeration.PCLOOKUP
                    + " WHERE PlaylistID = " + playlistID;
            return getRecords(query);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets a specific publisher by name
     * @param publisherID
     * @return 
     */
    public String getPublisherName(int publisherID) {
        
        try {
            String query = "SELECT PublisherName FROM Publisher"
                    + " WHERE PublisherID = '" + publisherID + "'";
            return getRecords(query).getString(1);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Returns the publisher id given a specific name
     * @param publisherName
     * @return 
     */
    public int getPublisherID(String publisherName) {
        String query = "SELECT PublisherName FROM " + DBEnumeration.PUBLISHER
                + " WHERE PublisherName = '" + publisherName + "'";
        return getKey(query);
    }

    
    
    
    /**
     * Gets the number of content published by a given publisher.
     * @param publisherName
     * @return 
     */
    public int getPublisherCount(String publisherName) {
        
        try {
            String query = "SELECT COUNT(*) AS " + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.PUBLISHER
                    + " p on c.PublisherID = p.PublisherID WHERE p.PublisherName = '"
                    + publisherName + "'";
            return getCount(getRecords(query));
        } 
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());  
        }
        
        return 0;
    }
    
    
    public String getSeriesName(int seriesID) {
        try {
            String query = "SELECT SeriesName FROM " + DBEnumeration.SERIES
                    + " WHERE SeriesID = " + seriesID;
            return getRecords(query).getString(1);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    public int getSeriesID(String seriesName) {
        String query = "SELECT SeriesID FROM " + DBEnumeration.SERIES
                + " WHERE SeriesName = '" + seriesName + "'";
        return getKey(query);
    }
    
    
    /**
     * Returns the number of content that belong to a specific series.
     * @param seriesName
     * @return 
     */
    public int getSeriesCount(String seriesName) {
        
        try {
            String query = "SELECT COUNT(*)" + DBEnumeration.COUNT 
                    + " FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.SERIES
                    + " s on c.SeriesID = s.SeriesID WHERE s.SeriesName = '"
                    + seriesName + "'";
            return getCount(getRecords(query));
        } 
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());  
        }
        
        return 0;
    }
    
    
    /**
     * Set the sync status of a particular piece of content to true.
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public boolean setSyncStatus(String contentName, String contentType, String creatorName) {
        
        try {
            String query = "UPDATE " + DBEnumeration.CONTENT
                    + " SET WantToSync = TRUE WHERE"
                    + " ContentName = '" + contentName + "' AND"
                    + " ContentTypeID = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                    + " WHERE ContentType = '" + contentType + "') AND"
                    + " CreatorID = (SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorName = '" + creatorName + "')";
            PreparedStatement prep = conn.prepareStatement(query);
            if(SQLExecute(prep)) {
                System.out.println("Set content to sync");
                return true;
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
        return false;
    }
    
    
    /**
     * Sets a sync status for a piece of content to true(i.e they want to sync it)
     * @param contentId
     * @return 
     */
    public boolean setSyncStatus(int contentId) {
        try {
            String query  = "UPDATE " + DBEnumeration.CONTENT
                    + " SET WantToSync = TRUE WHERE"
                    + " ContentID = " + contentId;
            PreparedStatement prep = conn.prepareCall(query);
            if(SQLExecute(prep)) {
                System.out.println("Set content to sync");
                return true;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
 
    /**
     * Set the sync status of a particular piece of content to false
     * @param contentName
     * @param contentType
     * @param creatorName
     * @return 
     */
    public boolean unsetSyncStatus(String contentName, String contentType, String creatorName) {
        try {
            String query = "UPDATE " + DBEnumeration.CONTENT
                    + " SET WantToSync = FALSE WHERE "
                    + " ContentName = '" + contentName + "' AND"
                    + " ContentTypeID = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                    + " WHERE ContentType = '" + contentType + "') AND"
                    + " CreatorID = (SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE CreatorName = '" + creatorName + "')";
            PreparedStatement prep = conn.prepareStatement(query);
            if(SQLExecute(prep)) {
                System.out.println("Set content to not sync");
                return true;
            }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        
        return false;
    }
    
    
    /**
     * Sets a sync status for a piece of content to false(i.e they don't want to sync it)
     * @param contentId
     * @return 
     */
    public boolean unsetSyncStatus(int contentId) {
        try {
            String query  = "UPDATE " + DBEnumeration.CONTENT
                    + " SET WantToSync = FALSE WHERE"
                    + " ContentID = " + contentId;
            PreparedStatement prep = conn.prepareCall(query);
            if(SQLExecute(prep)) {
                System.out.println("Set content to not sync");
                return true;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    
    /**
     * An attempt to translate the return values of SQL code into primitives the application can use.
     * @param res
     * @return 
     */
    public static List<String[]> SQLToPrimitives(ResultSet res) {
        
        try {
            int nCol = res.getMetaData().getColumnCount();
            List<String[]> table = new ArrayList<>();
            while(res.next()) {
                String[] row = new String[nCol];
                for(int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = res.getObject(iCol);
                    row[iCol-1] = (obj == null) ? null:obj.toString();
                }
                table.add(row);
            }
            return table;
        }
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Updates a specific piece of content. Could be used when something might
     * become a favorite. Still need to work on that functionality. 
     * @return 
     */
    public boolean updateContent() {
        return false;
    }
    
    
    /**
     * Updates a specific content type.
     * @return 
     */
    public boolean updateContentType() {
        return false;
    }
    
    
    /**
     * Updates info about a creator.
     * @return 
     */
    public boolean updateCreator() {
        return false;
    }
    
    
    /**
     * Updates info about a genre.
     * @return 
     */
    public boolean updateGenre() {
        return false;
    }
    
    
    /**
     * 
     * @return 
     */
    public boolean updatePlaylist() {
        return false;
    }
    
    
    /**
     * Updates info about a publisher. 
     * @return 
     */
    public boolean updatePublisher() {
        return false;
    }
    
    
    /**
     * Updates info about a series. 
     * @return 
     */
    public boolean updateSeries() {
        return false;
    }
    
    
    
    /**
     * 
     * This method establishes the connection to the db. Still need to add
     * one that closes said connection.
     */
    public void getConnection() {
        
        /**
         * This part is necessary. Specifies the library that allows Java to 
         * work with SQLite
         */
        
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbLocationPath);
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    /**
     * Close connection stream to DB File. 
     */
    public void closeConnection() {
        if(conn != null) {
            try{
                conn.close();
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    
    /*
     * Checks if a record exists in DB. Returns true if no record exists.
     * @param query
     * @return 
     */
    private boolean checkExistence(String query) {
        int count = 0;
        
        try {
            ResultSet res = getRecords(query);
            if(res.next()) {
                count = res.getInt("total");
            }
            
            //Content does exist if count not equal to zero
            if(count != 0) {
                //System.out.println("Entry already exists, cannot add");
                return false;
            }
            
            //Content does not exist, continue with adding
            else {
                return true;
            }
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return false;
    }
    
    
    /**
     * Cleans the content name of special characters
     * @param name
     * @return 
     */
    private static String cleanString(String name) {
        
            if (name.contains("!")) {
                name = name.replace("!", "");
            }
            if (name.contains("/")) {
                name = name.replace("/", "");
            }
            if (name.contains("\\")) {
                name = name.replace("\\", "");
            }
            if (name.contains("?")) {
                name = name.replace("?", "");
            }
            if (name.contains("%")) {
                name = name.replace("%", "");
            }
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            if (name.contains(":")) {
                name = name.replace(":", "");
            }
            if (name.contains("|")) {
                name = name.replace("|", "");
            }
            if (name.contains("\"")) {
                name = name.replace("\"", "");
            }
            if (name.contains("<")) {
                name = name.replace(">", "");
            }
            if (name.contains(">")) {
                name = name.replace(">", "");
            }
            if (name.contains(".")) {
                name = name.replace(".", "");
            }
            if (name.contains(" ")) {
                name = name.replace(" ", "_");
            }
            
            return name;
    }
    
    
    /**
     * Executes the delete query
     * @param query
     * @return 
     */
    private boolean deleteFromDB(String query) {
       if(conn == null) {
           getConnection();
       }

       try {
           PreparedStatement prep = conn.prepareStatement(query);
           prep.executeUpdate();
           return true;
       }

       catch(SQLException e) {
           System.out.println(e.getMessage());
           return false;
       }       
    }
    
    
    /**
     * Helper method for all the getCount methods that queries the db. 
     * @param res
     * @return 
     */
    private int getCount(ResultSet res) {
        
        try {
            return res.getInt(DBEnumeration.COUNT);
        }
        
        catch(SQLException e) {
            e.getMessage();
        }
        
        //Default Value
        return 0;
    }
    
    
    /**
     * private method that each getter can use to retrieve records
     * @param query
     * @return res
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private ResultSet getRecords(String query) throws SQLException, ClassNotFoundException {
        
        if(conn == null) {
            getConnection();
        }
        
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        return res;
    }
    
    
    /**
     * Returns the key of a given record. 
     * @param query
     * @return 
     */
    private int getKey(String query) {

        try {
            ResultSet res = getRecords(query);
            return res.getInt(1);
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    
    /*
     * This method will remove all parent keys. Say you delete a piece of content and
     * that was the last piece of content of a specific genre. This method
     * is designed to remove that genre entity from the genre table. 
     * Will take some work. 
     */
    
    /**
     * Remove extraneous records from dimension tables if no piece of content
     * uses them as a foreign key.
     * @param tableName
     * @param columnName
     * @return 
     */
    private boolean removeParentKey(String tableName, String columnName) {
        String removeColumn = "DELETE FROM " + tableName + 
                " WHERE " + columnName + " NOT IN (SELECT " + columnName + " FROM " 
                + DBEnumeration.CONTENT + ")";
        return deleteFromDB(removeColumn);
    }
    
 
    /**
     * Checks foreign key value for content to see if something already exists as 
     * as given content type.
     * @param query
     * @pram table
     * @return 
     */
    private int SQLCheckForeignKeyRecord(String query, String table) throws SQLException, ClassNotFoundException {
        
        if(conn == null) {
            getConnection();
        }
        
        //Sentinel value that signifies a record does not exist in the DB. 
        int id = DBEnumeration.SENTINEL;
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        
        if(res.next()) {
        //Gets the id dependent on what field is requested. 
            switch(table) {
                case DBEnumeration.CONTENT : 
                    id = res.getInt("ContentID");
                    break;

                case DBEnumeration.CONTENTTYPE :
                    id = res.getInt("ContentTypeID");
                    break;

                case DBEnumeration.CREATOR : 
                    id = res.getInt("CreatorID");
                    break;

                case DBEnumeration.GENRE :
                    id = res.getInt("GenreID");
                    break;   

                case DBEnumeration.PUBLISHER :
                    id = res.getInt("PublisherID");   
                    break;    

                case DBEnumeration.SERIES :
                    id = res.getInt("SeriesID");
                    break;    
            }
        }
        
        return id;
    }
    
    
    /**
     * Attempts to execute a SQL Statement to add stuff to DB
     * @param prep
     * @return 
     */
    private boolean SQLExecute(PreparedStatement prep) {
        
        boolean successful = false;
        
        try {
            prep.execute();
            successful = true;
        } 
        
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } 
        
        return successful;
    }
    
    
       

    
    /**
     * Sets a file path for a new piece of content. Needs to create a new 
     * directory if needed(possibly for a series, music/photo album, etc.)
     * Content should be divided into directories of that content's type. Then
     * further divided by series. If no series, go into folder called unlisted.
     * Then in their series folder they'll exist as accessible files. 
     * Alternatively, we could just divide by series, no need to divide by type.
     * @param contentName 
     * @param series
     * @param contentType
     */
    private String setContentLocation (String contentName, String contentType, String genreName, String seriesName) {  
        String location = DBEnumeration.UNKNOWN;
        
        //Genre name given
        if(!genreName.equals(DBEnumeration.UNKNOWN)) {
                //Series name given                
                if(!seriesName.equals(DBEnumeration.UNKNOWN)) {
                    location = DBEnumeration.PROJECTDIRECTORY 
                            + contentType + "/" + genreName
                            + "/" + seriesName + "/";
                    }

                //No series name given
                else {
                    location = DBEnumeration.PROJECTDIRECTORY 
                            + contentType + "/" + genreName + "/" + DBEnumeration.UNKNOWN
                            + "/";

                }
            }

            //No genre name given
            else {
                //Series name given
                if(!seriesName.equals(DBEnumeration.UNKNOWN)) {
                    location = DBEnumeration.PROJECTDIRECTORY 
                            + contentType + "/" + DBEnumeration.UNKNOWN
                            + "/" + seriesName + "/";
                }

                //No series name given
                else {
                    location = DBEnumeration.PROJECTDIRECTORY 
                            + contentType + "/" + DBEnumeration.UNKNOWN
                            + "/" + DBEnumeration.UNKNOWN + "/";
                }
            }
        
        return location;
    }
    
    
    
    /**
     * Get file extension for file path given in addContent
     * @param filePath
     * @return 
     */
    private String getExtension(String filePath) {
        String ext = FilenameUtils.getExtension(filePath);
        System.out.println(ext);
        return ext;
    }
    
    
    private String getPathToDB() {
        
        final String constant = "jdbc:sqlite:";
        String workingDirectory = System.getProperty("user.dir");
        File file = new File(workingDirectory);
        String parentDirectory = file.getParent();
        File file2 = new File(parentDirectory);
        String databaseDirectory = file2.getParent() + "\\Database\\PACDB.db";
        
        return constant + databaseDirectory;
    }
    
    
    
    public ResultSet getContentTest(String contentName, String contentType, String creatorName) {
        String query = "SELECT * FROM " + DBEnumeration.CONTENT
                + " WHERE ContentName = '" + contentName + "' AND"
                + " ContentTypeID = (SELECT ContentTypeID FROM "
                + DBEnumeration.CONTENTTYPE + " WHERE ContentType = '"
                + contentType + "') AND CreatorID = (SELECT CreatorID FROM "
                + DBEnumeration.CREATOR + " WHERE CreatorName = '" + creatorName
                + "')";
        try {
            return getRecords(query);
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public boolean addContent(String filePath, String contentType) {
        String ext = getExtension(filePath);
        Content content = null;
        switch(ext){
            case "mp3": 
                content = MetaDataReader.mp3Reader(contentType, filePath);
                break;
            case "epub":
                content = MetaDataReader.ePubReader(filePath);
        }

        return addContent(contentType, content.getCreatorName(), content.getGenreName(),
                content.getPublisherName(), content.getSeriesName(), content.getContentName(),
                content.getContentDescription(), content.getUploadDate(), content.getPageCount(),
                content.getDuration(), content.getIsbn(), content.isExplicit(), null,
                content.getUrl(), content.getWantToSync(), filePath);
    }
}