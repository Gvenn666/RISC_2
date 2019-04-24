/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risc.pkg1;

import javax.swing.JComponent;

/**
 *
 * @author George
 */
public class Teletype extends JComponent implements Copper{
    
  private int IRQ = 0xf0, WRITE_ADDR = (IRQ << 8) + 0x01, READ_ADDR = (IRQ << 8) + 0x02;
  
  
    
    
    
}
