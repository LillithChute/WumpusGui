package controller;

import interfaces.CreateDungeon;
import interfaces.DungeonPlay;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.PlayTheDungeon;

/** A helper class for the controller.*/
public class ControllerUtils {

  /**
   * Generate dungeons based on the number of adventurers.
   *
   * @param arguments     maze configuration arguments
   * @param createDungeon {@link CreateDungeon}
   * @param randomSeed    the random seed
   * @return              a list of {@link DungeonPlay}
   */
  public static List<DungeonPlay> generateMazeGame(
          Arguments arguments, CreateDungeon createDungeon, int randomSeed) {
    Random random = new Random(randomSeed);
    List<DungeonPlay> dungeonPlays = new ArrayList<>();

    if (!arguments.singlePlayer) {
      // player 1
      dungeonPlays.add(new PlayTheDungeon(
              createDungeon.getLayout(), false, "*", random));

      // player 2
      dungeonPlays.add(new PlayTheDungeon(
              createDungeon.getLayout(), false, "/", random
      ));
    } else {
      // player 1
      dungeonPlays.add(new PlayTheDungeon(
              createDungeon.getLayout(), true, "*", random));
    }
    return dungeonPlays;
  }

  /**
   * In multiplayer mode, when one player dies, the other game will be in single player mode.
   *
   * @param arguments configuration arguments.
   * @param dungeonPlays list of games.
   */
  public static void onePlayerDies(Arguments arguments, List<DungeonPlay> dungeonPlays) {
    // when one game ends, the other one will be single player
    if (!arguments.singlePlayer) {

      if (dungeonPlays.get(0).isFinished()) {
        dungeonPlays.get(1).setSinglePlayer(true);
      }

      if (dungeonPlays.get(1).isFinished()) {
        dungeonPlays.get(0).setSinglePlayer(true);
      }
    }
  }
}
