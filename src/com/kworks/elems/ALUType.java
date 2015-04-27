package com.kworks.elems;


public enum ALUType {

    INTEGER(1, false), FP_ADD(2, false), FP_MUL(3, true), FP_DIV(4, false);

    private int latency;
    private boolean segmented;

     ALUType(int latency, boolean segmented) {
         this.latency = latency;
         this.segmented = segmented;
    }

}
