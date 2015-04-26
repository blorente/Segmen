package com.kworks.elems;

/**
 * Created by Kerith on 26/04/2015.
 */
public interface Register {

    public abstract boolean readLocked();

    public abstract void readLock();

    public abstract boolean writeLocked();

    public abstract void writeLock();

    public abstract ALUType getALU();
}
