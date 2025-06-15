package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import controller.DataManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class represents the get portfolio value option of our application.
 */
public class GetPortfolioValue {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final boolean actual;
  private final LocalDate date;

  /**
   * Constructor to initialize the class variables to respective objects.
   *
   * @param view   is the view object of MVC architecture.
   * @param model  is the model object of MVC architecture.
   * @param date   is the flag for actual or static functionality
   * @param actual indicates which type of value is being calculated.
   */
  public GetPortfolioValue(AbstractView view, MainModelInterface model, LocalDate date,
                           boolean actual) {
    this.view = view;
    this.model = model;
    this.actual = actual;
    dataManager = new DataManager();
    this.date = date;
  }

  /**
   * Method to get the total value of a portfolio at a given date from the model which is then
   * displayed by the view.
   */
  protected void getPortfolioValueOption() throws IOException {
    view.fetchDetailsMsg();
    String portfolioStocks = model.showComposition();
    String[] stocks = portfolioStocks.split("\n");
    Map<String, Double> values = new HashMap<>();
    for (String stock : stocks) {
      if (actual && date.isBefore(LocalDate.parse(stock.split(",")[4]))) {
        values.put(stock.split(",")[0] + stock.split(",")[4], 0.0);
      } else {
        values.put(stock.split(",")[0] + stock.split(",")[4], dataManager.getStockValue(stock.
                split(",")[0], date));
      }
    }
    double value = model.getPortfolioValue(date.toString(), values);
    view.showMsg(
            "The total value of portfolio is: $ "
                    + ((double) Math.round(value * 100) / 100));
  }
}
