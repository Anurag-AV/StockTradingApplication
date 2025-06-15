package model;

import java.time.LocalDate;

/**
 * A class to represent a stock entity in our application. A stock object has a ticker symbol,
 * company name, purchase price, number of stocks purchased and the purchased date.
 */
class Stock implements StockInterface {

  private final String tickerSymbol;
  private LocalDate purchaseDate;
  private String companyName;
  private double stockCount;
  private double purchaseValue;

  /**
   * Constructor to initialize the values to the stock object.
   *
   * @param ticker      is the ticker symbol of the stock.
   * @param companyName is the company name of the stock.
   * @param quantity    is the quantity of stock purchased.
   * @param price       is the price at which stock was purchased.
   * @param date        is the date on which the stock was purchased.
   */
  public Stock(String ticker, String companyName, double quantity, double price, LocalDate date) {
    this.tickerSymbol = ticker;
    this.purchaseDate = date;
    this.purchaseValue = price;
    this.stockCount = quantity;
    this.companyName = companyName;
  }

  protected Stock(String ticker) {
    this.tickerSymbol = ticker;
  }


  @Override
  public String getTicker() {
    return this.tickerSymbol;
  }

  @Override
  public LocalDate getPurchaseDate() {
    return this.purchaseDate;
  }

  @Override
  public double getQuantity() {
    return this.stockCount;
  }

  @Override
  public double getStockPrice() {
    return this.purchaseValue;
  }

  @Override
  public String getCompanyName() {
    return this.companyName;
  }
}
