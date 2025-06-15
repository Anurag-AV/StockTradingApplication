package controllertest;


import view.MainViewInterface;

/**
 * A mock implementation of MainViewInterface to test the controller.
 */
public class MockView implements MainViewInterface {

  /**
   * Displays the primary menu options of the application.
   */
  @Override
  public void primaryMenu() {
    return;
  }

  /**
   * Displays the menu for creating a new portfolio.
   */
  @Override
  public void createPortfolioMenu() {
    return;
  }

  /**
   * Displays the menu for stock statistics available.
   */
  @Override
  public void stockView() {
    return;
  }

  @Override
  public void showList(String list) {
    return;
  }

  /**
   * Displays the composition of the portfolio is proper column format.
   *
   * @param stockDetails is the composition present in the portfolio.
   */
  @Override
  public void showPortfolioComposition(String[] stockDetails) {
    return;
  }

  /**
   * Displays a custom message.
   *
   * @param s is the message to be displayed.
   */
  @Override
  public void showMsg(String s) {
    return;
  }

  @Override
  public void showErrorMsg(String s) {
    return;
  }

  @Override
  public void showSuccessMsg(String s) {
    return;
  }

  /**
   * Displays that the system is processing data prompt to the user.
   */
  @Override
  public void fetchDetailsMsg() {
    return;
  }
}
