package view.gui;

import com.toedter.calendar.JDateChooser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JComboBox;


/**
 * This class acts as a container for all the input fields present in the view and provides it to
 * controller as per requirement.
 */

public class InputContainerImpl implements InputContainer {


  Map<String, JTextField> labels;
  Map<String, JComboBox> comboBoxes;
  Map<String, com.toedter.calendar.JDateChooser> dates;

  /**
   * Initializes a new hashMap for each type of input in the view.
   */
  public InputContainerImpl() {
    labels = new HashMap<>();
    comboBoxes = new HashMap<>();
    dates = new HashMap<>();
  }

  @Override
  public void setText(String element, JTextField field) {
    this.labels.put(element, field);
  }

  @Override
  public String getText(String element) {
    return this.labels.get(element).getText();
  }

  @Override
  public void setComboBox(String input, JComboBox field) {
    this.comboBoxes.put(input, field);
  }

  @Override
  public String getComboBox(String element) {
    return (String) comboBoxes.get(element).getSelectedItem();
  }

  @Override
  public void setDate(String input, JDateChooser date) {
    this.dates.put(input, date);
  }

  @Override
  public Date getDate(String element) {
    return dates.get(element).getDate();

  }


  @Override
  public void clearFields() {
    for (Map.Entry<String, JTextField> entry : this.labels.entrySet()) {
      entry.getValue().setText("");
    }
    for (Map.Entry<String, JDateChooser> entry : this.dates.entrySet()) {
      entry.getValue().setCalendar(null);
    }
  }

}