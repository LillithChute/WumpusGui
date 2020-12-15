package interfaces;

import java.util.List;

/**
 * This provides the operations available on a cave.
 */
public interface Cave {

  /**
   * Sets the ID of the adventurer to true when in this cave.
   */
  void setAdventurerToThisCave();

  /**
   * Sets the ID of the adventurer to false when in this cave.
   */
  void removeAdventurerFromCave();

  /**
   * Checks the cave for an adventurer.
   *
   * @return whether there is an adventurer here.
   */
  boolean doesCaveHaveAdventurer();

  /**
   * Adds superbats.
   */
  void setBats();

  /**
   * Are superbats here.
   *
   * @return whether the cave has bats.
   */
  boolean hasBats();

  /**
   * Has the cave been visited.
   *
   * @return whether this cave has been visited.
   */
  boolean isCaveVisited();

  /**
   * Set the cave to visited.
   */
  void setCaveToVisited();

  /**
   * Set the adventurer image.
   *
   * @param symbol the image
   */
  void setAdventurerSymbol(String symbol);

  /**
   * Get the adventurer image, "*" or "/".
   *
   * @return the adventurer image.
   */
  String getAdventurerSymbol();

  /**
   * Add a pit to the cave.
   */
  void setPit();

  /**
   * Is there a pit in this cave.
   *
   * @return whether the cave has a pit.
   */
  boolean hasPit();

  /**
   * Add a wumpus.
   */
  void setWumpus();

  /**
   * Kill the wumpus.
   */
  void removeWumpus();

  /**
   * Is there a wumpus here or not.
   *
   * @return whether the cave has a wumpus.
   */
  boolean hasWumpus();

  /**
   * Convert to a tunnel.
   */
  void changeToTunnel();

  /**
   * Is this a cave and not a tunnel.
   *
   * @return whether the cave is a cave and not a tunnel
   */
  boolean isCave();

  /**
   * Gets the cave location.
   *
   * @return the location of this cave
   */
  int[] getLocation();

  /**
   * Gets the cave that is located up from this cave.
   * Null indicates a wall.
   *
   * @return the cave above.
   */
  Cave getUp();

  /**
   * Gets the cave that is located down from this cave.
   * Null indicates a wall.
   *
   * @return the cave below.
   */
  Cave getDown();

  /**
   * Gets the cave that is located left from this cave.
   * Null indicates a wall.
   *
   * @return the cave to the left.
   */
  Cave getLeft();

  /**
   * Gets the cave that is located right from this cave.
   * Null indicates a wall.
   *
   * @return the cave to the "right".
   */
  Cave getRight();

  /**
   * Gets the cave ID which is used to differentiate a cave.
   *
   * @return the ID of the cave.
   */
  int getIdentifier();

  /**
   * Sets this cave's ID.
   *
   * @param newValue the ID.
   */
  void setIdentifier(int newValue);

  /**
   * Sets the cave above this cave.
   *
   * @param up the cave above.
   */
  void setUp(Cave up);

  /**
   * Sets the cave below this cave.
   *
   * @param down the cave below
   */
  void setDown(Cave down);

  /**
   * Sets the cave to the left of this cave.
   *
   * @param left the cave on the left.
   */
  void setLeft(Cave left);

  /**
   * Sets the cave to the right of this cave.
   *
   * @param right the cave to the right.
   */
  void setRight(Cave right);

  /**
   * Return the movement options for the adventurer from this cave.
   *
   * @return the possible move options
   */
  List<String> getOptions();

  /**
   * Get a copy of the current cave to avoid mutating.
   *
   * @return a copy of the room
   */
  Cave copy();
}
