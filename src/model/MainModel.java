package model;


import java.io.File;
import java.time.LocalDate;
import java.util.Map;


/**
 * This class implements the model in our model view controller (MVC) architecture. It implements
 * methods to perform all the functionalities on a given portfolio.
 * Changes: New methods have been added to the model so that the controller can communicate with
 * model and get the data for additional features.
 */
public class MainModel implements MainModelInterface {

  private PortfolioInterface currentPortfolio;

  /**
   * Constructor to create a new portfolio object.
   */
  public MainModel() {
    currentPortfolio = new Portfolio();
  }

  @Override
  public void setPortfolioName(String name) throws IllegalArgumentException {
    if (!name.matches("[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9]+)*")) {
      throw new IllegalArgumentException("Invalid Portfolio name");
    }
    currentPortfolio = new Portfolio();
    currentPortfolio.setPortfolioName(name);
  }

  /**
   * It interacts with the portfolio class to add a stock portfolio.
   * Changes: This method has an additional parameter named date which adds the stock on the given
   * date.
   *
   * @param ticker      is the ticker symbol.
   * @param companyName is the company name.
   * @param quantity    is the purchase quantity.
   * @param price       is the price.
   * @param date        is the date at which stock is to be added.
   * @throws IllegalArgumentException if quantity is invalid.
   */
  @Override
  public void addStocksToPortfolio(String ticker, String companyName, double quantity, double price,
                                   LocalDate date)
          throws IllegalArgumentException {
    currentPortfolio.addStocks(ticker, companyName, quantity, price, date);
  }

  @Override
  public void loadPortfolio(String name, String data) throws IllegalArgumentException {
    if (!name.matches("[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9]+)*")) {
      throw new IllegalArgumentException("Invalid Portfolio name");
    }
    try {
      Portfolio temp = new Portfolio();
      temp.setPortfolioName(name);
      temp.loadPortfolio(data);
      currentPortfolio = temp;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Selected portfolio file is not a valid csv file.");
    }
  }

  @Override
  public double getPortfolioValue(String date, Map<String, Double> values)
          throws IllegalArgumentException {
    return currentPortfolio.getPortfolioValue(date, values);
  }

  @Override
  public String listPortfolio(File[] files) throws IllegalArgumentException {
    StringBuilder portfolios = new StringBuilder();
    if (files != null) {
      // Loop through files
      try {
        for (File file : files) {
          // Check if the file is a CSV file
          if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
            portfolios.append(file.getName().split("\\.")[0]);
            portfolios.append(",");
          }
        }
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("There are no portfolios present.");
      }
    }
    return portfolios.toString();
  }

  @Override
  public String savePortfolio() throws IllegalArgumentException {
    return currentPortfolio.savePortfolio();
  }

  @Override
  public String showComposition() throws IllegalArgumentException {
    return currentPortfolio.showPortfolioComposition();
  }

  @Override
  public String sellStocks(String ticker, double quantity, String date)
          throws IllegalArgumentException {
    return currentPortfolio.sellStocks(ticker, quantity, date);
  }

  @Override
  public String getCurrentPortfolioName() {
    return currentPortfolio.getPortfolioName();
  }

  @Override
  public double getCostBasis(String transactions, String date) {
    return currentPortfolio.getCostBasis(transactions, date);
  }

  @Override
  public String stockGainLose(String data, String ticker, String date) {
    StockOperationInterface stk = new StockOperations(ticker);
    int res = stk.gainLose(data, ticker, date);
    return calcGainLose(res);
  }


  @Override
  public double xDayMov(String data, String ticker, String date, int x) {
    StockOperationInterface stk = new StockOperations(ticker);
    return stk.xDayMovingAvg(data, ticker, date, x);
  }

  @Override
  public String calcCrossover(String data, String ticker, String startDate, String endDate) {
    StockOperationInterface stk = new StockOperations(ticker);
    return stk.crossovers(data, ticker, startDate, endDate);
  }

  @Override
  public String calcMovingCrossover(String data, String ticker, String startDate, String endDate,
                                    int x, int y) {
    StockOperationInterface stk = new StockOperations(ticker);
    return stk.movingCrossover(data, ticker, startDate, endDate, x, y);
  }

  @Override
  public String getPerformance(LocalDate date1, LocalDate date2, String data, String ticker) {
    StockOperationInterface stk = new StockOperations(ticker);
    return stk.getPerformance(date1, date2, data);
  }

  @Override
  public String getPortfolioPerformance(LocalDate date1, LocalDate date2, String data) {
    return currentPortfolio.getPortfolioPerformance(date1, date2, data);
  }

  @Override
  public String stockGainLoseTimeSpan(String data, String ticker, String date, String date2) {
    StockOperationInterface stk = new StockOperations(ticker);
    int res = stk.gainLoseTimeSpan(data, ticker, date, date2);
    return calcGainLose(res);
  }

  private String calcGainLose(int res) {
    if (res == 0) {
      return "No loss or gain in stock.";
    } else if (res > 0) {
      return "The stock has lost value.";
    } else {
      return "The stock has gained value.";
    }
  }
}
