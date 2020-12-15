package controller;

import interfaces.Cave;
import interfaces.ControllerOperations;
import interfaces.CreateDungeon;
import interfaces.DungeonPlay;
import interfaces.ViewForDungeon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.WrappedNonPerfectMaze;

public class Controller implements ControllerOperations {
  protected ViewForDungeon view;
  protected Cave[][] map;
  protected List<DungeonPlay> dungeonPlays = new ArrayList<>();
  protected int randomSeedMap = 1;
  protected int randomSeedGame = 2;
  protected Arguments arguments;
  protected boolean player1Turn;
  protected String player1Result = "";
  protected String player2Result = "";

  @Override
  public void createGame(Arguments arguments) throws IOException {
    if (arguments == null) {
      throw new IllegalArgumentException("Null argument.");
    }
    view.resetStatus();
    // reset
    this.dungeonPlays.clear();
    this.player1Turn = true;
    this.map = null;
    player1Result = "";
    player2Result = "";

    // generate map
    this.arguments = arguments;
    CreateDungeon createDungeon = new WrappedNonPerfectMaze(
            arguments.x, arguments.y, arguments.bat, arguments.pit,
            this.randomSeedMap, arguments.wall, true
    );
    map = createDungeon.getLayout();
    // generate game
    dungeonPlays = ControllerUtils.generateMazeGame(arguments, createDungeon, randomSeedGame);

    if (arguments.singlePlayer) {
      view.firstAdventurerGameOver(false);
      view.secondAdventurerGameOver(true);
    } else {
      view.firstAdventurerGameOver(false);
      view.secondAdventurerGameOver(false);
    }
    view.initializeDungeon(dungeonPlays.get(0).copyDungeon(), this);
    updateInfo();
    view.startGame(this);
  }

  @Override
  public void adventurerOneFireArrow(String shotDirection, int distance) throws IOException {
    dungeonPlays.get(0).shootArrowTo(shotDirection, distance);
    checkWumpusKilled();
    if (dungeonPlays.get(0).isFinished()) {
      ControllerUtils.onePlayerDies(arguments, dungeonPlays);
      Cave curr = map[
              dungeonPlays.get(0).getAdventurerLocation()[0]][dungeonPlays.get(0)
              .getAdventurerLocation()[1]];
      if (!arguments.singlePlayer) {
        if (dungeonPlays.get(0).getAdventurerLocation() == dungeonPlays.get(1)
                .getAdventurerLocation()) {
          curr.setAdventurerSymbol("/");
        }
      }
    }

    // update view
    view.firstAdventurerGameOver(dungeonPlays.get(0).isFinished());

    if (!arguments.singlePlayer) {
      view.secondAdventurerGameOver(dungeonPlays.get(1).isFinished());
    }

    view.updateLayout(dungeonPlays.get(0).copyDungeon(), this);
    updateInfo();
  }

  @Override
  public void adventurerTwoFireArrow(String shootCommandDirection, int distance)
          throws IOException {
    dungeonPlays.get(1).shootArrowTo(shootCommandDirection, distance);
    checkWumpusKilled();
    if (dungeonPlays.get(1).isFinished()) {
      ControllerUtils.onePlayerDies(arguments, dungeonPlays);
      Cave curr = map[
              dungeonPlays.get(1).getAdventurerLocation()[0]][dungeonPlays.get(1)
              .getAdventurerLocation()[1]];
      if (dungeonPlays.get(0).getAdventurerLocation() == dungeonPlays.get(1)
              .getAdventurerLocation()) {
        curr.setAdventurerSymbol("*");
      }
    }

    // update view
    view.firstAdventurerGameOver(dungeonPlays.get(0).isFinished());

    if (!arguments.singlePlayer) {
      view.secondAdventurerGameOver(dungeonPlays.get(1).isFinished());
    }

    view.updateLayout(dungeonPlays.get(0).copyDungeon(), this);
    updateInfo();
  }

  private void checkWumpusKilled() {
    boolean killed = true;
    for (Cave[] caves : map) {
      for (Cave r : caves) {
        if (r.hasWumpus()) {
          killed = false;
        }
      }
    }

    if (killed) {
      for (DungeonPlay m : dungeonPlays) {
        m.setGameOver();
      }
    }
  }

  @Override
  public void restartGame() throws IOException {
    // same map, new player location
    randomSeedGame++;

    // regenerate
    createGame(arguments);
    view.resetFocus();
  }

  @Override
  public void moveAdventurerOne(String direction) throws IOException {
    dungeonPlays.get(0).moveAdventurer(direction);
    view.firstAdventurerGameOver(dungeonPlays.get(0).isFinished());
    view.updateLayout(dungeonPlays.get(0).copyDungeon(), this);
    view.resetFocus();
    if (!arguments.singlePlayer) {
      player1Turn = false;
      ControllerUtils.onePlayerDies(arguments, dungeonPlays);
    }
    updateInfo();
  }

  @Override
  public void moveAdventurerTwo(String direction) throws IOException {
    if (arguments.singlePlayer) {
      throw new IllegalArgumentException("\t- Player2 does not exist.");
    }
    dungeonPlays.get(1).moveAdventurer(direction);
    view.secondAdventurerGameOver(dungeonPlays.get(1).isFinished());
    view.updateLayout(dungeonPlays.get(0).copyDungeon(), this);
    view.resetFocus();
    player1Turn = true;
    ControllerUtils.onePlayerDies(arguments, dungeonPlays);
    updateInfo();
  }

  @Override
  public void moveAdventurer(int row, int col) throws IOException {
    view.resetFocus();
    Cave curr = map[row][col];
    boolean twoPlayerAlive = true;
    for (DungeonPlay m : dungeonPlays) {
      twoPlayerAlive = twoPlayerAlive && !m.isFinished();
    }
    String direction;
    if (twoPlayerAlive) {
      direction = findDirectionTwoPlayerAlive(curr);
      if (direction.length() == 0) {
        return;
      }
      if (player1Turn) {
        dungeonPlays.get(0).moveAdventurer(direction);
        view.firstAdventurerGameOver(dungeonPlays.get(0).isFinished());
      } else {
        dungeonPlays.get(1).moveAdventurer(direction);
        view.secondAdventurerGameOver(dungeonPlays.get(1).isFinished());
      }

      // don't switch turn if in single player mode
      player1Turn = arguments.singlePlayer == player1Turn;
    } else {
      direction = findDirectionOnePlayerAlive(curr);

      if (!dungeonPlays.get(0).isFinished()) {
        dungeonPlays.get(0).moveAdventurer(direction);
        view.firstAdventurerGameOver(dungeonPlays.get(0).isFinished());
      }

      if (dungeonPlays.size() > 1 && !dungeonPlays.get(1).isFinished()) {
        dungeonPlays.get(1).moveAdventurer(direction);
        view.secondAdventurerGameOver(dungeonPlays.get(1).isFinished());
      }
    }

    ControllerUtils.onePlayerDies(arguments, dungeonPlays);
    view.updateLayout(dungeonPlays.get(0).copyDungeon(), this);
    updateInfo();
  }

  protected void updateInfo() throws IOException {
    Map<String, String> message = new HashMap<>();
    Map<String, String> hints = new HashMap<>();
    // mouse player
    if (player1Turn && !dungeonPlays.get(0).isFinished()) {
      view.setAdventurerTurn(true);
    } else {
      if (dungeonPlays.size() > 1 && !dungeonPlays.get(1).isFinished()) {
        view.setAdventurerTurn(false);
      }
    }
    // message
    if (!arguments.singlePlayer) {
      String m = dungeonPlays.get(1).getMessage();
      message.put("player2", m);
      hints.put("player2", dungeonPlays.get(1).getHints());
      if (m.length() != 0) {
        player2Result = m.split("\n")[m.split("\n").length - 1];
      }
    }
    String m = dungeonPlays.get(0).getMessage();
    message.put("player1", m);
    // hints
    hints.put("player1", dungeonPlays.get(0).getHints());
    if (m.length() != 0) {
      player1Result = m.split("\n")[m.split("\n").length - 1];
    }
    view.updateInfo(message, hints);
    // check if game is over
    if (!arguments.singlePlayer) {
      if (dungeonPlays.get(0).isFinished() && dungeonPlays.get(1).isFinished()) {
        view.gameOver(player1Result, player2Result, this);
      }
    } else {
      if (dungeonPlays.get(0).isFinished()) {
        view.gameOver(player1Result, player2Result, this);
      }
    }
  }

  private String findDirectionOnePlayerAlive(Cave curr) {
    if (curr.getUp() != null) {
      if (curr.getUp().doesCaveHaveAdventurer()) {
        return "down";
      }
    }
    if (curr.getDown() != null) {
      if (curr.getDown().doesCaveHaveAdventurer()) {
        return "up";
      }
    }
    if (curr.getLeft() != null) {
      if (curr.getLeft().doesCaveHaveAdventurer()) {
        return "right";
      }
    }
    if (curr.getRight() != null) {
      if (curr.getRight().doesCaveHaveAdventurer()) {
        return "left";
      }
    }
    return "";
  }

  private String findDirectionTwoPlayerAlive(Cave curr) {
    String symbol;
    if (player1Turn) {
      symbol = "*";
    } else {
      symbol = "/";
    }
    // when 2 players are in the same location
    if (!arguments.singlePlayer
            && Arrays.equals(
            dungeonPlays.get(0).getAdventurerLocation(), dungeonPlays.get(1)
                    .getAdventurerLocation())) {
      if (player1Turn) {
        symbol = "/";
      } else {
        symbol = "*";
      }
    }
    if (curr.getUp() != null) {
      if (curr.getUp().doesCaveHaveAdventurer() && curr.getUp().getAdventurerSymbol()
              .equals(symbol)) {
        return "down";
      }
    }
    if (curr.getDown() != null) {
      if (curr.getDown().doesCaveHaveAdventurer() && curr.getDown().getAdventurerSymbol()
              .equals(symbol)) {
        return "up";
      }
    }
    if (curr.getLeft() != null) {
      if (curr.getLeft().doesCaveHaveAdventurer() && curr.getLeft().getAdventurerSymbol()
              .equals(symbol)) {
        return "right";
      }
    }
    if (curr.getRight() != null) {
      if (curr.getRight().doesCaveHaveAdventurer() && curr.getRight().getAdventurerSymbol()
              .equals(symbol)) {
        return "left";
      }
    }
    throw new IllegalArgumentException("- You cannot move to there.");
  }

  @Override
  public void startNewGame() throws IOException {
    // new map and player locations
    randomSeedGame++;
    randomSeedMap++;
    view.configureOptions();
    view.resetFocus();
  }

  @Override
  public void beginGame(ViewForDungeon view) throws IOException {
    this.view = view;
    this.view.addTheController(this);
    this.player1Turn = true;
    this.view.configureOptions();
  }
}
