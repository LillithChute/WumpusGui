package view.components;

import interfaces.Cave;
import interfaces.ControllerOperations;
import java.util.Map;

/**
 * The GUI Interface.
 */

public interface Game extends MazeComponent {

  /**
   * Load the layout for the first time.
   *
   * @param map      layout
   * @param controllerOperations controller
   */
  void initializeMaze(Cave[][] map, ControllerOperations controllerOperations);

  /**
   * Update layout.
   *
   * @param map       layout
   * @param controllerOperations  controller
   */
  void updateMaze(Cave[][] map, ControllerOperations controllerOperations);

  /**
   * Load the messaging.
   *
   * @param message message
   * @param hints   hints
   */
  void updateInfo(Map<String, String> message, Map<String, String> hints);

  /**
   * Load errors.
   *
   * @param message error message
   * @param isP1    whether is for Player 1
   */
  void setErrorMessage(String message, boolean isP1);

  /**
   * Clear errors.
   *
   * @param isP1 whether is for Player 1
   */
  void clearErrorMessage(boolean isP1);

  /**
   * Set the current player.
   *
   * @param player1Turn whether it's player 1' turn
   */
  void setPlayerTurn(boolean player1Turn);
}
