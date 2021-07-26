package com.fintrex.fintrexfinance.HelperClass;

public class Lc {

    private String lcNo,lcMaturity,lcAmount;

    public Lc(String lcNo, String lcAmount, String lcMaturity) {
        this.lcNo = lcNo;
        this.lcAmount = lcAmount;
        this.lcMaturity = lcMaturity;
    }

    public String getLcNo() {
        return lcNo;
    }

    public String getLcAmount() {
        return lcAmount;
    }

    public String getLaMaturity() {
        return lcMaturity;
    }
}
