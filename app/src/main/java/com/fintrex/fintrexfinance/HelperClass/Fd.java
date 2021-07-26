package com.fintrex.fintrexfinance.HelperClass;

public class Fd {

    private String fdNo,fdValue,fdmaturity,fdRenewal,fdRate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Fd(String fdNo, String fdValue, String fdRate, String maturity, String fdRenewal) {
        this.fdNo = fdNo;
        this.fdValue = fdValue;
        this.fdmaturity = maturity;
        this.fdRenewal = fdRenewal;
        this.fdRate = fdRate;
        this.expanded = expanded;
    }

    public String getFdNo() {
        return fdNo;
    }

    public void setFdNo(String fdNo) {
        this.fdNo = fdNo;
    }

    public String getFdValue() {
        return fdValue;
    }

    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }

    public String getMaturity() {
        return fdmaturity;
    }

    public void setMaturity(String maturity) {
        this.fdmaturity = maturity;
    }

    public String getFdRenewal() {
        return fdRenewal;
    }

    public void setFdRenewal(String fdRenewal) {
        this.fdRenewal = fdRenewal;
    }

    public String getFdRate() {
        return fdRate;
    }

    public void setFdRate(String fdRate) {
        this.fdRate = fdRate;
    }
}
