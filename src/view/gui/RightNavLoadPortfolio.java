package view.gui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;


/**
 * This class represents the right navigation panel for the load portfolio option.
 */
public class RightNavLoadPortfolio extends JPanel {

  /**
   * Constructor to set all the buttons, action listeners and input containers in the panel.
   *
   * @param listener       is the action listener.
   * @param inputContainer is the input container.
   * @param parent         is the parent.
   */
  public RightNavLoadPortfolio(ActionListener listener,
                               InputContainer inputContainer, ActionListener parent) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JTextField loadPortfolioField = new JTextField();
    inputContainer.setText("loadPortfolio", loadPortfolioField);
    add(loadPortfolioField);
    add(new CustomButton("Load portfolio",
            "loadPortfolio",
            listener,
            true, "").get());
  }

}
