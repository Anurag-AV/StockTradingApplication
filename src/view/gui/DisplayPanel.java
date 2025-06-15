package view.gui;

import javax.swing.JTextArea;
import javax.swing.JPanel;

/**
 * This class represents the display area of our graphical user interface where the data is
 * displayed to the user.
 */
public class DisplayPanel extends JPanel {
  public JTextArea pTextArea;

  /**
   * Constructor to set properties of our display area.
   *
   * @param data is the content to be displayed.
   */
  public DisplayPanel(String data) {
    pTextArea = new JTextArea(data);
    pTextArea.setOpaque(true);
    pTextArea.setBackground(null);
    pTextArea.setEditable(false);
    add(pTextArea);
  }
}
