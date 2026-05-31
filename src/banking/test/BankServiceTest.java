package banking.test;

import banking.model.BankAccount;
import banking.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

    private BankService service;

    @BeforeEach
    void setUp() {
        service = new BankService();
    }

    @Test
    void testCreateAccount_ValidInput() {
        BankAccount acc = service.createAccount("Ali Khan", 5000);
        assertNotNull(acc);
        assertEquals("Ali Khan", acc.getAccountHolder());
        assertEquals(5000, acc.getBalance());
    }

    @Test
    void testCreateAccount_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createAccount("", 1000));
    }

    @Test
    void testDeposit_ValidAmount_IncreasesBalance() {
        BankAccount acc = service.createAccount("Sara", 1000);
        service.deposit(acc.getAccountNumber(), 500);
        assertEquals(1500, acc.getBalance());
    }

    @Test
    void testWithdraw_InsufficientFunds_ThrowsException() {
        BankAccount acc = service.createAccount("Usman", 200);
        assertThrows(IllegalArgumentException.class,
                () -> service.withdraw(acc.getAccountNumber(), 500));
    }

    @Test
    void testWithdraw_ValidAmount_DecreasesBalance() {
        BankAccount acc = service.createAccount("Fatima", 1000);
        service.withdraw(acc.getAccountNumber(), 300);
        assertEquals(700, acc.getBalance());
    }

    @Test
    void testTransfer_ValidAccounts_UpdatesBothBalances() {
        BankAccount acc1 = service.createAccount("Hamza", 2000);
        BankAccount acc2 = service.createAccount("Zara", 500);
        service.transfer(acc1.getAccountNumber(), acc2.getAccountNumber(), 1000);
        assertEquals(1000, acc1.getBalance());
        assertEquals(1500, acc2.getBalance());
    }

    @Test
    void testTransfer_SameAccount_ThrowsException() {
        BankAccount acc = service.createAccount("Bilal", 1000);
        assertThrows(IllegalArgumentException.class,
                () -> service.transfer(acc.getAccountNumber(), acc.getAccountNumber(), 100));
    }

    @Test
    void testGetAccount_InvalidNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getAccount("ACC9999"));
    }
}