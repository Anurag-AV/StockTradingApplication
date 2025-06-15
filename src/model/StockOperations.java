package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * This class is the implementation of stock operation interface which provides us with different
 * statistics to analyze the current trends in an individual stock.
 */
class StockOperations extends Stock implements StockOperationInterface {

  /**
   * Constructor to initialize class variables.
   *
   * @param ticker is the ticker symbol of the stock.
   */
  public StockOperations(String ticker) {
    super(ticker);
  }

  @Override
  public int gainLose(String data, String ticker, String date) {
    checkDate(date);
    String[] prices = data.split("\n");
    int currDataIndex = getIndex(prices, date);
    double price1 = Double.parseDouble(prices[currDataIndex].split(",")[1]);
    double price2 = Double.parseDouble(prices[currDataIndex].split(",")[4]);
    return Double.compare(price1, price2);
  }

  @Override
  public int gainLoseTimeSpan(String data, String ticker, String date1, String date2) {
    checkDate(date1);
    checkDate(date2);
    String[] prices = data.split("\n");
    int currDataIndex = getIndex(prices, date1);
    int prevDataIndex = getIndex(prices, date2);
    double price1 = Double.parseDouble(prices[currDataIndex].split(",")[4]);
    double price2 = Double.parseDouble(prices[prevDataIndex].split(",")[4]);
    return Double.compare(price1, price2);
  }

  private int getIndex(String[] prices, String date) throws IllegalArgumentException {
    int currDataIndex = -1;
    for (int i = 0; i < prices.length; i++) {
      if (prices[i].contains(date)) {
        currDataIndex = i;
        break;
      }
    }
    if (currDataIndex == -1) {
      throw new IllegalArgumentException("The stock market was closed on the given date");
    }
    return currDataIndex;
  }

  @Override
  public double xDayMovingAvg(String data, String ticker, String date, int x)
          throws IllegalArgumentException {
    checkDate(date);
    if (x <= 0) {
      throw new IllegalArgumentException("Enter a valid value for number of days(x)");
    }
    double total = 0;
    String[] prices = data.split("\n");
    int currDataIndex = getIndex(prices, date);

    try {
      for (int i = currDataIndex; i < currDataIndex + x; i++) {
        total += Double.parseDouble(prices[i].split(",")[4]);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("No data available for the given days before " + date);
    }
    return total / x;
  }

  @Override
  public String crossovers(String data, String ticker, String startDate, String endDate)
          throws IllegalArgumentException {
    checkDate(startDate);
    checkDate(endDate);
    StringBuilder crossover = new StringBuilder();
    String[] prices = data.split("\n");
    int startDataIndex = getIndex(prices, startDate);
    int endDataIndex = getIndex(prices, endDate);

    try {
      for (int i = endDataIndex; i < startDataIndex + 1; i++) {
        double thirtyDayAvg = xDayMovingAvg(data, ticker, prices[i].split(",")[0], 30);
        double currClosingPrice = Double.parseDouble(prices[i].split(",")[4]);
        double prevClosingPrice = Double.parseDouble(prices[i + 1].split(",")[4]);

        if (currClosingPrice > thirtyDayAvg && prevClosingPrice < thirtyDayAvg) {
          crossover.append(prices[i].split(",")[0]).append("--->POSITIVE").append(",");
        }
        if (currClosingPrice < thirtyDayAvg && prevClosingPrice > thirtyDayAvg) {
          crossover.append(prices[i].split(",")[0]).append("--->NEGATIVE").append(",");
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
              "Insufficient data to calculate crossover. Please change the date range.");
    }
    if (crossover.toString().isEmpty()) {
      return "No crossovers occurred over the given period.";
    }
    return crossover.toString();
  }

  @Override
  public String movingCrossover(String data, String ticker, String startDate, String endDate, int x,
                                int y) throws IllegalArgumentException {
    checkDate(startDate);
    checkDate(endDate);
    if (x >= y) {
      throw new IllegalArgumentException("Value of X should be strictly less than Y");
    }
    StringBuilder movingCrossover = new StringBuilder();
    String[] prices = data.split("\n");
    int startDataIndex = getIndex(prices, startDate);
    int endDataIndex = getIndex(prices, endDate);
    try {
      for (int i = endDataIndex; i <= startDataIndex + 1; i++) {
        double currXDay = xDayMovingAvg(data, ticker, prices[i].split(",")[0], x);
        double prevXDay = xDayMovingAvg(data, ticker, prices[i + 1].split(",")[0], x);
        double currYDay = xDayMovingAvg(data, ticker, prices[i].split(",")[0], y);
        double prevYDay = xDayMovingAvg(data, ticker, prices[i + 1].split(",")[0], y);
        if (prevXDay > prevYDay && currXDay < currYDay) {
          movingCrossover.append(prices[i].split(",")[0]).append("--->NEGATIVE").append(",");
        }
        if (prevXDay < prevYDay && currXDay > currYDay) {
          movingCrossover.append(prices[i].split(",")[0]).append("--->POSITIVE").append(",");
        }
      }
      if (movingCrossover.toString().isEmpty()) {
        return "No moving crossovers occurred over the given period.";
      }
      return movingCrossover.toString();
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
              "Insufficient data to calculate crossover. Please change the date range.");
    }
  }

  /**
   * This method computes a bar chart for the stock with appropriate scaling and gaps.
   *
   * @param date1 first date of the range.
   * @param date2 second date of the range.
   * @param data  date of the stock.
   * @return a string containing all the lines of the chart.
   */
  @Override
  public String getPerformance(LocalDate date1, LocalDate date2, String data) {
    checkDate(date1.toString());
    checkDate(date2.toString());
    Plotter plot = new Plotter();
    return plot.getPerformance(date1, date2, data);
  }

  private void checkDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Incorrect date format.");
    }
  }

}
