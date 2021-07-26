package com.fintrex.fintrexfinance.HelperClass;

public class Saving {

    private String savingNo,currentBalance,accountType;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Saving(String savingNo, String currentBalance, String accountType) {
        this.savingNo = savingNo;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        this.expanded = expanded;
    }

    public String getSavingNo() {
        return savingNo;
    }

    public void setSavingNo(String savingNo) {
        this.savingNo = savingNo;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }


}
