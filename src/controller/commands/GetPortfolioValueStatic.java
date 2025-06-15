package controller.commands;

import java.io.IOException;
import java.time.LocalDate;

import controller.CommandOptionInterface;
import model.MainModelInterface;
import view.AbstractView;

/**
 * Class represents the get portfolio value option irrespective of the purchase dates of our
 * application.
 */
public class GetPortfolioValueStatic implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;
  private final LocalDate date;

  /**
   * Constructor to initialize the class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   * @param date  is the date at which value needs to be calculated.
   */
  public GetPortfolioValueStatic(AbstractView view, MainModelInterface model, LocalDate date) {
    this.view = view;
    this.model = model;
    this.date = date;
  }

  /**
   * Method to get the total value of a portfolio at a given date from the model which is then
   * displayed by the view.
   */
  private void getPortfolioValueOption() {
    try {
      GetPortfolioValue val = new GetPortfolioValue(view, model, date, false);
      val.getPortfolioValueOption();
    } catch (IllegalArgumentException | IOException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    getPortfolioValueOption();
  }
}
