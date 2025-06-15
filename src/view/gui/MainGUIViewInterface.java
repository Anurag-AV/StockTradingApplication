package view.gui;

import java.awt.event.ActionListener;

import view.AbstractView;

/**
 * This interface represents the view component in the model-view-controller (MVC) architecture. It
 * defines methods for displaying output operations to GUI. Implementations of this
 * interface are responsible for presenting information to the user in the Graphical User Interface.
 */
public interface MainGUIViewInterface extends AbstractView {

  /**
   * sets the action listener so that the controller can listen to view actions.
   *
   * @param listener controller Action listener object.
   */
  void setActionListener(ActionListener listener);


  /**
   * Used by controller to retrieve the inputs for the necessary action in the UI.
   *
   * @param inputType the action type for which the input is retrieved
   * @return
   */
  String getInput(String inputType);


  /**
   * Used by controller to call a certain action of the view Listener if required.
   *
   * @param action action string available in the view.
   */
  void takeAction(String action);

}

