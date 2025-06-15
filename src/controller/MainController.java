package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import controller.commands.AddStockNew;
import controller.commands.AddStockOld;
import controller.commands.DailyStockAnalysis;
import controller.commands.GetCostBasis;
import controller.commands.GetPortfolioPerformanceChart;
import controller.commands.GetPortfolioValueActual;
import controller.commands.GetPortfolioValueStatic;
import controller.commands.ListPortfolio;
import controller.commands.LoadPortfolio;
import controller.commands.MovingCrossover;
import controller.commands.SaveAndExit;
import controller.commands.SellStocks;
import controller.commands.ShowPortfolioComposition;
import controller.commands.StockCrossovers;
import controller.commands.StockPerformanceChart;
import controller.commands.TimeSpanStockGainLose;
import controller.commands.XDayMovingAvg;
import model.MainModelInterface;
import view.MainViewInterface;

/**
 * This class implements the main controller interface and is responsible for taking inputs from
 * user and calling the model for data processing and the view to display prompts to user.
 */
public class MainController extends AbstractController implements MainControllerInterface {

  private final Scanner in;
  private final MainViewInterface view;
  private final MainModelInterface model;

  /**
   * Constructor to initialize the model, view and input stream objects.
   *
   * @param in    is the InputStream object.
   * @param view  is the view object of our MVC architecture.
   * @param model is the model object of our MVC architecture.
   */
  public MainController(Readable in, MainViewInterface view, MainModelInterface model) {
    this.in = new Scanner(in);
    this.view = view;
    this.model = model;
  }

  /**
   * Method to begin the execution of our controller. Our application features start running after
   * this method is called.
   * Changes: Options are added to the controller switch case.
   */
  @Override
  public void startController() {
    String name;
    CommandOptionInterface listPortFolio = new ListPortfolio(view, model);
    CommandOptionInterface showPortfolioComposition = new ShowPortfolioComposition(view, model);
    boolean quit = false;
    while (!quit) {
      view.primaryMenu();
      String option = in.nextLine();
      switch (option) {
        // Create Portfolio
        case "1":
          name = getPortfolioName();
          model.setPortfolioName(name.strip());
          view.showSuccessMsg("Portfolio name is set.");
          boolean quit2 = false;
          while (!quit2) {
            view.createPortfolioMenu();
            String option2 = in.nextLine();
            switch (option2) {
              case "1":
              case "2":
                view.showMsg("Enter stock name: ");
                String ticker = in.nextLine().toUpperCase();
                view.showMsg("Add the number of stocks you want to purchase: ");
                int cnt = getQuantity();
                CommandOptionInterface addStockOld = new AddStockOld(view, model, ticker, cnt);
                addStockOld.execute();
                break;
              case "3":
                CommandOptionInterface saveAndExit = new SaveAndExit(view, model, name.strip());
                saveAndExit.execute();
                quit2 = true;
                break;
              default:
                view.showMsg("Invalid option. Please enter a valid option.");
            }
          }
          break;
        case "2":
          name = getPortfolioName();
          model.setPortfolioName(name.strip());
          view.showSuccessMsg("Portfolio name is set.");
          boolean quit3 = false;
          while (!quit3) {
            view.createPortfolioMenu();
            String option2 = in.nextLine();
            switch (option2) {
              case "1":
              case "2":
                view.showMsg("Enter name of stock: ");
                String keyWord = in.nextLine().toUpperCase();
                view.showMsg("Add the number of stocks you want to purchase: ");
                int cnt = getQuantity();
                LocalDate date = getDateMarketOpen();
                CommandOptionInterface addStockNew = new AddStockNew(view, model, keyWord,
                        date, cnt);
                addStockNew.execute();
                break;
              case "3":
                quit3 = true;
                break;
              default:
                view.showMsg("Invalid option. Please enter a valid option.");
            }
          }
          break;
        // Load Portfolio
        case "3":
          while (true) {
            view.showMsg("Enter name of your Portfolio: ");
            name = in.nextLine();
            try {
              if (!validateRegex(name)) {
                throw new IllegalArgumentException("Invalid file name.");
              } else {
                break;
              }
            } catch (IllegalArgumentException e) {
              view.showErrorMsg(e.getMessage());
            }
          }
          CommandOptionInterface loadPortfolio = new LoadPortfolio(view, model, name);
          loadPortfolio.execute();
          break;
        case "4":
          showPortfolioComposition.execute();
          break;
        //Get Portfolio Value
        case "5":
          try {
            CommandOptionInterface portfolioValueStatic = new GetPortfolioValueStatic(view, model,
                    getPastDate("Enter date as yyyy-mm-dd"));
            portfolioValueStatic.execute();
          } catch (IllegalArgumentException e) {
            view.showErrorMsg(e.getMessage());
          }
          break;
        case "6":
          try {
            CommandOptionInterface getPortfolioValueDynamic = new GetPortfolioValueActual(view,
                    model, getPastDate("Enter date as yyyy-mm-dd"));
            getPortfolioValueDynamic.execute();
          } catch (IllegalArgumentException e) {
            view.showErrorMsg(e.getMessage());
          }
          break;
        //List Portfolio
        case "7":
          listPortFolio.execute();
          break;
        case "8":
          view.showMsg("Enter name of stock: ");
          String keyWord = in.nextLine().toUpperCase();
          view.showMsg("Add the number of stocks you want to purchase: ");
          int cnt = getQuantity();
          CommandOptionInterface addStock = new AddStockNew(view, model, keyWord,
                  getDateMarketOpen(), cnt);
          addStock.execute();
          break;
        case "9":
          try {
            view.showMsg("Enter ticker symbol of stock: ");
            String ticker = in.nextLine();
            view.showMsg("Enter number of stocks to sell: ");
            cnt = getQuantity();
            CommandOptionInterface sellStock = new SellStocks(view, model, ticker,
                    getPastDate("Enter date at which you wish to sell " +
                            "the stocks in yyyy-MM-dd format"), cnt);
            sellStock.execute();
          } catch (IllegalArgumentException e) {
            view.showErrorMsg(e.getMessage());
          }
          break;
        case "10":
          try {
            CommandOptionInterface getCostBasis = new GetCostBasis(view, model,
                    getPastDate("Enter date as yyyy-mm-dd"));
            getCostBasis.execute();
          } catch (IllegalArgumentException e) {
            view.showErrorMsg(e.getMessage());
          }
          break;
        case "11":
          try {
            CommandOptionInterface getPortfolioPerformanceChart =
                    new GetPortfolioPerformanceChart(view,
                    model, getPastDate("Enter date as yyyy-mm-dd"),
                    getPastDate("Enter date as yyyy-mm-dd"));
            getPortfolioPerformanceChart.execute();
          } catch (IllegalArgumentException e) {
            view.showErrorMsg(e.getMessage());
          }
          break;
        case "12":
          boolean quit4 = false;
          while (!quit4) {
            view.stockView();
            String choice = in.nextLine();
            switch (choice) {
              case "1":
                view.showMsg("Enter ticker symbol of stock: ");
                String ticker = in.nextLine();
                try {
                  CommandOptionInterface dailyStock = new DailyStockAnalysis(view, model,
                          ticker, getPastDate("Enter date as yyyy-mm-dd"));
                  dailyStock.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "2":
                view.showMsg("Enter ticker symbol of stock: ");
                ticker = in.nextLine();
                try {
                  view.showMsg("Enter start date of evaluation: ");
                  String dateStr = in.nextLine();
                  LocalDate date = validateDateFormat(dateStr);
                  validateSingularDate(date);
                  view.showMsg("Enter end date of evaluation: ");
                  String dateStr2 = in.nextLine();
                  LocalDate date2 = validateDateFormat(dateStr2);
                  validateSingularDate(date2);
                  validateStartEndDate(date, date2);
                  CommandOptionInterface spanStock = new TimeSpanStockGainLose(view, model,
                          ticker, date, date2);
                  spanStock.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "3":
                view.showMsg("Enter ticker symbol of stock: ");
                ticker = in.nextLine();
                try {
                  view.showMsg("Enter date from which X-Day moving Avg. is to " +
                          "be calculated as yyyy-mm-dd");
                  String dateStr = in.nextLine();
                  LocalDate date = validateDateFormat(dateStr);
                  validateSingularDate(date);
                  view.showMsg("Enter the number of days for which you want X Day Moving Avg: ");
                  int x = getQuantity();
                  CommandOptionInterface xDayMoving = new XDayMovingAvg(view, model,
                          ticker, date, x);
                  xDayMoving.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "4":
                view.showMsg("Enter ticker symbol of stock: ");
                ticker = in.nextLine();
                try {
                  view.showMsg("Enter start date for crossover analysis as yyyy-mm-dd");
                  String dateStr = in.nextLine();
                  view.showMsg("Enter end date for crossover analysis as yyyy-mm-dd");
                  String dateStr1 = in.nextLine();
                  LocalDate date = validateDateFormat(dateStr);
                  LocalDate date1 = validateDateFormat(dateStr1);
                  validateSingularDate(date);
                  validateSingularDate(date1);
                  validateStartEndDate(date, date1);
                  CommandOptionInterface crossover = new StockCrossovers(view, model,
                          ticker, date, date1);
                  crossover.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "5":
                view.showMsg("Enter ticker symbol of stock: ");
                ticker = in.nextLine();
                try {
                  view.showMsg("Enter the start date of analysis as yyyy-mm-dd");
                  String dateStr = in.nextLine();
                  view.showMsg("Enter the end date of analysis as yyyy-mm-dd");
                  String dateStr1 = in.nextLine();
                  LocalDate date1;
                  LocalDate date = validateDateFormat(dateStr);
                  date1 = validateDateFormat(dateStr1);
                  validateSingularDate(date);
                  validateSingularDate(date1);
                  validateStartEndDate(date, date1);
                  view.showMsg("Enter the number of days for which you want X Day Moving Avg: ");
                  int x = getQuantity();
                  view.showMsg("Enter the number of days for which you want Y Day Moving Avg: ");
                  int y = getQuantity();
                  if (x >= y) {
                    throw new IllegalArgumentException("The X value should be " +
                            "strictly less than Y.");
                  }
                  CommandOptionInterface movCrossover = new MovingCrossover(view, model,
                          ticker, date, date1, x, y);
                  movCrossover.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "6":
                view.showMsg("Enter ticker symbol of stock: ");
                ticker = in.nextLine();
                try {
                  view.showMsg("Enter date as yyyy-mm-dd");
                  String dateStr = in.nextLine();
                  view.showMsg("Enter date as yyyy-mm-dd");
                  String dateStr1 = in.nextLine();
                  LocalDate date1;
                  LocalDate date = validateDateFormat(dateStr);
                  date1 = validateDateFormat(dateStr1);
                  validateSingularDate(date);
                  validateSingularDate(date1);
                  if (date1.isBefore(date)) {
                    LocalDate temp;
                    temp = LocalDate.parse(date.toString());
                    date = LocalDate.parse(date1.toString());
                    date1 = LocalDate.parse(temp.toString());
                  }
                  CommandOptionInterface stockPerformanceChart = new
                          StockPerformanceChart(view, model, ticker, date, date1);
                  stockPerformanceChart.execute();
                } catch (IllegalArgumentException e) {
                  view.showErrorMsg(e.getMessage());
                }
                break;
              case "7":
                quit4 = true;
                break;
              default:
                view.showMsg("Invalid option. Please enter a valid option.");
            }
          }
          break;
        case "13":
          view.showMsg("Adios! See you soon.");
          quit = true;
          break;
        default:
          view.showMsg("Invalid option. Please enter a valid option.");
      }
    }
  }

  private String getPortfolioName() {
    String name = null;
    while (true) {
      while (true) {
        view.showMsg("Enter name of your Portfolio: ");
        name = in.nextLine();
        try {
          if (!validateRegex(name)) {
            throw new IllegalArgumentException("Invalid file name.");
          } else {
            break;
          }
        } catch (IllegalArgumentException e) {
          view.showErrorMsg(e.getMessage());
        }
      }
      try {
        if (checkPortfolioPresent(name)) {
          view.showMsg("Portfolio exists. Please enter a different name.");
        } else {
          break;
        }
      } catch (IOException e) {
        view.showErrorMsg(e.getMessage());
      }
    }
    return name;
  }


  private int getQuantity() {
    int cnt;
    while (true) {
      try {
        String count = in.nextLine();
        if (validateCount(count)) {
          cnt = Integer.parseInt(count);
          break;
        }
      } catch (NumberFormatException e) {
        view.showErrorMsg("Please enter valid quantity");
      }
    }
    return cnt;
  }

  private LocalDate getDateMarketOpen() {
    LocalDate date;
    view.showMsg("Enter date as yyyy-mm-dd");
    while (true) {
      try {
        String dt = in.nextLine();

        LocalDate date1 = validateDateFormat(dt);
        validateSingularDate(date1);
        if (!checkIsStockMarketOpen(date1)) {
          throw new IllegalArgumentException("Market wasn't open. But on some other date");
        }
        date = date1;
        break;
      } catch (DateTimeParseException e) {
        view.showErrorMsg("Please enter a valid date");
      } catch (IllegalArgumentException e) {
        view.showErrorMsg(e.getMessage());
      }
    }
    return date;
  }

  private LocalDate getPastDate(String str) {
    view.showMsg(str);
    String dateStr = in.nextLine();
    LocalDate date;
    try {
      date = validateDateFormat(dateStr);
      validateSingularDate(date);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please enter a valid date");
    }
    return date;
  }
}