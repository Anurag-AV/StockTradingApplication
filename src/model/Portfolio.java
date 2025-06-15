package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A class to represent a Portfolio in our application. A portfolio is simply a collection of stocks
 * purchased by the user.
 * Changes: The portfolio class implements the PortfolioInterface such that different types of
 * portfolios are supported. It also has two additional methods for the additional functionalities
 * of cost basis and bar graph. These functionalities have been added here as they belong to a
 * particular portfolio and adding new method in a class is the safest modification.
 */
class Portfolio implements PortfolioInterface {

  private final DateTimeFormatter formatter;
  private String name = "";
  private List<Stock> stock;

  /**
   * Constructor to initialize values to the portfolio object.
   */
  Portfolio() {
    stock = new ArrayList<>();
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  }

  /**
   * This method creates a new ArrayList of stocks.
   */
  private void resetStocks() {
    stock = new ArrayList<>();
  }

  /**
   * Method to get the name of the portfolio.
   */
  @Override
  public String getPortfolioName() {
    return this.name;
  }

  /**
   * Method to set the name of the portfolio.
   *
   * @param name is the name of the portfolio.
   */
  @Override
  public void setPortfolioName(String name) {
    resetStocks();
    this.name = name;
  }

  /**
   * Method to add a stock to the Portfolio.
   *
   * @param ticker      is the ticker symbol of the stock.
   * @param companyName is the company name of the stock.
   * @param quantity    is the quantity of stocks purchased.
   * @param price       is the price at which the stock was purchased.
   * @param date        is the date of buying the stock.
   * @throws IllegalArgumentException if the given quantity or price is invalid, or when the stock
   *                                  is already present in the portfolio.
   */
  @Override
  public void addStocks(String ticker, String companyName, double quantity, double price,
                        LocalDate date)
          throws IllegalArgumentException {

    if (quantity <= 0) {
      throw new IllegalArgumentException("Please enter a valid quantity of shares");
    }
    if (price <= 0) {
      throw new IllegalArgumentException("Price is negative or zero");
    }
    for (Stock value : stock) {
      if (Objects.equals(value.getTicker(), ticker) && Objects.equals(
              value.getPurchaseDate().toString(), date.toString())) {
        quantity += value.getQuantity();
        stock.remove(value);
        break;
      }
    }
    Stock s = new Stock(ticker, companyName, quantity, price, date);
    this.stock.add(s);
  }

  /**
   * Sell a set number of stocks from the available stocks in the portfolio.
   *
   * @param ticker   Is the ticker symbol of the stock to be removed.
   * @param quantity Is the quantity of the stocks to be removed.
   * @return String of updated portfolio.
   * @throws IllegalArgumentException when the quantity is invalid or stock now owned.
   */
  @Override
  public String sellStocks(String ticker, double quantity, String date)
          throws IllegalArgumentException {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Please enter a valid quantity of shares");
    }
    double totalQuantity = 0;
    ArrayList<Stock> tickerStocks = new ArrayList<Stock>();
    LocalDate minDate = LocalDate.now();
    for (Stock value : stock) {
      if (Objects.equals(value.getTicker(), ticker) &&
              !LocalDate.parse(date).isBefore(value.getPurchaseDate())) {
        totalQuantity += value.getQuantity();
        tickerStocks.add(value);
        if (value.getPurchaseDate().isBefore(minDate)) {
          minDate = LocalDate.parse(value.getPurchaseDate().toString());
        }
      }
    }
    if (tickerStocks.isEmpty()) {
      throw new IllegalArgumentException("Stock not owned");
    }
    if (totalQuantity < quantity) {
      throw new IllegalArgumentException("Not enough shares owned");
    }
    while (quantity > 0) {
      LocalDate minDateTemp = LocalDate.now();
      Stock stk = null;
      for (Stock st : tickerStocks) {
        if (st.getPurchaseDate().isEqual(minDate)) {
          if (st.getQuantity() > quantity) {
            Stock temp = new Stock(st.getTicker(), st.getCompanyName(),
                    st.getQuantity() - quantity, st.getStockPrice(), st.getPurchaseDate());
            stock.remove(st);
            stock.add(temp);
            quantity -= quantity;
            stk = st;
            break;
          } else {
            quantity -= st.getQuantity();
            stock.remove(st);
            stk = st;
          }
        }
        if (st.getPurchaseDate().isBefore(minDateTemp) && st.getPurchaseDate().isAfter(minDate)) {
          minDateTemp = LocalDate.parse(st.getPurchaseDate().toString());
        }
      }
      minDate = LocalDate.parse(minDateTemp.toString());
      tickerStocks.remove(stk);
    }
    return this.savePortfolio();

  }

  /**
   * Method to return a standard formatted string which can be directly saved as a portfolio without
   * any need of processing.
   *
   * @return a formatted string so that portfolio can be saved.
   */
  @Override
  public String savePortfolio() {
    StringBuilder s = new StringBuilder();
    s.append("Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n");
    for (Stock stocks : stock) {
      s.append(stocks.getTicker()).append(",").append(stocks.getCompanyName()).append(",")
              .append(stocks.getQuantity()).append(",").append(stocks.getStockPrice()).append(",")
              .append(stocks.getPurchaseDate().toString()).append("\n");
    }
    return s.toString();
  }


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
  @Override
  public void loadPortfolio(String data) throws IllegalArgumentException {
    String[] rowData = data.split("\n");
    this.resetStocks();
    if (rowData.length == 0) {
      throw new IllegalArgumentException("Portfolio file is empty");
    }
    if (!Objects.equals(rowData[0], "Ticker Symbol,Company Name,Quantity,Purchase Price," +
            "Purchase Date")) {
      throw new IllegalArgumentException("Portfolio file is invalid");
    }
    for (int i = 1; i < rowData.length; i++) {
      String[] cellValue = rowData[i].split(",");
      if (cellValue.length != 5) {
        throw new IllegalArgumentException("Portfolio file is invalid.");
      }
      try {
        LocalDate date = LocalDate.parse(cellValue[4].strip(), formatter);
        if (Objects.equals(cellValue[0], "") || Objects.equals(cellValue[1], "")) {
          throw new IllegalArgumentException();
        }
        Stock s = new Stock(cellValue[0], cellValue[1], Double.parseDouble(cellValue[2]),
                Double.parseDouble(cellValue[3]), date);
        this.stock.add(s);
      } catch (NumberFormatException | NullPointerException | DateTimeParseException e) {
        throw new IllegalArgumentException("Portfolio file is invalid.");
      }
    }
  }

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
  @Override
  public double getPortfolioValue(String date, Map<String, Double> values)
          throws IllegalArgumentException {
    if (Objects.equals(name, "")) {
      throw new IllegalArgumentException("Please load the portfolio");
    }
    if (this.stock.isEmpty() || values.isEmpty()) {
      throw new IllegalArgumentException("Please load the portfolio first or add stocks to it.");
    }
    try {
      LocalDate checkDate = LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please enter the date in specified format.");
    }
    double value = 0;
    for (Stock stocks : stock) {
      value += (values.get(stocks.getTicker() + stocks.getPurchaseDate().toString())
              * stocks.getQuantity());
    }
    return value;
  }

  /**
   * Method to generate a formatted string which contains the composition of the portfolio.
   *
   * @return a standard formatted string which contains the portfolio composition.
   * @throws IllegalArgumentException if the portfolio is not loaded, or it does not contain any
   *                                  stocks.
   */
  @Override
  public String showPortfolioComposition() throws IllegalArgumentException {
    if (Objects.equals(this.name, "")) {
      throw new IllegalArgumentException("Please load the portfolio");
    }
    if (this.stock.isEmpty()) {
      throw new IllegalArgumentException("Portfolio does not have any stocks.");
    }
    StringBuilder composition = new StringBuilder();
    for (Stock stocks : stock) {
      double qnt = ((double) Math.round(stocks.getQuantity() * 100) / 100);
      composition.append(stocks.getTicker()).append(",").append(stocks.getCompanyName())
              .append(",").append(qnt).append(",").append(stocks.getStockPrice())
              .append(",").append(stocks.getPurchaseDate().toString()).append("\n");
    }
    return composition.toString();
  }

  /**
   * Method returns the cost basis of the portfolio using transactions.
   *
   * @param transactions transactions data of the portfolio.
   * @param date         date at which the cost basis needs to be calculated.
   * @return cost basis as a double value.
   */
  @Override
  public double getCostBasis(String transactions, String date) {
    LocalDate checkDate;
    if (Objects.equals(name, "")) {
      throw new IllegalArgumentException("Please load the portfolio");
    }
    if (transactions.split("\n").length < 1) {
      throw new IllegalArgumentException("Please load the portfolio first or add stocks to it.");
    }
    try {
      checkDate = LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please enter the date in specified format.");
    }
    double value = 0;
    LocalDate transactDate;
    try {
      for (String transaction : transactions.split("\n")) {
        if (transaction.contains("BUY")) {
          transactDate = LocalDate.parse(transaction.split(",")[2]);
          if (Objects.equals(transaction.split(",")[3], "BUY") &&
                  !checkDate.isBefore(transactDate)) {
            value += (Double.parseDouble(transaction.split(",")[1])
                    * Double.parseDouble(transaction.split(",")[4]));
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to read transactions");
    }
    return value;
  }

  /**
   * This method computes a bar chart for the portfolio with appropriate scaling and gaps.
   *
   * @param date1 first date of the rage.
   * @param date2 second date of the range.
   * @param data  date of the stock.
   * @return a string containing all the lines of the chart.
   */
  @Override
  public String getPortfolioPerformance(LocalDate date1, LocalDate date2, String data) {
    Plotter plot = new Plotter();
    return plot.getPerformance(date1, date2, data);
  }
}
