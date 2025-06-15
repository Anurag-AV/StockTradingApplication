package controller.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import controller.Parser;
import controller.ParserInterface;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class represents the get cost basis option of our application.
 */
public class GetCostBasis extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final LocalDate date;

  /**
   * Constructor to initialize the class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   * @param date  is the date at which cost basis needs to be calculated.
   */
  public GetCostBasis(AbstractView view, MainModelInterface model, LocalDate date) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.date = date;
  }

  /**
   * This method helps to calculate the cost basis of our portfolio using model and view.
   */
  private void getCostBasisOption() {
    try {
      double value = 0;
      if (Objects.equals(model.getCurrentPortfolioName(), "null")) {
        throw new IllegalArgumentException("Please load a portfolio");
      }
      String transactionData = parser.readFile(getTransactionsFilesPath() + File.separator
              + model.getCurrentPortfolioName() + ".csv");
      String[] transactions = transactionData.split("\n", 2)[1].split("\n");
      StringBuilder transactPrice = new StringBuilder();
      for (String transaction : transactions) {
        transactPrice.append(transaction).append(",")
                .append(dataManager.getStockValue(transaction.split(",")[0], date))
                .append("\n");
      }
      value = model.getCostBasis(transactPrice.toString(), date.toString());
      view.showMsg("The total cost basis of the portfolio is: $" +
              ((double) Math.round(value * 100) / 100));
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    getCostBasisOption();
  }
}
