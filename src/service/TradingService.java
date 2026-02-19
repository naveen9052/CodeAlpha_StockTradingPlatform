package service;

import model.Stock;
import model.Transaction;
import model.User;
import util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradingService {
    private final User user;
    private final Map<String, Stock> marketStocks;
    private final Map<String, Integer> portfolio;
    private final List<Transaction> transactions;
    private final String portfolioFilePath;

    public TradingService(User user, String portfolioFilePath) {
        this.user = user;
        this.portfolioFilePath = portfolioFilePath;
        this.marketStocks = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.portfolio = FileUtil.loadPortfolio(portfolioFilePath);
        seedStocks();
    }

    private void seedStocks() {
        marketStocks.put("AAPL", new Stock("AAPL", 190.00));
        marketStocks.put("GOOGL", new Stock("GOOGL", 145.00));
        marketStocks.put("TSLA", new Stock("TSLA", 210.00));
        marketStocks.put("MSFT", new Stock("MSFT", 410.00));
        marketStocks.put("AMZN", new Stock("AMZN", 175.00));
    }

    public Map<String, Stock> getMarketStocks() {
        return marketStocks;
    }

    public User getUser() {
        return user;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean buyStock(String symbol, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        String normalized = symbol.toUpperCase();
        Stock stock = marketStocks.get(normalized);
        if (stock == null) {
            return false;
        }

        double totalCost = stock.getPrice() * quantity;
        if (user.getBalance() < totalCost) {
            return false;
        }

        user.setBalance(user.getBalance() - totalCost);
        portfolio.put(normalized, portfolio.getOrDefault(normalized, 0) + quantity);
        transactions.add(new Transaction("BUY", normalized, quantity, stock.getPrice()));
        savePortfolio();
        return true;
    }

    public boolean sellStock(String symbol, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        String normalized = symbol.toUpperCase();
        Stock stock = marketStocks.get(normalized);
        if (stock == null) {
            return false;
        }

        int owned = portfolio.getOrDefault(normalized, 0);
        if (owned < quantity) {
            return false;
        }

        double totalValue = stock.getPrice() * quantity;
        user.setBalance(user.getBalance() + totalValue);
        int remaining = owned - quantity;
        if (remaining == 0) {
            portfolio.remove(normalized);
        } else {
            portfolio.put(normalized, remaining);
        }
        transactions.add(new Transaction("SELL", normalized, quantity, stock.getPrice()));
        savePortfolio();
        return true;
    }

    private void savePortfolio() {
        FileUtil.savePortfolio(portfolioFilePath, portfolio);
    }
}
