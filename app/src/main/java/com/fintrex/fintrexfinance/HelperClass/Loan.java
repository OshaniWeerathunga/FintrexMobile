package com.fintrex.fintrexfinance.HelperClass;

public class Loan {

    private String loanNo,loanOutstanding,loanInstallment,loanNextDue,loanAmount,loanmaturity,loanLastPayAmount,loanLastPayDate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Loan(String loanNo, String loanOutstanding, String loanInstallment, String loanNextDue, String loanAmount, String loanmaturity, String loanLastPayAmount, String loanLastPayDate) {
        this.loanNo = loanNo;
        this.loanOutstanding = loanOutstanding;
        this.loanInstallment = loanInstallment;
        this.loanNextDue = loanNextDue;
        this.loanAmount = loanAmount;
        this.loanmaturity = loanmaturity;
        this.loanLastPayAmount = loanLastPayAmount;
        this.loanLastPayDate = loanLastPayDate;
        this.expanded = expanded;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getLoanOutstanding() {
        return loanOutstanding;
    }

    public void setLoanOutstanding(String loanOutstanding) {
        this.loanOutstanding = loanOutstanding;
    }

    public String getLoanInstallment() {
        return loanInstallment;
    }

    public void setLoanInstallment(String loanInstallment) {
        this.loanInstallment = loanInstallment;
    }

    public String getLoanNextDue() {
        return loanNextDue;
    }

    public void setLoanNextDue(String loanNextDue) {
        this.loanNextDue = loanNextDue;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanmaturity() {
        return loanmaturity;
    }

    public void setLoanmaturity(String loanmaturity) {
        this.loanmaturity = loanmaturity;
    }

    public String getLoanLastPayAmount() {
        return loanLastPayAmount;
    }

    public void setLoanLastPayAmount(String loanLastPayAmount) {
        this.loanLastPayAmount = loanLastPayAmount;
    }

    public String getLoanLastPayDate() {
        return loanLastPayDate;
    }

    public void setLoanLastPayDate(String loanLastPayDate) {
        this.loanLastPayDate = loanLastPayDate;
    }
}
