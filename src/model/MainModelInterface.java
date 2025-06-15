package model;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

/**
 * This interface represents the model component of the model-view-controller (MVC) architecture. It
 * defines methods for processing and computing data. Implementations of this interface are
 * responsible to perform all calculations and algorithms required on the data.
 */
public interface MainModelInterface {

  /**
   * It interacts with the portfolio class to set the name of the portfolio.
   *
   * @param name is the name of the portfolio.
   * @throws IllegalArgumentException if portfolio name is invalid.
   */
  void setPortfolioName(String name) throws IllegalArgumentException;

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
   * @throws IllegalArgumentException if the purchase quantity or price is invalid, or the portfolio
   *                                  is not loaded.
   */
  void addStocksToPortfolio(String ticker, String companyName, double quantity, double price,
                            LocalDate date)
          throws IllegalArgumentException;

  /**
   * Method to interacts with portfolio class to load a portfolio.
   *
   * @param name is the name of the portfolio.
   * @param data is the stock data to be loaded.
   * @throws IllegalArgumentException is name or stock data is invalid.
   */
  void loadPortfolio(String name, String data) throws IllegalArgumentException;

  /**
   * Method to return the portfolio value.
   *
   * @param date   is the date at which the portfolio value needs to be calculated.
   * @param values is a ticker,price pair for stocks present in portfolio.
   * @return the total value of the portfolio.
   * @throws IllegalArgumentException if there are no stocks in portfolio or the date or values are
   *                                  invalid.
   */
  double getPortfolioValue(String date, Map<String, Double> values)
          throws IllegalArgumentException;

  /**
   * Method to return a String of all the portfolios present.
   *
   * @param files is an array of consisting of the paths of all files present in the folder.
   * @return only the  valid portfolio names present in the files as a comma separated string.
   * @throws IllegalArgumentException if there are no files present.
   */
  String listPortfolio(File[] files) throws IllegalArgumentException;

  /**
   * Method to return a formatted string which has stock details of a portfolio.
   *
   * @return a formatted string containing stock details.
   * @throws IllegalArgumentException if the portfolio is not loaded, or there are no stocks present
   *                                  in the portfolio.
   */
  String savePortfolio() throws IllegalArgumentException;

  /**
   * Method to return a String containing the composition of the portfolio.
   *
   * @return a formatted string containing the composition of the portfolio.
   * @throws IllegalArgumentException if the portfolio is not loaded, or there are no stocks present
   *                                  in the portfolio.
   */
  String showComposition() throws IllegalArgumentException;

  /**
   * Method to sell the given number of stocks from the current portfolio.
   *
   * @param ticker   ticker value of the stock to be sold as an integer.
   * @param quantity quantity of the socks to be sold.
   * @param date     date to sell.
   * @return String of updated portfolio.
   * @throws IllegalArgumentException if the quantity is invalid or portfolio does not include the
   *                                  stock.
   */
  String sellStocks(String ticker, double quantity, String date) throws IllegalArgumentException;


  /**
   * Returns the name of the current portfolio.
   *
   * @return String name of portfolio.
   */
  String getCurrentPortfolioName();

  /**
   * Returns the cost basis of the portfolio.
   *
   * @return a double value as the cost basis.
   */
  double getCostBasis(String transactions, String date);

  /**
   * Returns a message indicating whether a particular stock has gained or lost value over the
   * given day (open price vs close price).
   *
   * @param data   is the stock data.
   * @param ticker is the stock ticker symbol.
   * @param date   is the date at which analysis needs to be done.
   * @return a message indicating whether the stock has gained or lost value.
   */
  String stockGainLose(String data, String ticker, String date);

  /**
   * Returns a message indicating whether a particular stock has gained or lost value over the
   * given period of time(close price vs close price).
   *
   * @param data   is the stock data.
   * @param ticker is the ticker symbol of stock.
   * @param date   is the start date for analysis.
   * @param date2  is the end date for analysis.
   * @return a message indicating whether the stock has gained or lost value.
   */
  String stockGainLoseTimeSpan(String data, String ticker, String date, String date2);

  /**
   * Method to calculate the X Day Moving Average of a stock.
   *
   * @param data   is the stock data.
   * @param ticker is the ticker symbol.
   * @param date   is the date from which moving average needs to be calculated.
   * @param x      is the number of days including given day for which value needs to be calculated.
   * @return the X Day moving average for the stock.
   */
  double xDayMov(String data, String ticker, String date, int x);

  /**
   * Method to calculate the crossover days for a stock over a given period of time.
   *
   * @param data      is the stock data.
   * @param ticker    is the ticker symbol of stock.
   * @param startDate is the starting date for the analysis.
   * @param endDate   is the end date for the analysis.
   * @return a String composed of dates and indication whether the crossover is positive or
   *     negative.
   */
  String calcCrossover(String data, String ticker, String startDate, String endDate);

  /**
   * Method to calculate the moving crossover days for a stock over a given period of time.
   *
   * @param data      is the stock data.
   * @param ticker    is the ticker symbol of stock.
   * @param startDate is the starting date for the analysis.
   * @param endDate   is the end date for the analysis.
   * @param x         is the number of days to calculate X day average from the start date.
   * @param y         is the number of days to calculate X day average from the end date.
   * @return a string composed of dates and indication whether the moving crossover is positive
   *     or negative.
   */
  String calcMovingCrossover(String data, String ticker, String startDate, String endDate, int x,
                             int y);

  /**
   * Method to get the bar chart performance for a given stock.
   *
   * @param date1  is the starting date of analysis.
   * @param date2  is the ending date for analysis.
   * @param data   is the stock data.
   * @param ticker is the ticker symbol of stock.
   * @return a string representing the bar chart for a stock.
   */
  String getPerformance(LocalDate date1, LocalDate date2, String data, String ticker);

  /**
   * Method to get the bar chart performance for a given portfolio.
   *
   * @param date1 is the starting date of analysis.
   * @param date2 is the ending date for analysis.
   * @param data  is the stock data.
   * @return a string representing the bar chart for a portfolio.
   */
  String getPortfolioPerformance(LocalDate date1, LocalDate date2, String data);
}
