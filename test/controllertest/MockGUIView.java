package controllertest;

import java.awt.event.ActionListener;
import java.util.Map;

import view.gui.MainGUIViewInterface;

/**
 * This class is a mock graphical user interface view class for testing the controller.
 */
public class MockGUIView implements MainGUIViewInterface {

  StringBuilder logger;
  StringBuilder errorLogger;
  StringBuilder successLogger;
  Map<String, String> knownInput;

  /**
   * Constructor to initialize class variables.
   * @param logger is the log.
   * @param successLogger is the success log.
   * @param errorLogger is the error log.
   * @param knownInput is the input.
   */
  public MockGUIView(StringBuilder logger, StringBuilder successLogger, StringBuilder errorLogger
          , Map<String, String> knownInput) {
    this.successLogger = successLogger;
    this.errorLogger = errorLogger;
    this.logger = logger;
    this.knownInput = knownInput;
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
    logger.append(s);
  }

  @Override
  public void showErrorMsg(String s) {
    errorLogger.append(s);
  }

  @Override
  public void showSuccessMsg(String s) {
    successLogger.append(s);
  }

  @Override
  public void fetchDetailsMsg() {
    return;
  }

  /**
   * set the action listener so that the controller can listen to view actions.
   *
   * @param listener controller Action listener object.
   */
  @Override
  public void setActionListener(ActionListener listener) {
    return;
  }

  /**
   * Used by controller to retrieve the inputs for the necessary action in the UI.
   *
   * @param inputType the action type for which the input is retrieved.
   * @return the input string.
   */
  @Override
  public String getInput(String inputType) {
    return knownInput.get(inputType);
  }

  /**
   * Used by controller to call a certain action of the view Listener if required.
   *
   * @param action action string available in the view.
   */
  @Override
  public void takeAction(String action) {
    return;
  }
}
