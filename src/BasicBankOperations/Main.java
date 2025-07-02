package task_5.BankOperations.src.BasicBankOperations;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🏦 Welcome to Bank Account Simulation!");
        System.out.println("=".repeat(50));

        // Create some sample accounts for demonstration
        Account johnAccount = new Account("John Doe", 1000.00);
        Account janeAccount = new Account("Jane Smith", 500.00);
        accounts.add(johnAccount);
        accounts.add(janeAccount);

        System.out.println("✅ Sample accounts created:");
        System.out.println("- John Doe (Initial: $1000.00)");
        System.out.println("- Jane Smith (Initial: $500.00)");

        // Interactive menu loop
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    performWithdrawal();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    viewAccountInfo();
                    break;
                case 7:
                    viewTransactionHistory();
                    break;
                case 8:
                    displayAllAccounts();
                    break;
                case 9:
                    System.out.println("Thank you for using Bank Account Simulation! Goodbye! 👋");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }

            waitForEnter();
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                 MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1.  Create New Account");
        System.out.println("2.  Deposit Money");
        System.out.println("3.  Withdraw Money");
        System.out.println("4.  Transfer Money");
        System.out.println("5.  Check Balance");
        System.out.println("6.  View Account Information");
        System.out.println("7.  View Transaction History");
        System.out.println("8.  Display All Accounts");
        System.out.println("9.  Exit");
        System.out.println("=".repeat(50));
    }

    private static void createNewAccount() {
        System.out.println("\n📝 Create New Account");
        System.out.println("-".repeat(30));

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Account holder name cannot be empty!");
            return;
        }

        double initialDeposit = getDoubleInput("Enter initial deposit amount (0 for none): $");
        if (initialDeposit < 0) {
            System.out.println("❌ Initial deposit cannot be negative!");
            return;
        }

        Account newAccount = new Account(name, initialDeposit);
        accounts.add(newAccount);

        System.out.printf("🎉 Account created successfully!%n");
        System.out.printf("Account Number: %s%n", newAccount.getAccountNumber());
        System.out.printf("Account Holder: %s%n", newAccount.getAccountHolder());
        System.out.printf("Initial Balance: $%.2f%n", newAccount.getBalance());
    }

    private static void performDeposit() {
        System.out.println("\n💰 Deposit Money");
        System.out.println("-".repeat(20));

        Account account = selectAccount();
        if (account == null)
            return;

        double amount = getDoubleInput("Enter deposit amount: $");
        account.deposit(amount);
    }

    private static void performWithdrawal() {
        System.out.println("\n💸 Withdraw Money");
        System.out.println("-".repeat(20));

        Account account = selectAccount();
        if (account == null)
            return;

        double amount = getDoubleInput("Enter withdrawal amount: $");
        account.withdraw(amount);
    }

    private static void performTransfer() {
        System.out.println("\n🔄 Transfer Money");
        System.out.println("-".repeat(20));

        if (accounts.size() < 2) {
            System.out.println("❌ Need at least 2 accounts to perform transfer!");
            return;
        }

        System.out.println("Select SOURCE account (money will be taken from):");
        Account sourceAccount = selectAccount();
        if (sourceAccount == null)
            return;

        System.out.println("Select DESTINATION account (money will be sent to):");
        Account destAccount = selectAccount();
        if (destAccount == null)
            return;

        if (sourceAccount == destAccount) {
            System.out.println("❌ Cannot transfer money to the same account!");
            return;
        }

        double amount = getDoubleInput("Enter transfer amount: $");
        sourceAccount.transfer(destAccount, amount);
    }

    private static void checkBalance() {
        System.out.println("\n💳 Check Balance");
        System.out.println("-".repeat(20));

        Account account = selectAccount();
        if (account == null)
            return;

        System.out.printf("Current Balance: $%.2f%n", account.getBalance());
    }

    private static void viewAccountInfo() {
        System.out.println("\n📊 Account Information");
        System.out.println("-".repeat(25));

        Account account = selectAccount();
        if (account == null)
            return;

        account.displayAccountInfo();
    }

    private static void viewTransactionHistory() {
        System.out.println("\n📜 Transaction History");
        System.out.println("-".repeat(25));

        Account account = selectAccount();
        if (account == null)
            return;

        if (account.getTransactionCount() == 0) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("\n1. View All Transactions");
        System.out.println("2. View Recent Transactions");
        int choice = getIntInput("Choose option: ");

        switch (choice) {
            case 1:
                account.displayTransactionHistory();
                break;
            case 2:
                int count = getIntInput("Enter number of recent transactions to view: ");
                if (count > 0) {
                    account.displayRecentTransactions(count);
                } else {
                    System.out.println("❌ Please enter a positive number!");
                }
                break;
            default:
                System.out.println("❌ Invalid choice!");
        }
    }

    private static void displayAllAccounts() {
        System.out.println("\n🏦 All Bank Accounts");
        System.out.println("-".repeat(25));

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.printf("%-12s | %-15s | %-10s | %s%n",
                "ACCOUNT #", "HOLDER", "BALANCE", "TRANSACTIONS");
        System.out.println("-".repeat(60));

        double totalBalance = 0;
        for (Account account : accounts) {
            System.out.printf("%-12s | %-15s | $%-9.2f | %d%n",
                    account.getAccountNumber(),
                    account.getAccountHolder(),
                    account.getBalance(),
                    account.getTransactionCount());
            totalBalance += account.getBalance();
        }

        System.out.println("-".repeat(60));
        System.out.printf("Total Accounts: %d | Total Balance: $%.2f%n", accounts.size(), totalBalance);
    }

    private static Account selectAccount() {
        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts available! Please create an account first.");
            return null;
        }

        System.out.println("\nAvailable Accounts:");
        System.out.println("-".repeat(50));
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.printf("%d. %s (%s) - Balance: $%.2f%n",
                    i + 1, acc.getAccountHolder(), acc.getAccountNumber(), acc.getBalance());
        }
        System.out.println("-".repeat(50));

        int choice = getIntInput("Select account number: ");
        if (choice < 1 || choice > accounts.size()) {
            System.out.println("❌ Invalid account selection!");
            return null;
        }

        return accounts.get(choice - 1);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount!");
            }
        }
    }

    private static void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}