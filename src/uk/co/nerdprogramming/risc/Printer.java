/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.nerdprogramming.risc;

import java.io.BufferedWriter;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author George
 */
public class Printer extends Processor {
    
   
    
    BufferedWriter writer;
    private int[] mem;
    private final int RX = 0xB001, TX = 0xB002, COMM = 0xB000;
    public Printer(String path, int[] mem_) {
        writer = IO.openBufferedWriter(path);
        mem = mem_;
    }

    @Override
    public int clock() {
        try {
            switch(mem[COMM]) {
                
                case 0x00: break;
                case 0x01: writer.write(""+(char)mem[RX]);
                case 0x02: writer.write(""+mem[RX]);
                case 0x10: writer = IO.openBufferedWriter(String.format("c:/tmp/%04d.txt",mem[RX]));break;
                case 0x11: IO.clearFile(new File(String.format("c:/tmp/%04d.txt",mem[RX])));break;
                case 0x12: writer.close();
                default: System.err.println("Printer: Unknown Command :"+mem[COMM]);
            }
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;
    }
    
    
    
    
}
