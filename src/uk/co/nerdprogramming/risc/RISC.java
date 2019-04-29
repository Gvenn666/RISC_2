/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.nerdprogramming.risc;

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
    int[] mem = new int[0x10000];
    Stack<Integer> calls = new Stack();
    
    @Override
    public int clock() {
        int state = 0;
        for(int i = 0; i < 100; i++) {
            state = execute(decode(fetch()));
            if(haltFlag) { System.out.println("RISC #"+Thread.currentThread().getId()+" Has Halted"); break; }
        }
      
       return state;
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
        if(haltFlag) return 1;
        if(prog.length < 1) return 0;
        
        int state = 0;
        
        int[] para = getArgs(1, inp);
        
        switch(Symbols.valueOf(inp[0].toUpperCase())) {
            
            case LDI:
                reg[para[0]] = para[1];
                break;
                
            case LD: 
                reg[para[0]] = mem[para[1]];
                break;
                
            case HLT: state = 1; haltFlag = true; break;
            
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
                
            case NOP: OS.sleep(para[0]); break;
            
            case ST:
                mem[para[1]] = reg[para[0]];
                break;
                
                
            case PRINT:
                txtOut.append(""+(char)reg[para[0]]);
                break;
                
            case PRINTN:
                txtOut.append(""+reg[para[0]]);
                
                
            case CLS: 
                txtOut.setText("");
            
            case PRINTSTR:
                char c = ' ';
                int i = 0;
                while((c = (char) mem[para[0] + i]) > 0) {
                    if(c == 0) break;
                    txtOut.append(""+c);
                    System.err.println(c);
                    i++;
                }
                
                break;
                
            case LBL: break;
            case EQU: break;
            case STRING: break;
            case FOPEN: break;
            case INCLUDE: break;
                
            default:
                System.err.println("Unkown Instruction "+inp[0]);
                break;
                
        }
        System.out.println("INS:" + inp[0] + ":");
        System.out.println(this);
        
        return state;
    }
    
    HashMap<String, Integer> vars = new HashMap();
    boolean haltFlag = false;
    int nextStringPtr = 0;
    public void pass1() {
        int ln = 0;
        for(String line : prog) {
            String[] toks = line.split(" ");
            switch(toks[0]) {
                case "EQU":
                    if(vars.containsKey(toks[1])) break;
                    
                    vars.put(toks[1], parseData(toks[2],mem,vars,reg));
                    System.out.println(toks[1]+":"+vars.get(toks[1]));
                    break;
                    
                case "LBL":
                    if(vars.containsKey(toks[1])) break;
                    vars.put(toks[1], ln);
                    
                    System.out.println(toks[1]+":"+vars.get(toks[1]));
                    break;
                    
                case "INCLUDE":
                    String[] addition = IO.loadStrings(new File(toks[1]));
                    prog = ARRAY.combine(prog, addition);
                    break;
                    
                case "STRING":
                    if(vars.containsKey(toks[1])) break;
                    int i = nextStringPtr;
                    vars.put(toks[1], i);
                    int ptr = i;
                    for(char c : toks[2].replace('_', ' ').toCharArray()) {
                        mem[i++] = (int) c;
                    }
                    mem[++i] = 0x00;
                    
                    nextStringPtr +=  toks[2].length()+1;
                    
                    
                   // nextStringPtr += i;
                    System.err.println(toks[1]+":"+i+" NSP:"+nextStringPtr);
                    break;
                    
            }
            
            ln++;
            
            for(String s : toks) {
                System.err.println(s);
            }
        }
    }
    
    public void load(String[] prog_) {
        prog = prog_;
        pass1();
    }
    
    public void load(File f) {
        load(IO.loadStrings(f));
    }
    
//    public int searchForSpace(int len) {
//        for(int begin = 0; begin < mem.length - len; begin++) {
//            for(int end = 0; end < mem.length; end++) {
//                if()
//                    
//                    
//            }
//        }
//    }
    
        
        
    
    public int[] getArgs(int offset, String[] inputs) {
        int[] output = new int[inputs.length - offset];
        for(int i = 0; i < inputs.length - offset; i++) {
            output[i] = parseData(inputs[i + offset], mem, vars,reg);
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
