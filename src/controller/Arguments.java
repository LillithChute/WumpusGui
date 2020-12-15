package controller;

/** This class is used to retain the parameters for creating a new dungeon.*/
public class Arguments {

  protected final int x;
  protected final int y;
  protected final int wall;
  protected final double bat;
  protected final double pit;
  protected final boolean singlePlayer;

  /**
   * Construct an {@link Arguments} instance.
   *
   * @param x    the number of rows
   * @param y    the number of columns
   * @param wall the number of walls
   * @param bat  the proportion of bat rooms
   * @param pit  the proportion of pit rooms
   * @param singlePlayer whether it's single player
   */
  public Arguments(
          int x, int y, int wall, double bat, double pit, boolean singlePlayer) {
    this.x = x;
    this.y = y;
    this.wall = wall;
    this.bat = bat;
    this.pit = pit;
    this.singlePlayer = singlePlayer;
  }

  /**
   * Construct an {@link Arguments} instance.
   *
   * @param x    the number of rows
   * @param y    the number of columns
   * @param wall the number of walls
   * @param bat  the proportion of bat rooms
   * @param pit  the proportion of pit rooms
   * @param singlePlayer whether it's single player
   */
  public Arguments(
          String x, String y, String wall, String bat, String pit, boolean singlePlayer) {
    // check null
    if (x == null || y == null || wall == null || bat == null || pit == null
            || x.length() == 0 || y.length() == 0 || wall.length() == 0 || bat.length() == 0
            || pit.length() == 0) {
      throw new IllegalArgumentException(
              "Null or empty x, y, number of walls, bat, or pit proportion.");
    }

    int intX;
    int intY;
    int intWall;
    double doubleBat;
    double doublePit;

    try {
      intX = Integer.parseInt(x);
      intY = Integer.parseInt(y);
      intWall = Integer.parseInt(wall);
      doubleBat = Double.parseDouble(bat);
      doublePit = Double.parseDouble(pit);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid number.");
    }

    this.x = intX;
    this.y = intY;
    this.wall = intWall;
    this.bat = doubleBat;
    this.pit = doublePit;
    this.singlePlayer = singlePlayer;
  }

  @Override
  public String toString() {
    return String.format("x = %d, y = %d, walls = %d, bat = %s, pit = %s, singlePlayer = %s.",
            x, y, wall, bat, pit, singlePlayer);
  }
}
