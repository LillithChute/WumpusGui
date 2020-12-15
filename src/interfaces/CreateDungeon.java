package interfaces;

/**
 * Provides the operations necessary to produce a dungeon layout.
 */
public interface CreateDungeon {
  /**
   * Retrieves the layout of the dungeon.
   *
   * @return the layout.
   */
  Cave[][] getLayout();
}
