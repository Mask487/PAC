package Database;

import java.sql.*;
import Interfaces.DBInterface;
import Util.DBEnumeration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Oleson
 * 
 * @update 2/27/2019
 * 
 * Translator class that actually speaks to the DB File.
 */

//@TODO Determine how to do the ResultSet to Primitives translation.
//@TODO Add music, videos, and photos to DB Content Table. 
public class SQLTranslator implements DBInterface{
    
    //Establishes connection to db file.
    private static Connection conn;
    
    /**
         * The jdbc:sqlite: part is permanent. The part after that specifies
         * the filepath. There needs to be a way to specify the filepath 
         * in relation to where this file is in the project directory.
         * For now just specify where it is on your own machine.
         */
    //private final String url = "jdbc:sqlite:C:/sqlite/PACDB.db";
    private final String url = "jdbc:sqlite:C:/PAC/Database/PACDB.db";
    
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
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getAllContent() throws SQLException, ClassNotFoundException {        
        String query = "SELECT * FROM " + DBEnumeration.CONTENT;
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Returns the Content Types the DB currently knows.
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getAllContentTypes() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM ContentType";
        return SQLToPrimitives(getRecords(query));
    }
    
        
    /**
     * This method displays all current creators held in db. 
     * 
     * @return res returns the set of all creators and the information associated
     * with them that was requested.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getAllCreators() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + DBEnumeration.CREATOR;
        return SQLToPrimitives(getRecords(query));
    }
        
    
    /**
     * Gets all the genres that the DB knows about. 
     * @return gets all genres that the DB holds.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getAllGenres() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + DBEnumeration.GENRE;
        return SQLToPrimitives(getRecords(query));
    }
        
    
    /**
     * 
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getAllPublishers() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Publisher";
        return SQLToPrimitives(getRecords(query));
    }
        
   
    /**
     * Gets all series that the DB currently holds.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getAllSeries() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Series";
        return SQLToPrimitives(getRecords(query));
    }
        
    
    /**
     * Gets all sync statuses from the DB. Don't know why this would be used but just in case. 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getAllSyncStatus() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM SyncStatus";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets content determined by their creator.
     * @param firstName Cannot be null.
     * @param middleName Can be null.
     * @param lastName Cannot be null.
     * @return returns all content associated with one creator.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String[]> getContentByCreator(String firstName, String middleName, String lastName) throws SQLException, ClassNotFoundException {
        String query = "SELECT c.ContentID FROM Content c JOIN Creator a on c.CreatorID = a.CreatorID WHERE "
               +  "c.ContentCreatorID = (SELECT CreatorID FROM Creator WHERE FirstName = '" + firstName + "' AND MiddleName = '" + middleName + "' AND LastName = '" + lastName + "')";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets all content by a given genre.
     * @param genreName
     * @return returns all content of the given genre.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String[]> getContentByGenre(String genreName) throws SQLException, ClassNotFoundException {        
        String query = "SELECT c.ContentID FROM Content c JOIN Creator a WHERE " 
                + "c.GenreID = (SELECT GenreID FROM Genre WHERE GenreName = '" + genreName + "')";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets a specific content determined by its name.
     * @param contentName
     * @return returns an individual piece of content.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String[]> getContentByName(String contentName) throws SQLException, ClassNotFoundException {
        String query = "SELECT c.ContentID FROM Content c WHERE c.ContentName = '" + contentName + "'"; 
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets content associated with a publisher.
     * @param publisherName
     * @return returns all content by a given publisher.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String[]> getContentByPublisher(String publisherName) throws SQLException, ClassNotFoundException {
        String query = "SELECT c.ContentID FROM Content c JOIN Publisher p WHERE "
                + "c.PublisherID = (SELECT PublisherID FROM Publisher WHERE PublisherName = '" + publisherName + "')";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets all content in DB that is a part of a specific series. 
     * @param seriesName
     * @return returns all content associated with a given series. 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getContentBySeries(String seriesName) throws SQLException, ClassNotFoundException {
        String query = "SELECT c.ContentID FROM Content c JOIN Series s WHERE "
                + "c.SeriesID = (SELECT SeriesID FROM Series WHERE SeriesName = '" + seriesName + "')";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets content determined by their type.
     * @param contentType 
     * @return returns all content by a given type.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String[]> getContentByType(String contentType) throws SQLException, ClassNotFoundException {
        String query = "SELECT c.ContentID FROM Content c JOIN ContentType ct WHERE "
                + "c.ContentID = (SELECT ContentTypeID FROM ContentType WHERE ContentType = '" + contentType + "')";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Gets a specific content type from the db. 
     * @param contentType
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getContentType(String contentType) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM ContentType ct WHERE ContentType = '" + contentType + "'";
        return SQLToPrimitives(getRecords(query));
    }
       
    
    /**
     * Gets a specific creator determined by their name.
     * @param firstName
     * @param middleName
     * @param lastName
     * @return returns a specific creator. 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getCreator(String firstName, String middleName, String lastName) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Creator a WHERE a.FirstName = '" + firstName + "' AND a.MiddleName = '" + middleName + "' AND a.LastName = '" + lastName + "'";
        return SQLToPrimitives(getRecords(query));
    }    
    
    
    /**
     * Gets a specific genre from the DB.
     * @param genreName
     * @return returns a specific genre. 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getGenre(String genreName) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Genre g WHERE g.GenreName = '" + genreName + "'";
        return SQLToPrimitives(getRecords(query));
    }

    
    /**
     * Gets a specific publisher by name
     * @param publisherName
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public List<String[]> getPublisher(String publisherName) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Publisher p WHERE p.PublisherName = '" + publisherName + "'";
        return SQLToPrimitives(getRecords(query));
    }

    
    
    /**
     * Gets a specific series from the DB. 
     * @param seriesName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getSeries(String seriesName) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Series s WHERE s.SeriesName = '" + seriesName + "'";
        return SQLToPrimitives(getRecords(query));
    }

   
    
    /**
     * Gets a specific sync status from the DB.
     * @param syncStatusDescription
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public List<String[]> getSyncStatus(String syncStatusDescription) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM SyncStatus sy WHERE sy.SyncStatusDescription = '" + syncStatusDescription + "'";
        return SQLToPrimitives(getRecords(query));
    }
    
    
    /**
     * Adds a new piece of content to DB. Desperately needs to be refactored down.
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
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean addContent(String contentType, String syncStatusType, 
            String firstName, String middleName, String lastName, 
            String genreName, String publisherName, String seriesName, 
            String contentName, String contentDescription, String uploadDate,
            int pageCount, String duration, String isbn, boolean explicit, 
            String location) throws SQLException, ClassNotFoundException {
        
        if(conn == null) {
            getConnection();
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
        if(contentTypeID == -1) {
            addContentType(contentType);
            contentTypeID = SQLCheckForeignKeyRecord(queryContentType, DBEnumeration.CONTENTTYPE);
        }
        
        int syncStatusID = SQLCheckForeignKeyRecord(querySyncStatus, DBEnumeration.SYNCSTATUS);
        if(syncStatusID == -1) {
            addSyncStatus(syncStatusType);
            syncStatusID = SQLCheckForeignKeyRecord(querySyncStatus, DBEnumeration.SYNCSTATUS);
        }
        
        int creatorID = SQLCheckForeignKeyRecord(queryCreator, DBEnumeration.CREATOR);
        if(creatorID == -1) {
            addCreator(firstName, middleName, lastName);
            creatorID = SQLCheckForeignKeyRecord(queryCreator, DBEnumeration.CREATOR);
        }
        
        int genreID = SQLCheckForeignKeyRecord(queryGenre, DBEnumeration.GENRE);
        if(genreID == -1) {
            addGenre(genreName);
            genreID = SQLCheckForeignKeyRecord(queryGenre, DBEnumeration.GENRE);
        }
        
        int publisherID = SQLCheckForeignKeyRecord(queryPublisher, DBEnumeration.PUBLISHER);
        if(publisherID == -1) {
            addPublisher(publisherName);
            publisherID = SQLCheckForeignKeyRecord(queryPublisher, DBEnumeration.PUBLISHER);
        }
        
        int seriesID = SQLCheckForeignKeyRecord(querySeries, DBEnumeration.SERIES);
        if(seriesID == -1) {
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
                + "PageCount, Duration, ISBN, Explicit, Location)" 
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
    
    
    /**
     * Adds a new content type that the DB can hold.
     * @param contentTypeName
     * @return 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean addContentType(String contentTypeName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
        
        String insertQuery = "INSERT INTO " + DBEnumeration.CONTENTTYPE + "(ContentType)"
                + " VALUES(?);";
        
        PreparedStatement prep = conn.prepareStatement(insertQuery);
        prep.setString(1, contentTypeName);
        
        if(SQLInsert(prep)) {
            System.out.println("ContentType added successfully");
            return true;
        }
        else {
            return false;
        }
    }
     
     
    /**
     * This method adds a new creator to the creator table. 
     * 
     * @param firstName 
     * @param middleName
     * @param lastName
     * @return 
     * 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean addCreator(String firstName, String middleName, String lastName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
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
            return false;
        }
    }
    
    
    /**
     * Adds a new genre to DB.
     * @param genreName 
     * @return  
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public boolean addGenre(String genreName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
        String insertQuery = "INSERT INTO " + DBEnumeration.GENRE + "(GenreName)"
                + " VALUES(?);";
        PreparedStatement prep = conn.prepareStatement(insertQuery);
        prep.setString(1, genreName);
   
        if(SQLInsert(prep)) {
            System.out.println("Genre added successfully");
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * Adds a new publisher to DB.
     * @param publisherName 
     * @return  
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public boolean addPublisher(String publisherName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
        
        String insertQuery = "INSERT INTO " + DBEnumeration.PUBLISHER + "(PublisherName)"
                + " VALUES(?);";
        PreparedStatement prep = conn.prepareStatement(insertQuery);
        prep.setString(1, publisherName);
        
        if(SQLInsert(prep)) {
            System.out.println("Publisher added successfully");
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * Adds a new Series to the DB.
     * @param seriesName 
     * @return  
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public boolean addSeries(String seriesName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
        String insertQuery = "INSERT INTO " + DBEnumeration.SERIES + "(SeriesName)"
                + " VALUES(?);";
        PreparedStatement prep = conn.prepareStatement(insertQuery);
        prep.setString(1, seriesName);
        
        if(SQLInsert(prep)) {
            System.out.println("Series added successfully");
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * Adds a new kind of status to sync. 
     * @param syncName 
     * @return  
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public boolean addSyncStatus(String syncName) throws SQLException, ClassNotFoundException {
        if(conn == null) {
            getConnection();
        }
        
        String insertQuery = "INSERT INTO " + DBEnumeration.SYNCSTATUS + "(SyncStatusDescription)"
                + " VALUES(?);";
        PreparedStatement prep = conn.prepareStatement(insertQuery);
        prep.setString(1, syncName);
        
        if(SQLInsert(prep)) {
            System.out.println("Sync Status added successfully");
            return true;
        } 
        else {
            return false;
        }   
    }
    
    
    /**
     * 
     * This method establishes the connection to the db. Still need to add
     * one that closes said connection.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public void getConnection() throws SQLException, ClassNotFoundException {
        
        /**
         * This part is necessary. Specifies the library that allows Java to 
         * work with SQLite
         */
        Class.forName("org.sqlite.JDBC");
        
        try {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        int id = -1;
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
    private static List<String[]> SQLToPrimitives(ResultSet res) throws SQLException {
        
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
}
