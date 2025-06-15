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
 * This class represents the buy stock by splitting a fixed amount into stock investment
 * percentages option of our application.
 */
public class InvestStocksSplitPercentage extends AbstractController
        implements CommandOptionInterface {

  private final String stockSplit;
  private final LocalDate date;
  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final double amount;

  /**
   * Constructor to initialize class variables.
   *
   * @param model      is the model object of our application.
   * @param view       is the view object of our application.
   * @param stockSplit is the stock data.
   * @param date       is the buy date.
   * @param amt        is the amount to be invested.
   */
  public InvestStocksSplitPercentage(MainModelInterface model, AbstractView view,
                                     String stockSplit, LocalDate date, double amt) {
    this.model = model;
    this.view = view;
    this.stockSplit = stockSplit;
    this.date = date;
    dataManager = new DataManager();
    parser = new Parser();
    this.amount = amt;
  }

  /**
   * Method to add the stocks based on the given percentage split for an amount.
   */
  private void addStocks() {
    try {
      if (Objects.equals(model.getCurrentPortfolioName(), "")) {
        throw new IllegalArgumentException("Please load a portfolio");
      }
      view.fetchDetailsMsg();
      String[] stockList = stockSplit.split(",");
      double tot = 0;
      for (String string : stockList) {
        tot += Double.parseDouble(string.split(" ")[1].strip());
      }
      if (tot != 100) {
        throw new IllegalArgumentException("The percentage split of shares does " +
                "not equal 100 percentage");
      }
      for (String s : stockList) {
        String keyWord = s.split(" ")[0].strip();
        double cnt = Double.parseDouble(s.split(" ")[1].strip());
        String companyInfo = getCompanyDetails(keyWord.strip());
        String ticker = companyInfo.split(",")[0];
        String companyName = companyInfo.split(",")[1];
        double price = dataManager.getStockValue(ticker, date);
        double investCnt = (double) Math.round((amount * cnt / (price * 100.0)) * 100.0) / 100.0;
        if (investCnt == 0) {
          throw new IllegalArgumentException("The provided stock investment amount " +
                  "and split results in share purchase count of nearly zero.");
        }
      }
      for (String s : stockList) {
        String keyWord = s.split(" ")[0].strip();
        double cnt = Double.parseDouble(s.split(" ")[1].strip());

        String companyInfo = getCompanyDetails(keyWord.strip());
        String ticker = companyInfo.split(",")[0];
        String companyName = companyInfo.split(",")[1];
        double price = dataManager.getStockValue(ticker, date);
        double investCnt = (double) Math.round(((amount * cnt / 100) / price) * 100) / 100;
        model.addStocksToPortfolio(ticker.strip(), companyName.strip(), investCnt, price, date);
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
        tm.storeBuyTransactions(ticker + "," + investCnt + "," + date,
                model.getCurrentPortfolioName());
      }
      view.showSuccessMsg("Stocks added successfully.");
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    addStocks();
  }
}
