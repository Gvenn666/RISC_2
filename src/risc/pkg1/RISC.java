/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risc.pkg1;

import java.io.File;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author George
 */
public class RISC extends Processor {

    private JTextArea txtOut;
    
    public RISC(JTextArea txtOut_) {
        txtOut = txtOut_;
    }
    
    int[] reg = new int[32];
    String[] prog = {};
    private final int PC = 31;
    int[] mem;
    Stack<Integer> calls = new Stack();
    
    @Override
    public int clock() {
       return execute(decode(fetch()));
    }
    
    public String fetch() {
        if(prog.length > 0)
            return prog[reg[PC]++ % prog.length];
        
        return "NOP";
        
    }
    
    public String[] decode(String s) {
        return s.split(" ");
    }
    
    public int execute(String[] inp) {
        
        
        
        int state = 0;
        
        int[] para = getArgs(1, inp);
        
        switch(Symbols.valueOf(inp[0].toUpperCase())) {
            
            case LDI:
                reg[para[0]] = para[1];
                break;
                
            case LD: 
                reg[para[0]] = mem[para[1]];
                break;
                
            case HLT: state = 1; break;
            
            case JMP:
                reg[PC] = para[0];
                break;
                
            case JMZ:
                if(reg[para[0]] == 0) reg[PC] = para[0];
                break;
                
            case JNZ:
                if(reg[para[0]] != 0) reg[PC] = para[0];
                break;
                
            case JSR:
                calls.push(++reg[PC]);
                reg[PC] = para[0];
                break;
                
            case RET:
                reg[PC] = calls.pop();
                break;
                
            case ADD:
                reg[para[0]] += reg[para[1]];
                break;
                
            case ADDI:
                reg[para[0]] += para[1];
                break;
                
            case DIV:
                reg[para[0]] /= reg[para[1]];
                break;
              
            case DIVI:
                reg[para[0]] /= para[1];
                break;
                
            case SUBI:
                reg[para[0]] /= reg[para[1]];
                break;
                
                
            case SUB:
                reg[para[0]] -= reg[para[1]];
                break;
                
            case MUL:
                reg[para[0]] *= reg[para[1]];
                break;
                
            case MULI:
                reg[para[0]] *= para[1];
                break;
                
            case NOP: break;
            
            case ST:
                mem[para[1]] = reg[para[0]];
                break;
                
                
            case PRINT:
                txtOut.append(""+(char)para[0]);
                break;
                
                
            case CLS: 
                txtOut.setText("");
            
                
            case LBL: break;
            case EQU: break;
                
            default:
                System.err.println("Unkown Instruction "+inp[0]);
                break;
                
        }
        System.out.println("INS:" + inp[0] + ":");
        System.out.println(this);
        
        return state;
    }
    
    HashMap<String, Integer> vars = new HashMap();
    
    public void pass1() {
        int ln = 0;
        for(String line : prog) {
            String[] toks = line.split(" ");
            switch(toks[0]) {
                case "EQU":
                    if(vars.containsKey(toks[1])) break;
                    
                    vars.put(toks[1], parseData(toks[2],mem,vars));
                    System.out.println(toks[1]+":"+vars.get(toks[1]));
                    break;
                    
                case "LBL":
                    if(vars.containsKey(toks[1])) break;
                    vars.put(toks[1], ln);
                    
                    System.out.println(toks[1]+":"+vars.get(toks[1]));
                    break;
            }
            
            ln++;
        }
    }
    
    public void load(String[] prog_) {
        prog = prog_;
        pass1();
    }
    
    public void load(File f) {
        load(IO.loadStrings(f));
    }
    
        
        
    
    public int[] getArgs(int offset, String[] inputs) {
        int[] output = new int[inputs.length - offset];
        for(int i = 0; i < inputs.length - offset; i++) {
            output[i] = parseData(inputs[i + offset], mem, vars);
        }
        
        return output;
    }
    
    @Override
    public String toString() {
        String out = "";
        String sReg = "";
        for(int i = 0; i < reg.length; i++) {
            sReg += String.format(" R%02d: %04xH",i,reg[i]);
        }
        
        out += "RISC "+Thread.currentThread().getId()+" -"+sReg;
        return out;
    }
    
}
