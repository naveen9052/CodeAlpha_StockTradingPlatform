# CodeAlpha Stock Trading Platform

A simple Java console application to simulate stock trading operations such as buying and selling stocks, viewing portfolio holdings, and tracking transactions.

## Features
- View available market stocks and prices
- Buy stocks (with balance validation)
- Sell stocks (with holdings validation)
- View current portfolio and cash balance
- View transaction history
- Save and load portfolio data from `data/portfolio.txt`

## Project Structure
```text
CodeAlpha_StockTradingPlatform/
|-- src/
|   |-- model/
|   |   |-- Stock.java
|   |   |-- User.java
|   |   `-- Transaction.java
|   |-- service/
|   |   `-- TradingService.java
|   |-- util/
|   |   `-- FileUtil.java
|   `-- main/
|       `-- MainApp.java
|-- data/
|   `-- portfolio.txt
`-- README.md
```

## Requirements
- Java JDK 8+
- PowerShell or Command Prompt

## Compile
```powershell
mkdir out
javac -d out src/main/MainApp.java src/service/TradingService.java src/util/FileUtil.java src/model/Stock.java src/model/User.java src/model/Transaction.java
```

## Run
```powershell
java -cp out main.MainApp
```

## Sample Menu
```text
=== CodeAlpha Stock Trading Platform ===
1. View Market Stocks
2. Buy Stock
3. Sell Stock
4. View Portfolio
5. View Transactions
6. Exit
```

## Screenshots
Use these filenames for the screenshots you captured:
- `01-market-overview-and-buy-order.png`
- `02-portfolio-summary-and-transaction-history.png`

Example markdown:
```md
![Market Overview And Buy Order](01-market-overview-and-buy-order.png)
![Portfolio Summary And Transaction History](02-portfolio-summary-and-transaction-history.png)
```

## Author
Naveen
