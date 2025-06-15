package controller.commands;

import java.io.IOException;
import java.time.LocalDate;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class implements the add stock to portfolio without a date option of our application.
 */
public class AddStockOld extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private String ticker;
  private final int cnt;

  /**
   * Constructor to initialize class variables.
   *
   * @param view   is the view object of MVC architecture.
   * @param model  is the model object of MVC architecture.
   * @param ticker is the company ticker symbol.
   * @param cnt    is the quantity of shares to buy.
   */
  public AddStockOld(AbstractView view, MainModelInterface model, String ticker, int cnt) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    this.ticker = ticker;
    this.cnt = cnt;
  }

  /**
   * Method to add stocks to a portfolio using ticker symbol using the model.
   */
  private void addStockTickerOption() {
    try {
      view.fetchDetailsMsg();
      String companyInfo = getCompanyDetails(ticker.strip());
      ticker = companyInfo.split(",")[0];
      String companyName = companyInfo.split(",")[1];
      double price = dataManager.getStockValue(ticker.strip().toUpperCase(), LocalDate.now());
      model.addStocksToPortfolio(ticker.strip().toUpperCase(), companyName.strip(), cnt, price,
              LocalDate.now());
      view.showSuccessMsg("Stock added successfully.");
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }


  @Override
  public void execute() {
    addStockTickerOption();
  }
}
