package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class represents the get portfolio performance chart option of our application.
 */
public class GetPortfolioPerformanceChart extends AbstractController implements
        CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private LocalDate date;
  private LocalDate date1;

  /**
   * Constructor to initialize the class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   * @param date  is the start date of analysis.
   * @param date1 is the end date of analysis.
   */
  public GetPortfolioPerformanceChart(AbstractView view, MainModelInterface model,
                                      LocalDate date, LocalDate date1) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    this.date = date;
    this.date1 = date1;
  }

  /**
   * This method helps to show the bar chart for the portfolio by coordinating with the model and
   * the view.
   */
  private void getPortfolioPerformanceChartOption() {
    try {
      LocalDate temp;
      if (date1.isBefore(date)) {
        temp = LocalDate.parse(date.toString());
        date = LocalDate.parse(date1.toString());
        date1 = LocalDate.parse(temp.toString());
      }
      temp = LocalDate.parse(date.toString());

      view.fetchDetailsMsg();
      StringBuilder data = new StringBuilder();
      String portfolioStocks = model.showComposition();
      String[] stocks = portfolioStocks.split("\n");
      do {
        Map<String, Double> values = new HashMap<>();
        for (String stock : stocks) {
          if (temp.isBefore(LocalDate.parse(stock.split(",")[4]))) {
            values.put(stock.split(",")[0], 0.0);
          } else {
            values.put(stock.split(",")[0], dataManager.getStockValue(stock.
                    split(",")[0], temp));
          }
        }
        double value = model.getPortfolioValue(temp.toString(),
                values);          // timestamp,open,high,low,close,volume
        data.append(temp)
                .append(",,,,")
                .append((double) Math.round(value * 100) / 100)
                .append("\n");
        temp = temp.plusDays(1);
      }
      while (!temp.isAfter(date1));
      String cols = ",,,,,\n";
      String graph = model.getPortfolioPerformance(date, date1, cols + data);
      String header = "\n\nPerformance of portfolio " + model.getCurrentPortfolioName() + " from "
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
    getPortfolioPerformanceChartOption();
  }
}
