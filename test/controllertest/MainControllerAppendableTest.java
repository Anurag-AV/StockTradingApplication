package controllertest;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import controller.MainController;
import controller.MainControllerInterface;
import model.MainModel;
import model.MainModelInterface;
import view.MainView;
import view.MainViewInterface;

import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class to test the functionalities of the MainController class. This class tests
 * whether the controller sends the correct data to the view.
 */
public class MainControllerAppendableTest {

  StringBuilder logger;
  MainModelInterface model;
  MainViewInterface view;
  OutputStream out;
  InputStream in;
  Appendable ap;
  StringBuilder reqOutput;

  @Before
  public void setUp() {
    logger = new StringBuilder();
    model = new MainModel();
    out = new ByteArrayOutputStream();
    ap = new PrintStream(out);
    view = new MainView(ap);
  }

  /**
   * This takes input from the user and converts it to a readable object.
   *
   * @param input is the input.
   * @return the input.
   */
  Readable setReadable(String input) {
    in = new ByteArrayInputStream(input.getBytes());
    return new InputStreamReader(in);
  }

  private void runController(String input) {
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
  }

  private void createDemo() {
    String input = "2" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "MSFT" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "IBM" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "13";
    runController(input);
  }

  private void writeMessage(String str) {
    reqOutput.append(str).append(System.lineSeparator());
  }

  @Test
  public void testPromptOnFirstLoad() {
    reqOutput = new StringBuilder();
    String input = "13" + System.lineSeparator();
    runController(input);
    writeMessage("Menu:");
    writeMessage("1. Create Portfolio by adding stocks without a specified date");
    writeMessage("2. Create Portfolio by adding stocks at a specific date");
    writeMessage("3. Load Portfolio");
    writeMessage("4. Show Portfolio Composition");
    writeMessage("5. Get Total Portfolio Value");
    writeMessage(
            "   (Above option will give the total portfolio value at a certain date " +
                    "irrespective of the share buy date)");
    writeMessage("6. Get Portfolio Value");
    writeMessage(
            "   (Above option will give the total portfolio value considering only " +
                    "the shares that were bought till the given date)");
    writeMessage("7. List Portfolio");
    writeMessage("8. Add Stock To a Portfolio");
    writeMessage("9. Sell Stocks from a Portfolio");
    writeMessage("10. Get Cost Basis of Portfolio");
    writeMessage("11. Get Portfolio performance chart");
    writeMessage("12. Check Stock Statistics");
    writeMessage("13. Exit");
    writeMessage("Please enter your choice: ");
    assertTrue(out.toString().startsWith(reqOutput.toString()));
  }

  @Test
  public void testPromptOnExit() {
    reqOutput = new StringBuilder();
    String input = "13" + System.lineSeparator();
    runController(input);
    writeMessage("Adios! See you soon.");
    assertTrue(out.toString().endsWith(reqOutput.toString()));
  }

  @Test
  public void testPromptStockView() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("Menu:");
    writeMessage("1. Check Stock Performance for a Day");
    writeMessage("2. Check Stock Performance over a Time Period");
    writeMessage("3. Find X-Day Moving Average of a Stock");
    writeMessage("4. Find Crossover for a Stock");
    writeMessage("5. Find Moving Crossover for a Stock");
    writeMessage("6. View stock performance chart");
    writeMessage("7. Exit to previous menu");
    writeMessage("Please enter your choice: ");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testPromptCreateView() {
    reqOutput = new StringBuilder();
    String input = "1" + System.lineSeparator()
            + "abc" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("1. Add Stocks to Portfolio using Ticker Symbol.");
    writeMessage("2. Add Stocks to Portfolio using Company name.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testDateError() {

    reqOutput = new StringBuilder();
    String input = "2" + System.lineSeparator()
            + "demo99" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "13" + System.lineSeparator()
            + "2024-03-35" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("Please enter a valid date");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testSellCountError() {
    createDemo();
    reqOutput = new StringBuilder();
    String input = "3" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "9" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "13" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("not enough stocks owned in the portfolio");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testFutureDateError() {
    createDemo();
    reqOutput = new StringBuilder();
    String input = "3" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "2025-03-26" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("Please enter a date that is not in the present or future.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testPortfolioValueTotal() {
    createDemo();
    reqOutput = new StringBuilder();
    String input = "3" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The total value of portfolio is: $ 7618.5");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testPortfolioValueActual() {
    createDemo();
    reqOutput = new StringBuilder();
    String input = "3" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "6" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The total value of portfolio is: $ 7618.5");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testPortfolioCostBasis() {
    createDemo();
    reqOutput = new StringBuilder();
    String input = "3" + System.lineSeparator()
            + "demox" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The total cost basis of the portfolio is: $7618.5");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockPerformanceDay() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The stock has lost value.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockPerformancePeriod() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2023-10-26" + System.lineSeparator()
            + "30" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The X-Day Moving Average is: 435.66");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockXdayMoving() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);
    writeMessage("The stock has gained value.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockMovingCross() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2022-03-29" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "50" + System.lineSeparator()
            + "200" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);

    writeMessage("The dates at which moving crossovers have occurred are: ");
    writeMessage("1. 2023-01-24--->POSITIVE");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockMovingCrossXYInconsistency() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2022-03-29" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "500" + System.lineSeparator()
            + "20" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);

    writeMessage("The X value should be strictly less than Y.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

  @Test
  public void testStockMovingCrossXYInconsistency2() {
    reqOutput = new StringBuilder();
    String input = "12" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "NVDA" + System.lineSeparator()
            + "2022-03-29" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "20" + System.lineSeparator()
            + "20" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13" + System.lineSeparator();
    runController(input);

    writeMessage("The X value should be strictly less than Y.");
    assertTrue(out.toString().contains(reqOutput.toString()));
  }

}
