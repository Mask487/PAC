package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 */
public class MetaDataReader {
    private final Tika defaultTika = new Tika();
    private final Tika mimeTika = new Tika(new MimeTypes());
    private final Tika typeTika = new Tika(new TypeDetector());
    private static final MetaDataReader mdr = new MetaDataReader();
    
    private String location = "C:/Test/test1.mp3";
    private File file;
    private Parser parser;
    private BodyContentHandler handler;
    private Metadata metadata;
    private FileInputStream inputStream;
    private ParseContext context;
    String[] metadataNames;
    
    
    protected void MetaDataReader(String _location) {
        this.location = _location;
        this.file = new File(location);
        this.parser = new AutoDetectParser();
        this.handler = new BodyContentHandler();

        try {
            this.metadata = new Metadata();
            this.inputStream = new FileInputStream(file);
            this.context = new ParseContext();
            this.parser.parse(inputStream, handler, metadata, context);
            this.metadataNames = metadata.names();

            for(String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));
            }
        }

        catch(IOException | SAXException e) {
            System.out.println(e.getMessage());
        } 
        catch (TikaException ex) {
            Logger.getLogger(MetaDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(handler.toString());        
    }
    
    protected String getTitle(){
        return this.metadata.get(MetaDataEnum.TITLE);
    }
    
    protected String getCreator(){
        return this.metadata.get(MetaDataEnum.CREATOR);
    }
    
    protected String getGenre() {
        return this.metadata.get(MetaDataEnum.GENRE);
    }
    
    protected String getReleaseData() {
        return this.metadata.get(MetaDataEnum.RELEASEDATE);
    }
    
    protected String getSeries() {
        return this.metadata.get(MetaDataEnum.SERIES);
    }
    
    
    
    
   
    public static void main(String[] args) {
        test("C:/Test/test4.epub");
    }
    public static void test(String location) {
        
        try {
            File file = new File(location);
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputStream = new FileInputStream(file);
            ParseContext context = new ParseContext();

            parser.parse(inputStream, handler, metadata, context);
            System.out.println(handler.toString());

            String[] metadataNames = metadata.names();

            for(String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));
            }
            
            String contentName = metadata.get("title");
            String genreName = metadata.get("xmpDM:genre");
            String creatorName = metadata.get("creator");
            String seriesName = metadata.get("xmpDM:album");
            String uploadDate = metadata.get("xmpDM:releaseDate");
            System.out.println(contentName);
        }
        
        catch (FileNotFoundException e) {
            e.getMessage();
        }
        
        catch (IOException | SAXException | TikaException ex) {
            ex.getMessage();
        }
    }
    
    
    private static String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }
    
    
    private String identifyFileTypesUsingDefaultTika(final String fileName) {
    
        return defaultTika.detect(fileName);   
    }
    
    private String identifyFileTypesUsingMimeTypesTika(final String fileName) {
        
        return mimeTika.detect(fileName);
    }
    
    private String identifyFileTypesUsingTypeDetectorTika(final String fileName) {
        return typeTika.detect(fileName);
    }
}