package view.gui;


import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;


/**
 * This class is used to create an input form through which user input is taken.
 */
public class InputForm {
  private final JPanel form;
  private final JPanel parent;
  private final String title;
  private final ActionListener listener;
  private final String action;

  /**
   * Constructor to initialize the popup features.
   *
   * @param action   is the action to be performed.
   * @param parent   is the parent of the button.
   * @param listener is the listener object.
   * @param title    is the title of the button.
   */
  InputForm(String action, JPanel parent, ActionListener listener, String title) {
    this.title = title;
    this.parent = parent;
    this.action = action;
    this.listener = listener;
    form = new JPanel();
    form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
  }

  /**
   * This method adds a label to the input form.
   *
   * @param label is the label to be added.
   * @return the updated input form.
   */
  public InputForm addLabel(JLabel label) {
    form.add(label);
    return this;
  }

  /**
   * This method adds a text field to the input form.
   *
   * @param textField is the text field to be added.
   * @return the updated input form.
   */
  public InputForm addTextField(JTextField textField) {
    form.add(textField);
    return this;
  }

  /**
   * This method adds the date chooser to the input form.
   *
   * @param dateField is the date chooser.
   * @return the updated input form.
   */
  public InputForm addDateField(JDateChooser dateField) {
    form.add(dateField);
    return this;
  }

  /**
   * This method is used to display the created input form on the parent.
   */
  public void show() {
    JPanel finalContainer = new JPanel();
    finalContainer.add(form);

    int option = JOptionPane.showConfirmDialog(parent, finalContainer,
            this.title, JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE);
    if (option == JOptionPane.OK_OPTION) {
      this.listener.actionPerformed(new ActionEvent(this,
              ActionEvent.ACTION_PERFORMED, this.action));
    }
  }
}
