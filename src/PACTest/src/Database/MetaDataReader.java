package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.detect.TypeDetector;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
/*
 * @author Jacob Oleson
 *
 * @update 3/24/2019
 *
 * At the moment this is a pathetic attempt to familiarize myself with 
 * a really useful API to get all the relevant data from a file that 
 * the DB needs to store it. Cannot get contettype, that has to be supplied by 
 * the user/application. Much work needed here.

Here's what the DB NEEDS for any file

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
     * @param url
 */
public class MetaDataReader {
    
    private static MetaDataReader mdr = new MetaDataReader();
    private static File file;
    private static Parser parser;
    private static BodyContentHandler handler;
    private static Metadata metadata;
    private static FileInputStream inputStream;
    private static ParseContext context;
    
    private static final String location = "C:/Test/epubTest.epub";
    
    public static void MetaDataReader() throws IOException, TikaException, SAXException {
        MetaDataReader.file = new File(location);
        MetaDataReader.parser = new AutoDetectParser();
        MetaDataReader.handler = new BodyContentHandler();
        MetaDataReader.metadata = new Metadata();
        MetaDataReader.inputStream = new FileInputStream(file);
        MetaDataReader.context = new ParseContext();
        
        parser.parse(inputStream, handler, metadata, context);
        
        String[] metadataNames = metadata.names();
        for(String name : metadataNames) {
            System.out.println(name);
            //System.out.println(name + ": " + metadata.get(name));
        }
    }
    
    public static void main(String[] args) throws IOException, TikaException, SAXException {
        MetaDataReader();
    }
    
    protected String getFileType() {
        return null;
    }
    
    
    /*
     * Get the author's name associated with the mp3 or epub file.
     */
    protected String getCreatorName() {
        
        String foo;
        
        /* There are many types that metadata can return as. 
         * Check for those types and if we still get nothing, then
         * return null and db can handle that. 
         */
        
        foo = MetaDataReader.metadata.get("creator");
        
        if(foo == null) {
            foo = MetaDataReader.metadata.get("dc:creator");
        }
        
        if(foo == null) {
            foo = MetaDataReader.metadata.get("meta:author");
        }
        
        if(foo == null) {
            foo = MetaDataReader.metadata.get("xmpDM:artist");
        }
        
        if(foo == null) {
            foo = MetaDataReader.metadata.get("Author");
        }
        
        return foo;
    }
    
    
    /*
     * Get the genre of the content
     */
    protected String getGenreName() {
        String foo;
        
        foo = MetaDataReader.metadata.get("xmpDM:genre");
        if(foo == null) {
            foo = MetaDataReader.metadata.get("subject");
        }
        
        return foo;
    }
    
    
    
    /*
     * Get the Publisher of the content in the file.
     */
    protected String getPublisherName() {
        String foo;
        
        foo = MetaDataReader.metadata.get("publisher");
        
        if(foo == null) {
            foo = MetaDataReader.metadata.get("dc:publisher");
        }
        
        return foo;
    }
    
    
    /*
     * Get the Series that the content in file is a part of.  
     */
     protected String getSeries() {
        String foo;
        
        foo = MetaDataReader.metadata.get("series");
        
        return foo;
    }
    
     
    /*
     * Get the album name if one exists in the mp3 file.  
     */
    protected String getAlbum() {
         return null;
     }
     
     /*
      * Get the name of the content in the file. 
      */
    protected String getContentName() {
        
        String foo;
        
        foo = MetaDataReader.metadata.get("title");
        if(foo == null) {
            foo = MetaDataReader.metadata.get("dc:title");
        }
        if(foo == null) {
            
        }
        
        return foo;
    }
    
    
    /*
     * Get the description (if there is one) for the content in file. 
     */
    protected String getContentDescription() {
        return null;
    }
    
    
    /*
     * Get the upload date on file. 
     */
    protected String getUploadDate() {
        return null;
    }
    
   
    /*
     * Get the page count for ebooks.
     */
    protected String getPageCount() {
        return null;
    }
    
    
    /*
     * Get the duration of the mp3 file. Could be song, or podcast. 
     */
    protected String getDuration() {
        return null;
    }
    
    
    /*
     * Get the isbn for a book.
     */
    protected String getISBN() {
        return null;
    }
    
    
    /*
     * Mark if content is explicit or not. 
     */
    protected String getExplicit() {
        return null;
    }
    
    
    /*
     * Get the download url from where the content is from.
     */
    protected String getUrl() {
        return null;
    }
    
}