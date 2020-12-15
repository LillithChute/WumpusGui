package model;

import interfaces.Cave;
import java.util.Random;

/**  concrete implementation of a normal dungeon layout.*/
public class PlayTheDungeon extends AbstractDungeonPlay {
  /**
   * Constructs a normal dungeon.
   *
   * @param map the layout
   */
  public PlayTheDungeon(Cave[][] map, boolean singlePlayer, String symbol, Random random) {
    super(map, singlePlayer, symbol, random);
  }
}
