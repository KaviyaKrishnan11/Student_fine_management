package com.zoho.college;

import java.util.Date;

public class Payment {
    int paymentId;
    int studentId;
    String fineType;
    double amount;
    Date date;

    public Payment(int paymentId, int studentId, String fineType, double amount, Date date) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.fineType = fineType;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentID: " + paymentId +
               ", StudentID: " + studentId +
               ", FineType: " + fineType +
               ", Amount: " + amount +
               ", Date: " + date;
    }
}
