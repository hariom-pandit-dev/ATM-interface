import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getUserId(), account);
    }

    public Account findAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean transfer(Account sender, String recipientId, double amount) {
        Account recipient = findAccount(recipientId);

        if (recipient == null || recipient == sender || !sender.hasSufficientFunds(amount)) {
            return false;
        }

        if (!sender.withdraw(amount)) {
            return false;
        }

        recipient.deposit(amount);
        return true;
    }
}