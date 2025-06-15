
# Stock Trading Application

A comprehensive stock portfolio management tool supporting both Command Line Interface (CLI) and Graphical User Interface (GUI). Users can manage portfolios, simulate investment strategies like dollar-cost averaging, analyze performance, and visualize data, all built on a robust Model-View-Controller (MVC) architecture using only standard JDK libraries.

---

## 🚀 Features

### ✅ Portfolio Management
- Create flexible portfolios with multiple stocks.
- Add stocks via ticker or company name.
- Load/save portfolios in human-readable CSV format.
- Restrict invalid portfolios (e.g., no stocks or fractional quantities).
- Edit portfolios (buy/sell shares) with date tracking.

### 📈 Analysis & Reporting
- View portfolio value on a specific date.
- Query cost basis (total investment) on a date.
- Gain/loss insights for individual stocks over dates/ranges.
- Compute X-day moving averages.
- Identify positive/negative moving average crossovers.
- Generate bar charts (relative/absolute scale) for stocks or portfolios.

### 📊 Investment Strategies
- Fixed investment into existing portfolio with custom stock weights.
- One-time and recurring dollar-cost averaging strategies.
- Intelligent handling of non-trading days (e.g., holidays).
- Extendable long-term investment strategy interface.

### 🖥️ Interface Support
- Fully functional interactive CLI.
- Java Swing-based GUI:
  - Intuitive design for creating/loading portfolios.
  - Execute all investment and analysis features.
  - Visualize bar charts.
  - Support for flexible strategy-based investing.

---

## 🧱 Architecture Overview

This application uses an MVC (Model-View-Controller) design pattern:

### Model
- Handles portfolio, stock, and API logic.
- Separation between core computation and external API access.
- Uses caching for offline support and API rate limit handling.
- Contains interfaces like `StockInterface`, `PortfolioInterface`, and `StockOperationInterface`.

### View
- Responsible only for output.
- Two implementations: CLI and GUI.
- Shares common functionalities via an `AbstractView`.

### Controller
- Manages input and flow of the application.
- Two controllers: one for CLI and one for GUI, both implementing a shared interface.
- Manages file I/O, caching, API access, logging, and feature command delegation.

---

## 📦 Setup & Usage

### Requirements
- Java JDK installed.
- AlphaVantage API key (pre-integrated; caching limits dependency).

### Running the Application

1. Place `StockTrading.jar` in a directory.
2. Create a `portfolio/` directory alongside the jar to store CSV portfolios.
3. Launch:

```bash
# For GUI
java -jar StockTrading.jar

# For CLI
java -jar StockTrading.jar cli
```

### Sample Portfolio Format

Create CSV files like `P1.csv` inside the `portfolio/` folder:

```
Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date
MSFT,Microsoft Corporation,10,404.52,2024-03-11
GOOG,Alphabet Inc - Class C,10,138.94,2024-03-11
```

---

## 🧩 Notes

- The app supports offline usage via cached data for previously queried stocks.
- Real-time data for new stocks requires an internet connection.
- Does not accept future dates for most features (except for long-term strategies).
- Portfolios cannot be altered once saved.
- Avoid tampering with `cache/` and `transactions/` folders.

---

## 📚 Dependencies

- **Java Standard Library (JDK only)** — no external libraries.
- **AlphaVantage API** — used for real-time and historical stock data.

---

## 🛠 Limitations

- No support for today's or future dates in queries, except in strategy setup.
- Reliance on AlphaVantage—any API changes may affect functionality.

---

## 👨‍💻 Contributing

This project was built for academic purposes. If you're extending it or using it as a base, consider:
- Adding support for other stock APIs.
- Implementing new investment strategies via the `LongTermInvestmentStrategies` interface.
- Enhancing the GUI with more visual analytics.
