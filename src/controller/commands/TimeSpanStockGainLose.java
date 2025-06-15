package controller.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import controller.Parser;
import controller.ParserInterface;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class represents the stock analysis over a given span option for our application.
 */
public class TimeSpanStockGainLose extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final String ticker;
  private final LocalDate date;
  private final LocalDate date2;

  /**
   * Constructor to initialize class variables.
   *
   * @param view   is the view object of our application.
   * @param model  is the model object of our application.
   * @param ticker is the ticker symbol of stock.
   * @param date   is the start date of analysis.
   * @param date2  is the end date of analysis.
   */
  public TimeSpanStockGainLose(AbstractView view, MainModelInterface model, String ticker,
                               LocalDate date, LocalDate date2) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.ticker = ticker;
    this.date = date;
    this.date2 = date2;
  }

  /**
   * This method helps to find out whether the given stock has gained or lost over a specified
   * period of time by coordinating with the model and the view.
   */
  private void getStockGainLose() {
    try {
      view.fetchDetailsMsg();
      String companyInfo = getCompanyDetails(ticker.strip());
      String cache = dataManager.getStockValueAll(ticker, LocalDate.now());
      String[] files = dataManager.listFiles(dataManager.getCachePath()).split(",");
      String currFile = "";
      for (String file : files) {
        if (file.contains(ticker + "_")) {
          currFile = dataManager.getCachePath() + File.separator + file;
        }
      }
      if (currFile.equals("")) {
        throw new IllegalArgumentException("Data does not exist.");
      }
      String data = parser.readFile(currFile);
      String res = model.stockGainLoseTimeSpan(data, ticker, date.toString(), date2.toString());
      view.showMsg(res);
    } catch (IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    getStockGainLose();
  }
}
