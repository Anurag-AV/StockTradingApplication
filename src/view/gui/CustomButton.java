package view.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;


/**
 * This class is used to create a custom button for the graphical user interface.
 */
public class CustomButton {
  JButton pButton;

  /**
   * Constructor to assign button properties.
   *
   * @param title   is the text displayed in the button.
   * @param action  is the type of action performed in the action listener.
   * @param parent  is the parent of the button.
   * @param visible sets the button as visible.
   * @param toolTip is the tool tip text.
   */
  public CustomButton(String title, String action, ActionListener parent,
                      boolean visible, String toolTip) {
    pButton = new JButton(title);
    pButton.addActionListener(parent);
    pButton.setActionCommand(action);
    pButton.setVisible(visible);
    if (!toolTip.isEmpty()) {
      pButton.setToolTipText(toolTip);
    }
  }

  /**
   * This method gives a custom button.
   *
   * @return a custom button.
   */
  public JButton get() {
    return pButton;
  }
}
