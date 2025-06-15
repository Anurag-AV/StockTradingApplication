package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import controller.commands.AddStockNew;
import controller.commands.DailyStockAnalysis;
import controller.commands.DollarCostAveraging;
import controller.commands.GetCostBasis;
import controller.commands.GetPortfolioValueActual;
import controller.commands.InvestStocksSplitPercentage;
import controller.commands.ListPortfolio;
import controller.commands.LoadPortfolio;
import controller.commands.LongTermInvestmentStrategies;
import controller.commands.MovingCrossover;
import controller.commands.SellStocks;
import controller.commands.ShowPortfolioComposition;
import controller.commands.StockCrossovers;
import controller.commands.TimeSpanStockGainLose;
import controller.commands.XDayMovingAvg;
import model.MainModelInterface;
import view.gui.MainGUIViewInterface;

/**
 * This class implements the main controller interface and is responsible for taking inputs from
 * user and calling the model for data processing and the view to display.
 */
public class MainControllerGUI extends AbstractController implements
        MainControllerInterface, ActionListener {
  private final MainGUIViewInterface view;
  private final MainModelInterface model;

  Map<String, Function<String, Void>> knownCommands;

  /**
   * Constructor to initialize the model, view objects.
   *
   * @param view  is the view object of our MVC architecture.
   * @param model iss the model object of our MVC architecture.
   */
  public MainControllerGUI(MainGUIViewInterface view, MainModelInterface model) {

    this.view = view;
    this.model = model;

    knownCommands = new HashMap<String, Function<String, Void>>();
    knownCommands.put("loadPortfolio", (String s) -> {
      try {
        if (!validateRegex(s)) {
          throw new IllegalArgumentException("Invalid file name.");
        }
        CommandOptionInterface loadPortfolio = new LoadPortfolio(this.view, this.model, s);
        loadPortfolio.execute();
        if (!Objects.equals(model.getCurrentPortfolioName(), "")) {
          CommandOptionInterface showPortfolioComposition =
                  new ShowPortfolioComposition(view, model);
          showPortfolioComposition.execute();
          view.takeAction("portfolioOptions");
        }
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }

      return null;
    });
    knownCommands.put("buyStock", (String s) -> {
      try {
        checkInvalid(s);
        CommandOptionInterface addStockNew = new AddStockNew(view, model, s.split(",")[0],
                getPastDate(s.split(",")[1]),
                Integer.parseInt(s.split(",")[2]));
        addStockNew.execute();
        view.takeAction("clearFields");
        CommandOptionInterface showPortfolioComposition = new ShowPortfolioComposition(view, model);
        showPortfolioComposition.execute();
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number.");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });
    knownCommands.put("sellStock", (String s) -> {
      try {
        checkInvalid(s);
        CommandOptionInterface sellStock = new SellStocks(view, model, s.split(",")[0],
                getPastDate(s.split(",")[1]),
                Integer.parseInt(s.split(",")[2]));
        sellStock.execute();
        view.takeAction("clearFields");
        CommandOptionInterface showPortfolioComposition = new ShowPortfolioComposition(view, model);
        showPortfolioComposition.execute();
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number.");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });
    knownCommands.put("listPortfolio", (String s) -> {
      CommandOptionInterface listPortfolio = new ListPortfolio(view, model);
      listPortfolio.execute();
      return null;
    });
    knownCommands.put("showComposition", (String s) -> {
      CommandOptionInterface showPortfolioComposition = new ShowPortfolioComposition(view, model);
      showPortfolioComposition.execute();
      view.takeAction("portfolioOptions");
      return null;
    });
    knownCommands.put("getValue", (String dateStr) -> {
      try {
        checkInvalid(dateStr);
        CommandOptionInterface getPortfolioValueDynamic =
                new GetPortfolioValueActual(view, model, getPastDate(dateStr));
        getPortfolioValueDynamic.execute();
        view.takeAction("clearFields");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      }
      return null;
    });
    knownCommands.put("getCostBasis", (String dateStr) -> {
      try {
        checkInvalid(dateStr);
        CommandOptionInterface getCostBasis = new GetCostBasis(view, model, getPastDate(dateStr));
        getCostBasis.execute();
        view.takeAction("clearFields");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      }
      return null;
    });
    knownCommands.put("createPortfolio", (String name) -> {
      try {
        checkInvalid(name);
        name = getPortfolioName(name);
        model.setPortfolioName(name.strip());
        view.showSuccessMsg("Portfolio name is set.");
        view.takeAction("clearFields");
        view.takeAction("portfolioOptions");
        view.takeAction("resetDisplayNoStock");
      } catch (IllegalArgumentException | IOException e) {
        view.showErrorMsg(e.getMessage());
      }
      return null;
    });


    knownCommands.put("dayPerformance", (String s) -> {

      try {
        checkInvalid(s);
        String ticker = s.split(",")[0].strip();
        CommandOptionInterface dailyStock =
                new DailyStockAnalysis(view, model, ticker, getPastDate(s.split(",")[1]));
        dailyStock.execute();
        view.takeAction("clearFields");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });

    knownCommands.put("timeSpanPerformance", (String s) -> {

      try {
        checkInvalid(s);
        String ticker = s.split(",")[0].strip();
        LocalDate date = validateDateFormat(s.split(",")[1]);
        validateSingularDate(date);
        LocalDate date2 = validateDateFormat(s.split(",")[2]);
        validateSingularDate(date2);
        validateStartEndDate(date, date2);
        CommandOptionInterface timeSpan =
                new TimeSpanStockGainLose(view, model, ticker, date, date2);
        timeSpan.execute();
        view.takeAction("clearFields");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });

    knownCommands.put("xDayMovingAvg", (String s) -> {

      try {
        checkInvalid(s);
        String ticker = s.split(",")[0].strip();
        LocalDate date = validateDateFormat(s.split(",")[1]);
        validateSingularDate(date);
        validateCount(s.split(",")[2]);
        CommandOptionInterface xDayAvg = new XDayMovingAvg(view, model, ticker, date,
                Integer.parseInt(s.split(",")[2]));
        xDayAvg.execute();
        view.takeAction("clearFields");
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number.");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });

    knownCommands.put("crossover", (String s) -> {

      try {
        checkInvalid(s);
        String ticker = s.split(",")[0].strip();
        LocalDate date = validateDateFormat(s.split(",")[1]);
        LocalDate date1 = validateDateFormat(s.split(",")[2]);
        validateSingularDate(date);
        validateSingularDate(date1);
        validateStartEndDate(date, date1);
        CommandOptionInterface crossover = new StockCrossovers(view, model, ticker, date, date1);
        crossover.execute();
        view.takeAction("clearFields");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });

    knownCommands.put("movingCrossover", (String s) -> {

      try {
        checkInvalid(s);
        String ticker = s.split(",")[0].strip();
        LocalDate date = validateDateFormat(s.split(",")[1]);
        LocalDate date1 = validateDateFormat(s.split(",")[2]);
        validateSingularDate(date);
        validateSingularDate(date1);
        validateStartEndDate(date, date1);
        validateCount(s.split(",")[3]);
        int x = Integer.parseInt(s.split(",")[3]);
        validateCount(s.split(",")[4]);
        int y = Integer.parseInt(s.split(",")[4]);
        if (x >= y) {
          throw new IllegalArgumentException("The X value should be strictly less than Y.");
        }
        CommandOptionInterface movCrossover = new MovingCrossover(view, model, ticker,
                date, date1, x, y);
        movCrossover.execute();
        view.takeAction("clearFields");
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number.");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });

    knownCommands.put("costAvgPortfolio", (String s) -> {
      try {
        String[] data = s.split("\\*");
        String name = data[0];
        checkInvalid(name);
        name = getPortfolioName(name);
        String companyDetails = data[1];
        LocalDate startDate = validateDateFormat(data[2].strip());
        String endDate = data[3].strip();
        double amount = Double.parseDouble(data[4].strip());
        if (amount <= 0) {
          throw new IllegalArgumentException("Please Enter a valid amount");
        }
        int recurringDays = Integer.parseInt(data[5].strip());
        if (recurringDays <= 0) {
          throw new IllegalArgumentException("Recurring Days should be greater than zero");
        }
        String recurringPeriod = data[6].strip();
        LongTermInvestmentStrategies dollarAvg = new DollarCostAveraging(model, view,
                name, companyDetails, startDate, endDate, recurringDays, amount, recurringPeriod);
        dollarAvg.executeStrategy();
        view.takeAction("clearFields");
        if (!(Objects.equals(model.getCurrentPortfolioName(), ""))) {
          CommandOptionInterface showPortfolioComposition =
                  new ShowPortfolioComposition(view, model);
          showPortfolioComposition.execute();
          view.takeAction("portfolioOptions");
        }
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number");
      } catch (IllegalArgumentException | IOException e) {
        try {
          model.setPortfolioName("");
        } catch (Exception ignored) {
        }
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });
    knownCommands.put("multiStock", (String s) -> {
      try {
        String[] data = s.split("\\*");
        String companyDetails = data[0];
        LocalDate startDate = validateDateFormat(data[1].strip());
        double amount = Double.parseDouble(data[2].strip());
        if (amount <= 0) {
          throw new IllegalArgumentException("Please enter a valid amount");
        }
        CommandOptionInterface stockSplit = new InvestStocksSplitPercentage(model,
                view, companyDetails, startDate, amount);
        stockSplit.execute();
        CommandOptionInterface showPortfolioComposition = new ShowPortfolioComposition(view, model);
        showPortfolioComposition.execute();
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter a valid number");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        view.showErrorMsg("Not enough data provided");
      }
      return null;
    });


  }

  /**
   * Method to begin the execution of our controller. Our application features start running after
   * this method is called.
   */
  @Override
  public void startController() {
    view.setActionListener(this);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      knownCommands.get(e.getActionCommand()).apply(view.getInput(e.getActionCommand()));
    } catch (NullPointerException err) {
      view.showErrorMsg("Insufficient data");
    }


  }

  private String getPortfolioName(String name) throws IOException, IllegalArgumentException {
    if (!validateRegex(name)) {
      throw new IllegalArgumentException("Invalid file name.");
    }
    if (checkPortfolioPresent(name)) {
      throw new IllegalArgumentException("Portfolio exists. Please enter a different name.");
    }
    return name;
  }

  private void checkInvalid(String text) {
    if (text.isEmpty()) {
      throw new IllegalArgumentException("Please necessary input");
    }
  }

  private LocalDate getPastDate(String str) {
    LocalDate date;
    try {
      date = validateDateFormat(str);
      validateSingularDate(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please enter a valid date");
    }
    return date;
  }

}

