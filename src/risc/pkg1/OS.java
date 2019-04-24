/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risc.pkg1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George
 */
public class OS {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
