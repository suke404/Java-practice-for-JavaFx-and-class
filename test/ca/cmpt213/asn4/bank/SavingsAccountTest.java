package ca.cmpt213.asn4.bank;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The CheckingAccount class extends the abstract BankAccount class
 * to provide specific functionalities for a checking account.
 */
public class SavingsAccountTest {
    private SavingsAccount account;

    @Before
    public void setUp() {
        account = new SavingsAccount(50, 0.05);
    }

    @Test
    public void testInitialStatus() {
        assertTrue(account.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawInactive() {
        account.withdraw(30); // balance goes to 20, account becomes inactive
        account.withdraw(5);  // should throw exception
    }

    @Test
    public void testDepositActivateAccount() {
        account.withdraw(30); // balance goes to 20, account becomes inactive
        assertFalse(account.isActive());
        account.deposit(10);  // balance goes to 30, account becomes active
        assertTrue(account.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        account.deposit(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount() {
        account.withdraw(-10);
    }

    @Test
    public void testMonthlyProcess() {
        for (int i = 0; i < 5; i++) {
            account.withdraw(1); // 5 withdrawals
        }
        account.monthlyProcess();
        assertEquals(44.18, account.getBalance(), 0.01);
        assertEquals(0, account.getDepositsThisMonth());
        assertEquals(0, account.getWithdrawalsThisMonth());
        assertEquals(0, account.getMonthlyServiceCharges(), 0.0);
    }

    @Test
    public void testBalanceAfterMonthlyProcess() {
        account.deposit(50);
        for (int i = 0; i < 5; i++) {
            account.withdraw(1); // 5 withdrawals
        }
        account.monthlyProcess();
        assertEquals(94.39, account.getBalance(), 0.01); // balance after service charge and interest calculation
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawMoreThanBalance() {
        account.withdraw(100); // more than balance
    }
}
