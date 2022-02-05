package com.example.rentalmanagement;

public class DATA {
    DATA(){}

    String desc,rentpermonth,status;

    public DATA(String desc, String rentpermonth,String status) {
        this.desc = desc;
        this.rentpermonth = rentpermonth;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRentpermonth() {
        return rentpermonth;
    }

    public void setRentpermonth(String rentpermonth) {
        this.rentpermonth = rentpermonth;
    }

    public DATA(String desc){
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
