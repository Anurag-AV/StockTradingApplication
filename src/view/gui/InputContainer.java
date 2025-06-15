package view.gui;

import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * The implementation of this interface will hold all the input elements of the
 * graphical user interface. The implementations of this interface will provide the entered data
 * from the GUI to the controller.
 */
public interface InputContainer {

  /**
   * This method will set the text of the input box of the GUI.
   *
   * @param input is the text to be set.
   * @param field is the text filed.
   */
  void setText(String input, JTextField field);

  /**
   * This method will get the text from the input box of the GUI.
   *
   * @param element is the text.
   * @return the entered text.
   */
  String getText(String element);

  /**
   * This method will set a JCombo box through which user can enter inputs.
   *
   * @param input is the input to be entered.
   * @param field is the field whose input needs to be entered.
   */
  void setComboBox(String input, JComboBox field);

  /**
   * This method is used to get the elements of the combo box.
   *
   * @param element is the elements entered by the user.
   * @return the inputs after reading them.
   */
  String getComboBox(String element);

  /**
   * This method is used to set the date in the graphical user interface using a date picker.
   *
   * @param input is the input entered by the user.
   * @param date  is the date chosen via the date picker.
   */
  void setDate(String input, com.toedter.calendar.JDateChooser date);

  /**
   * This method is used to get the graphical user interface.
   *
   * @param element is the date entered.
   * @return a date.
   */
  Date getDate(String element);

  /**
   * This method clears the previously added input values from all the fields.
   */
  void clearFields();
}
