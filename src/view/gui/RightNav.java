package view.gui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;


/**
 * This class represents the right navigation panel of our application.
 */
public class RightNav extends JPanel {

  /**
   * Constructor to set all the buttons, action listeners and input containers in the panel.
   *
   * @param module         is the option that user selects from the left navigation panel.
   * @param listener       is the action listener.
   * @param inputContainer is the input container.
   * @param parent         is the parent.
   */
  public RightNav(String module, ActionListener listener, InputContainer inputContainer,
                  ActionListener parent) {

    switch (module) {
      case "portfolioOptions":
        add(new RightNavPortfolioOptions(listener, inputContainer, parent));
        break;
      case "stockAnalysis":
        add(new RightNavStockAnalysis(listener, inputContainer, parent));
        break;
      case "loadPortfolio":
        add(new RightNavLoadPortfolio(listener, inputContainer, parent));
        break;
      default:
        add(new JPanel().add(new JLabel("Please select an option")));
    }
  }

}
