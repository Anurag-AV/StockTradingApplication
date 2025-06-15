package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.Parser;
import controller.ParserInterface;
import controller.TransactionsManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class implements the sell stock option of our application.
 */
public class SellStocks extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final ParserInterface parser;
  private final String ticker;
  private final LocalDate date;
  private final int cnt;

  /**
   * Constructor to initialize the class variables.
   *
   * @param view   is the view object of MVC architecture.
   * @param model  is the model object of the MVC architecture.
   * @param ticker is the ticker symbol.
   * @param date   is the selling date.
   * @param cnt    is the selling quantity.
   */
  public SellStocks(AbstractView view, MainModelInterface model, String ticker, LocalDate date,
                    int cnt) {
    this.view = view;
    this.model = model;
    parser = new Parser();
    this.date = date;
    this.cnt = cnt;
    this.ticker = ticker;
  }

  /**
   * This method sells a stock from the portfolio by coordinating with the model and the view.
   */
  private void sellStockOption() {
    try {
      validateSingularDate(date);
      String[] stocks = model.showComposition().split("\n");
      double owned = 0;
      for (String stock : stocks) {
        if (Objects.equals(stock.split(",")[0], ticker)) {
          owned += Double.parseDouble(stock.split(",")[2]);
        }
      }
      if (owned == 0) {
        throw new IllegalArgumentException("Company stock not owned in the portfolio");
      }
      if (cnt > owned) {
        throw new IllegalArgumentException("not enough stocks owned in the portfolio");
      }
      String updatedData = model.sellStocks(ticker, cnt, date.toString());
      TransactionsManager tm = new TransactionsManager();
      //Ticker,quantity,date,action
      tm.storeSellTransactions(ticker + "," + cnt + "," + date, model.getCurrentPortfolioName());
      parser.updateFile(updatedData, model.getCurrentPortfolioName() + ".csv",
              getPortfolioFilesPath());
      view.showSuccessMsg("Stock sold successfully");
    } catch (IllegalArgumentException e) {
      view.showErrorMsg(e.getMessage());
    } catch (IOException e) {
      view.showErrorMsg("unable to save changes");
    }
  }

  /**
   * This method holds the logic that our application performs depending on the implementation of
   * the interface.
   */
  @Override
  public void execute() {
    sellStockOption();
  }
}
