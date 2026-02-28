package com.zoho.college;

public class Admin extends User {
    public Admin(int userId, String username, String password) {
        super(userId, username, password, "Admin");
    }
}
