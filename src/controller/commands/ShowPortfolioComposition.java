package controller.commands;

import java.util.Objects;

import controller.CommandOptionInterface;
import model.MainModelInterface;
import view.AbstractView;


/**
 * This class implements the show portfolio composition option of our application.
 */
public class ShowPortfolioComposition implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;


  /**
   * Constructor to initialize class variables to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   */
  public ShowPortfolioComposition(AbstractView view, MainModelInterface model) {
    this.view = view;
    this.model = model;
  }

  /**
   * This method shows the portfolio composition using the model and displays the
   * appropriate prompt using the controller.
   */
  private void showPortfolioComposition() {
    try {
      view.fetchDetailsMsg();
      String stockDetails = model.showComposition();
      view.showMsg("The Portfolio Composition is as follows: ");
      view.showPortfolioComposition(stockDetails.split("\n"));
    } catch (IllegalArgumentException e) {
      if (Objects.equals(e.getMessage(), "Portfolio does not have any stocks.")) {
        view.showMsg("Portfolio has no stocks");
      } else {
        view.showErrorMsg(e.getMessage());
      }
    }
  }

  @Override
  public void execute() {
    showPortfolioComposition();
  }
}
