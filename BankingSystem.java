import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ". New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount <= balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred " + amount + " to " + toAccount.getAccountHolderName());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void printStatement() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}

class Bank {
    private ArrayList<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(String accountHolderName, double initialDeposit) {
        String accountNumber = "ACC" + (accounts.size() + 1);
        BankAccount newAccount = new BankAccount(accountNumber, accountHolderName, initialDeposit);
        accounts.add(newAccount);
        System.out.println("Account created. Account number: " + accountNumber);
        return newAccount;
    }

    public BankAccount getAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        System.out.println("Welcome to the Banking System!");
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter initial deposit: ");
        double deposit = sc.nextDouble();

        BankAccount account = bank.createAccount(name, deposit);

        boolean running = true;
        while (running) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Transfer\n4. Account Statement\n5. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter deposit amount: ");
                    double depositAmount = sc.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount: ");
                    double withdrawalAmount = sc.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 3:
                    System.out.println("Enter recipient account number: ");
                    String recipientAccountNumber = sc.next();
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = sc.nextDouble();
                    BankAccount recipientAccount = bank.getAccount(recipientAccountNumber);
                    if (recipientAccount != null) {
                        account.transfer(recipientAccount, transferAmount);
                    } else {
                        System.out.println("Recipient account not found.");
                    }
                    break;
                case 4:
                    account.printStatement();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        sc.close();
    }
}
