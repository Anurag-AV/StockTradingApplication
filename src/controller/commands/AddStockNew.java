package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import controller.Parser;
import controller.ParserInterface;
import controller.TransactionsManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class implements the add stock using a provided date option for our application.
 */
public class AddStockNew extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final String keyWord;
  private final LocalDate date;
  private final int cnt;

  /**
   * Constructor to initialize the class variables.
   *
   * @param view    is the view object of MVC architecture.
   * @param model   is the model object of the MVC architecture.
   * @param keyWord is either the ticker symbol or the company name.
   * @param date    is the date at which the user wants to buy.
   * @param cnt     is the quantity of shares to buy.
   */
  public AddStockNew(AbstractView view, MainModelInterface model, String keyWord, LocalDate date,
                     int cnt) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.keyWord = keyWord;
    this.cnt = cnt;
    this.date = date;
  }

  /**
   * Method to add stock to a portfolio using the company name through model.
   */
  private void addStockCompanyOption() {
    try {
      if (Objects.equals(model.getCurrentPortfolioName(), "")) {
        throw new IllegalArgumentException("Please load a portfolio");
      }
      view.fetchDetailsMsg();
      String companyInfo = getCompanyDetails(keyWord.strip());
      String ticker = companyInfo.split(",")[0];
      String companyName = companyInfo.split(",")[1];
      double price = dataManager.getStockValue(ticker, date);
      model.addStocksToPortfolio(ticker.strip(), companyName.strip(), cnt, price, date);
      String saveData = model.savePortfolio();
      if (saveData.split("\n").length < 2) {
        throw new IllegalArgumentException("Cannot save portfolio as no stocks present");
      }
      if (checkPortfolioPresent(model.getCurrentPortfolioName())) {
        parser.updateFile(saveData, model.getCurrentPortfolioName() + ".csv",
                getPortfolioFilesPath());
      } else {
        parser.createFile(saveData, model.getCurrentPortfolioName() + ".csv",
                getPortfolioFilesPath());
      }
      TransactionsManager tm = new TransactionsManager();
      //Ticker,quantity,date,action
      tm.storeBuyTransactions(ticker + "," + cnt + "," + date,
              model.getCurrentPortfolioName());
      view.showSuccessMsg("Stock added successfully.");
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    addStockCompanyOption();
  }
}
