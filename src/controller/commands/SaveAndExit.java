package controller.commands;

import java.io.IOException;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.Parser;
import controller.ParserInterface;
import controller.TransactionsManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class represents the save and exit option of our application.
 */
public class SaveAndExit extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final ParserInterface parser;
  private final String name;


  /**
   * Constructor to initialize class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   * @param name  is the name of the portfolio.
   */
  public SaveAndExit(AbstractView view, MainModelInterface model, String name) {
    this.view = view;
    this.model = model;
    parser = new Parser();
    this.name = name;
  }

  /**
   * This method saves the portfolio and exits using the model and displays the prompt using the
   * view.
   *
   * @param name is the name of the portfolio.
   */
  private void saveAndExitOption(String name) {
    try {
      view.fetchDetailsMsg();
      String saveData = model.savePortfolio();
      if (saveData.split("\n").length < 2) {
        throw new IllegalArgumentException("Cannot save portfolio as no stocks present");
      }
      parser.createFile(saveData, name + ".csv", getPortfolioFilesPath());
      String[] stocks = saveData.split("\n");
      StringBuilder transactions = new StringBuilder();
      for (int i = 1; i < stocks.length; i++) {
        String[] value = stocks[i].split(",");
        transactions.append(value[0])
                .append(",")
                .append(value[2])
                .append(",")
                .append(value[4])
                .append("\n");
      }
      TransactionsManager tm = new TransactionsManager();
      //Ticker,quantity,date,action
      tm.storeBuyTransactions(transactions.toString(), model.getCurrentPortfolioName());
      view.showSuccessMsg("Portfolio saved successfully.");
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    saveAndExitOption(name);
  }
}
