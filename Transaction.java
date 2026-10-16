import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final String details;
    private final double amount;
    private final LocalDateTime time;

    public Transaction(String type, String details, double amount) {
        this.type = type;
        this.details = details;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String timestamp = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return timestamp + " | " + type + " | " + details
                + " | Amount: Rs. " + String.format("%.2f", amount);
    }
}