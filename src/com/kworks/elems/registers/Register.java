package com.kworks.elems.registers;

/**
 * Created by Kerith on 26/04/2015.
 */
public interface Register {

    public String getID();

    public abstract boolean readLocked();

    public abstract void readLock();

    public abstract void readUnlock();

    public abstract boolean writeLocked();

    public abstract void writeLock();

    public abstract void writeUnlock();
}
