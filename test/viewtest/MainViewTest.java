package viewtest;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import view.MainView;
import view.MainViewInterface;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class to test the functionalities of the MainView class. This test class checks
 * whether proper view messages are displayed or not.
 */
public class MainViewTest {

  OutputStream out;
  MainViewInterface view;

  @Before
  public void setUp() {
    out = new ByteArrayOutputStream();
    view = new MainView(new PrintStream(out));
  }

  @Test
  public void primaryMenuTest() {
    view.primaryMenu();
    assertEquals("Menu:" + System.lineSeparator() +
            "1. Create Portfolio by adding stocks without a specified date"
            + System.lineSeparator() +
            "2. Create Portfolio by adding stocks at a specific date" + System.lineSeparator() +
            "3. Load Portfolio" + System.lineSeparator() +
            "4. Show Portfolio Composition" + System.lineSeparator() +
            "5. Get Total Portfolio Value" + System.lineSeparator() +
            "   (Above option will give the total portfolio value at a certain date " +
            "irrespective of the share buy date)" + System.lineSeparator() +
            "6. Get Portfolio Value" + System.lineSeparator() +
            "   (Above option will give the total portfolio value considering only the " +
            "shares that were bought till the given date)" + System.lineSeparator() +
            "7. List Portfolio" + System.lineSeparator() +
            "8. Add Stock To a Portfolio" + System.lineSeparator() +
            "9. Sell Stocks from a Portfolio" + System.lineSeparator() +
            "10. Get Cost Basis of Portfolio" + System.lineSeparator() +
            "11. Get Portfolio performance chart" + System.lineSeparator() +
            "12. Check Stock Statistics" + System.lineSeparator() +
            "13. Exit" + System.lineSeparator() +
            "Please enter your choice: " + System.lineSeparator(), out.toString());
  }

  @Test
  public void createPortfolioMenuTest() {
    view.createPortfolioMenu();
    assertEquals("1. Add Stocks to Portfolio using Ticker Symbol."
            + System.lineSeparator() + "2. Add Stocks to Portfolio using Company" +
            " name." + System.lineSeparator() + "3. Save Portfolio and go " +
            "to previous menu." + System.lineSeparator(), out.toString());
  }

  @Test
  public void stockViewTest() {
    view.stockView();
    assertEquals("Menu:" + System.lineSeparator() +
            "1. Check Stock Performance for a Day" + System.lineSeparator() +
            "2. Check Stock Performance over a Time Period" + System.lineSeparator() +
            "3. Find X-Day Moving Average of a Stock" + System.lineSeparator() +
            "4. Find Crossover for a Stock" + System.lineSeparator() +
            "5. Find Moving Crossover for a Stock" + System.lineSeparator() +
            "6. View stock performance chart" + System.lineSeparator() +
            "7. Exit to previous menu" + System.lineSeparator() +
            "Please enter your choice: " + System.lineSeparator(), out.toString());
  }

  @Test
  public void fetchDetailsMsgTest() {
    view.fetchDetailsMsg();
    assertEquals("Please wait....processing." +
            System.lineSeparator(), out.toString());
  }

  @Test
  public void showPortfolioCompositionTest() {
    String[] data = {"NVDA,NVIDIA Corp,10,200.5,2024-01-03"};
    view.showPortfolioComposition(data);
    String result = String.format("%-15s%-10s%-15s%-15s%-15s",
            "Ticker Symbol", "Quantity",
            "Purchase Price", "Purchase Date", "Company Name") + System.lineSeparator()
            + (String.format(
            "%-15s%-10s%-15s%-15s%-15s", "NVDA", "10", "200.5",
            "2024-01-03", "NVIDIA Corp"))
            + System.lineSeparator();
    assertEquals(result, out.toString());
  }

  @Test
  public void showListTest() {
    String data = "P1,P2,P3";
    view.showList(data);
    assertEquals("1. P1" + System.lineSeparator() +
            "2. P2" + System.lineSeparator() +
            "3. P3" + System.lineSeparator(), out.toString());
  }

  @Test
  public void showMsgTest() {
    view.showMsg("This is test msg");
    assertEquals("This is test msg" + System.lineSeparator(), out.toString());
  }
}