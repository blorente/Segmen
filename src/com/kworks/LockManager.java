package com.kworks;


import com.kworks.elems.ALUType;
import com.kworks.elems.registers.Register;

import java.util.ArrayList;

public class LockManager {

    private int cycle;
    private ArrayList<Register> regs;
    private ArrayList<ALUType> alus;
    private int[] writeDelays; //delays[0] contains the delay for regs[0]
                            //When delays[0] == 0, unlock the register
    private int[] readDelays;
    private int[] aluDelays;  //delays[0] contains the delay an alu

    //When delays[0] == 0, unlock the alu

    public LockManager(ArrayList<Register> regs) {
        this.regs = regs;
        this.cycle = 0;

        this.readDelays = new int[regs.size()];
        this.writeDelays = new int[regs.size()];
        this.aluDelays = new int[ALUType.values().length];
    }

    public void tick() {
        for (int i = 0; i < this.writeDelays.length; i++) {
            if (writeDelays[i] == 0 && this.regs.get(i).writeLocked()) {
                this.regs.get(i).writeUnlock();
            } else if (writeDelays[i] > 0) {
                writeDelays[i]--;
            }
        }
        for (int i = 0; i < this.readDelays.length; i++)  {
            if (readDelays[i] == 0 && this.regs.get(i).readLocked()) {
                this.regs.get(i).readUnlock();
            } else if (readDelays[i] > 0) {
                readDelays[i]--;
            }
        }
        for (int i = 0; i < this.aluDelays.length; i++)  {
            if (aluDelays[i] == 0 && this.alus.get(i).locked()) {
                this.alus.get(i).unlock();
            } else if (aluDelays[i] > 0) {
                aluDelays[i]--;
            }
        }
        this.cycle++;
    }

    public void addReadLockForSomeCycles(Register reg, int delay) {
        int i = regs.indexOf(reg);
        regs.get(i).readLock();
        readDelays[i] = delay;
    }

    public void addWriteLockForSomeCycles(Register reg, int delay) {
        int i = regs.indexOf(reg);
        regs.get(i).writeLock();
        writeDelays[i] = delay;
    }
    public int getCycle() {
        return this.cycle;
    }
}
