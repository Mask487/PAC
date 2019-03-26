package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
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
 */
public class MetaDataReader {

    
    
    /**
     * @param args
     */
    public static void test(String[] args) {
        String fileLocation = "C:/Test/test1.mp3";

        try {

        InputStream input2 = new FileInputStream(new File(fileLocation));
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input2, handler, metadata, parseCtx);
        input2.close();

        // List all metadata
        String[] metadataNames = metadata.names();

        for(String name : metadataNames){
        System.out.println(name + ": " + metadata.get(name));
        }

        // Retrieve the necessary info from metadata
        // Names - title, xmpDM:artist etc. - mentioned below may differ based
        System.out.println("----------------------------------------------");
        System.out.println("Title: " + metadata.get("title"));
        System.out.println("Artists: " + metadata.get("xmpDM:artist"));
        System.out.println("Composer : "+metadata.get("xmpDM:composer"));
        System.out.println("Genre : "+metadata.get("xmpDM:genre"));
        System.out.println("Album : "+metadata.get("xmpDM:album"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void getMetadataMp3(String fileLocation) {
        
            InputStream input = null;
        try {
            input = new FileInputStream(new File(fileLocation));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetaDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
        try {
            parser.parse(input, handler, metadata, parseCtx);
        } catch (IOException | SAXException | TikaException ex) {
            Logger.getLogger(MetaDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(MetaDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            String[] metadataNames = metadata.names();
            
            String contentName = metadata.get("title");
            String artistName = metadata.get("xmpDM:artist");
            String genreName = metadata.get("xmpDM:genre");
            String seriesName = metadata.get("xmpDM:album");
        
    }
    
    }