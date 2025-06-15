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
 * This class represents the X Day moving average option for a given stock in our application.
 */
public class XDayMovingAvg extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final ParserInterface parser;
  private final DataManager dataManager;
  private final String ticker;
  private final LocalDate date;
  private final int x;

  /**
   * Constructor to initialize the class variables.
   *
   * @param view   is the view object of MVC architecture.
   * @param model  is the model object of the MVC architecture.
   * @param ticker is the ticker symbol of stock.
   * @param date   is the date of analysis.
   * @param x      is the number of days from start date for finding the moving average.
   */
  public XDayMovingAvg(AbstractView view, MainModelInterface model, String ticker,
                       LocalDate date, int x) {
    this.view = view;
    this.model = model;
    parser = new Parser();
    dataManager = new DataManager();
    this.ticker = ticker;
    this.date = date;
    this.x = x;
  }

  /**
   * This method helps in calculating the X day moving average for a given stock by coordinating
   * with the model and the view.
   */
  private void calcXDayMovAvg() {
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
      double xDayAvg = model.xDayMov(data, ticker, date.toString(), x);
      view.showMsg("The X-Day Moving Average is: " + ((double) Math.round(xDayAvg * 100) / 100));
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    calcXDayMovAvg();
  }
}
