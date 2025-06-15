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
 * This class represents the daily stock analysis option of our application.
 */
public class DailyStockAnalysis extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final String ticker;
  private final LocalDate date;

  /**
   * Constructor to initialize class variables.
   *
   * @param view   is the view object of our application.
   * @param model  is the model object of our application.
   * @param ticker is the ticker symbol of the company.
   * @param date   is the date of analysis.
   */
  public DailyStockAnalysis(AbstractView view, MainModelInterface model, String ticker,
                            LocalDate date) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.ticker = ticker;
    this.date = date;
  }

  /**
   * Method to helps to calculate whether a stock has gained or lost at the end of the day by
   * coordinating with model and view.
   */
  private void dailyGainLose() {
    try {
      view.fetchDetailsMsg();
      String companyInfo = getCompanyDetails(ticker.strip());
      String cache = dataManager.getStockValueAll(ticker, date);
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
      String res = model.stockGainLose(data, ticker, date.toString());
      view.showMsg(res);
    } catch (IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    dailyGainLose();
  }
}
