package controllertest;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

import model.MainModelInterface;

/**
 * A mock implementation of MainModelInterface to test the controller.
 */
class MockModel implements MainModelInterface {

  private final StringBuilder logger;
  private final String demo = "Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n" +
          "NVDA,NVIDIA Corp,10,20.4,2024-03-25\nMSFT,Microsoft Corporation," +
          "15,30.5,2024-03-25\n" +
          "IBM,International Business Machines Corp,10,100.0,2024-03-25\n" +
          "GOOG,Alphabet Inc - Class C,12.0,134.2,2024-03-04\n";

  private final String demo2 =
          "NVDA,NVIDIA Corp,10,20.4,2024-03-25\nMSFT,Microsoft Corporation," +
                  "15,30.5,2024-03-25\n" +
                  "IBM,International Business Machines Corp,10,100.0,2024-03-25\n" +
                  "GOOG,Alphabet Inc - Class C,12.0,134.2,2024-03-04\n";
  private String name;

  MockModel(StringBuilder logger) {
    this.logger = logger;
  }

  /**
   * It interacts with the portfolio class to set the name of the portfolio.
   *
   * @param name is the name of the portfolio.
   * @throws IllegalArgumentException if portfolio name is invalid.
   */
  @Override
  public void setPortfolioName(String name) throws IllegalArgumentException {
    logger.append(name)
            .append(" ");
    this.name = name;
  }

  /**
   * It interacts with the portfolio class to add a stock portfolio.
   *
   * @param ticker      is the ticker symbol.
   * @param companyName is the company name.
   * @param quantity    is the purchase quantity.
   * @param price       is the price.
   * @param date        is the date at which stock is bought.
   * @throws IllegalArgumentException if the purchase quantity or price is invalid, or the portfolio
   *                                  is not loaded.
   */
  @Override
  public void addStocksToPortfolio(String ticker, String companyName, double quantity, double price,
                                   LocalDate date) throws IllegalArgumentException {
    logger
            .append(ticker)
            .append(" ")
            .append(companyName)
            .append(" ")
            .append(quantity)
            .append(" ")
            .append(date.toString());


  }

  /**
   * Method to interacts with portfolio class to load a portfolio.
   *
   * @param name is the name of the portfolio.
   * @param data is the stock data to be loaded.
   * @throws IllegalArgumentException is name or stock data is invalid.
   */
  @Override
  public void loadPortfolio(String name, String data) throws IllegalArgumentException {
    this.setPortfolioName(name);
  }

  /**
   * Method to return the portfolio value.
   *
   * @param date   is the date at which the portfolio value needs to be calculated.
   * @param values is a ticker,price pair for stocks present in portfolio.
   * @return the total value of the portfolio.
   * @throws IllegalArgumentException if there are no stocks in portfolio or the date or values are
   *                                  invalid.
   */
  @Override
  public double getPortfolioValue(String date, Map<String, Double> values)
          throws IllegalArgumentException {
    logger
            .append(date);
    return 0;
  }

  /**
   * Method to return a String of all the portfolios present.
   *
   * @param files is an array of consisting of the paths of all files present in the folder.
   * @return only the  valid portfolio names present in the files as a comma separated string.
   * @throws IllegalArgumentException if there are no files present.
   */
  @Override
  public String listPortfolio(File[] files) throws IllegalArgumentException {
    return null;
  }

  /**
   * Method to return a formatted string which has stock details of a portfolio.
   *
   * @return a formatted string containing stock details.
   * @throws IllegalArgumentException if the portfolio is not loaded, or there are no stocks present
   *                                  in the portfolio.
   */
  @Override
  public String savePortfolio() throws IllegalArgumentException {
    return demo;
  }

  /**
   * Method to return a String containing the composition of the portfolio.
   *
   * @return a formatted string containing the composition of the portfolio.
   * @throws IllegalArgumentException if the portfolio is not loaded, or there are no stocks present
   *                                  in the portfolio.
   */
  @Override
  public String showComposition() throws IllegalArgumentException {
    return demo2;
  }

  /**
   * Method to sell the given number of stocks from the current portfolio.
   *
   * @param ticker   ticker value of the stock to be sold as an integer.
   * @param quantity quantity of the socks to be sold.
   * @param date     date to sell.
   * @return String of updated portfolio.
   * @throws IllegalArgumentException id the quantity is invalid or portfolio does not include the
   *                                  stock.
   */
  @Override
  public String sellStocks(String ticker, double quantity, String date)
          throws IllegalArgumentException {
    logger
            .append(ticker)
            .append(" ")
            .append(quantity)
            .append(" ")
            .append(date);
    return demo;
  }

  /**
   * Returns the name of the current portfolio.
   *
   * @return String name of portfolio.
   */
  @Override
  public String getCurrentPortfolioName() {
    return name;
  }

  /**
   * Returns the cost basis of the portfolio.
   *
   * @param transactions is the logger data.
   * @param date         is the date at which cost basis is to be calculated.
   * @return a double value as the cost basis
   */
  @Override
  public double getCostBasis(String transactions, String date) {
    logger
            .append(date);
    return 0;
  }

  @Override
  public String stockGainLose(String data, String ticker, String date) {
    logger
            .append(ticker)
            .append(" ")
            .append(date);
    return "The stock has gained value.";
  }

  @Override
  public String stockGainLoseTimeSpan(String data, String ticker, String date, String date2) {
    logger
            .append(ticker)
            .append(" ")
            .append(date)
            .append(" ")
            .append(date2);
    return "The stock has gained value.";
  }

  @Override
  public double xDayMov(String data, String ticker, String date, int x) {

    logger
            .append(ticker)
            .append(" ")
            .append(date)
            .append(" ")
            .append(x);
    return 0;
  }

  @Override
  public String calcCrossover(String data, String ticker, String startDate, String endDate) {
    logger
            .append(ticker)
            .append(" ")
            .append(startDate)
            .append(" ")
            .append(endDate);
    return "2024-03-14--->POSITIVE\n"
            + "2024-02-15--->NEGATIVE\n"
            + "2024-02-05--->POSITIVE\n"
            + "2024-01-31--->NEGATIVE\n"
            + "2023-12-18--->POSITIVE\n"
            + "2023-12-12--->NEGATIVE\n"
            + "2023-12-07--->POSITIVE\n"
            + "2023-12-04--->NEGATIVE\n"
            + "2023-12-01--->NEGATIVE\n"
            + "2023-11-14--->POSITIVE\n"
            + "2023-10-25--->NEGATIVE";
  }

  @Override
  public String calcMovingCrossover(String data, String ticker, String startDate, String endDate,
                                    int x, int y) {
    logger
            .append(ticker)
            .append(" ")
            .append(startDate)
            .append(" ")
            .append(endDate);
    return "No moving crossover";
  }

  @Override
  public String getPerformance(LocalDate date1, LocalDate date2, String data, String ticker) {
    logger
            .append(date1)
            .append(" ")
            .append(date2);
    return null;
  }

  @Override
  public String getPortfolioPerformance(LocalDate date1, LocalDate date2, String data) {
    logger
            .append(date1)
            .append(" ")
            .append(date2);
    return null;
  }
}
