package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * This implementation of this interface will represent a portfolio.
 */
interface PortfolioInterface {

  /**
   * Method to get the name of the portfolio.
   */
  String getPortfolioName();

  /**
   * Method to set the name of the portfolio.
   *
   * @param name is the name of the portfolio.
   */
  void setPortfolioName(String name);

  /**
   * Method to add a stock to the Portfolio.
   *
   * @param ticker      is the ticker symbol of the stock.
   * @param companyName is the company name of the stock.
   * @param quantity    is the quantity of stocks purchased.
   * @param price       is the price at which the stock was purchased.
   * @param date        is the date of buying the stock
   * @throws IllegalArgumentException if the given quantity or price is invalid, or when the stock
   *                                  is already present in the portfolio.
   */
  void addStocks(String ticker, String companyName, double quantity, double price, LocalDate date)
          throws IllegalArgumentException;


  /**
   * Sell a set number of stocks from the available stocks in the portfolio.
   *
   * @param ticker   Is the ticker symbol of the stock to be removed
   * @param quantity Is the quantity of the stocks to be removed
   * @return String of updated portfolio
   * @throws IllegalArgumentException when the quantity is invalid or stock now owned
   */
  String sellStocks(String ticker, double quantity, String date) throws IllegalArgumentException;

  /**
   * Method to return a standard formatted string which can be directly saved as a portfolio without
   * any need of processing.
   *
   * @return a formatted string so that portfolio can be saved.
   */
  String savePortfolio();

  /**
   * This method reads a formatted string and loads the portfolio by adding the portfolio name and
   * stock details present in the string to the portfolio object.
   *
   * @param data is the formatted string which has the stock details.
   * @throws IllegalArgumentException if there are no stocks present in the string, if the string is
   *                                  not formatted correctly, if the string has access or deficient
   *                                  values for each stock or the values present in the string are
   *                                  invalid.
   */
  void loadPortfolio(String data) throws IllegalArgumentException;

  /**
   * Method to get the total value of the portfolio. The total portfolio value is the sum of the
   * price of stocks at a particular date multiplied by the quantity of stock the investor holds.
   *
   * @param date   is the date at which the value needs to be calculated.
   * @param values is a hashmap containing the ticker symbol and the price at that date.
   * @return the total value of the portfolio.
   * @throws IllegalArgumentException if portfolio is not loaded or there are no stocks present in
   *                                  the portfolio or the date is invalid.
   */
  double getPortfolioValue(String date, Map<String, Double> values)
          throws IllegalArgumentException;

  /**
   * Method to generate a formatted string which contains the composition of the portfolio.
   *
   * @return a standard formatted string which contains the portfolio composition.
   * @throws IllegalArgumentException if the portfolio is not loaded, or it does not contain any
   *                                  stocks.
   */
  String showPortfolioComposition() throws IllegalArgumentException;

  /**
   * Method returns the cost basis of the portfolio using transactions.
   *
   * @param transactions transactions data of the portfolio.
   * @param date         date at which the cost basis needs to be calculated.
   * @return cost basis as a double value.
   */
  double getCostBasis(String transactions, String date);

  /**
   * This method computes a bar chart for the portfolio with appropriate scaling and gaps.
   *
   * @param date1 first date of the range.
   * @param date2 second date of the range.
   * @param data  date of the stock.
   * @return a string containing all the lines of the chart.
   */
  String getPortfolioPerformance(LocalDate date1, LocalDate date2, String data);
}
