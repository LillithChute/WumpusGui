package model;

/**
 * This is the adventurer in the dungeon.
 */
public class Adventurer {

  private int arrowCount;

  /**
   * Construct adventurer and set the number of arrows to 3.
   */
  public Adventurer() {
    this.arrowCount = 3;
  }

  /**
   * After firing an arrow, decrease the number.
   */
  public void shootArrow() {
    if (this.arrowCount <= 0) {
      throw new IllegalStateException("You have no arrows.");
    }

    this.arrowCount--;
  }

  /**
   * Get the current arrow count.
   *
   * @return the arrow count
   */
  public int getArrowCount() {
    return this.arrowCount;
  }
}
