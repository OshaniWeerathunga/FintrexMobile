package com.fintrex.fintrexfinance.HelperClass;

public class Lease {

    private String leaseNo, totalOutstanding,nextPayDate,maturityDate,rental,lastPayAmount,lastPayDate,vehicleNo,insuranceExpire;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Lease(String leaseNo, String totalOutstanding, String nextPayDate, String maturityDate,String rental, String lastPayAmount, String lastPayDate, String vehicleNo, String insuranceExpire) {
        this.leaseNo = leaseNo;
        this.totalOutstanding = totalOutstanding;
        this.nextPayDate = nextPayDate;
        this.maturityDate = maturityDate;
        this.rental = rental;
        this.lastPayAmount = lastPayAmount;
        this.lastPayDate = lastPayDate;
        this.vehicleNo = vehicleNo;
        this.insuranceExpire = insuranceExpire;
        this.expanded = expanded;
    }

    public String getLeaseNo() {
        return leaseNo;
    }

    public void setLeaseNo(String leaseNo) {
        this.leaseNo = leaseNo;
    }

    public String getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(String totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public String getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getLastPayAmount() {
        return lastPayAmount;
    }

    public void setLastPayAmount(String lastPayAmount) {
        this.lastPayAmount = lastPayAmount;
    }

    public String getLastPayDate() {
        return lastPayDate;
    }

    public void setLastPayDate(String lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getInsuranceExpire() {
        return insuranceExpire;
    }

    public void setInsuranceExpire(String insuranceExpire) {
        this.insuranceExpire = insuranceExpire;
    }
}

