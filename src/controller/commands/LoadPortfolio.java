package controller.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import controller.AbstractController;
import controller.CommandOptionInterface;
import controller.DataManager;
import controller.Parser;
import controller.ParserInterface;
import controller.TransactionsManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class implements the load portfolio option of our application.
 * Changes: We have added a functionality of long term investment strategies so the load
 * portfolio has been updated with an if-clause such that no earlier functionality are modified.
 */
public class LoadPortfolio extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final DataManager dataManager;
  private final ParserInterface parser;
  private final String name;

  /**
   * Constructor to initialize class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   * @param name  is the name of the portfolio to be loaded.
   */
  public LoadPortfolio(AbstractView view, MainModelInterface model, String name) {
    this.view = view;
    this.model = model;
    dataManager = new DataManager();
    parser = new Parser();
    this.name = name;
  }

  /**
   * This method loads the portfolio using the model and displays the appropriate prompt using the
   * view.
   */
  private void loadPortfolioOption() {
    try {
      view.fetchDetailsMsg();
      Path path = Paths.get((Paths.get(getPortfolioFilesPath()))
              .resolve(name + ".csv").toUri());
      String data = parser.readFile(path.toString());
      String[] dataRows = data.split("\n");
      for (int i = 1; i < dataRows.length; i++) {
        try {
          dataManager.getCompanyNameAndTicker(dataRows[i].split(",")[0]);
        } catch (IOException e) {
          throw new IllegalArgumentException("Details in CSV file are not valid.");
        }
      }
      model.loadPortfolio(name, data);
      Map<String, String> details = new HashMap<>();
      Path transactionPath = Path.of(Paths.get((Paths.get(getTransactionsFilesPath())).toUri())
              .resolve(name + ".csv").toUri());
      String transactionData = parser.readFile(transactionPath.toString());
      if (transactionData.contains("LONGTERM")) {
        String[] transDataRows = transactionData.split("\n");
        for (int i = 0; i < transDataRows.length; i++) {
          if (transDataRows[i].contains("LONGTERM")) {
            String[] lineData = transDataRows[i].split(",");
            if (details.containsKey(lineData[0])) {
              LocalDate startDate = validateDateFormat(lineData[2].split("\\*")[2]);

              LocalDate latestBuy = validateDateFormat(details.get(lineData[0])
                      .split(",")[2].split("\\*")[2]);
              if (latestBuy.isBefore(startDate)) {
                details.put(lineData[0], transDataRows[i]);
              }
            } else {
              details.put(lineData[0], transDataRows[i]);
            }
          }
        }
        StringBuilder transactions = new StringBuilder();
        StringBuilder transactionsBuy = new StringBuilder();
        for (Map.Entry<String, String> entry : details.entrySet()) {
          String validTicker = entry.getKey();
          String stockData = entry.getValue();
          LocalDate startDate = validateDateFormat(stockData.split(",")[2]
                  .split("\\*")[2]);
          String endDateStr = stockData.split(",")[2].split("\\*")[3];
          LocalDate endDate;
          if (Objects.equals(endDateStr, "-")) {
            endDate = LocalDate.now();
          } else {
            endDate = validateDateFormat(endDateStr);
          }
          double quantity = Double.parseDouble(stockData.split(",")[1]);
          double amount = Double.parseDouble(stockData.split(",")[2].split("\\*")[4]);
          String reccuringFactor = stockData.split(",")[2].split("\\*")[1];
          int reccuringDays = Integer.parseInt(stockData.split(",")[2]
                  .split("\\*")[0]);
          String companyInfo = getCompanyDetails(validTicker.strip());
          String companyName = companyInfo.split(",")[1];
          if (Objects.equals(reccuringFactor, "Day(s)")) {
            startDate = startDate.plusDays(reccuringDays);
          } else if (Objects.equals(reccuringFactor, "Month(s)")) {
            startDate = startDate.plusMonths(reccuringDays);
          } else if (Objects.equals(reccuringFactor, "Year(s)")) {
            startDate = startDate.plusYears(reccuringDays);
          }
          while (!endDate.isBefore(startDate)) {
            double price = dataManager.getStockValue(validTicker, startDate);
            double investCnt = (double) ((Math.round((amount * quantity / 100) / price)
                    * 100) / 100);
            model.addStocksToPortfolio(validTicker, companyName, investCnt, price, startDate);
            transactions.append(validTicker)
                    .append(",")
                    .append(quantity)
                    .append(",")
                    .append(reccuringDays)
                    .append("*")
                    .append(reccuringFactor)
                    .append("*")
                    .append(startDate)
                    .append("*")
                    .append(endDate)
                    .append("*")
                    .append(amount)
                    .append("\n");
            transactionsBuy.append(validTicker)
                    .append(",")
                    .append(investCnt)
                    .append(",")
                    .append(startDate)
                    .append("\n");
            if (Objects.equals(reccuringFactor, "Day(s)")) {
              startDate = startDate.plusDays(reccuringDays);
            } else if (Objects.equals(reccuringFactor, "Month(s)")) {
              startDate = startDate.plusMonths(reccuringDays);
            } else if (Objects.equals(reccuringFactor, "Year(s)")) {
              startDate = startDate.plusYears(reccuringDays);
            }
          }
        }
        String saveData = model.savePortfolio();
        if (saveData.split("\n").length < 2) {
          throw new IllegalArgumentException("Cannot save portfolio as no stocks present");
        }
        if (checkPortfolioPresent(model.getCurrentPortfolioName())) {
          parser.updateFile(saveData, model.getCurrentPortfolioName() + ".csv",
                  getPortfolioFilesPath());
        } else {
          parser.createFile(saveData, model.getCurrentPortfolioName() + ".csv",
                  getPortfolioFilesPath());
        }
        TransactionsManager tm = new TransactionsManager();
        if (!transactions.toString().isEmpty()) {
          tm.storeLongTermTransactions(transactions.toString(), model.getCurrentPortfolioName());
          tm.storeBuyTransactions(transactionsBuy.toString(), model.getCurrentPortfolioName());
        }
      }
      view.showSuccessMsg("Portfolio loaded successfully.");
    } catch (IOException | IllegalArgumentException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    loadPortfolioOption();
  }
}
