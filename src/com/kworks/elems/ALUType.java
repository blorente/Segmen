package com.kworks.elems;


public enum ALUType {

    INTEGER(1, false, "IN"), FP_ADD(2, false, "FA"), FP_MUL(3, true, "FM"), FP_DIV(4, false, "FD");

    private int latency;
    private boolean segmented;
    private boolean locked;
    private String name;

     ALUType(int latency, boolean segmented, String name) {
         this.latency = latency;
         this.segmented = segmented;
         this.locked = false;
         this.name = name;
    }

    public int getLatency() {
        return this.latency;
    }

    public void lock() {if (!this.locked) this.locked = true;}
    public void unlock() {this.locked = false;}


    public boolean locked() {
        return locked;
    }

    @Override
    public String toString() {
        return name;
    }
}
