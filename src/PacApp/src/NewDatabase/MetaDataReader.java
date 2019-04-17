package NewDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
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
    
    private static String location = "C:/pacapp.Test/FreePodcast.mp3";
    
    /**
     * You have to tell this methods if the mp3 file you're passing
     * is an audiobook, podcast, or music file. Metadata reader cannot tell 
     * the difference.
     * @param contentType 
     * @param _location 
     * @return  
     */
    public static Content mp3Reader(String contentType, String _location) {
        
        try {
            InputStream input = new FileInputStream(new File(_location));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            // List all metadata
            //String[] metadataNames = metadata.names();

//            for(String name : metadataNames){
//            System.out.println(name + ": " + metadata.get(name));
//            }
//
//            // Retrieve the necessary info from metadata
//            // Names - title, xmpDM:artist etc. - mentioned below may differ based
//            // on the standard used for processing and storing standardized and/or
//            // proprietary information relating to the contents of a file.
//
//            System.out.println("Title: " + metadata.get("title"));
//            System.out.println("Artists: " + metadata.get("xmpDM:artist"));
//            System.out.println("Genre: " + metadata.get("xmpDM:genre"));

            Content content = new Content();
            content.setContentName(metadata.get("title"));
            content.setSeriesName(metadata.get("album"));
            content.setCreatorName(metadata.get("xmpDM:artist"));
            content.setGenreName(metadata.get("xmpDM:genre"));
            content.setContentTypeName(contentType);
            return content;
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        catch (IOException | SAXException | TikaException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    } 
    
    
    
    /**
     * Reads in an ebook of the epub file extension.
     * @param _location 
     * @return  
     */
    public static Content ePubReader(String _location) {
         try {
            InputStream input = new FileInputStream(new File(_location));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new EpubParser() {};
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            // List all metadata
            String[] metadataNames = metadata.names();

            for(String name : metadataNames){
            System.out.println(name + ": " + metadata.get(name));
            }

            // Retrieve the necessary info from metadata
            // Names - title, xmpDM:artist etc. - mentioned below may differ based
            // on the standard used for processing and storing standardized and/or
            // proprietary information relating to the contents of a file.

            System.out.println("Title: " + metadata.get("title"));
            System.out.println("Author: " + metadata.get("Author"));
            System.out.println("Genre: " + metadata.get("Genre"));

            Content content = new Content();
            content.setContentName(metadata.get("title"));
            content.setSeriesName(metadata.get("Series"));
            content.setCreatorName(metadata.get("Author"));
            content.setGenreName(metadata.get("Genre"));
            content.setContentTypeName("EBook");
            content.setUploadDate(metadata.get("Creation-Date"));
            content.setPublisherName(metadata.get("Publisher"));
            
            return content;
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        catch (IOException | SAXException | TikaException e) {
            System.out.println(e.getMessage());
        }
         
         return null;
    }
    
    
    /**
     * This is the default parser if the file type is neither mp3 or epub.
     * @param _location
     * @return 
     */
    public static Content genericReader(String _location) {
        try {
            InputStream input = new FileInputStream(new File(_location));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser() {};
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            // List all metadata
            String[] metadataNames = metadata.names();

            for(String name : metadataNames){
            System.out.println(name + ": " + metadata.get(name));
            }

            // Retrieve the necessary info from metadata
            // Names - title, xmpDM:artist etc. - mentioned below may differ based
            // on the standard used for processing and storing standardized and/or
            // proprietary information relating to the contents of a file.

            System.out.println("Title: " + metadata.get("title"));
            System.out.println("Author: " + metadata.get("Author"));
            System.out.println("Genre: " + metadata.get("Genre"));

            Content content = new Content();
            content.setContentName(metadata.get("title"));
            content.setSeriesName(metadata.get("Series"));
            content.setCreatorName(metadata.get("Author"));
            content.setGenreName(metadata.get("Genre"));
            content.setContentTypeName("EBook");
            content.setUploadDate(metadata.get("Creation-Date"));
            content.setPublisherName(metadata.get("Publisher"));
            
            return content;
        } 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        catch (IOException | SAXException | TikaException e) {
            System.out.println(e.getMessage());
        }
         
         return null;
    }
    
//    public static void main(String[] args) {
//        //mp3Reader("pacapp.Podcast");
//        ePubReader();
//    }
}
    
    
