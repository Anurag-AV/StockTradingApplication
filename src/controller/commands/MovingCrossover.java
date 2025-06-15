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
 * Class to represent the moving crossover option of our application.
 */
public class MovingCrossover extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final String ticker;
  private final LocalDate date;
  private final LocalDate date1;
  private final int x;
  private final int y;

  /**
   * Constructor to initialize class variables.
   *
   * @param view   is the view object of our application.
   * @param model  is the model object of our application.
   * @param ticker is the ticker symbol of stock.
   * @param date   is the start date of analysis.
   * @param date1  is the end date of analysis.
   * @param x      is X day moving crossover from start date.
   * @param y      is y day moving crossover from end date.
   */
  public MovingCrossover(AbstractView view, MainModelInterface model, String ticker,
                         LocalDate date, LocalDate date1, int x, int y) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.ticker = ticker;
    this.date = date;
    this.date1 = date1;
    this.x = x;
    this.y = y;
  }

  /**
   * This method helps to calculate the moving crossover for a stock over a period of time by
   * coordinating with the model and view.
   */
  private void calcMovingCrossover() {
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
      String result = model.calcMovingCrossover(data, ticker, date.toString(), date1.toString(), x,
              y);
      if (result.contains("No moving crossover")) {
        view.showMsg(result);
      } else {
        view.showMsg("The dates at which moving crossovers have occurred are: ");
        view.showList(result);
      }
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    calcMovingCrossover();
  }
}
