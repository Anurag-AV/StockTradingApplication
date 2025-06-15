package model;

import java.time.LocalDate;

/**
 * This interface represents the various statistical operations that can be calculated for the
 * historical data for a given stock.
 */
public interface StockOperationInterface extends StockInterface {

  int gainLose(String data, String ticker, String date);

  int gainLoseTimeSpan(String data, String ticker, String date1, String date2);

  /**
   * This method calculates the X day moving average for a given stock.
   *
   * @param data   is the historical data of the stock.
   * @param ticker is the ticker symbol of the stock.
   * @param date   is the date from which X day moving average has to be calculated.
   * @param x      is the number of days for which X day moving average has to be calculated.
   * @return the X day moving average of the stock.
   * @throws IllegalArgumentException if the stock market was closed for the given date or no
   *                                  historical data beyond a date is available for the stock.
   */
  double xDayMovingAvg(String data, String ticker, String date, int x)
          throws IllegalArgumentException;

  /**
   * This method calculates the crossovers for a given stock over a given period of time.
   *
   * @param data      is the historical data of the stock.
   * @param ticker    is the ticker symbol of the stock.
   * @param startDate is the start date of the analysis.
   * @param endDate   is the end date of the analysis.
   * @return a String containing dates when crossover occurs.
   * @throws IllegalArgumentException if stock market was closed on the given dates.
   */
  String crossovers(String data, String ticker, String startDate, String endDate)
          throws IllegalArgumentException;

  /**
   * This method calculates the moving crossovers for a given stock over a given period of time.
   *
   * @param data      is the historical data of the stock.
   * @param ticker    is the ticker symbol of the stock.
   * @param startDate is the start date of the analysis.
   * @param endDate   is the end date of the analysis.
   * @param x         is the number of days for X day moving average.
   * @param y         is the number of days for Y day moving average.
   * @return a String containing dates when moving crossover occurs.
   * @throws IllegalArgumentException if stock market was closed on the given dates.
   */
  String movingCrossover(String data, String ticker, String startDate, String endDate, int x, int y)
          throws IllegalArgumentException;

  /**
   * This method computes a bar chart for the given data with appropriate scaling and gaps.
   *
   * @param date1 first date of the rage
   * @param date2 second date of the range
   * @param data  date of the stock
   * @return a string containing all the lines of the chart
   */
  String getPerformance(LocalDate date1, LocalDate date2, String data);
}
