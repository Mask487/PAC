package Database;

import java.sql.*;
import Interfaces.DBInterface;
import Util.DBEnumeration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Oleson
 * 
 * @update 3/14/2019
 * 
 * Translator class that actually speaks to the DB File.
 */

//@TODO How does actual content file fit into folder that DB creates for it
//@TODO Create directories for new content being added in.
public class SQLTranslator implements DBInterface{
    
    //Establishes connection to db file.
    private static Connection conn;
    
    /**
     * The jdbc:sqlite: part is permanent. The part after that specifies
     * the filepath. There needs to be a way to specify the filepath 
     * in relation to where this file is in the project directory.
     * For now just specify where it is on your own machine.
     */
    private final String dbLocationPath = "jdbc:sqlite:C:/PAC/Database/PACDB.db";
   
    
    /**
     * Adds a new piece of content to DB. Desperately needs to be refactored down.
     * You must give all parameters but if you do not have information to give, just put null.
     * Giving null will allow for info to be stored as null and set the foreign keys
     * to an UNKNOWN value that should be pre-populated in the DB 
     * @param contentType
     * @param syncStatusType
     * @param firstName
     * @param middleName
     * @param lastName
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
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public boolean addContent(String contentType, String syncStatusType, 
            String firstName, String middleName, String lastName, 
            String genreName, String publisherName, String seriesName, 
            String contentName, String contentDescription, String uploadDate,
            int pageCount, String duration, String isbn, boolean explicit, 
            String location, String url) throws SQLException, ClassNotFoundException {
        
        try {
            if(conn == null) {
                getConnection();
            }

            if(contentType == null) {
                contentType = DBEnumeration.UNKNOWN;
            }
            if(syncStatusType == null){
                syncStatusType = DBEnumeration.UNKNOWN;
            }
            if(firstName == null) {
                firstName = DBEnumeration.UNKNOWN;
            }
            if(middleName == null) {
                middleName = DBEnumeration.UNKNOWN;
            }
            if(lastName == null) {
                lastName = DBEnumeration.UNKNOWN;
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
            if(contentDescription == null) {
                contentDescription = DBEnumeration.UNKNOWN;
            }
            //Upload Date, Page Count, Duration, ISBN and Exlicit can remain UNKNOWN
            if(location == null) {
                setContentLocation(contentName, seriesName, contentType);
            }
            //Check if attributes of content exist by querying relevant tables
            String queryContentType = "SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                    + " WHERE ContentType = '" + contentType + "'";
            String querySyncStatus = "SELECT SyncStatusID FROM " + DBEnumeration.SYNCSTATUS
                    + " WHERE SyncStatusDescription = '" + syncStatusType + "'";
            String queryCreator = "SELECT CreatorID FROM " + DBEnumeration.CREATOR
                    + " WHERE FirstName = '" + firstName + "' AND MiddleName = '" 
                    + middleName + "' AND LastName = '" + lastName + "'";
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

            int syncStatusID = SQLCheckForeignKeyRecord(querySyncStatus, DBEnumeration.SYNCSTATUS);
            if(syncStatusID == DBEnumeration.SENTINEL) {
                addSyncStatus(syncStatusType);
                syncStatusID = SQLCheckForeignKeyRecord(querySyncStatus, DBEnumeration.SYNCSTATUS);
            }

            int creatorID = SQLCheckForeignKeyRecord(queryCreator, DBEnumeration.CREATOR);
            if(creatorID == DBEnumeration.SENTINEL) {
                addCreator(firstName, middleName, lastName);
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

            //Check if a piece of content already exists as a given type. If it does, don't add it.
            String queryCount = "SELECT COUNT(*) AS total FROM " + DBEnumeration.CONTENT
                    + " c JOIN ContentType ct on c.ContentTypeID = ct.contentTypeID"
                    + " WHERE c.ContentName = '" + contentName + "' AND ct.contentTypeID = "
                    + "(" + queryContentType + ")";

            int count = 0;
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(queryCount);
            while(rs2.next()) {
                count = rs2.getInt("total");
            }

            /**
             * Count here denotes the amount of content with a specific name as a given type.
             * If harry potter already exists as an ebook, I don't want to add it as
             * an ebook again. That's what this prevents. If I have it as an ebook
             * and I want to add it as an audiobook count will return 0 and we can
             * continue with adding the audio book.
             */ 
            if(count != 0) {
                System.out.println("Content already exists");
                return false;
            }

            String query = "INSERT INTO " + DBEnumeration.CONTENT 
                    + "(ContentTypeID, SyncStatusID, CreatorID, GenreID, PublisherID"
                    + ", SeriesID, ContentName, ContentDescription, UploadDate, "
                    + "PageCount, Duration, ISBN, Explicit, Location, DownloadURL)" 
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, contentTypeID);
            prep.setInt(2, syncStatusID);
            prep.setInt(3, creatorID);
            prep.setInt(4, genreID);
            prep.setInt(5, publisherID);
            prep.setInt(6, seriesID);
            prep.setString(7, contentName);
            prep.setString(8, contentDescription);
            prep.setString(9, uploadDate);
            prep.setInt(10, pageCount);
            prep.setString(11, duration);
            prep.setString(12, isbn);
            prep.setBoolean(13, explicit);
            prep.setString(14, location);
            prep.setString(15, url);

            //Check if content already exists. If it doesn't, add it. 

            if(SQLInsert(prep)) {
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
    @Override
    public boolean addContentType(String contentTypeName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.CONTENTTYPE + " ct WHERE ct.ContentType = '" 
                    + contentTypeName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("ContentType already exists, cannot add");
                return false;
            }

            //Insert record into DB
            String insertQuery = "INSERT INTO " + DBEnumeration.CONTENTTYPE + "(ContentType)"
                    + " VALUES(?);";

            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, contentTypeName);

            if(SQLInsert(prep)) {
                System.out.println("ContentType added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new content type");
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
     * This method adds a new creator to the creator table. 
     * 
     * @param firstName 
     * @param middleName
     * @param lastName
     * @return 
     */
    @Override
    public boolean addCreator(String firstName, String middleName, String lastName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.CREATOR + " cr WHERE cr.FirstName = '" 
                    + firstName + "' AND cr.MiddleName = '" + middleName 
                    + "' AND cr.LastName = '" + lastName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Creator already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.CREATOR + "(FirstName, MiddleName, LastName)"
                    + " VALUES(?,?,?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, firstName);
            prep.setString(2, middleName);
            prep.setString(3, lastName);

            if(SQLInsert(prep)) {
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
    @Override
    public boolean addGenre(String genreName) {
        
        try {         
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.GENRE + " g WHERE g.GenreName = '" 
                    + genreName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Genre already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.GENRE + "(GenreName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, genreName);

            if(SQLInsert(prep)) {
                System.out.println("Genre added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new genre");
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
     * 
     * @param playlistName
     * @return 
     */
    @Override
    public boolean addPlaylist(String playlistName) {
        
        try {       
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.PLAYLIST + " pl WHERE pl.PlaylistName = '" 
                    + playlistName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Playlist already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.PLAYLIST 
                    + "(PlaylistName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, playlistName);

            if(SQLInsert(prep)) {
                System.out.println("New playlist added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new playlist");
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
     * Adds a new piece of content, of a given type, to a playlist. 
     * @param contentName
     * @param contentType
     * @param playlistName
     * @return 
     */
    @Override
    public boolean addToPlaylist(String contentName, String contentType, String playlistName) {
        
        try {    
            if(conn == null) {
                getConnection();
            }
            
            //Check if content exists
            String query1 = "SELECT ContentID FROM " + DBEnumeration.CONTENT 
                    + " WHERE ContentName = '" + contentName 
                    + " AND ContentTypeID = (SELECT ContentTypeID FROM " 
                    + DBEnumeration.CONTENTTYPE + " WHERE ContentType = '"
                    + contentType + "')";
            
            if(!getRecords(query1).next()) {
                System.out.println("Content does not exist!");
                return false;
            }
            
            String query2 = "SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST
                    + " WHERE PlaylistName = '" + playlistName + "'";
            if(!getRecords(query2).next()) {
                System.out.println("Playlist does not exist!");
                return false;
            }
            
            //Check if content already exists in playlist
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.PCLOOKUP + " pc WHERE pc.PlaylistID ="
                    + " (SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST 
                    + " WHERE PlaylistName = '" + playlistName + "') AND"
                    + " ContentID = (SELECT ContentID FROM " + DBEnumeration.CONTENT
                    + " WHERE ContentName = '" + contentName + "' AND ContentTypeID ="
                    + " (SELECT ContentTypeID FROM ContentType WHERE ContentType = '" 
                    + contentType + "'))";

            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Content already exists in playlist, cannot add");
                return false;
            }

            //Insert content into playlist
            String query = "INSERT INTO " + DBEnumeration.PCLOOKUP
                    + " (PlaylistID, ContentID)"
                    + " VALUES ((SELECT PlaylistID FROM " + DBEnumeration.PLAYLIST
                    + " WHERE PlaylistName = '" + playlistName + "'),"
                    + " (SELECT ContentID FROM " + DBEnumeration.CONTENT 
                    + " WHERE ContentName = '" + contentName + "' AND"
                    + " ContentTypeID = (SELECT ContentTypeID FROM " + DBEnumeration.CONTENTTYPE
                    + " WHERE ContentType = '" + contentType + "')))";

            PreparedStatement prep = conn.prepareStatement(query);

            if(SQLInsert(prep)) {
                System.out.println("Content added to playlist successfully");
                return true;
            }
            else {
                System.out.println("Error with adding content to playlist");
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
     * Adds a new publisher to DB.
     * @param publisherName 
     * @return  
     */
    @Override
    public boolean addPublisher(String publisherName) {
        
        try {
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.PUBLISHER + " p WHERE p.PublisherName = '" 
                    + publisherName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Publisher already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.PUBLISHER + "(PublisherName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, publisherName);

            if(SQLInsert(prep)) {
                System.out.println("Publisher added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new publisher");
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
     * Adds a new Series to the DB.
     * @param seriesName 
     * @return  
     */
    @Override
    public boolean addSeries(String seriesName) {
        
        try {
            if(conn == null) {
                getConnection();
            }        

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.SERIES + " s WHERE s.SeriesName = '" 
                    + seriesName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Series already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.SERIES + "(SeriesName)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, seriesName);

            if(SQLInsert(prep)) {
                System.out.println("Series added successfully");
                return true;
            }
            else {
                System.out.println("Error with adding new series.");
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
     * Adds a new kind of status to sync. 
     * @param syncName 
     * @return   
     */
    @Override
    public boolean addSyncStatus(String syncName) {
        
        try {
            
            if(conn == null) {
                getConnection();
            }

            //Check if record already exists
            int count = 0;
            String checkQuery = "SELECT COUNT(*) AS total FROM " 
                    + DBEnumeration.SYNCSTATUS + " sy WHERE sy.SyncStatusDescription = '" 
                    + syncName + "'";
            ResultSet res = getRecords(checkQuery);
            if(res.next()) {
                count = res.getInt("total");
            }

            if(count != 0) {
                System.out.println("Sync Status already exists, cannot add");
                return false;
            }

            //Insert record into DB.
            String insertQuery = "INSERT INTO " + DBEnumeration.SYNCSTATUS + "(SyncStatusDescription)"
                    + " VALUES(?);";
            PreparedStatement prep = conn.prepareStatement(insertQuery);
            prep.setString(1, syncName);

            if(SQLInsert(prep)) {
                System.out.println("Sync Status added successfully");
                return true;
            } 
            else {
                System.out.println("Error with adding new Sync Status");
                return false;
            }
        }
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    
    
    
    /********
     * IMPORTANT: All tables aside from content are set to cascade delete.
     * That means if one of them is deleted, all content that had that given key
     * will be deleted too!! Need to discuss if that is best. Might be good for some 
     * instances and not others(Good for series, bad for genres).
     ********/
    
    /**
     * Deletes a specific piece of content from the content table.
     * DB handles cascade deletes so if a piece of content is the only 
     * @return 
     */
    @Override
    public boolean deleteContent() {
        return false;
    }
    
    
    /**
     * Deletes a specific content type from the ContentType table.
     * Does it need to delete all content of that type as well? I'm not sure. 
     * @return 
     */
    @Override
    public boolean deleteContentType() {
        return false;
    }
    
    
    /**
     * Deletes a specific creator from the creator table. Does it need to 
     * delete all content associated with that creator? I'm inclined to yes. 
     * @return 
     */
    @Override
    public boolean deleteCreator() {
        return false;
    }
    
    
    /**
     * Deletes a genre from the genre table. Does it need to delete all content
     * of a given genre too? I'm inclined to no.
     * @return 
     */
    @Override
    public boolean deleteGenre() {
        return false;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean deletePlaylist() {
        return false;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean deleteFromPlaylist() {
        return false;
    }
    
    
    /**
     * Deletes a publisher from the publisher table. Does it need to 
     * delete all content associated with a given publisher too?
     * I'm inclined to no.
     * @return 
     */
    @Override
    public boolean deletePublisher() {
        return false;
    }
    
    
    /**
     * Delete a series from the series table. Needs to delete all content associated
     * with that series from the content table too. I'm inclined to yes.
     * I think an extra 
     * parameter obtained from user needs to specify this. 
     * @return 
     */
    @Override
    public boolean deleteSeries() {
        return false;
    }
    
    
    /**
     * delete a specific type of sync status. Maybe status for a record is no longer supported
     * or its been de-synched?
     * @return 
     */
    @Override
    public boolean deleteSyncStatus() {
        return false;
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
    @Override
    public List<String[]> getAllContent() {        
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENT;
            return SQLToPrimitives(getRecords(query));
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
    @Override
    public List<String[]> getAllContentTypes() {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CONTENTTYPE;
            return SQLToPrimitives(getRecords(query));
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
    @Override
    public List<String[]> getAllCreators() {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.CREATOR;
            return SQLToPrimitives(getRecords(query));
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
    @Override
    public List<String[]> getAllGenres() {
        
        try {
            String query = "SELECT * FROM " + DBEnumeration.GENRE;
            return SQLToPrimitives(getRecords(query));
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
    @Override
    public List<String[]> getAllPublishers() {
        
        try {
            String query = "SELECT * FROM Publisher";
            return SQLToPrimitives(getRecords(query));
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
    @Override
    public List<String[]> getAllSeries() {
        
        try {
            String query = "SELECT * FROM Series";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
        
    
    /**
     * Gets all sync statuses from the DB. Don't know why this would be used but just in case. 
     * @return
     */
    @Override
    public List<String[]> getAllSyncStatus() {
       
        try {
            String query = "SELECT * FROM SyncStatus";
            return SQLToPrimitives(getRecords(query));
        }
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets content determined by their creator.
     * @param _firstName Cannot be null.
     * @param _middleName Can be null.
     * @param _lastName Cannot be null.
     * @return returns all content associated with one creator.
     */
    @Override
    public List<String[]> getContentByCreator(String _firstName, String _middleName, String _lastName) {
        
        try {
            String query = "SELECT * FROM Content c JOIN "
                    + "Creator a on c.CreatorID = a.CreatorID WHERE "
                    + "a.ContentCreatorID = (SELECT CreatorID FROM Creator WHERE "
                    + "FirstName = '" + _firstName + "' AND MiddleName = '" 
                    + _middleName + "' AND LastName = '" + _lastName + "')";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets all content by a given genre.
     * @param _genreName
     * @return returns all content of the given genre.
     */
    @Override
    public List<String[]> getContentByGenre(String _genreName) {        
        
        try {
            String query = "SELECT * FROM Content c JOIN "
                    + "Genre g on c.GenreID = g.GenreID "
                    + "WHERE g.GenreName = '" + _genreName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets a specific content determined by its name.
     * @param contentName
     * @return returns an individual piece of content.
     */
    @Override
    public List<String[]> getContentByName(String contentName) {
        
        try{
            String query = "SELECT c.ContentID FROM Content c WHERE c.ContentName = '" + contentName + "'"; 
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets content associated with a publisher.
     * @param publisherName
     * @return returns all content by a given publisher.
     */
    @Override
    public List<String[]> getContentByPublisher(String publisherName) {
        
        try {
            String query = "SELECT c.ContentID FROM Content c JOIN "
                    + "Publisher p on c.PublisherID = p.PublisherID "
                    + "WHERE p.PublisherName = '" + publisherName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets all content in DB that is a part of a specific series. 
     * @param seriesName
     * @return returns all content associated with a given series. 
     */
    @Override
    public List<String[]> getContentBySeries(String seriesName) {
        
        try {
            String query = "SELECT * FROM Content c "
                    + "JOIN Series s on c.SeriesID = s.SeriesID WHERE "
                    + "s.SeriesName = '" + seriesName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
    
    
    /**
     * Gets content determined by their type.
     * @param contentType 
     * @return returns all content by a given type.
     */
    @Override
    public List<String[]> getContentByType(String contentType) {
        
        try {
            String query = "SELECT * FROM Content c JOIN ContentType ct on "
                    + "c.ContentTypeID = ct.contentTypeID WHERE "
                    + "ct.ContentType = '" + contentType + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    
    /**
     * Gets a specific content type from the db. 
     * @param contentType
     * @return
     */
    @Override
    public List<String[]> getContentType(String contentType) {
        
        try {
            String query = "SELECT * FROM ContentType "
                    + "WHERE ContentType = '" + contentType + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }
       
    
    /**
     * Gets a specific creator determined by their name.
     * @param firstName
     * @param middleName
     * @param lastName
     * @return returns a specific creator. 
     */
    @Override
    public List<String[]> getCreator(String firstName, String middleName, String lastName) {
        
        try {
            String query = "SELECT * FROM Creator a WHERE a.FirstName = '" 
                    + firstName + "' AND a.MiddleName = '" + middleName 
                    + "' AND a.LastName = '" + lastName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }


    /**
     * Gets the count of the number of content created by a specific author
     * @param firstName
     * @param middleName
     * @param lastName
     * @return 
     */
    @Override
    public int getCreatorCount(String firstName, String middleName, String lastName) {
        
        try {
            String query = "SELECT COUNT(*) TotalCount FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.CREATOR 
                    + " cr on c.CreatorID = cr.CreatorID WHERE cr.FirstName = '"
                    + firstName + "' AND cr.MiddleName = '" + middleName
                    + "' AND cr.LastName = '" + lastName + "'";
            return getCount(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default value
        return 0;
    }
    
    
    /**
     * Gets a specific genre from the DB.
     * @param genreName
     * @return returns a specific genre.  
     */
    @Override
    public List<String[]> getGenre(String genreName) {
        
        try {
            String query = "SELECT * FROM Genre g "
                    + "WHERE g.GenreName = '" + genreName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default Value
        return null;
    }

    
    /**
     * Method that returns a count of the number of entries of a certain genre
     * Hopefully useful for the recommendation class
     * @param genreName
     * @return 
     */
    @Override
    public int getGenreCount(String genreName) {
        
        try {
            String query = "SELECT COUNT(*) TotalCount FROM " + DBEnumeration.CONTENT
                    + " c JOIN " + DBEnumeration.GENRE 
                    + " g on c.GenreID = g.genreID WHERE g.GenreName = '"
                    + genreName + "'";
            return getCount(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return 0;
    }
    
    
    /**
     * Gets a specific publisher by name
     * @param publisherName
     * @return 
     */
    @Override
    public List<String[]> getPublisher(String publisherName) {
        
        try {
            String query = "SELECT * FROM Publisher p "
                    + "WHERE p.PublisherName = '" + publisherName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    
    /**
     * Gets the number of content published by a given publisher.
     * @param publisherName
     * @return 
     */
    @Override
    public int getPublisherCount(String publisherName) {
        
        try {
            String query = "SELECT COUNT(*)" + DBEnumeration.COUNT 
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
    
    
    /**
     * Gets a specific series from the DB. 
     * @param seriesName
     * @return 
     */
    @Override
    public List<String[]> getSeries(String seriesName) {
        
        try {
            String query = "SELECT * FROM Series s "
                    + "WHERE s.SeriesName = '" + seriesName + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage()); 
        }
        
        
        //Default to null if nothing returned.
        return null;  
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
     * Gets a specific sync status from the DB.
     * @param syncStatusDescription
     * @return
     */
    @Override
    public List<String[]> getSyncStatus(String syncStatusDescription){
        
        try {
            String query = "SELECT * FROM SyncStatus sy "
                    + "WHERE sy.SyncStatusDescription = '" 
                    + syncStatusDescription + "'";
            return SQLToPrimitives(getRecords(query));
        }
        
        catch(SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        //Default value
        return null;
    }
    
    
    
    
    /**
     * Updates a specific piece of content. Could be used when something might
     * become a favorite. Still need to work on that functionality. 
     * @return 
     */
    @Override
    public boolean updateContent() {
        return false;
    }
    
    
    /**
     * Updates a specific content type.
     * @return 
     */
    @Override
    public boolean updateContentType() {
        return false;
    }
    
    
    /**
     * Updates info about a creator.
     * @return 
     */
    @Override
    public boolean updateCreator() {
        return false;
    }
    
    
    /**
     * Updates info about a genre.
     * @return 
     */
    @Override
    public boolean updateGenre() {
        return false;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean updatePlaylist() {
        return false;
    }
    
    
    /**
     * Updates info about a publisher. 
     * @return 
     */
    @Override
    public boolean updatePublisher() {
        return false;
    }
    
    
    /**
     * Updates info about a series. 
     * @return 
     */
    @Override
    public boolean updateSeries() {
        return false;
    }
    
    
    /**
     * Updates info about a specific sync status.
     * @return 
     */
    @Override
    public boolean updateSyncStatus() {
        return false;
    }
    
    
    
    
    /**
     * 
     * This method establishes the connection to the db. Still need to add
     * one that closes said connection.
     */
    @Override
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
    @Override
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
                case DBEnumeration.SYNCSTATUS :
                    id = res.getInt("SyncStatusID");
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
    private boolean SQLInsert(PreparedStatement prep) {
        
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
     * An attempt to translate the return values of SQL code into primitives the application can use.
     * @param res
     * @return 
     */
    private static List<String[]> SQLToPrimitives(ResultSet res) {
        
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
    private void setContentLocation (String contentName, String seriesName, String contentType) {  
        
    }
}