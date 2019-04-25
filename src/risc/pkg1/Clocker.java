/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risc.pkg1;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author George
 */
public class Clocker implements Runnable {
    ArrayList<Processor> proc = new ArrayList<>();
    
    public void add(Processor p) {proc.add(p);}

    @Override
    public void run() {
        int state = 0;
        while(state == 0) {
            for(Processor p : proc) {
                state = p.clock();
                //System.out.println(p.toString());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Clocker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
    
}
