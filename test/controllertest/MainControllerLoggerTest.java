package controllertest;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import controller.MainController;
import controller.MainControllerInterface;
import model.MainModelInterface;
import view.MainViewInterface;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class to test the functionalities of the MainController class. This class tests
 * whether the controller  sends the correct data to the model.
 */
public class MainControllerLoggerTest {

  StringBuilder logger;
  MainModelInterface model;
  MainViewInterface view;
  OutputStream out;
  PrintStream pr;
  InputStream in;
  Appendable ap;
  boolean demoExists = false;

  @Before
  public void setUp() {
    logger = new StringBuilder();
    model = new MockModel(logger);
    view = new MockView();
    out = new ByteArrayOutputStream();
    pr = new PrintStream(out);
    ap = new PrintStream(new ByteArrayOutputStream());
  }

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
            + "demo" + System.lineSeparator()
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
    demoExists = true;
  }

  @Test
  public void testDataSentToModelAddStockOld() {
    String input = "1" + System.lineSeparator()
            + "demo" + 1 + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "13";
    runController(input);
    String requiredOutput = "demo1 GOOG Alphabet Inc - Class C 10.0 " + LocalDate.now();
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelAddStockNew() {
    String input = "2" + System.lineSeparator()
            + "demo" + 2 + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "13";
    runController(input);
    String requiredOutput = "demo2 GOOG Alphabet Inc - Class C 10.0 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelDailyStockAnalysis() {
    String input = "12" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";
    runController(input);
    String requiredOutput = "GOOG 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelGetCostBasis() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "10" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelGetPortfolioPerformanceChart() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "11" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "2024-03-23" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo 2024-03-232024-03-242024-03-252024-03-23 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelGetPortfolioValueActual() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "6" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelGetPortfolioValueTotal() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelSellStocks() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "9" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "1" + System.lineSeparator()
            + "2024-03-25" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo GOOG 1.0 2024-03-25";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelLoadPortfolio() {
    String input = "3" + System.lineSeparator()
            + "demo" + System.lineSeparator()
            + "13";

    if (!demoExists) {
      createDemo();
    }
    logger = new StringBuilder();
    model = new MockModel(logger);
    MainControllerInterface controller = new MainController(setReadable(input), view, model);
    controller.startController();
    String requiredOutput = "demo ";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelMovingCrossover() {
    String input = "12" + System.lineSeparator()
            + "5" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "30" + System.lineSeparator()
            + "100" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";
    runController(input);
    String requiredOutput = "GOOG 2023-10-23 2024-03-26";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelStockCrossovers() {
    String input = "12" + System.lineSeparator()
            + "4" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";
    runController(input);
    String requiredOutput = "GOOG 2023-10-23 2024-03-26";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelStockPerformanceChart() {
    String input = "12" + System.lineSeparator()
            + "6" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";

    runController(input);
    String requiredOutput = "2023-10-23 2024-03-26";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelStockTimespanGainLose() {
    String input = "12" + System.lineSeparator()
            + "2" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "2024-03-26" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";

    runController(input);
    String requiredOutput = "GOOG 2023-10-23 2024-03-26";
    assertEquals(requiredOutput, logger.toString());
  }

  @Test
  public void testDataSentToModelXdayMovingAvg() {
    String input = "12" + System.lineSeparator()
            + "3" + System.lineSeparator()
            + "GOOG" + System.lineSeparator()
            + "2023-10-23" + System.lineSeparator()
            + "30" + System.lineSeparator()
            + "7" + System.lineSeparator()
            + "13";

    runController(input);
    String requiredOutput = "GOOG 2023-10-23 30";
    assertEquals(requiredOutput, logger.toString());
  }


}
