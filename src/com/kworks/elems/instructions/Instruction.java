package com.kworks.elems.instructions;

import com.kworks.elems.ALUType;
import com.kworks.elems.registers.Register;

import java.util.HashSet;

/**
 * Created by Kerith on 26/04/2015.
 */
public abstract class Instruction {

    private HashSet<Register> readRegList;
    private HashSet<Register> writeRegList;
    private ALUType alutype;

    public Instruction(HashSet<Register> readRegList, HashSet<Register> writeRegList, ALUType alutype) {
        this.readRegList = readRegList;
        this.writeRegList = writeRegList;
        this.alutype = alutype;
    }

    public abstract ALUType getType();

    public abstract HashSet<Register> getReadRegisterList();

    public abstract HashSet<Register> getWriteRegisterList();

}
