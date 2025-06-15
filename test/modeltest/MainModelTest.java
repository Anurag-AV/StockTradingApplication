package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;

import model.MainModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * A JUnit class to test the functionalities of the MainModel class to ensure that all the
 * requirements are working in proper manner.
 */
public class MainModelTest {

  MainModel model;
  String stockList;
  String headers;

  String details;

  HashMap<String, Double> values;

  @Before
  public void setUp() {
    stockList = String.format("NVDA,NVIDIA Corp,10.0,20.4,%1$s\nMSFT,Microsoft Corporation," +
            "15.0,30.5,%1$s\n" +
            "IBM,International Business Machines Corp,10.0,100.0,%1$s\n", LocalDate.now());
    headers = "Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n";
    values = new HashMap<>();
    values.put("NVDA" + LocalDate.now(), 20.4);
    values.put("MSFT" + LocalDate.now(), 30.5);
    values.put("IBM" + LocalDate.now(), 100.0);

    details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.6100,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608\n" +
            "2024-03-22,911.4100,947.7799,908.3401,942.8900,58671936\n" +
            "2024-03-21,923.0000,926.4800,904.0500,914.3500,48037231\n" +
            "2024-03-20,897.9700,904.1000,882.2300,903.7200,47906279\n" +
            "2024-03-19,867.0000,905.4400,850.1000,893.9800,67217127\n" +
            "2024-03-18,903.8800,924.0500,870.8500,884.5500,66897593\n" +
            "2024-03-15,869.3000,895.4600,862.5700,878.3650,64208616\n" +
            "2024-03-14,895.7700,906.4600,866.0000,879.4400,59711081\n" +
            "2024-03-13,910.5500,915.0400,884.3500,908.8800,62726626\n" +
            "2024-03-12,880.4900,919.6000,861.5010,919.1300,65437480\n" +
            "2024-03-11,864.2900,887.9700,841.6600,857.7400,66611510\n" +
            "2024-03-08,951.3790,974.0000,865.0600,875.2800,114226906\n" +
            "2024-03-07,901.5800,927.6700,896.0201,926.6900,60811916\n" +
            "2024-03-06,880.2200,897.2400,870.3001,887.0000,58252030\n" +
            "2024-03-05,852.7000,860.9700,834.1701,859.6400,52063930\n" +
            "2024-03-04,841.3000,876.9500,837.1900,852.3700,61561645\n" +
            "2024-03-01,800.0000,823.0000,794.3503,822.7900,47913510\n" +
            "2024-02-29,790.9400,799.9000,783.5000,791.1200,50728898\n" +
            "2024-02-28,776.2000,789.3300,771.2500,776.6300,39311040\n" +
            "2024-02-27,793.8100,794.8000,771.6200,787.0100,39170524\n" +
            "2024-02-26,797.0000,806.4599,785.0500,790.9200,50397273\n" +
            "2024-02-23,807.9000,823.9400,775.7000,788.1700,82938837\n" +
            "2024-02-22,750.2500,785.7500,742.2000,785.3800,86509974\n" +
            "2024-02-21,680.0600,688.8800,662.4800,674.7200,69029813\n" +
            "2024-02-20,719.4700,719.5600,677.3400,694.5200,70483310\n" +
            "2024-02-16,741.0000,744.0200,725.0100,726.1300,49532662\n" +
            "2024-02-15,738.6900,739.7500,724.0000,726.5800,42012181\n" +
            "2024-02-14,732.0200,742.3600,719.3800,739.0000,50491742\n" +
            "2024-02-13,704.0000,734.5000,696.2000,721.2800,60258015\n" +
            "2024-02-12,726.0000,746.1100,712.5000,722.4800,61371018\n" +
            "2024-02-09,705.3300,721.8500,702.1200,721.3300,43663689\n" +
            "2024-02-08,700.7400,707.9400,694.5500,696.4100,41442211\n" +
            "2024-02-07,683.1900,702.2000,676.0000,700.9900,49557455\n" +
            "2024-02-06,696.3000,697.5399,663.0000,682.2300,68311136\n";
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelObj() {
    model = new MainModel();
    model.showComposition();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPortfolioName() {
    model = new MainModel();
    model.setPortfolioName("*");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddStocksToPortfolio() {
    model = new MainModel();
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", -10,
            200, LocalDate.now());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddStocksToPortfolio1() {
    model = new MainModel();
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10,
            -200, LocalDate.now());
  }


  @Test
  public void testAddStocksToPortfolio3() {
    model = new MainModel();
    model.setPortfolioName("P1");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10,
            20.4, LocalDate.now());
    assertEquals("NVDA,NVIDIA Corp,10.0,20.4," + LocalDate.now() + "\n",
            model.showComposition());
  }

  @Test
  public void testAddStocksToPortfolio4() {
    model = new MainModel();
    model.setPortfolioName("P1");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10,
            20.4, LocalDate.now());
    model.addStocksToPortfolio("MSFT", "Microsoft Corporation",
            15, 30.5, LocalDate.now());
    model.addStocksToPortfolio("IBM", "International Business Machines Corp",
            10, 100, LocalDate.now());
    assertEquals(stockList, model.showComposition());
  }

  @Test
  public void testAddStocksToPortfolio5() {
    model = new MainModel();
    model.setPortfolioName("P1");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10,
            20.4, LocalDate.now());
    model.addStocksToPortfolio("MSFT", "Microsoft Corporation",
            15, 30.5, LocalDate.now());
    assertEquals(stockList.split("\n")[0] + "\n" + stockList.split("\n")[1]
            + "\n", model.showComposition());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio() {
    model = new MainModel();
    model.loadPortfolio("", stockList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio1() {
    model = new MainModel();
    model.loadPortfolio("P1", stockList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio3() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,10,20.4,%1$s\n");
  }

  @Test
  public void testLoadPortfolio4() {
    model = new MainModel();
    model.loadPortfolio("p1", headers + "NVDA,NVIDIA Corp,10.0,20.4," +
            LocalDate.now() + "\n");
    assertEquals("NVDA,NVIDIA Corp,10.0,20.4," + LocalDate.now() + "\n",
            model.showComposition());
  }

  @Test
  public void testLoadPortfolio5() {
    model = new MainModel();
    model.loadPortfolio("p1", headers + stockList);
    assertEquals(stockList, model.showComposition());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio6() {
    model = new MainModel();
    model.loadPortfolio("/abc", headers + stockList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio7() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,10,20.4,03-01-2024\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio8() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVIDIA Corp,10,20.4,2024-01-03\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio9() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,10,-20.4,2024-01-03\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio10() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,10,-20.4,2024-01-03,05\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio11() {
    model = new MainModel();
    model.loadPortfolio("p1", ",NVIDIA Corp,10,-20.4,2024-01-03,05\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio12() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,10.5,20.4,2024-01-03,05\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadPortfolio13() {
    model = new MainModel();
    model.loadPortfolio("p1", "NVDA,NVIDIA Corp,-10,20.4,2024-01-03,05\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    HashMap<String, Double> hashMap1 = new HashMap<>();
    model.getPortfolioValue("2024-01-03", hashMap1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue1() {
    model = new MainModel();
    model.getPortfolioValue("2024-01-03", values);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue2() {
    model = new MainModel();
    model.getPortfolioValue("01-03-2024", values);
  }

  @Test
  public void testGetPortfolioValue3() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    assertEquals(stockList, model.showComposition());
    assertEquals(1661.5, model.getPortfolioValue("2024-01-03", values), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue4() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    assertEquals(stockList, model.showComposition());
    assertEquals(1661.5, model.getPortfolioValue("", values), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue5() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    assertEquals(stockList, model.showComposition());
    assertEquals(1661.5, model.getPortfolioValue("2024-13-03", values), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioValue6() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    assertEquals(stockList, model.showComposition());
    assertEquals(1661.5, model.getPortfolioValue("2024-01-32", values), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testListPortfolio1() {
    File[] files = new File[5];
    model = new MainModel();
    String fileName = model.listPortfolio(files);
  }

  @Test
  public void testSavePortfolio() {
    model = new MainModel();
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp",
            10, 20.4, LocalDate.now());
    model.addStocksToPortfolio("MSFT", "Microsoft Corporation",
            15, 30.5, LocalDate.now());
    String saveData = model.savePortfolio();
    assertEquals(headers + stockList.split("\n")[0] + "\n" +
            stockList.split("\n")[1] + "\n", saveData);
  }

  @Test
  public void testSavePortfolio1() {
    model = new MainModel();
    model.loadPortfolio("P1", headers);
    String saveData = model.savePortfolio();
    assertEquals("Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n",
            saveData);
  }

  @Test
  public void testShowComposition() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String composition = model.showComposition();
    assertEquals(stockList, composition);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShowComposition2() {
    model = new MainModel();
    model.setPortfolioName("P1");
    model.showComposition();
  }

  @Test
  public void testCreatePortfolioLoadOther() {
    model = new MainModel();
    model.setPortfolioName("P2");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10, 20.4,
            LocalDate.now());
    HashMap<String, Double> h1 = new HashMap<>();
    h1.put("NVDA" + LocalDate.now(), 20.4);
    assertEquals(204.0, model.getPortfolioValue("2024-03-08", h1), 0.001);

    model.loadPortfolio("P1", headers + stockList);
    assertEquals(1661.5, model.getPortfolioValue("2024-03-08", values), 0.001);
  }

  @Test
  public void testCreatePortfolioShowCompositionOther() {
    model = new MainModel();
    model.setPortfolioName("P2");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10, 20.4,
            LocalDate.now());
    HashMap<String, Double> h1 = new HashMap<>();
    h1.put("NVDA" + LocalDate.now(), 20.4);
    assertEquals(204.0, model.getPortfolioValue("2024-03-08", h1), 0.001);

    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
  }

  @Test
  public void testLoadPortfolioCreateOther() {
    model = new MainModel();

    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);

    model.setPortfolioName("P2");
    model.addStocksToPortfolio("NVDA", "NVIDIA Corp", 10,
            20.4, LocalDate.now());
    HashMap<String, Double> h1 = new HashMap<>();
    h1.put("NVDA" + LocalDate.now(), 20.4);
    assertEquals(204.0, model.getPortfolioValue("2024-03-08", h1), 0.001);
  }

  @Test
  public void testLoadPortfolioLoadOther() {
    model = new MainModel();

    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);

    model.loadPortfolio("P2", headers + String.format("NVDA,NVIDIA Corp,10,20.4,%1$s\n",
            LocalDate.now()));
    HashMap<String, Double> h1 = new HashMap<>();
    h1.put("NVDA" + LocalDate.now(), 20.4);
    assertEquals(204.0, model.getPortfolioValue("2024-03-08", h1), 0.001);
    assertEquals(String.format("NVDA,NVIDIA Corp,10.0,20.4,%1$s\n", LocalDate.now()),
            model.showComposition());
  }

  @Test
  public void testStockGainLoseDay() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.6100,51122415";
    String res = model.stockGainLose(details, "NVDA", "2024-03-26");
    assertEquals("The stock has lost value.", res);
  }

  @Test
  public void testStockGainLoseDay1() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,900.5000,963.7500,925.0200,925.6100,51122415";
    String res = model.stockGainLose(details, "NVDA", "2024-03-26");
    assertEquals("The stock has gained value.", res);
  }

  @Test
  public void testStockGainLoseDay2() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,925.6100,963.7500,925.0200,925.6100,51122415";
    String res = model.stockGainLose(details, "NVDA", "2024-03-26");
    assertEquals("No loss or gain in stock.", res);
  }

  @Test
  public void testStockGainLoseDay3() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,925.6100,963.7500,925.0200,925.6100,51122415";
    try {
      String res = model.stockGainLose(details, "NVDA", "2024-03-24");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The stock market was closed on the given date", e.getMessage());
    }
  }

  @Test
  public void testStockGainLosePeriod() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.6100,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    String res = model.stockGainLoseTimeSpan(details, "NVDA", "2024-03-25",
            "2024-03-26");
    assertEquals("The stock has lost value.", res);
  }

  @Test
  public void testStockGainLosePeriod1() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,975.6100,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    String res = model.stockGainLoseTimeSpan(details, "NVDA", "2024-03-25",
            "2024-03-26");
    assertEquals("The stock has gained value.", res);
  }

  @Test
  public void testStockGainLosePeriod2() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,950.02,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    String res = model.stockGainLoseTimeSpan(details, "NVDA", "2024-03-25",
            "2024-03-26");
    assertEquals("No loss or gain in stock.", res);
  }

  @Test
  public void testStockGainLosePeriod3() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,950.02,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    try {
      String res = model.stockGainLoseTimeSpan(details, "NVDA", "2024-03-24",
              "2024-03-26");
      fail();
    } catch (Exception e) {
      assertEquals("The stock market was closed on the given date", e.getMessage());
    }
  }

  @Test
  public void testStockGainLosePeriod4() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,950.02,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    String res = model.stockGainLoseTimeSpan(details, "NVDA",
            "2024-03-26", "2024-03-25");
    assertEquals("No loss or gain in stock.", res);
  }

  @Test
  public void testXDayMovAvg() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,950.02,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    double xDay = model.xDayMov(details, "NVDA", "2024-03-26", 1);
    assertEquals(950.02, xDay, 0.0001);
  }

  @Test
  public void testXDayMovAvg1() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.61,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    double xDay = model.xDayMov(details, "NVDA", "2024-03-26", 2);
    assertEquals(937.815, xDay, 0.0001);
  }

  @Test
  public void testXDayMovAvg2() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.61,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    try {
      double xDay = model.xDayMov(details, "NVDA", "2024-03-24", 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The stock market was closed on the given date", e.getMessage());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXDayMovAvg3() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.61,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    double xDay = model.xDayMov(details, "NVDA", "2024-03-26", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXDayMovAvg4() {
    model = new MainModel();
    String details = "timestamp,open,high,low,close,volume\n" +
            "2024-03-26,958.5000,963.7500,925.0200,925.61,51122415\n" +
            "2024-03-25,939.4100,967.6599,935.1000,950.0200,55213608";
    double xDay = model.xDayMov(details, "NVDA", "2024-03-26", -1);
  }

  @Test
  public void testCrossover() {
    model = new MainModel();
    String res = model.calcCrossover(details, "NVDA", "2024-03-26",
            "2024-03-20");
    assertEquals("No crossovers occurred over the given period.", res);
  }

  @Test
  public void testCrossover1() {
    model = new MainModel();
    try {
      String res = model.calcCrossover(details, "NVDA", "2024-03-26",
              "2024-02-17");
      fail();
    } catch (Exception e) {
      assertEquals("The stock market was closed on the given date", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossover() {
    model = new MainModel();
    String res = model.calcMovingCrossover(details, "NVDA", "2024-03-26",
            "2024-03-20", 10, 20);
    assertEquals("No moving crossovers occurred over the given period.", res);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingCrossover1() {
    model = new MainModel();
    String res = model.calcMovingCrossover(details, "NVDA", "2024-03-26",
            "2024-03-20", 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingCrossover2() {
    model = new MainModel();
    String res = model.calcMovingCrossover(details, "NVDA", "2024-03-26",
            "2024-03-20", 3, 2);
  }

  @Test
  public void testSellStocks() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);

    String res = model.sellStocks("NVDA", 5, LocalDate.now().toString());
    assertEquals("Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n" +
            "MSFT,Microsoft Corporation,15.0,30.5," + LocalDate.now() + "\n" +
            "IBM,International Business Machines Corp,10.0,100.0," + LocalDate.now() + "\n" +
            "NVDA,NVIDIA Corp,5.0,20.4," + LocalDate.now() + "\n", res);
  }

  @Test
  public void testSellStocks1() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
    try {
      String res = model.sellStocks("NVDA", 15, LocalDate.now().toString());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough shares owned", e.getMessage());
    }
  }

  @Test
  public void testSellStocks2() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
    try {
      String res = model.sellStocks("NVDA", 0, LocalDate.now().toString());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Please enter a valid quantity of shares", e.getMessage());
    }
  }

  @Test
  public void testSellStocks3() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
    try {
      String res = model.sellStocks("NVDA", -1, LocalDate.now().toString());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Please enter a valid quantity of shares", e.getMessage());
    }
  }

  @Test
  public void testSellStocks4() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
    try {
      String res = model.sellStocks("NVDA", 10, "2024-01-03");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not owned", e.getMessage());
    }
  }

  @Test
  public void testSellStocks5() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);
    try {
      String res = model.sellStocks("GOOG", 10, LocalDate.now().toString());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock not owned", e.getMessage());
    }
  }

  @Test
  public void testSellStocks6() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + stockList);
    String saveData = model.showComposition();
    assertEquals(stockList, saveData);

    String res = model.sellStocks("NVDA", 5.5, LocalDate.now().toString());
    assertEquals("Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date\n" +
            "MSFT,Microsoft Corporation,15.0,30.5," + LocalDate.now() + "\n" +
            "IBM,International Business Machines Corp,10.0,100.0," + LocalDate.now() + "\n" +
            "NVDA,NVIDIA Corp,4.5,20.4," + LocalDate.now() + "\n", res);
  }

  @Test
  public void testCostBasis() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + "NVDA,NVIDIA Corp,10,20.4,2024-03-27\n");
    String saveData = model.showComposition();
    assertEquals("NVDA,NVIDIA Corp,10.0,20.4,2024-03-27\n", saveData);

    double res = model.getCostBasis("NVDA,10,2024-03-25,BUY,100\n", "2024-03-24");
    assertEquals(0, res, 0.001);
  }

  @Test
  public void testCostBasis1() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + "NVDA,NVIDIA Corp,10,20.4,2024-03-27\n");
    String saveData = model.showComposition();
    assertEquals("NVDA,NVIDIA Corp,10.0,20.4,2024-03-27\n", saveData);

    double res = model.getCostBasis("NVDA,10,2024-03-25,BUY,100\n", "2024-03-26");
    assertEquals(1000, res, 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCostBasis2() {
    model = new MainModel();
    model.loadPortfolio("P1", headers + "NVDA,NVIDIA Corp,10,20.4,2024-03-27\n");
    String saveData = model.showComposition();
    assertEquals("NVDA,NVIDIA Corp,10.0,20.4,2024-03-27\n", saveData);

    double res = model.getCostBasis("NVDA,10,2024-03-25,BUY,100\n", "01-03-2024");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCostBasis3() {
    model = new MainModel();
    double res = model.getCostBasis("NVDA,10,2024-03-25,BUY,100\n", "01-03-2024");
  }
}
