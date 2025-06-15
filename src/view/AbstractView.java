package view;

/**
 * This interface captures all the common functionalities of all the views supported by our
 * application. Every view implementation of our application will be implemented using this
 * interface.
 */
public interface AbstractView {
  void showList(String list);

  /**
   * Displays the composition of the portfolio is proper column format.
   *
   * @param stockDetails is the composition present in the portfolio.
   */
  void showPortfolioComposition(String[] stockDetails);

  /**
   * Displays a custom message.
   *
   * @param s is the message to be displayed.
   */
  void showMsg(String s);

  /**
   * This method shows an error message in our application.
   *
   * @param s is the error message.
   */
  void showErrorMsg(String s);

  /**
   * This method displays success message in the application.
   *
   * @param s is the success message.
   */
  void showSuccessMsg(String s);

  /**
   * Displays that the system is processing data prompt to the user.
   */
  void fetchDetailsMsg();

}
