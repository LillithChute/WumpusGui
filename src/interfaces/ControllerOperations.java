package interfaces;

import controller.Arguments;
import java.io.IOException;

/** These are the functions this controller can provide.*/
public interface ControllerOperations {

  /**
   * Create a dungeon.
   *
   * @param arguments    These are the available parameters for dungeon building.
   * @throws IOException exception
   */
  void createGame(Arguments arguments) throws IOException;

  /**
   * Start a new game.
   *
   * @throws IOException exception
   */
  void startNewGame() throws IOException;

  /**
   * Adventurer movement.
   *
   * @param row the cave row
   * @param col the cave col
   * @throws IOException exception
   */
  void moveAdventurer(int row, int col) throws IOException;

  /**
   * Adventurer 1 moves using keyboard.
   *
   * @param direction    the direction
   * @throws IOException exception
   */
  void moveAdventurerOne(String direction) throws IOException;

  /**
   * Adventurer 2 moves using keyboard.
   *
   * @param direction    the direction
   * @throws IOException exception
   */
  void moveAdventurerTwo(String direction) throws IOException;

  /**
   * Restart same game with different adventurer starting points.
   *
   * @throws IOException exception
   */
  void restartGame() throws IOException;

  /**
   * Adventurer 1 shoots an arrow.
   *
   * @param shotDirection direction
   * @param distance              distance
   * @throws IOException          exception
   */
  void adventurerOneFireArrow(String shotDirection, int distance) throws IOException;

  /**
   * Adventurer 2 shoots an arrow.
   *
   * @param shotDirection direction
   * @param distance              distance
   * @throws IOException          exception
   */
  void adventurerTwoFireArrow(String shotDirection, int distance) throws IOException;

  /**
   * Start the game using the view.
   *
   * @param view          the view of the game
   * @throws IOException  exception
   */
  void beginGame(ViewForDungeon view) throws IOException;
}
