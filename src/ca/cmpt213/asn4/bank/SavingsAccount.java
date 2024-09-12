package ca.cmpt213.asn4.bank;

/**
 * The SavingsAccount class represents a savings account that inherits from the BankAccount class.
 * It has additional functionality to handle the active status of the account based on the balance.
 */
public class SavingsAccount extends BankAccount {
    private boolean isActive;

    // Constructor
    public SavingsAccount(double balance, double annualInterestRate) {
        super(balance, annualInterestRate);
        this.isActive = balance >= 25;
    }

    // Check if account is active
    private void checkStatus() {
        isActive = getBalance() >= 25;
    }

    @Override
    public void withdraw(double amount) {
        checkStatus();
        if (!isActive) {
            throw new IllegalArgumentException("Account is inactive, withdrawals are not allowed.");
        }
        super.withdraw(amount);
        checkStatus();
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        super.deposit(amount);
        checkStatus();
    }

    @Override
    public void monthlyProcess() {
        if (getWithdrawalsThisMonth() > 4) {
            setMonthlyServiceCharges(getMonthlyServiceCharges() + (getWithdrawalsThisMonth() - 4));
        }
        super.monthlyProcess();
        checkStatus();
    }

    public boolean isActive() {
        return isActive;
    }
}
