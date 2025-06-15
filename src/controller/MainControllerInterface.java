package controller;

/**
 * This interface represents the controller of our model view controller (MVC) architecture. It
 * defines methods which take inputs and then calls the respective view and model components.
 */
public interface MainControllerInterface {

  /**
   * Method to begin the execution of our controller. Our application features start running after
   * this method is called.
   */
  void startController();
}
