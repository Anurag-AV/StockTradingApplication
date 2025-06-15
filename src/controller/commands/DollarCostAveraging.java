package controller.commands;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.AbstractController;
import controller.DataManager;
import controller.Parser;
import controller.ParserInterface;
import controller.TransactionsManager;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class implements a long term investment strategy known as Dollar Cost Averaging.
 * In Dollar Cost Averaging the investor splits the amount to be invested into multiple stocks
 * and then buys them consecutively over a period of time.
 */
public class DollarCostAveraging extends AbstractController
        implements LongTermInvestmentStrategies {
  private final MainModelInterface model;
  private final AbstractView view;
  private final ParserInterface parser;
  private final DataManager dataManager;
  private final String data;
  private LocalDate startDate;
  private final String endingDate;
  private final int reccuringDays;
  private final double amount;
  private final String reccuringFactor;
  private final String name;

  /**
   * Constructor to initialize class variables.
   *
   * @param model           is the view object of our application.
   * @param view            is the view object of our application.
   * @param name            is the portfolio name.
   * @param data            is the stock data.
   * @param startDate       is the start date of investment.
   * @param endDate         is the end date of investment.
   * @param recurringDays   is the span of recursion.
   * @param amount          is the amount to be invested.
   * @param recurringFactor is the time of recursion (like monthly,daily or yearly).
   */
  public DollarCostAveraging(MainModelInterface model, AbstractView view, String name,
                             String data, LocalDate startDate, String endDate, int recurringDays,
                             double amount, String recurringFactor) {
    this.model = model;
    this.view = view;
    parser = new Parser();
    dataManager = new DataManager();
    this.data = data;
    this.startDate = startDate;
    this.endingDate = endDate;
    this.reccuringDays = recurringDays;
    this.amount = amount;
    this.reccuringFactor = recurringFactor;
    this.name = name;
  }

  /**
   * Private Method to implement the dollar cost averaging strategy.
   */
  private void dollarCostAvg() {
    try {
      view.fetchDetailsMsg();
      List<String> ticker = new ArrayList<>();
      List<Double> quantity = new ArrayList<>();
      String[] dataPair = data.split(",");
      for (String s : dataPair) {
        ticker.add(s.split(" ")[0].strip());
        quantity.add(Double.parseDouble(s.split(" ")[1].strip()));
      }
      if (ticker.size() != quantity.size()) {
        throw new IllegalArgumentException("Number of stocks does not match quantities provided");
      }
      double tot = 0;
      for (Double aDouble : quantity) {
        tot += aDouble;
      }
      if (tot != 100) {
        throw new IllegalArgumentException("The percentage split of shares does not " +
                "equal 100 percentage");
      }
      List<String> companyDetails = new ArrayList<>();
      List<String> validTicker = new ArrayList<>();
      for (int i = 0; i < ticker.size(); i++) {
        String companyInfo = getCompanyDetails(ticker.get(i).strip());
        String tick = companyInfo.split(",")[0];
        String companyName = companyInfo.split(",")[1];
        companyDetails.add(companyName);
        validTicker.add(tick);
      }
      LocalDate endDate;
      if (Objects.equals(endingDate, "-")) {
        endDate = LocalDate.now();
      } else {
        endDate = validateDateFormat(endingDate);
        validateStartEndDate(startDate, endDate);
      }
      LocalDate startDateTemp = startDate;
      validateStartEndDate(startDate, endDate);
      while (!endDate.isBefore(startDateTemp)) {
        List<Double> investCnt = new ArrayList<>();
        List<Double> investPrice = new ArrayList<>();
        for (int i = 0; i < validTicker.size(); i++) {
          double price = dataManager.getStockValue(validTicker.get(i), startDateTemp);
          double cnt = (Math.round((amount * quantity.get(i) / (price * 100.0)) * 100.0) / 100.0);
          if (cnt == 0) {
            throw new IllegalArgumentException("The provided stock investment amount and split " +
                    "results in share purchase count of nearly zero.");
          }
          investCnt.add(cnt);
          investPrice.add(price);
        }
        if (Objects.equals(reccuringFactor, "Day(s)")) {
          startDateTemp = startDateTemp.plusDays(reccuringDays);
        } else if (Objects.equals(reccuringFactor, "Month(s)")) {
          startDateTemp = startDateTemp.plusMonths(reccuringDays);
        } else if (Objects.equals(reccuringFactor, "Year(s)")) {
          startDateTemp = startDateTemp.plusYears(reccuringDays);
        }
      }

      model.setPortfolioName(name);
      while (!endDate.isBefore(startDate)) {
        List<Double> investCnt = new ArrayList<>();
        List<Double> investPrice = new ArrayList<>();
        for (int i = 0; i < validTicker.size(); i++) {
          double price = dataManager.getStockValue(validTicker.get(i), startDate);
          double cnt = (Math.round((amount * quantity.get(i) / (price * 100.0)) * 100.0) / 100.0);
          investCnt.add(cnt);
          investPrice.add(price);
        }
        for (int i = 0; i < investCnt.size(); i++) {
          model.addStocksToPortfolio(validTicker.get(i), companyDetails.get(i), investCnt.get(i),
                  investPrice.get(i), getValidBuyDates(validTicker.get(i), startDate));
        }
        if (Objects.equals(reccuringFactor, "Day(s)")) {
          startDate = startDate.plusDays(reccuringDays);
        } else if (Objects.equals(reccuringFactor, "Month(s)")) {
          startDate = startDate.plusMonths(reccuringDays);
        } else if (Objects.equals(reccuringFactor, "Year(s)")) {
          startDate = startDate.plusYears(reccuringDays);
        }
      }
      String saveData = model.savePortfolio();
      if (saveData.split("\n").length < 2) {
        throw new IllegalArgumentException("Cannot save portfolio as no stocks present");
      }
      parser.createFile(saveData, model.getCurrentPortfolioName() + ".csv"
              , getPortfolioFilesPath());
      String[] stocks = saveData.split("\n");
      StringBuilder transactions = new StringBuilder();
      StringBuilder transactionsBuy = new StringBuilder();
      for (int i = 1; i < stocks.length; i++) {
        String[] value = stocks[i].split(",");
        double cnt = quantity.get(validTicker.indexOf(value[0]));
        transactions.append(value[0])
                .append(",")
                .append(cnt)
                .append(",")
                .append(reccuringDays)
                .append("*")
                .append(reccuringFactor)
                .append("*")
                .append(value[4])
                .append("*")
                .append(endingDate)
                .append("*")
                .append(amount)
                .append("\n");

        transactionsBuy.append(value[0])
                .append(",")
                .append(value[2])
                .append(",")
                .append(value[4])
                .append("\n");
      }
      TransactionsManager tm = new TransactionsManager();
      tm.storeLongTermTransactions(transactions.toString(), model.getCurrentPortfolioName());
      tm.storeBuyTransactions(transactionsBuy.toString(), model.getCurrentPortfolioName());
      view.showSuccessMsg("Portfolio saved successfully.");
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  private LocalDate getValidBuyDates(String ticker, LocalDate date) throws IOException {
    LocalDate buyDate = date;
    while (buyDate.isBefore(LocalDate.now())) {
      String data = dataManager.getStockValueAll(ticker, buyDate);
      if (data.contains(buyDate.toString())) {
        return buyDate;
      } else {
        buyDate = buyDate.plusDays(1);
      }
    }
    return LocalDate.now();
  }

  @Override
  public void executeStrategy() {
    dollarCostAvg();
  }
}
