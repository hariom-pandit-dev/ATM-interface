import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner scanner;
    private final List<Transaction> transactions = new ArrayList<>();

    public ATM(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("================================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("================================");

        Account currentAccount = authenticate();
        if (currentAccount == null) {
            System.out.println("Access denied. Too many incorrect attempts.");
            return;
        }

        System.out.println("\\nLogin successful. Welcome, " + currentAccount.getUserId() + "!");
        runMenu(currentAccount);
    }

    private Account authenticate() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.findAccount(userId);
            if (account != null && account.verifyPin(pin)) {
                return account;
            }

            System.out.println("Incorrect User ID or PIN. Attempts remaining: " + (3 - attempt));
        }
        return null;
    }

    private void runMenu(Account account) {
        boolean running = true;

        while (running) {
            System.out.println("\\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from 1 to 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    showHistory();
                    break;
                case 2:
                    withdraw(account);
                    break;
                case 3:
                    deposit(account);
                    break;
                case 4:
                    transfer(account);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 to 5.");
            }
        }
    }

    private void showHistory() {
        System.out.println("\\n======= TRANSACTION HISTORY =======");
        if (transactions.isEmpty()) {
            System.out.println("No transactions in this session yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void withdraw(Account account) {
        double amount = readAmount("Enter amount to withdraw: Rs. ");
        if (amount <= 0) {
            System.out.println("Please enter an amount greater than zero.");
            return;
        }

        if (!account.hasSufficientFunds(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        account.withdraw(amount);
        transactions.add(new Transaction("WITHDRAW", "Cash withdrawal", amount));
        System.out.printf("Withdrawal successful. Remaining balance: Rs. %.2f%n", account.getBalance());
    }

    private void deposit(Account account) {
        double amount = readAmount("Enter amount to deposit: Rs. ");
        if (amount <= 0) {
            System.out.println("Please enter an amount greater than zero.");
            return;
        }

        account.deposit(amount);
        transactions.add(new Transaction("DEPOSIT", "Cash deposit", amount));
        System.out.printf("Deposit successful. Updated balance: Rs. %.2f%n", account.getBalance());
    }

    private void transfer(Account sender) {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine().trim();

        double amount = readAmount("Enter amount to transfer: Rs. ");
        if (amount <= 0) {
            System.out.println("Please enter an amount greater than zero.");
            return;
        }

        Account recipient = bank.findAccount(recipientId);
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }
        if (recipient == sender) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }
        if (!sender.hasSufficientFunds(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        boolean success = bank.transfer(sender, recipientId, amount);
        if (success) {
            transactions.add(new Transaction("TRANSFER",
                    "Transfer to " + recipientId, amount));
            System.out.printf("Transfer successful. Remaining balance: Rs. %.2f%n",
                    sender.getBalance());
        } else {
            System.out.println("Transfer could not be completed.");
        }
    }

    private double readAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a numeric value.");
            return -1;
        }
    }
}