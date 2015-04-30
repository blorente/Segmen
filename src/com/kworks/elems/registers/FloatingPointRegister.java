package com.kworks.elems.registers;

public class FloatingPointRegister implements Register {

    boolean rLock, wLock;
    String id;

    public FloatingPointRegister(String id) {
        this.rLock = false;
        this.wLock = false;
        this.id = id;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public boolean readLocked() {
        return this.rLock;
    }

    @Override
    public void readLock() {
        if (!this.rLock)  this.rLock = true;
    }

    @Override
    public void readUnlock() {
        this.rLock = false;
    }

    @Override
    public boolean writeLocked() {
        return this.wLock;
    }

    @Override
    public void writeLock() {
        if (!this.wLock)  this.wLock = true;
    }

    @Override
    public void writeUnlock() {
        this.wLock = false;
    }
}
