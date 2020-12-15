package view.components;

import interfaces.ControllerOperations;
import javax.swing.JComponent;

/** Operations available to the primary view.*/
public interface MazeComponent {

  /**
   * Add listener.
   *
   * @param controllerOperations the controller
   */
  void addListener(ControllerOperations controllerOperations);

  /**
   * Get the primary component.
   *
   * @return the primary component
   */
  JComponent getMainComponent();

  /**
   * Set the main component as visible.
   */
  void setVisible();

  /**
   * Set the main component as invisible.
   */
  void setInvisible();
}
