package com.zoho.college;

public class Student extends User {
    private String name;
    private double balance;

    public Student(int userId, String username, String password, String name, double balance) {
        super(userId, username, password, "Student");
        this.name = name;
        this.balance = balance;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "StudentID: " + userId + ", Name: " + name + ", Balance: " + balance;
    }
}
