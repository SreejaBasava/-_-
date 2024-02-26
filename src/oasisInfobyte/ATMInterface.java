package oasisInfobyte;

import java.util.ArrayList;
import java.util.Scanner;

public class ATMInterface {
    static class Account {
        private String accountId;
        private double balance;
        private ArrayList<String> transactionHistory;

        public Account(String accountId, double balance) {
            this.accountId = accountId;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
        }

        public String getAccountId() {
            return accountId;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
            transactionHistory.add("Deposit: +" + amount);
        }

        public void withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                transactionHistory.add("Withdraw: -" + amount);
            } else {
                System.out.println("Insufficient balance.");
            }
        }

        public void transfer(Account recipient, double amount) {
            if (balance >= amount) {
                balance -= amount;
                recipient.deposit(amount);
                transactionHistory.add("Transfer: -" + amount + " to " + recipient.getAccountId());
            } else {
                System.out.println("Insufficient balance.");
            }
        }

        public void showTransactionHistory() {
            System.out.println("Transaction History for Account: " + accountId);
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
            System.out.println();
        }
    }

    static class Bank {
        private ArrayList<Account> accounts;

        public Bank() {
            this.accounts = new ArrayList<>();
        }

        public void addAccount(Account account) {
            accounts.add(account);
        }

        public Account getAccount(String accountId) {
            for (Account account : accounts) {
                if (account.getAccountId().equals(accountId)) {
                    return account;
                }
            }
            return null;
        }
    }

    static class ATM {
        private Account currentAccount;
        private Bank bank;
        private Scanner scanner;

        public ATM(Bank bank) {
            this.bank = bank;
            this.scanner = new Scanner(System.in);
        }

        public void run() {
            System.out.println("Welcome to the ATM!");
            System.out.print("Enter account ID: ");
            String accountId = scanner.nextLine();
            currentAccount = bank.getAccount(accountId);

            if (currentAccount != null) {
                System.out.println("Account found. Please proceed.");
                showMenu();
            } else {
                System.out.println("Account not found. Exiting...");
            }
        }

        private void showBalance() {
            double balance = currentAccount.getBalance();
            System.out.println("Current Balance: " + balance);
        }

        private void showTransactionHistory() {
            currentAccount.showTransactionHistory();
        }

        private void performWithdrawal() {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            currentAccount.withdraw(amount);
        }

        private void performDeposit() {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            currentAccount.deposit(amount);
        }

        private void performTransfer() {
            System.out.print("Enter recipient account ID: ");
            String recipientAccountId = scanner.nextLine();
            Account recipientAccount = bank.getAccount(recipientAccountId);

            if (recipientAccount != null) {
                System.out.print("Enter amount to transfer: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                currentAccount.transfer(recipientAccount, amount);
            } else {
                System.out.println("Recipient account not found.");
            }
        }

        private void showMenu() {
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== ATM Menu ===");
                System.out.println("1. Show Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Show Balance");
                System.out.println("6. Quit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        showTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal();
                        break;
                    case 3:
                        performDeposit();
                        break;
                    case 4:
                        performTransfer();
                        break;
                    case 5:
                        showBalance();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        // Creating some accounts
        Account account1 = new Account("AC001", 15000);
        Account account2 = new Account("AC002", 10000);
        Account account3 = new Account("AC003", 5000);

        // Adding the accounts to the bank
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);

        // Starting the ATM
        ATM atm = new ATM(bank);
        atm.run();
    }
}

