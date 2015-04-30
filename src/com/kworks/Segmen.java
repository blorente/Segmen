package com.kworks;

import com.kworks.elems.ALUType;
import com.kworks.elems.instructions.Instruction;
import com.kworks.elems.registers.FloatingPointRegister;
import com.kworks.elems.registers.IntegerRegister;
import com.kworks.elems.registers.Register;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Segmen {

    private static final String FILENAME = "program.txt";

    public static void main(String[] args) {
        Scanner in = new Scanner(FILENAME);


        ArrayList<Register> regs = Segmen.createRegisters();
        ArrayList<Instruction> instructions = Segmen.readInstructions(in);

        LockManager lm = new LockManager(regs);

        mainLoop(lm, regs, instructions);

        printEverything();
    }



    private static void mainLoop(LockManager lm, ArrayList<Register> regs, ArrayList<Instruction> instructions) {
        int c = 0;
        for (Instruction in : instructions) {
            c = lm.getCycle();
            //1) Initialize values
            in.setcIF(c);
            in.setcID(c + 1);
            //2) Check registers are available

            //3) Fill in the rest of cXX
            in.setcE(in.getcID() + in.getType().getLatency());
            in.setcM(in.getcE() + 1);
            in.setcWB(in.getcM() + 1);
            //4) Compare the instruction's WB, M, E to avoid collisions: Delay when necessary

            //UPDATE COUNT
            lm.tick();
        }
    }

    private static void printEverything() {
    }

    private static ArrayList<Instruction> readInstructions(Scanner in) {
        String i = "START";
        do {
            String[] tokens = i.split("");
            if (tokens != null) {

            }
        } while (i != "END");

        return null;
    }

    private static ArrayList<Register> createRegisters() {
        ArrayList<Register> reg = new ArrayList<Register>();

        reg.add(new IntegerRegister("R0"));
        reg.add(new IntegerRegister("R1"));
        reg.add(new IntegerRegister("R2"));
        reg.add(new IntegerRegister("R3"));
        reg.add(new IntegerRegister("R4"));
        reg.add(new FloatingPointRegister("F0"));
        reg.add(new FloatingPointRegister("F1"));
        reg.add(new FloatingPointRegister("F2"));
        reg.add(new FloatingPointRegister("F3"));
        reg.add(new FloatingPointRegister("F4"));

        return reg;
    }

    private static Register findRegByName(HashSet<Register> regs, String name) {
        Register r = null ;
        for (Register rg: regs) {
            if (rg.getID().equals(name)) {
                r = rg;
            }
        }
        return r;
    }

}
