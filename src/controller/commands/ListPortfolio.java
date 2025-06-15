package controller.commands;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import controller.AbstractController;
import controller.CommandOptionInterface;
import model.MainModelInterface;
import view.AbstractView;

/**
 * This class to represents the list portfolio option of our application.
 */
public class ListPortfolio extends AbstractController implements CommandOptionInterface {

  private final AbstractView view;
  private final MainModelInterface model;


  /**
   * Constructor to initialize the class variable to respective objects.
   *
   * @param view  is the view object of MVC architecture.
   * @param model is the model object of MVC architecture.
   */
  public ListPortfolio(AbstractView view, MainModelInterface model) {
    this.view = view;
    this.model = model;
  }

  /**
   * Method to call the model and get list of all portfolios which is then displayed by the view.
   */
  private void listPortfolioOption() {
    try {
      view.fetchDetailsMsg();
      File directory = new File(getPortfolioFilesPath());
      File[] files = directory.listFiles();
      String portfolioList = model.listPortfolio(files);
      if (!Objects.equals(portfolioList, "")) {
        view.showMsg("Current portfolios are: ");
        view.showList(portfolioList);
      } else {
        throw new IllegalArgumentException("There are no portfolios created.");
      }
    } catch (IOException | IllegalArgumentException e) {
      view.showErrorMsg(e.getMessage());
    }
  }

  @Override
  public void execute() {
    listPortfolioOption();
  }
}
