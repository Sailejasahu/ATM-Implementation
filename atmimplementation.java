import java.util.*;
import java.time.*;

abstract class BankAccount {
    private String accountHolderName;
    private double balance;
    private String phoneNumber;
    private List<String> transactionHistory = new ArrayList<>();
    private double dailyWithdrawn = 0;
    private static final double DAILY_LIMIT = 25000;

    public BankAccount(String name, double balance, String phone) {
        this.accountHolderName = name;
        this.balance = balance;
        this.phoneNumber = phone;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void updatePhoneNumber(String newNumber) {
        this.phoneNumber = newNumber;
        System.out.println("Phone number updated.");
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: ₹" + amount);
        } else {
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance && (dailyWithdrawn + amount) <= DAILY_LIMIT) {
            balance -= amount;
            dailyWithdrawn += amount;
            System.out.println("Withdrawn: ₹" + amount);
        } else {
            System.out.println("Invalid amount or daily limit exceeded.");
        }
    }

    public void displayDetails() {
        System.out.println("Date & Time: " + LocalDateTime.now());
        System.out.println("Name: " + accountHolderName);
        System.out.println("Balance: ₹" + balance);
        System.out.println("Phone: " + phoneNumber);
    }

    public void showTransactions() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String t : transactionHistory) {
                System.out.println(t);
            }
        }
    }
    private void addTransaction(String detail) {
    transactionHistory.add(LocalDateTime.now() + " - " + detail);
}


    

    public void resetDailyLimit() {
        dailyWithdrawn = 0;
    }
}

class SavingsAccount extends BankAccount {
    public SavingsAccount(String name, double balance, String phone) {
        super(name, balance, phone);
    }
}

public class atmimplementation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String correctPin = "124";
        int otp = 2304;
        int attempts = 0;
        boolean isLocked = false;

        SavingsAccount account = new SavingsAccount("Saileja", 1000.00, "9876543210");

        while (true) {
            if (isLocked) {
                System.out.println("Account locked due to 3 incorrect PIN attempts.");
                return;
            }

            System.out.print("Enter your ATM PIN: ");
            String enteredPin = sc.nextLine();

            if (!enteredPin.equals(correctPin)) {
                attempts++;
                if (attempts == 3) {
                    isLocked = true;
                } else {
                    System.out.println("Incorrect PIN. Attempts left: " + (3 - attempts));
                }
                continue;
            }

            System.out.println("Welcome, " + account.getAccountHolderName());
            boolean logout = false;
            account.resetDailyLimit();

            while (!logout) {
                System.out.println("\n1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Display Details");
                System.out.println("4. Change PIN");
                System.out.println("5. Transaction History");
                System.out.println("6. Update Phone Number");
                System.out.println("7. Logout");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double dep = sc.nextDouble();
                        account.deposit(dep);
                        
                        System.out.println("Current Balance: ₹" + account.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double wd = sc.nextDouble();
                        account.withdraw(wd);
                       
                        System.out.println("Current Balance: ₹" + account.getBalance());
                        break;

                    case 3:
                        account.displayDetails();
                        break;

                    case 4:
                        System.out.print("Enter OTP: ");
                        int enteredOtp = sc.nextInt();
                        if (enteredOtp == otp) {
                            System.out.print("Enter new PIN: ");
                            correctPin = sc.next();
                            System.out.println("PIN changed successfully.");
                        } else {
                            System.out.println("Incorrect OTP.");
                        }
                        break;

                    case 5:
                        account.showTransactions();
                        break;

                    case 6:
                        System.out.print("Enter new phone number: ");
                        String newPhone = sc.nextLine();
                        account.updatePhoneNumber(newPhone);
                        break;

                    case 7:
                        logout = true;
                        System.out.println("Logged out successfully.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }
}
