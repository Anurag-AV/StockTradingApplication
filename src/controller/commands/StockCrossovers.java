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
 * This class represent the stock crossover option for our application.
 */
public class StockCrossovers extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final ParserInterface parser;
  private final DataManager dataManager;
  private final String ticker;
  private final LocalDate date;
  private final LocalDate date1;


  /**
   * Constructor to initialize the class variables.
   *
   * @param view   is the view object of MVC architecture.
   * @param model  is the model object of the MVC architecture.
   * @param ticker is the ticker symbol of the stock.
   * @param date   is the start date of analysis.
   * @param date1  is the end date of analysis.
   */
  public StockCrossovers(AbstractView view, MainModelInterface model, String ticker,
                         LocalDate date, LocalDate date1) {
    this.view = view;
    this.model = model;
    parser = new Parser();
    dataManager = new DataManager();
    this.ticker = ticker;
    this.date = date;
    this.date1 = date1;
  }

  /**
   * This method helps to calculate the crossover days for a given stock over a period of time by
   * coordinating with the model and the view.
   */
  private void calcCrossover() {
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
      String crossoverDates = model.calcCrossover(data, ticker, date.toString(), date1.toString());
      if (crossoverDates.contains("No crossover")) {
        view.showMsg(crossoverDates);
      } else {
        view.showMsg(
                "The following are the dates and types of crossovers occurred over " +
                        "the given period:");
        view.showList(crossoverDates);
      }
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    calcCrossover();
  }
}
