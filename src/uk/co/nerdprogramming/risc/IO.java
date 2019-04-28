/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.nerdprogramming.risc;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author George
 */
public final class IO {
    public static PrintStream getStdOut() {return System.out;} 
    public static PrintStream getStdErr() {return System.err;} 
    public static InputStream getStdIn() {return System.in;} 
    
    public static String[] loadStrings(File f) {
        try {
            BufferedReader reader = openBufferedReader(f);
            ArrayList<String> source = new ArrayList<String>();
            String line = "";
            
            while((line = reader.readLine()) != null) {
                source.add(line);
            }
            
            return source.toArray(new String[source.size()]);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private static BufferedReader openBufferedReader(File f) {
        try {
            return new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static File toFile(String path) {
        return new File(path);
    }
    
    public static String[][] loadCSV(String path, String delim) {
        String[] lines = loadStrings(toFile(path));
        ArrayList<String[]> table = new ArrayList<String[]>();
        
        for(String line : lines) {
            line.split(delim);
            //table.add(line.split(delim));
        }
        
        String[][] out = new String[table.size()][0];
        
        int i = 0;
        for(String[] row : table) {
            out[i] = row;
        }
        
        return out;
        
    }
    
    
    public static boolean makeDirectory(String path) {
        File dir = new File(path);
        dir.mkdir();
        return dir.isDirectory();
    }
    
    public static String makeDirInAppData(String name) {
        makeDirectory(getAppData()+"/"+name);
        return getAppData()+"/"+name;
    }
    
    public static String getAppData() {
        return System.getenv("APPDATA");
    }
    
    public static InputStream getFromURL(String url) {
        try {
            return new URL(url).openStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static BufferedWriter openBufferedWriter(String path) {
        try {
            return new BufferedWriter(new FileWriter(path));
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static PrintStream openPrintStream(String path) {
        try {
            return new PrintStream(new FileOutputStream(path));
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void clearFile(File f) {
        
        try {
            f.delete();
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public static void hexDump(int[] dat, int colNum, String path) {
    	
    	BufferedWriter bw = openBufferedWriter(path);
    	String line;
    	
    	for(int i = 0; i < dat.length;) {
    		line = String.format("%016x", i);
    		for(int j = 0; j < colNum; j++) {
    			
    			line += String.format(" %08x", dat[i++]);
    			
    		}
                
                try {
                    bw.write(line+System.lineSeparator());
                } catch (IOException ex) {
                    Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
                }
    		
    	}
    	
    }
    
    
    
    
    
}
