package com.zoho.college;

public abstract class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String role;

    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "UserID: " + userId + ", Role: " + role + ", Username: " + username;
    }
}
