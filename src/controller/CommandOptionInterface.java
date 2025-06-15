package controller;

/**
 * This interface represents the command line features executed by the controller. The
 * implementations of this interface will be options or functionalities that our application
 * provides to the user.
 * Changes: ALl the implementations of this interface were taking inputs using the readable object
 * which made the reuse of code difficult for a new view implementation. Thus, all these classes
 * have been decoupled from the readable object so any type of controller can reuse them.
 */
public interface CommandOptionInterface {

  /**
   * This method holds the logic that our application performs depending on the implementation of
   * the interface.
   */
  void execute();
}
