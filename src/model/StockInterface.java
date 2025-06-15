package model;

import java.time.LocalDate;

/**
 * This interface represents a stock entity. It defines methods for accessing various properties of
 * a stock.
 */
public interface StockInterface {

  /**
   * This method acts as a getter for the ticker symbol of the stock.
   *
   * @return the ticker symbol os the stock as a string.
   */
  String getTicker();

  /**
   * This method acts as a getter for the purchase date of the stock.
   *
   * @return the purchase date as a LocalDate object.
   */
  LocalDate getPurchaseDate();

  /**
   * This method acts as a getter for the quantity od stocks brought.
   *
   * @return the quantity of stocks brought.
   */
  double getQuantity();

  /**
   * This method acts as a getter for the price at which the stock was purchased.
   *
   * @return the purchase price of the stock.
   */
  double getStockPrice();

  /**
   * This method acts as a getter for the company name of the stock.
   *
   * @return the company name of the stock.
   */
  String getCompanyName();

}
