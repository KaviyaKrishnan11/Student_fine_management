package com.zoho.college;

public class FineType {

    private int fineId;
    private String fineType;
    private double amount;

    public FineType(int fineId, String fineType, double amount) {
        this.fineId = fineId;
        this.fineType = fineType;
        this.amount = amount;
    }

    public int getFineId() { return fineId; }
    public String getFineType() { return fineType; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return fineId + " | " + fineType + " | " + amount;
    }
}
