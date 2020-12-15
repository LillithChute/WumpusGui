package interfaces;

import java.io.IOException;
import java.util.Map;

/** Operations available to the view.*/
public interface ViewForDungeon {
  /**
   * This represents the controller.
   *
   * @param controllerOperations the controller
   */
  void addTheController(ControllerOperations controllerOperations);

  /**
   * Configuration.
   *
   * @throws IOException exception
   */
  void configureOptions() throws IOException;

  /**
   * Begin the game.
   *
   * @param controllerOperations the controller
   * @throws IOException exception
   */
  void startGame(ControllerOperations controllerOperations) throws IOException;

  /**
   * Initialize the dungeon.
   *
   * @param layout      the dungeon layout.
   * @param controllerOperations the controller
   * @throws IOException exception
   */
  void initializeDungeon(Cave[][] layout, ControllerOperations controllerOperations)
          throws IOException;

  /**
   * Update.
   *
   * @param layout      the layout
   * @param controllerOperations the controller
   * @throws IOException exception
   */
  void updateLayout(Cave[][] layout, ControllerOperations controllerOperations)
          throws IOException;

  /**
   * Reset focus.
   */
  void resetFocus();

  /**
   * Update the messaging.
   *
   * @param message  the message
   * @param hints    the hints
   * @throws IOException exception
   */
  void updateInfo(Map<String, String> message, Map<String, String> hints) throws IOException;

  /**
   * Set adventurer 1's status.
   *
   * @param finished whether it's over
   */
  void firstAdventurerGameOver(boolean finished);

  /**
   * Set adventurer 2's status.
   *
   * @param finished whether it's over
   */
  void secondAdventurerGameOver(boolean finished);

  /**
   * The game is over.
   *
   * @param player1Result the result of player 1
   * @param player2Result the result of player 2
   * @param controllerOperations the controller
   */
  void gameOver(String player1Result, String player2Result,
                ControllerOperations controllerOperations);

  /**
   * Reset game status.
   */
  void resetStatus();

  /**
   * Set the current adventurer in control.
   *
   * @param firstAdventurerTurn whether it's player 1's turn
   */
  void setAdventurerTurn(boolean firstAdventurerTurn);
}
