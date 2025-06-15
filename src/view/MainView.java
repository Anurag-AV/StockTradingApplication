package view;

import java.io.IOException;

/**
 * This class implements the view in our model view controller(MVC) architecture. It implements
 * methods for displaying user interface elements and handling output operations using PrintStream.
 * This class is basically used responsible for presenting information to user.
 * Changes: The earlier redundant methods have been removed and now the showMsg() function will
 * display the message taking a string containing a message as input.
 */
public class MainView implements MainViewInterface {

  private final Appendable out;

  /**
   * Constructor of the class to assign the PrintStream object.
   *
   * @param out is the PrintStream object.
   */
  public MainView(Appendable out) {
    this.out = out;
  }

  private void writeMessage(String message) throws IllegalStateException {
    try {
      out.append(message).append(System.lineSeparator());

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void primaryMenu() {
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
  }

  @Override
  public void stockView() {
    writeMessage("Menu:");
    writeMessage("1. Check Stock Performance for a Day");
    writeMessage("2. Check Stock Performance over a Time Period");
    writeMessage("3. Find X-Day Moving Average of a Stock");
    writeMessage("4. Find Crossover for a Stock");
    writeMessage("5. Find Moving Crossover for a Stock");
    writeMessage("6. View stock performance chart");
    writeMessage("7. Exit to previous menu");
    writeMessage("Please enter your choice: ");
  }

  @Override
  public void createPortfolioMenu() {
    writeMessage("1. Add Stocks to Portfolio using Ticker Symbol.");
    writeMessage("2. Add Stocks to Portfolio using Company name.");
    writeMessage("3. Save Portfolio and go to previous menu.");
  }

  @Override
  public void fetchDetailsMsg() {
    writeMessage("Please wait....processing.");
  }

  @Override
  public void showErrorMsg(String s) {
    showMsg("Error: " + s);
  }

  @Override
  public void showSuccessMsg(String s) {
    showMsg("Success: " + s);
  }

  @Override
  public void showList(String portfolioList) {
    String[] list = portfolioList.split(",");
    for (int i = 1; i <= list.length; i++) {
      writeMessage(i + ". " + list[i - 1]);
    }
  }

  @Override
  public void showPortfolioComposition(String[] stockDetails) {
    writeMessage(String.format("%-15s%-10s%-15s%-15s%-15s", "Ticker Symbol", "Quantity",
            "Purchase Price", "Purchase Date", "Company Name"));
    for (String stockDetail : stockDetails) {
      String[] data = stockDetail.split(",");
      writeMessage(String.format("%-15s%-10s%-15s%-15s%-15s", data[0], data[2], data[3],
              data[4], data[1]));
    }
  }

  @Override
  public void showMsg(String s) {
    writeMessage(s);
  }
}

