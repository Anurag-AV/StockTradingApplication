package view;

/**
 * This interface represents the view component in the model-view-controller (MVC) architecture. It
 * defines methods for displaying output operations using PrintStream. Implementations of this
 * interface are responsible for presenting information to the user.
 * Changes: This view extends the AbstractView and all the common functionalities that are required
 * for any type of view implementation have been abstracted out.
 */
public interface MainViewInterface extends AbstractView {

  /**
   * Displays the primary menu options of the application.
   */
  void primaryMenu();

  /**
   * Displays the menu for creating a new portfolio.
   */
  void createPortfolioMenu();

  /**
   * Displays the menu for stock statistics available.
   */
  void stockView();


  /**
   * Displays that the system is processing data prompt to the user.
   */
  void fetchDetailsMsg();

}
