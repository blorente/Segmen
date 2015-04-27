package com.kworks;

import com.kworks.elems.ALUType;
import com.kworks.elems.instructions.Instruction;
import com.kworks.elems.registers.FloatingPointRegister;
import com.kworks.elems.registers.IntegerRegister;
import com.kworks.elems.registers.Register;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

public class Segmen {

    private static final String FILENAME = "program.txt";
    private Scanner in;

    private HashSet<Register> registers;
    private HashSet<Instruction> instructions;

    public Segmen(Scanner in) {
        this.in = in;
        this.registers = createRegisters();
        this.instructions = readInstructions(in);

    }

    private static HashSet<Instruction> readInstructions(Scanner in) {
        return null;
    }

    private HashSet<Register> createRegisters() {
        HashSet<Register> reg = new HashSet<Register>();

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

    public static void main(String[] args) {
        Scanner in = new Scanner(FILENAME);
        HashSet<Instruction> instructions = Segmen.readInstructions(in);


    }
}
