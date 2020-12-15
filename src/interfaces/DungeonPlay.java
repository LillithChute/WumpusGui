package interfaces;

import java.util.List;

/**
 * This provides the operations for the dungeon layout and the play of the game.
 */
public interface DungeonPlay {

  /**
   * Gets the current location of the adventurer.
   *
   * @return the current location of the adventurer
   */
  int[] getAdventurerLocation();

  /**
   * Gets possible movement for the adventurer.
   *
   * @return the possible move options
   */
  List<String> getMovementOptions();

  /**
   * Move the adventurer.
   *
   * @param option the movement direction
   */
  void moveAdventurer(String option);

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over.
   */
  boolean isFinished();

  /**
   * The adventurer shoots an arrow to the specified direction with the specified distance.
   *
   * @param option   the shot direction
   * @param distance the number of rooms the arrow travels through
   */
  void shootArrowTo(String option, int distance);

  /**
   * Get the message generated after moving or shooting an arrow.
   *
   * @return the message
   */
  String getMessage();

  /**
   * Sets whether the game is over or not.
   */
  void setGameOver();

  /**
   * Get information about adjacent rooms, bats, pits or wumpus.
   *
   * @return the hints
   */
  String getHints();

  /**
   * Refresh the adventurer location.
   */
  void locationRefresh();

  /**
   * Determine whether it's a single player game.
   *
   * @param flag yes or no
   */
  void setSinglePlayer(boolean flag);

  /**
   * Copy a dungeon, which can be used in the view.
   *
   * @return a copy of the current dungeon.
   */
  Cave[][] copyDungeon();
}
