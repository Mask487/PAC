/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;

/**
 *
 * @author jacob
 */
public class ContentLocationDirectory {
    public static String CONTENTFILEDIRECTORY = getPathToContentFiles();
    private static final String contentFiles = File.separator + "ContentFiles" + File.separator;
    
    private static String getPathToContentFiles() {
        String foo = "C:\\";
        String workingDirectory = System.getProperty("user.dir");
        File file = new File(workingDirectory);
        String parentDirectory = file.getParent();
        File file2 = new File(parentDirectory);
        String contentFileDirectory = file2.getParent();
        foo = contentFileDirectory.concat(contentFiles);
        System.out.println(CONTENTFILEDIRECTORY);
        return foo;
    }
}
