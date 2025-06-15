import java.io.InputStreamReader;
import java.util.Objects;

import controller.MainController;
import controller.MainControllerGUI;
import controller.MainControllerInterface;
import model.MainModel;
import model.MainModelInterface;
import view.MainView;
import view.MainViewInterface;
import view.gui.GUIMainView;
import view.gui.MainGUIViewInterface;

/**
 * This is the main class of our model view controller (MVC) architecture. It is the responsibility
 * of this class to launch our application by calling the controller.
 */
public class MVCMain {

  /**
   * The main method of our MVC architecture which initializes MVC objects and calls the controller
   * method to start execution of application.
   * Changes: Now the program consists of two views simultaneously so we have added command line
   * arguments to decide which view to execute.
   *
   * @param args are the command line arguments.
   */
  public static void main(String[] args) {
    String arg1 = "";
    if (args.length > 0) {
      arg1 = args[0];
    }
    MainModelInterface model = new MainModel();
    if (Objects.equals(arg1, "cli")) {
      MainViewInterface view = new MainView(System.out);
      MainControllerInterface controller = new MainController(new InputStreamReader(System.in)
              , view, model);
      controller.startController();
    } else {
      MainGUIViewInterface view = new GUIMainView();
      MainControllerInterface controller = new MainControllerGUI(view, model);
      controller.startController();
    }
  }
}
