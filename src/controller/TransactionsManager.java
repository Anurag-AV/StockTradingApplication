package controller;

import java.io.File;
import java.io.IOException;

/**
 * This class stores various buy and sell transactions occurring on a portfolio so that the
 * logs can be used for future analysis of portfolio such as to calculate the total amount
 * invested in the portfolio.
 */
public class TransactionsManager extends AbstractController {

  private final String header = "Ticker,Quantity,Date,Action";

  public void storeBuyTransactions(String data, String portfolio) throws IOException {
    storeTransactions(data, portfolio, Action.BUY);
  }

  public void storeSellTransactions(String data, String portfolio) throws IOException {
    storeTransactions(data, portfolio, Action.SELL);
  }

  public void storeLongTermTransactions(String data, String portfolio) throws IOException {
    storeTransactions(data, portfolio, Action.LONGTERM);
  }

  /**
   * Stores the transaction details of the respective portfolio.
   */
  private void storeTransactions(String data, String portfolio, Action action) throws IOException {
    // format is Ticker, Quantity, Purchase Price, Purchase date, company name
    String[] records = data.split("\n");
    StringBuilder addition = new StringBuilder();
    String fileName = portfolio + ".csv";
    for (String record : records) {
      String[] value = record.split(",");
      addition.append(value[0])
              .append(",")
              .append(value[1])
              .append(",")
              .append(value[2])
              .append(",")
              .append(action.toString())
              .append("\n");
    }
    DataManager dm = new DataManager();
    ParserInterface p = new Parser();
    if (dm.fileExists(getTransactionsFilesPath(), fileName)) {
      String transactions = p.readFile(getTransactionsFilesPath() + File.separator + fileName);
      transactions += addition.toString();
      p.updateFile(transactions, fileName, getTransactionsFilesPath());
    } else {
      p.createFile(this.header + "\n" + addition, fileName,
              getTransactionsFilesPath());
    }
  }

  /**
   * This enum is used to store logs of stocks in the transactions folder.
   */
  enum Action {
    SELL,
    BUY,
    LONGTERM
  }
}
