/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.nerdprogramming.risc;

import java.util.HashMap;

/**
 *
 * @author George
 */
public abstract class Processor {
    public abstract int clock();
     protected int parseData(String s, int[] mem, HashMap<String, Integer> vars, int[] reg) {
        int dat = 0;
        //XXXXt
        char type = s.charAt(0);
        String val = s.substring(1);
       // if(!vars.containsKey(val)) return 0;
        switch(type) {
            case '#':
                dat = Integer.parseInt(val, 10);
                break;
                
            case '$':
                dat = Integer.parseInt(val, 16);
                break;
                
            case '%':
                dat = Integer.parseInt(val, 2);
                break;
                
            case '@':
                dat = mem[Integer.parseInt(val, 10)];
                break;
                
            case ':':
                dat = vars.get(val);
                break;
             
            case 'r':
                dat = Integer.parseInt(val, 10);
                break;
                
            case '"':
                if(val.equals("_")) { dat = (int) ' '; break; }
                dat = (int) val.charAt(0);
                break;
                
            case '*':
                dat = reg[Integer.parseInt(val)];
                break;
            
        }
        
        
        return dat;
    }
}
