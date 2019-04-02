package Util;

import java.util.List;

/**
 *
 * @author Jacob Oleson
 * 
 * @update 2/28/2019
 * 
 * Used to print results from the database. 
 */
public class DBPrint {
       
    public static void printContents(List<String[]> result) {
                
        for(int i = 0; i < result.size(); i++) {
            String[] strings = result.get(i);
            for(int j = 0; j < strings.length; j++) {
                System.out.println(strings[j] + " ");
            }
            System.out.println();
        }
    }
}
