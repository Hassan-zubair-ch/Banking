package banking.service;

import banking.model.BankAccount;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private int accountCounter = 1001;

    public BankAccount createAccount(String holderName, double initialDeposit) {
        if (holderName == null || holderName.trim().isEmpty())
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        if (initialDeposit < 0)
            throw new IllegalArgumentException("Initial deposit cannot be negative.");

        String accountNumber = "ACC" + accountCounter++;
        BankAccount account = new BankAccount(holderName.trim(), accountNumber, initialDeposit);
        accounts.put(accountNumber, account);
        return account;
    }

    public BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null)
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        return account;
    }

    public void deposit(String accountNumber, double amount) {
        getAccount(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, double amount) {
        getAccount(accountNumber).withdraw(amount);
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        if (fromAccount.equals(toAccount))
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }

    public Map<String, BankAccount> getAllAccounts() {
        return accounts;
    }
}