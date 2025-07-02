package task_5.BankOperations.src.BasicBankOperations;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Transaction class to represent individual transactions
class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private double balanceAfter;
    
    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getBalanceAfter() { return balanceAfter; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%-10s | $%8.2f | $%10.2f | %s", 
            type, amount, balanceAfter, timestamp.format(formatter));
    }
}

// Account class representing a bank account
public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<Transaction> transactionHistory;
    private static int accountCounter = 1000;
    
    // Constructor with account holder name and initial deposit
    public Account(String accountHolder, double initialDeposit) {
        this.accountNumber = "ACC" + (++accountCounter);
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        
        // Make initial deposit if provided
        if (initialDeposit > 0) {
            deposit(initialDeposit);
        }
    }
    
    // Constructor with only account holder name (no initial deposit)
    public Account(String accountHolder) {
        this(accountHolder, 0.0);
    }
    
    // Deposit method - adds money to account
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Deposit amount must be positive!");
            return false;
        }
        
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance));
        System.out.printf("✅ Successfully deposited $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }
    
    // Withdraw method - removes money from account
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Withdrawal amount must be positive!");
            return false;
        }
        
        if (amount > balance) {
            System.out.printf("❌ Insufficient funds! Available balance: $%.2f%n", balance);
            return false;
        }
        
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAW", amount, balance));
        System.out.printf("✅ Successfully withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }
    
    // Transfer method - transfers money to another account
    public boolean transfer(Account recipient, double amount) {
        if (recipient == null) {
            System.out.println("❌ Invalid recipient account!");
            return false;
        }
        
        if (amount <= 0) {
            System.out.println("❌ Transfer amount must be positive!");
            return false;
        }
        
        if (amount > balance) {
            System.out.printf("❌ Insufficient funds for transfer! Available balance: $%.2f%n", balance);
            return false;
        }
        
        // Withdraw from this account
        balance -= amount;
        transactionHistory.add(new Transaction("TRANSFER OUT", amount, balance));
        
        // Deposit to recipient account
        recipient.balance += amount;
        recipient.transactionHistory.add(new Transaction("TRANSFER IN", amount, recipient.balance));
        
        System.out.printf("✅ Successfully transferred $%.2f to %s. Your new balance: $%.2f%n", 
            amount, recipient.accountHolder, balance);
        return true;
    }
    
    // Get current account balance
    public double getBalance() {
        return balance;
    }
    
    // Display complete account information
    public void displayAccountInfo() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           ACCOUNT INFORMATION");
        System.out.println("=".repeat(50));
        System.out.printf("Account Number: %s%n", accountNumber);
        System.out.printf("Account Holder: %s%n", accountHolder);
        System.out.printf("Current Balance: $%.2f%n", balance);
        System.out.printf("Total Transactions: %d%n", transactionHistory.size());
        System.out.println("=".repeat(50));
    }
    
    // Display complete transaction history
    public void displayTransactionHistory() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    TRANSACTION HISTORY");
        System.out.println("=".repeat(70));
        
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.printf("%-10s | %-8s | %-10s | %s%n", 
                "TYPE", "AMOUNT", "BALANCE", "TIMESTAMP");
            System.out.println("-".repeat(70));
            
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
        System.out.println("=".repeat(70));
    }
    
    // Display recent transactions (last n transactions)
    public void displayRecentTransactions(int count) {
        System.out.printf("%n=== LAST %d TRANSACTIONS ===%n", count);
        
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        int start = Math.max(0, transactionHistory.size() - count);
        List<Transaction> recent = transactionHistory.subList(start, transactionHistory.size());
        
        System.out.printf("%-10s | %-8s | %-10s | %s%n", 
            "TYPE", "AMOUNT", "BALANCE", "TIMESTAMP");
        System.out.println("-".repeat(70));
        
        for (Transaction transaction : recent) {
            System.out.println(transaction);
        }
    }
    
    // Check if account has sufficient funds
    public boolean hasSufficientFunds(double amount) {
        return balance >= amount;
    }
    
    // Get account summary as string
    public String getAccountSummary() {
        return String.format("Account: %s | Holder: %s | Balance: $%.2f | Transactions: %d",
            accountNumber, accountHolder, balance, transactionHistory.size());
    }
    
    // Getters
    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public String getAccountHolder() { 
        return accountHolder; 
    }
    
    public int getTransactionCount() { 
        return transactionHistory.size(); 
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return copy to maintain encapsulation
    }
}