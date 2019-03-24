/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.io.File;
/**
 *
 * @author Jacob Oleson
 * 
 * @update 3/22/2019
 * 
 * Used by the DB to place content files in directory.
 * DB keeps track of file paths and gives to application to access content.
 */
public class DBDirectories {
    
    public static boolean createDirectoy(String filePath) {
        boolean success = (new File(filePath)).mkdir();
        
        if(success) {
            System.out.println("Directory: " + filePath + " created");
            return true;
        }
        
        else {
            return false;
        }
    }
    
    public static boolean createDirectories(String filePath) {
        boolean success = (new File(filePath)).mkdirs();
        
        if(success) {
            System.out.println("Directory: " + filePath + " created");
            return true;
        }
        
        else {
            return false;
        }
    }
    
    public static boolean insertIntoDirectory(String contentName, String filePath) {
        return false;
    }
    
    public static boolean removeFromDirectory(String contentName, String filePath) {
        return false;
    }
    
    //Very dangerous, probably don't want.
    public static boolean removeDirectory() {
        return false;
    }
}
