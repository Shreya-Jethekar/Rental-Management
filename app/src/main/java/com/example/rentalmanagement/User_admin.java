package com.example.rentalmanagement;

public class User_admin {
    public  String a_admin_name,a_admin_email,a_admin_password;
    public User_admin(){

    }
    public User_admin(String a_admin_email,String a_admin_password,String a_admin_name) {
        this.a_admin_email= a_admin_email;
        this.a_admin_password= a_admin_password;
        this.a_admin_name=a_admin_name;

    }
}
