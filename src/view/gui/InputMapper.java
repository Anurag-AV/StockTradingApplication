package view.gui;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is used to map the buttons from the graphical user interface to an action.
 */
public class InputMapper {
  Map<String, Function<Void, String>> knownInput;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  InputContainer inputContainer;

  /**
   * Constructor to map the buttons to an action.
   *
   * @param inputContainer is the input container.
   */
  public InputMapper(InputContainer inputContainer) {
    this.knownInput = new HashMap<>();
    this.inputContainer = inputContainer;

    knownInput.put("loadPortfolio", (Void) -> inputContainer.getText("loadPortfolio"));
    knownInput.put("buyStock", (Void) -> {
      String ticker = inputContainer.getText("buyStockName").toUpperCase();
      String quantity = inputContainer.getText("buyStockQuantity");
      String date = dateToString(inputContainer.getDate("buyStockDate"));
      return ticker + "," + date + "," + quantity;
    });
    knownInput.put("sellStock", (Void) -> {
      String ticker = inputContainer.getText("sellStockName").toUpperCase();
      String quantity = inputContainer.getText("sellStockQuantity");
      String date = dateToString(inputContainer.getDate("sellStockDate"));
      return ticker + "," + date + "," + quantity;
    });
    knownInput.put("getValue", (Void) -> dateToString(inputContainer
            .getDate("getValueDate")));
    knownInput.put("getCostBasis", (Void) -> dateToString(inputContainer
            .getDate("getCostBasisDate")));
    knownInput.put("createPortfolio", (Void) -> inputContainer
            .getText("createPortfolioName"));

    knownInput.put("dayPerformance", (Void) -> {
      String ticker = inputContainer.getText("dayPerformanceName").toUpperCase();
      String date = dateToString(inputContainer.getDate("dayPerformanceDate"));
      return ticker + "," + date;
    });

    knownInput.put("timeSpanPerformance", (Void) -> {
      String ticker = inputContainer.getText("timeSpanPerformanceName").toUpperCase();
      String startDate = dateToString(inputContainer
              .getDate("timeSpanPerformanceStartDate"));
      String endDate = dateToString(inputContainer
              .getDate("timeSpanPerformanceEndDate"));
      return ticker + "," + startDate + "," + endDate;
    });

    knownInput.put("xDayMovingAvg", (Void) -> {
      String ticker = inputContainer.getText("xDayPerformanceName").toUpperCase();
      String startDate = dateToString(inputContainer.getDate("xDayPerformanceDate"));
      String xDays = inputContainer.getText("xDays");
      return ticker + "," + startDate + "," + xDays;
    });

    knownInput.put("crossover", (Void) -> {
      String ticker = inputContainer.getText("crossPerformanceName").toUpperCase();
      String startDate = dateToString(inputContainer.getDate("crossPerformanceStartDate"));
      String endDate = dateToString(inputContainer.getDate("crossPerformanceEndDate"));
      return ticker + "," + startDate + "," + endDate;
    });

    knownInput.put("movingCrossover", (Void) -> {
      String ticker = inputContainer.getText("movCrossPerformanceName").toUpperCase();
      String startDate = dateToString(inputContainer
              .getDate("movCrossPerformanceStartDate"));
      String endDate = dateToString(inputContainer
              .getDate("movCrossPerformanceEndDate"));
      String xDays = inputContainer.getText("xDay");
      String yDays = inputContainer.getText("yDay");
      return ticker + "," + startDate + "," + endDate + "," + xDays + "," + yDays;
    });

    knownInput.put("costAvgPortfolio", (Void) -> {
      String name = inputContainer.getText("costAvgPortfolioName");
      String amount = inputContainer.getText("costAvgTotalAmount");
      String factorType = inputContainer.getComboBox("costAvgFactorType");
      String frequency = inputContainer.getText("costAvgFrequency");
      String startDate = dateToString(inputContainer.getDate("costAvgStartDate"));
      String endDate = "-";
      if (inputContainer.getDate("costAvgEndDate") != null) {
        endDate = dateToString(inputContainer.getDate("costAvgEndDate"));
      }
      int size = Integer.parseInt(inputContainer.getText("costAvgSize"));
      StringBuilder stocks = new StringBuilder(name + "*");
      for (int i = 0; i < size; i++) {
        stocks.append(inputContainer.getText("costAvgName" + i).toUpperCase())
                .append(" ")
                .append(inputContainer.getText("costAvgQuantity" + i))
                .append(",");
      }
      stocks.deleteCharAt(stocks.length() - 1);
      stocks.append("*")
              .append(startDate)
              .append("*")
              .append(endDate)
              .append("*")
              .append(amount)
              .append("*")
              .append(frequency)
              .append("*")
              .append(factorType);
      return stocks.toString();
    });

    knownInput.put("multiStock", (Void) -> {
      String amount = inputContainer.getText("multiStockTotalAmount");
      String startDate = dateToString(inputContainer.getDate("multiStockStartDate"));
      int size = Integer.parseInt(inputContainer.getText("multiStockSize"));
      StringBuilder stocks = new StringBuilder();
      for (int i = 0; i < size; i++) {
        stocks.append(inputContainer.getText("multiStockName" + i).toUpperCase())
                .append(" ")
                .append(inputContainer.getText("multiStockQuantity" + i))
                .append(",");
      }
      stocks.deleteCharAt(stocks.length() - 1);
      stocks.append("*")
              .append(startDate)
              .append("*")
              .append(amount);
      return stocks.toString();
    });

  }


  public Function<Void, String> get(String code) {

    return knownInput.getOrDefault(code, null);
  }

  private String dateToString(Date element) {
    return formatter.format(element.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate());
  }

}
