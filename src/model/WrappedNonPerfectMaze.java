package model;

import interfaces.Cave;

/**
 * This will create the wrapping non-perfect dungeon.
 */
public class WrappedNonPerfectMaze extends AbstractDungeonCreator {

  private final int numberOfRemainWalls;
  private final boolean wrapRow;

  /**
   * Constructor.
   *
   * @param x                   the number of rows
   * @param y                   the number of columns
   * @param numberOfRemainWalls the number of walls remain
   */
  public WrappedNonPerfectMaze(int x, int y, double bats, double pits, int seed,
                               int numberOfRemainWalls, boolean wrapRow) {
    super(x, y, bats, pits, seed);

    if (numberOfRemainWalls < 0) {
      throw new IllegalArgumentException("Invalid number of remaining walls.\n");
    }

    this.wrapRow = wrapRow;
    this.numberOfRemainWalls = numberOfRemainWalls;
    this.generateMaze();
  }

  private void generateMaze() {
    int n = super.getNumberOfRows();
    int x = super.getNumberOfColumns();
    super.generateNonPerfect(this.numberOfRemainWalls);

    // wrap column
    if (!this.wrapRow) {
      for (int j = 0; j < x; j++) {
        Cave up = this.getLayout()[0][j];
        Cave down = this.getLayout()[n - 1][j];
        up.setUp(down);
        down.setDown(up);
      }
    } else {
      // wrap row
      for (int i = 0; i < n; i++) {
        Cave left = this.getLayout()[i][0];
        Cave right = this.getLayout()[i][x - 1];
        left.setLeft(right);
        right.setRight(left);
      }
    }

    super.addSpecialRooms();
  }
}
