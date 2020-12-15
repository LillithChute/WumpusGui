import controller.Controller;
import interfaces.ControllerOperations;
import interfaces.ViewForDungeon;
import java.io.IOException;
import java.io.InputStreamReader;
import view.GuiViewForDungeon;
import view.TextViewForDungeon;

public class Driver {

  /**
   * Main method.
   *
   * @param args start arguments
   * @throws IOException exception
   */
  public static void main(String[] args) throws IOException {
    boolean isGui;
    try {
      isGui = readViewOption(args);
    } catch (Exception e) {
      System.out.println("Usage: java -jar hw6.jar <--text | --gui>\n");
      return;
    }
    ControllerOperations controller = new Controller();
    ViewForDungeon view;
    if (isGui) {
      view = new GuiViewForDungeon();
    } else {
      view = new TextViewForDungeon(new InputStreamReader(System.in), System.out);
    }
    controller.beginGame(view);
  }

  private static boolean readViewOption(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Invalid command.");
    }
    switch (args[0]) {
      case "--text":
        return false;
      case "--gui":
        return true;
      default:
        throw new IllegalArgumentException(String.format("Unknown command %s.", args[0]));
    }
  }
}
