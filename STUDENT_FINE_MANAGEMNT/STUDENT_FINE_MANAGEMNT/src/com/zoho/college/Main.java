package com.zoho.college;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    static List<User> users = new ArrayList<>();
    static List<Payment> payments = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        users.add(new Admin(1, "admin", "admin123"));
        users.add(new Cashier(2, "cashier", "cash123"));
        users.add(new Student(3, "student", "stud123", "abinaya", 500.0));

        System.out.println("Welcome to Student Fine Management System");

        while (true) {
            User loggedIn = null;
            int attempts = 0;
            while (attempts < 5 && loggedIn == null) {
                System.out.println("\n1. Login\n2. Register (Student Only)\n3. Exit");
                System.out.print("Choose option: ");
                int opt;
                try {
                    opt = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                if (opt == 1) {
                    System.out.print("Enter username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();
                    loggedIn = login(uname, pass);
                    if (loggedIn == null) {
                        attempts++;
                        System.out.println("Invalid credentials! Attempts left: " + (5 - attempts));
                    }
                } else if (opt == 2) {
                    registerStudent();
                } else if (opt == 3) {
                    System.out.println("Exiting system...");
                    return;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            }

            if (loggedIn == null) {
                System.out.println("Too many failed attempts. Exiting...");
                return;
            }
            switch (loggedIn.role) {
                case "Admin": adminMenu(); break;
                case "Cashier": cashierMenu(); break;
                case "Student": studentMenu((Student) loggedIn); break;
                default: System.out.println("Unknown role. Logging out."); break;
            }
            System.out.println("\nYou have logged out. Returning to login screen...\n");
        }
    }

    static User login(String uname, String pass) {
        for (User u : users) {
            if (u.username.equals(uname) && u.password.equals(pass)) return u;
        }
        return null;
    }

    static void registerStudent() {
        System.out.print("Enter new student username: ");
        String uname = sc.nextLine().trim();
        if (uname.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        for (User u : users) {
            if (u.username.equals(uname)) {
                System.out.println("Username already exists. Choose another.");
                return;
            }
        }
        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double bal;
        try {
            bal = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance. Registration cancelled.");
            return;
        }

        int id = generateUserId();
        users.add(new Student(id, uname, pass, name, bal));
        System.out.println("Student registered successfully! Your StudentID is " + id + ". You can now login.");
    }

    static int generateUserId() {
        int max = 0;
        for (User u : users) if (u.userId > max) max = u.userId;
        return max + 1;
    }
    static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View all payments");
            System.out.println("2. Filter by fine type");
            System.out.println("3. Filter by student");
            System.out.println("4. Filter by date");
            System.out.println("5. Filter fine type within date range");
            System.out.println("6. Add payment");
            System.out.println("7. Update payment");
            System.out.println("8. Delete payment");
            System.out.println("9. Register new student");
            System.out.println("10. Logout");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: viewAllPayments(); break;
                case 2: filterByFineType(); break;
                case 3: filterByStudent(); break;
                case 4: filterByDate(); break;
                case 5: filterFineTypeWithinDate(); break;
                case 6: addPayment(); break;
                case 7: updatePayment(); break;
                case 8: deletePayment(); break;
                case 9: registerStudent(); break;
                case 10: return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }
    static void cashierMenu() {
        while (true) {
            System.out.println("\nCashier Menu:");
            System.out.println("1. Add payment");
            System.out.println("2. Update payment");
            System.out.println("3. Delete payment");
            System.out.println("4. Logout");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: addPayment(); break;
                case 2: updatePayment(); break;
                case 3: deletePayment(); break;
                case 4: return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }
    static void studentMenu(Student s) {
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View profile");
            System.out.println("2. Edit profile");
            System.out.println("3. View transactions");
            System.out.println("4. View balance");
            System.out.println("5. Logout");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println(s);
                    break;
                case 2:
                    editProfile(s);
                    break;
                case 3:
                    viewStudentTransactions(s.userId);
                    break;
                case 4:
                    System.out.println("Balance: " + s.getBalance());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    static void addPayment() {
        System.out.print("Enter studentId: ");
        int sid;
        try {
            sid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid studentId.");
            return;
        }
        Student st = findStudentById(sid);
        if (st == null) {
            System.out.println("Student not found. Payment not added.");
            return;
        }

        System.out.print("Enter fine type: ");
        String fine = sc.nextLine();
        System.out.print("Enter amount: ");
        double amt;
        try {
            amt = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        Date date = new Date();
        Payment p = new Payment(payments.size() + 1, sid, fine, amt, date);
        payments.add(p);
        double newBal = st.getBalance() - amt;
        st.setBalance(newBal);

        System.out.println("Payment added. New student balance: " + st.getBalance());
    }

    static void updatePayment() {
        System.out.print("Enter paymentId to update: ");
        int pid;
        try {
            pid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid paymentId.");
            return;
        }
        Payment target = null;
        for (Payment p : payments) {
            if (p.paymentId == pid) {
                target = p;
                break;
            }
        }
        if (target == null) {
            System.out.println("Payment not found.");
            return;
        }

        System.out.println("Existing: " + target);
        System.out.print("Enter new fine type (leave blank to keep): ");
        String fine = sc.nextLine();
        System.out.print("Enter new amount (leave blank to keep): ");
        String amtStr = sc.nextLine();

        String newFine = fine.isEmpty() ? target.fineType : fine;
        double newAmt;
        if (amtStr.isEmpty()) {
            newAmt = target.amount;
        } else {
            try {
                newAmt = Double.parseDouble(amtStr.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Update cancelled.");
                return;
            }
        }
        Student st = findStudentById(target.studentId);
        if (st != null) {
            st.setBalance(st.getBalance() + target.amount - newAmt);
        }

        Payment updated = new Payment(pid, target.studentId, newFine, newAmt, new Date());
        int idx = payments.indexOf(target);
        payments.set(idx, updated);
        System.out.println("Payment updated.");
    }

    static void deletePayment() {
        System.out.print("Enter paymentId to delete: ");
        int pid;
        try {
            pid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid paymentId.");
            return;
        }
        Iterator<Payment> it = payments.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            Payment p = it.next();
            if (p.paymentId == pid) {
                Student st = findStudentById(p.studentId);
                if (st != null) st.setBalance(st.getBalance() + p.amount);
                it.remove();
                removed = true;
                break;
            }
        }
        if (removed) System.out.println("Payment deleted.");
        else System.out.println("Payment not found.");
    }
    static void viewAllPayments() {
        if (payments.isEmpty()) {
            System.out.println("No payments recorded.");
            return;
        }
        for (Payment p : payments) System.out.println(p);
    }

    static void filterByFineType() {
        System.out.print("Enter fine type: ");
        String fine = sc.nextLine();
        boolean found = false;
        for (Payment p : payments) {
            if (p.fineType.equalsIgnoreCase(fine)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No payments found for fine type: " + fine);
    }

    static void filterByStudent() {
        System.out.print("Enter studentId: ");
        int sid;
        try {
            sid = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid studentId.");
            return;
        }
        boolean found = false;
        for (Payment p : payments) {
            if (p.studentId == sid) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No payments found for studentId: " + sid);
    }

    static void filterByDate() {
        System.out.print("Enter date (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        Date targetDate;
        try {
            targetDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }
        boolean found = false;
        for (Payment p : payments) {
            String pd = sdf.format(p.date);
            if (pd.equals(sdf.format(targetDate))) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No payments found on date: " + dateStr);
    }

    static void filterFineTypeWithinDate() {
        System.out.print("Enter fine type: ");
        String fine = sc.nextLine();
        System.out.print("Enter start date (yyyy-mm-dd): ");
        String start = sc.nextLine();
        System.out.print("Enter end date (yyyy-mm-dd): ");
        String end = sc.nextLine();

        Date startDate, endDate;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        boolean found = false;
        for (Payment p : payments) {
            if (!p.fineType.equalsIgnoreCase(fine)) continue;
            Date pd = p.date;
            if (!pd.before(startDate) && !pd.after(endDate)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No payments found for given criteria.");
    }
    static void editProfile(Student s) {
        System.out.println("Edit Profile:");
        System.out.println("1. Change name");
        System.out.println("2. Change password");
        System.out.println("3. Update balance");
        System.out.println("4. Cancel");
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = sc.nextLine();
                s.setName(newName);
                System.out.println("Name updated.");
                break;
            case 2:
                System.out.print("Enter new password: ");
                String newPass = sc.nextLine();
                s.password = newPass;
                System.out.println("Password updated.");
                break;
            case 3:
                System.out.print("Enter new balance: ");
                double newBal;
                try {
                    newBal = Double.parseDouble(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid balance.");
                    return;
                }
                s.setBalance(newBal);
                System.out.println("Balance updated.");
                break;
            case 4:
                System.out.println("Cancelled.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    static void viewStudentTransactions(int studentId) {
        boolean found = false;
        for (Payment p : payments) {
            if (p.studentId == studentId) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No transactions found for your account.");
    }

    static Student findStudentById(int id) {
        for (User u : users) {
            if (u instanceof Student && u.userId == id) return (Student) u;
        }
        return null;
    }
}