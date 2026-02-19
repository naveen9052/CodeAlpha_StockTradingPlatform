package main;

import model.Stock;
import model.Transaction;
import model.User;
import service.TradingService;

import java.util.Map;
import java.util.Scanner;

public class MainApp {
    private static final String DATA_FILE = "data/portfolio.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User("Naveen", 10000.00);
        TradingService tradingService = new TradingService(user, DATA_FILE);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showMarketData(tradingService);
                    break;
                case "2":
                    handleBuy(scanner, tradingService);
                    break;
                case "3":
                    handleSell(scanner, tradingService);
                    break;
                case "4":
                    showPortfolio(tradingService);
                    break;
                case "5":
                    showTransactions(tradingService);
                    break;
                case "6":
                    running = false;
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== CodeAlpha Stock Trading Platform ===");
        System.out.println("1. View Market Stocks");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Transactions");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    private static void showMarketData(TradingService tradingService) {
        System.out.println("Available Stocks:");
        for (Stock stock : tradingService.getMarketStocks().values()) {
            System.out.printf("- %s: %.2f%n", stock.getSymbol(), stock.getPrice());
        }
    }

    private static void handleBuy(Scanner scanner, TradingService tradingService) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().trim();
        System.out.print("Enter quantity: ");
        int quantity = parsePositiveInt(scanner.nextLine().trim());

        if (quantity <= 0) {
            System.out.println("Quantity must be a positive integer.");
            return;
        }

        boolean success = tradingService.buyStock(symbol, quantity);
        if (success) {
            System.out.println("Stock bought successfully.");
        } else {
            System.out.println("Buy failed. Check symbol, balance, or quantity.");
        }
    }

    private static void handleSell(Scanner scanner, TradingService tradingService) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().trim();
        System.out.print("Enter quantity: ");
        int quantity = parsePositiveInt(scanner.nextLine().trim());

        if (quantity <= 0) {
            System.out.println("Quantity must be a positive integer.");
            return;
        }

        boolean success = tradingService.sellStock(symbol, quantity);
        if (success) {
            System.out.println("Stock sold successfully.");
        } else {
            System.out.println("Sell failed. Check symbol, holdings, or quantity.");
        }
    }

    private static void showPortfolio(TradingService tradingService) {
        System.out.printf("Cash Balance: %.2f%n", tradingService.getUser().getBalance());
        Map<String, Integer> portfolio = tradingService.getPortfolio();
        if (portfolio.isEmpty()) {
            System.out.println("Portfolio is empty.");
            return;
        }

        System.out.println("Holdings:");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            System.out.printf("- %s: %d%n", entry.getKey(), entry.getValue());
        }
    }

    private static void showTransactions(TradingService tradingService) {
        if (tradingService.getTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("Transaction History:");
        for (Transaction transaction : tradingService.getTransactions()) {
            System.out.println("- " + transaction);
        }
    }

    private static int parsePositiveInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
