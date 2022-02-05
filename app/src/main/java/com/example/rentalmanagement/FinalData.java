package com.example.rentalmanagement;

public class FinalData {
   String ShopName,TenantName,TenantMno,PerMonthRent,DefaultMonth,RentPaid,OccupiedMonth,paidamt;
    public FinalData() {

    }

    public String getPaidamt() {
        return paidamt;
    }

    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        this.ShopName = shopName;
    }

    public String getTenantName() {
        return TenantName;
    }

    public void setTenantName(String tenantName) {
        this.TenantName = tenantName;
    }

    public String getTenantMno() {
        return TenantMno;
    }

    public void setTenantMno(String tenantMno) {
        this.TenantMno = tenantMno;
    }

    public String getPerMonthRent() {
        return PerMonthRent;
    }

    public void setPerMonthRent(String perMonthRent) {
        this.PerMonthRent = perMonthRent;
    }

    public String getDefaultMonth() {
        return DefaultMonth;
    }

    public void setDefaultMonth(String defaultMonth) {
        this.DefaultMonth = defaultMonth;
    }

    public String getRentPaid() {
        return RentPaid;
    }

    public void setRentPaid(String rentPaid) {
        this.RentPaid = rentPaid;
    }

    public String getOccupiedMonth() {
        return OccupiedMonth;
    }

    public void setOccupiedMonth(String occupiedMonth) {
        this.OccupiedMonth = occupiedMonth;
    }
}

