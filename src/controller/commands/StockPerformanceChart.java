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
 * This class implements the stock performance bar chart option for our application.
 */
public class StockPerformanceChart extends AbstractController implements CommandOptionInterface {

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
  public StockPerformanceChart(AbstractView view, MainModelInterface model, String ticker,
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
   * This method helps in generating and displaying a bar graph by coordinating with the model and
   * the view.
   */
  private void stockPerformanceChartOption() {
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
      if (currFile.isEmpty()) {
        throw new IllegalArgumentException("Data does not exist.");
      }
      String data = parser.readFile(currFile);
      String graph = model.getPerformance(date, date1, data, ticker);
      String header = "\n\nPerformance of stock " + companyInfo.split(",")[1] + " from "
              + date + " to " + date1 + "\n\n";
      view.showMsg(header + graph);
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  /**
   * This method holds the logic that our application performs depending on the implementation of
   * the interface.
   */
  @Override
  public void execute() {
    stockPerformanceChartOption();
  }
}
