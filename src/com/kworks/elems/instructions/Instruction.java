package com.kworks.elems.instructions;

import com.kworks.elems.ALUType;
import com.kworks.elems.registers.Register;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Instruction {

    private HashSet<Register> readRegList;
    private HashSet<Register> writeRegList;
    private ALUType alutype;
    private String instString;
    private int cIF;
    private int cID;
    private int cE;
    private int cM;
    private int cWB;
    private String label;

    public Instruction(HashSet<Register> readRegList, HashSet<Register> writeRegList, ALUType alutype) {
        this.readRegList = readRegList;
        this.writeRegList = writeRegList;
        this.alutype = alutype;
    }

    public abstract ALUType getType();

    public abstract ArrayList<Register> getReadRegisterList();

    public abstract ArrayList<Register> getWriteRegisterList();

    @Override
    public String toString() {
        String s = instString + "|";
        for (int i = 0; i <= this.cWB;i++) {
            if (i == this.cIF) {
                s += "IF|";
            } else if ( (i > this.cIF) && (i <= this.cID) ) {
                s += "ID|";
            }else if ((i > this.cID) && (i <= this.cE) ){
                s += this.alutype.toString() + "|";
            } else if (i == this.cM) {
                s += " M|";
            } if (i == this.cWB) {
                s += "WB|";
            }
        }
        return s;
    }

    public int getcIF() {
        return cIF;
    }

    public void setcIF(int cIF) {
        this.cIF = cIF;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public int getcE() {
        return cE;
    }

    public void setcE(int cE) {
        this.cE = cE;
    }

    public int getcM() {
        return cM;
    }

    public void setcM(int cM) {
        this.cM = cM;
    }

    public int getcWB() {
        return cWB;
    }

    public void setcWB(int cWB) {
        this.cWB = cWB;
    }

    public int lenth() {
        return cWB - cIF;
    }

    public void delay(int delay) {
        this.cID += delay;
        this.cE += delay;
        this.cM += delay;
        this.cWB += delay;
    }
}
