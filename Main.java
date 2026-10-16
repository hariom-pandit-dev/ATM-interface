import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank();
        // Demo accounts for this learning project (not connected to a real bank).
        bank.addAccount(new Account("Hariom01", "Hariom@123", 5000.00));
        bank.addAccount(new Account("Hariom02", "Hariom@12345", 3000.00));

        ATM atm = new ATM(bank, scanner);
        atm.start();

        scanner.close();
    }
}