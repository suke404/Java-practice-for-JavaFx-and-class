package ca.cmpt213.asn4.bank;

/**
 * The BankAccount class represents a simple bank account with basic functionality
 * such as deposits, withdrawals, and calculating interest. It also keeps track of
 * the number of deposits and withdrawals made within a month and applies monthly
 * service charges. This class is intended to be used as a base class for more
 * specific types of bank accounts.
 */
public abstract class BankAccount {
    private double balance;
    private int depositsThisMonth;
    private int withdrawalsThisMonth;
    private double annualInterestRate;
    private double monthlyServiceCharges;

    // Constructor
    public BankAccount(double balance, double annualInterestRate) {
        if (balance < 0 || annualInterestRate < 0) {
            throw new IllegalArgumentException("Balance and interest rate must be non-negative");
        }
        this.balance = balance;
        this.annualInterestRate = annualInterestRate;
        this.depositsThisMonth = 0;
        this.withdrawalsThisMonth = 0;
        this.monthlyServiceCharges = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public int getDepositsThisMonth() {
        return depositsThisMonth;
    }

    public int getWithdrawalsThisMonth() {
        return withdrawalsThisMonth;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public double getMonthlyServiceCharges() {
        return monthlyServiceCharges;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.annualInterestRate = annualInterestRate;
    }

    public void setMonthlyServiceCharges(double monthlyServiceCharges) {
        if (monthlyServiceCharges < 0) {
            throw new IllegalArgumentException("Service charges cannot be negative");
        }
        this.monthlyServiceCharges = monthlyServiceCharges;
    }

    // Methods to handle deposits and withdrawals
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        depositsThisMonth++;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
        withdrawalsThisMonth++;

    }

    // Method to calculate monthly interest
    public void calcInterest() {
        double monthlyInterestRate = annualInterestRate / 12;
        double monthlyInterest = balance * monthlyInterestRate;
        balance += monthlyInterest;
    }

    // Method to process monthly activities
    public void monthlyProcess() {
        if(withdrawalsThisMonth > 4) {
            monthlyServiceCharges = withdrawalsThisMonth - 4;
        }
        balance -= monthlyServiceCharges;
        calcInterest();
        depositsThisMonth = 0;
        withdrawalsThisMonth = 0;
        monthlyServiceCharges = 0.0;
    }
}
