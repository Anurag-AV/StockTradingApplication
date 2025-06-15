package controllertest;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import controller.MainControllerGUI;
import model.MainModel;
import model.MainModelInterface;

import static org.junit.Assert.assertEquals;

/**
 * JUnit class to test the controller for the Graphical User Interface.
 */
public class MainGUIControllerLoggerTest {
  StringBuilder logger;
  StringBuilder successLogger;
  StringBuilder errorLogger;
  MainModelInterface model;
  MainControllerGUI controller;

  @Before
  public void setUp() {
    logger = new StringBuilder();
    successLogger = new StringBuilder();
    errorLogger = new StringBuilder();
    model = new MainModel();
  }

  private void createDemo() {
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("createPortfolio", "demo60");
    knownInputs.put("buyStock", "GOOG,2024-04-09,12");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "createPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "buyStock"));

  }


  @Test
  public void testCreatePortfolioAndAddStock() {
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("createPortfolio", "demo61");
    knownInputs.put("buyStock", "GOOG,2024-04-09,12");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "createPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "buyStock"));
    String expected = "The Portfolio Composition is as follows: ";
    assertEquals(expected, logger.toString());
  }

  @Test
  public void testSellStocksInvalid() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("sellStock", "GOOG,2024-04-08,11");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "sellStock"));
    String expected = "Stock not owned";
    assertEquals(expected, errorLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolio() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "GOOG 50,MSFT 50*2024-04-09*2000");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("Portfolio loaded successfully.Stocks added successfully.",
            successLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolioNotPercent() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "GOOG 20,NVDA 20*2024-04-09*2000");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("The percentage split of shares does not equal 100 percentage",
            errorLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolioHiPercent() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "GOOG 80,NVDA 80*2025-04-09*2000");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("The percentage split of shares does not equal 100 percentage",
            errorLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolioInvalidTicker() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "GINGER 20,NVDA 80*2025-04-09*2000");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger, this.errorLogger,
            knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("Company data not found.", errorLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolioInvalidAmount() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "GINGER 20,NVDA 80*2025-04-09*");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger, this.errorLogger,
            knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("Not enough data provided", errorLogger.toString());
  }

  @Test
  public void testAddMultipleStockPortfolioInsufficientData() {
    createDemo();
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("loadPortfolio", "demo60");
    knownInputs.put("multiStock", "*2025-04-09*2000");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger, this.errorLogger,
            knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "multiStock"));
    assertEquals("Not enough data provided", errorLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolio() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo68*GOOG 20,MSFT 80*2022-04-01*2024-04-" +
            "11*20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "loadPortfolio"));
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("Portfolio saved successfully.", successLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioNotPercent() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo69*GOOG 20,MSFT 20*2022-04-01*2024-04-11" +
            "*20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    String expected = "Stock not owned";
    assertEquals("The percentage split of shares does not equal 100 percentage",
            errorLogger.toString());
  }


  @Test
  public void testDailyCostAvgPortfolioHiPercent() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo70*GOOG 90,MSFT 20*2022-04-01*2024-04-11*" +
            "20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("The percentage split of shares does not equal 100 percentage",
            errorLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioDateInverse() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo71*GOOG 90,MSFT 10*2024-04-11*2024-04-01" +
            "*20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("The start date should be before the end date", errorLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioOngoing() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo72*GOOG 90,MSFT 10*2023-04-11*-*20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("Portfolio saved successfully.", successLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioInvalidTicker() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo73*GINGER 90,MSFT 10*2023-04-11*-*20000*1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    String expected = "Stock not owned";
    assertEquals("Company data not found.", errorLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioInvalidAmount() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo73*GINGER 90,MSFT 10*2023-04-11*-**1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("Please enter a valid number", errorLogger.toString());
  }

  @Test
  public void testDailyCostAvgPortfolioInsufficienetFunds() {
    this.logger = new StringBuilder();
    this.errorLogger = new StringBuilder();
    this.successLogger = new StringBuilder();
    Map<String, String> knownInputs = new HashMap<String, String>();
    knownInputs.put("costAvgPortfolio", "demo73*GINGER 90,MSFT 10*2024-04-04*-**1*Year(s)");
    MockGUIView view = new MockGUIView(this.logger, this.successLogger,
            this.errorLogger, knownInputs);
    MainControllerGUI controller = new MainControllerGUI(view, model);
    controller.actionPerformed(new ActionEvent(view, ActionEvent.ACTION_PERFORMED,
            "costAvgPortfolio"));
    assertEquals("Please enter a valid number", errorLogger.toString());
  }
}
