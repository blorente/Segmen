package com.kworks;

import com.kworks.elems.ALUType;
import com.kworks.elems.instructions.Instruction;
import com.kworks.elems.registers.FloatingPointRegister;
import com.kworks.elems.registers.IntegerRegister;
import com.kworks.elems.registers.Register;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Segmen {

    private static final String FILENAME = "C:\\Users\\Kerith\\Dropbox\\Universidad\\EC\\SEGMEN\\program";

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException, UnsupportedEncodingException {

        Scanner in = new Scanner(new File(FILENAME));

        ArrayList<Register> regs = Segmen.createRegisters(); //WORKING
        ArrayList<Instruction> instructions = Segmen.readInstructions(regs, in); //WORKING

        LockManager lm = new LockManager(regs);

        mainLoop(lm, regs, instructions);

        printEverything(instructions); //WORKING
    }



    private static void mainLoop(LockManager lm, ArrayList<Register> regs, ArrayList<Instruction> instructions) {
        int c = 0;
        for (Instruction in : instructions) {
            c = lm.getCycle();
            //1) Initialize values
            in.setcIF(c);
            in.setcID(c + 1);
            //2) Check registers are available
            for (Register r: in.getReadRegisterList()) {
                int cycle = in.getcID();
                while (lm.isGoingToBeReadLockedOnCycle(r, cycle)) {
                    in.delay(1);
                    cycle++;
                }
            }
            for (Register r: in.getWriteRegisterList()) {
                int cycle = in.getcID();
                while (lm.isGoingToBeWriteLockedOnCycle(r, cycle)) {
                    in.delay(1);
                    cycle++;
                }
            }
            //3) Fill in the rest of cXX
            in.setcE(in.getcID() + in.getType().getLatency());
            in.setcM(in.getcE() + 1);
            in.setcWB(in.getcM() + 1);
            //4) Wait until ALU is free
            for (int i = 0; i < instructions.indexOf(in) ; i++)  {
                Instruction other = instructions.get(i);
                if ((other.getType() == in.getType()) &&
                        (other.getcE() >= in.getcID()) &&
                        (!other.getType().isSegmented())) { //segmented alus

                    in.delay(other.getcE() - in.getcID());

                } else if ((other.getType() == in.getType()) &&
                        (other.getcID() > in.getcID()) &&
                        (other.getType().isSegmented())) {

                    in.delay(other.getcID() - in.getcID());

                }
            }
            //5) Compare the instruction's WB, M to avoid collisions: Delay when necessary
            for (int i = 0; i < instructions.indexOf(in) ; i++)  {
                Instruction instr = instructions.get(i);
                int delay = 0;
                while ( (instr.getcM() == in.getcM()) || //There is a collision
                        (instr.getcWB() == in.getcWB()) ||
                        (instr.getcID() == in.getcID())) {
                    in.delay(1);
                }
            }
            //UPDATE COUNT
            for (Register r : in.getWriteRegisterList()) lm.addReadLockForSomeCycles(r, in.getcE() - in.getcIF());
            lm.tick();
        }
    }

    private static void printEverything(ArrayList<Instruction> instructions) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("result.txt", "ASCII");
        //Print to command line
        for (Instruction i : instructions) {
            writer.println(i);
            System.out.println(i);
        }

        writer.close();
    }

    private static ArrayList<Instruction> readInstructions(ArrayList<Register> regs, Scanner in) {
        String i = null;
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        do {
            if (i != null) {
                String[] tokens = i.split(" ");
                Instruction inst = new Instruction();
                int tokenIndex = 0;

                if (tokens[tokenIndex].matches("(.*):")) {
                    inst.setLabel(tokens[tokenIndex]);
                    tokenIndex = 1;
                    inst.setInstString(i.substring(i.indexOf(':') + 2));
                } else {
                    inst.setInstString(i);
                }
                switch (tokens[tokenIndex]) {

                    case "ADDD":
                        inst.setType(ALUType.FP_ADD); //Select appropriate ALU
                        //Read registers
                        inst.getWriteRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 3]));
                        break;
                    case "MULD":
                        inst.setType(ALUType.FP_MUL);
                        //Read registers
                        inst.getWriteRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 3]));
                        break;
                    case "DIVD":
                        inst.setType(ALUType.FP_DIV);
                        //Read registers
                        inst.getWriteRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 3]));
                        break;
                    case "ADD":
                        inst.setType(ALUType.INTEGER);
                        //Read registers
                        inst.getWriteRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 3]));
                        break;
                    case "STR":
                        inst.setType(ALUType.INTEGER);
                        //Read registers
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        break;
                    case "LDR":
                        inst.setType(ALUType.INTEGER);
                        //Read registers
                        inst.getWriteRegisterList().add(findRegByName(regs, tokens[tokenIndex + 1]));
                        inst.getReadRegisterList().add(findRegByName(regs, tokens[tokenIndex + 2]));
                        break;
                    default:
                        //throw new InvelidInstructionException("UR doing it wrong!");
                        break;
                }
                instructions.add(inst);
            }
            i = in.nextLine();
        } while (!i.equals("END"));

        return instructions;
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

    private static Register findRegByName(ArrayList<Register> regs, String name) {
        Register r = null ;
        for (Register rg: regs) {
            if (name.equals(rg.getID() + ",") || name.equals(rg.getID())) {
                r = rg;
            }
        }
        return r;
    }

}
