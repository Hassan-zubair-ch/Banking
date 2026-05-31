package banking.ui;

import banking.model.BankAccount;
import banking.service.BankService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BankingAppUI extends JFrame {

    private BankService bankService = new BankService();
    private DefaultTableModel tableModel;

    private JTextField nameField, depositField, accountField, amountField;
    private JTextField transferFromField, transferToField, transferAmountField;
    private JLabel statusLabel;

    public BankingAppUI() {
        setTitle("Simple Banking App");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Create Account"));
        panel.setBackground(new Color(230, 240, 255));

        nameField    = new JTextField();
        depositField = new JTextField("0.00");

        panel.add(labeledField("Holder Name:", nameField));
        panel.add(labeledField("Initial Deposit (Rs.):", depositField));

        JButton createBtn = new JButton("Create Account");
        createBtn.setBackground(new Color(0, 102, 204));
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(e -> handleCreateAccount());

        JPanel btnPanel = new JPanel();
        btnPanel.add(createBtn);
        panel.add(btnPanel);

        return panel;
    }

    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.add(buildDepositWithdrawPanel());
        panel.add(buildTransferPanel());
        panel.add(buildAccountsTablePanel());
        return panel;
    }

    private JPanel buildDepositWithdrawPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Deposit / Withdraw"));
        panel.setBackground(new Color(240, 255, 240));

        accountField = new JTextField();
        amountField  = new JTextField();

        panel.add(new JLabel("Account Number:"));
        panel.add(accountField);
        panel.add(new JLabel("Amount (Rs.):"));
        panel.add(amountField);

        JButton depositBtn  = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");

        depositBtn.setBackground(new Color(0, 153, 51));
        depositBtn.setForeground(Color.WHITE);
        withdrawBtn.setBackground(new Color(204, 0, 0));
        withdrawBtn.setForeground(Color.WHITE);

        depositBtn.addActionListener(e -> handleDeposit());
        withdrawBtn.addActionListener(e -> handleWithdraw());

        panel.add(depositBtn);
        panel.add(withdrawBtn);

        return panel;
    }

    private JPanel buildTransferPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Transfer"));
        panel.setBackground(new Color(255, 255, 230));

        transferFromField   = new JTextField();
        transferToField     = new JTextField();
        transferAmountField = new JTextField();

        panel.add(new JLabel("From Account:"));
        panel.add(transferFromField);
        panel.add(new JLabel("To Account:"));
        panel.add(transferToField);
        panel.add(new JLabel("Amount (Rs.):"));
        panel.add(transferAmountField);

        JButton transferBtn = new JButton("Transfer");
        transferBtn.setBackground(new Color(153, 0, 153));
        transferBtn.setForeground(Color.WHITE);
        transferBtn.addActionListener(e -> handleTransfer());

        panel.add(transferBtn);
        return panel;
    }

    private JPanel buildAccountsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("All Accounts"));

        String[] columns = {"Account No.", "Holder", "Balance (Rs.)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildStatusBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Welcome to Simple Banking App");
        statusLabel.setForeground(new Color(0, 100, 0));
        panel.add(statusLabel);
        return panel;
    }

    private void handleCreateAccount() {
        try {
            String name    = nameField.getText();
            double deposit = parseAmount(depositField.getText());
            BankAccount acc = bankService.createAccount(name, deposit);
            setStatus("✅ Account created: " + acc.getAccountNumber(), true);
            nameField.setText("");
            depositField.setText("0.00");
            refreshTable();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void handleDeposit() {
        try {
            String accNo  = accountField.getText().trim();
            double amount = parseAmount(amountField.getText());
            bankService.deposit(accNo, amount);
            setStatus("✅ Deposited Rs. " + amount + " to " + accNo, true);
            refreshTable();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void handleWithdraw() {
        try {
            String accNo  = accountField.getText().trim();
            double amount = parseAmount(amountField.getText());
            bankService.withdraw(accNo, amount);
            setStatus("✅ Withdrew Rs. " + amount + " from " + accNo, true);
            refreshTable();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void handleTransfer() {
        try {
            String from   = transferFromField.getText().trim();
            String to     = transferToField.getText().trim();
            double amount = parseAmount(transferAmountField.getText());
            bankService.transfer(from, to, amount);
            setStatus("✅ Transferred Rs. " + amount + " from " + from + " to " + to, true);
            refreshTable();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private double parseAmount(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount entered. Please enter a number.");
        }
    }

    private JPanel labeledField(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        p.setOpaque(false);
        return p;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (BankAccount acc : bankService.getAllAccounts().values()) {
            tableModel.addRow(new Object[]{
                    acc.getAccountNumber(),
                    acc.getAccountHolder(),
                    String.format("%.2f", acc.getBalance())
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        setStatus("❌ " + message, false);
    }

    private void setStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setForeground(success ? new Color(0, 128, 0) : Color.RED);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankingAppUI::new);
    }
}