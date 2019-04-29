/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.nerdprogramming.risc;


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
    int state = 0;
    @Override
    public void run() {
        
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
