package com.kworks.elems.instructions;

import com.kworks.elems.ALUType;
import com.kworks.elems.registers.Register;

import java.util.ArrayList;
import java.util.HashSet;

public class Instruction {

    private ArrayList<Register> readRegList;
    private ArrayList<Register> writeRegList;
    private ALUType alutype;
    private String instString;
    private int cIF;
    private int cID;
    private int cE;
    private int cM;
    private int cWB;
    private String label;

    public Instruction(ArrayList<Register> readRegList, ArrayList<Register> writeRegList, ALUType alutype) {
        this.readRegList = readRegList;
        this.writeRegList = writeRegList;
        this.alutype = alutype;
    }

    public Instruction() {
        this.readRegList = new ArrayList<Register>();
        this.writeRegList = new ArrayList<Register>();
        this.alutype = ALUType.FP_ADD;
    }

    public ALUType getType() {
        return this.alutype;
    }

    public ArrayList<Register> getReadRegisterList() {return this.readRegList;}

    public ArrayList<Register> getWriteRegisterList(){return this.writeRegList;}

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

    public int length() {
        return cWB - cIF;
    }

    public void delay(int delay) {
        this.cID += delay;
        this.cE += delay;
        this.cM += delay;
        this.cWB += delay;
    }

    public void setLabel(String label) {this.label = label;}

    public void setType(ALUType type) {
        this.alutype = type;
    }

    public void setInstString(String instString) {
        this.instString = instString;
    }
}
