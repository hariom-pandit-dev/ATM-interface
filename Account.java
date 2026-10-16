public class Account {
    private final String userId;
    private final String pin;
    private double balance;

    public Account(String userId, String pin, double openingBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = openingBalance;
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasSufficientFunds(double amount) {
        return amount > 0 && balance >= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (!hasSufficientFunds(amount)) {
            return false;
        }
        balance -= amount;
        return true;
    }
}