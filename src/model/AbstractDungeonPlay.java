package model;

import interfaces.Cave;
import interfaces.DungeonPlay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import vizualizer.MazeVisualizer;

/** This abstract class will provide the concrete implementation of the dungeon crawl.*/
public abstract class AbstractDungeonPlay implements DungeonPlay {

  private final Adventurer adventurer;
  private int[] adventurerLocation;
  private final Cave[][] layout;
  private boolean finished = false;
  private final Random random;
  private final StringBuilder message = new StringBuilder();
  private final String adventurerSymbol;
  private boolean singlePlayer;

  /**
   * Constructs an abstract dungeon.
   *
   * @param layout   the dungeon layout
   */
  public AbstractDungeonPlay(Cave[][] layout, boolean singlePlayer, String adventurerSymbol,
                             Random random) {
    if (layout == null || layout.length == 0) {
      throw new IllegalArgumentException("Null or empty map.");
    }
    this.adventurer = new Adventurer();
    this.adventurerSymbol = adventurerSymbol;
    this.random = random;
    this.singlePlayer = singlePlayer;
    this.adventurerLocation = startAdventurerLocation(layout);
    this.layout = layout;
    this.layout[this.adventurerLocation[0]][this.adventurerLocation[1]].setAdventurerToThisCave();
    this.updateStatus();
  }

  private int[] startAdventurerLocation(Cave[][] map) {
    int n = map.length;
    int x = map[0].length;
    List<int[]> possibleLocations = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < x; j++) {
        Cave curr = map[i][j];

        if (!curr.hasWumpus() && !curr.hasPit() && !curr.hasBats()
                && curr.isCave() && !curr.doesCaveHaveAdventurer()) {
          possibleLocations.add(new int[]{i, j});
        }
      }
    }

    if (possibleLocations.size() == 0) {
      throw new IllegalArgumentException("No possible starting point.");
    }

    int index = random.nextInt(possibleLocations.size());

    return possibleLocations.get(index);
  }

  @Override
  public int[] getAdventurerLocation() {
    return this.adventurerLocation;
  }

  @Override
  public List<String> getMovementOptions() {
    int[] location = this.getAdventurerLocation();
    Cave curr = this.layout[location[0]][location[1]];
    return curr.getOptions();
  }

  @Override
  public void shootArrowTo(String option, int distance) {
    if (this.isFinished()) {
      throw new IllegalStateException("Game is already over.");
    }

    if (option == null) {
      throw new IllegalArgumentException("Null shooting option");
    }

    if (!option.equals("up") && !option.equals("down")
            && !option.equals("left") && !option.equals("right")) {
      throw new IllegalArgumentException("Invalid shooting options.");
    }

    if (distance <= 0) {
      throw new IllegalArgumentException("Non-positive distance.");
    }

    try {
      this.adventurer.shootArrow();
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }

    Cave curr = this.layout[this.adventurerLocation[0]][this.adventurerLocation[1]];

    while (distance > 0) {
      try {
        RoomWithOption temp = this.arrowToNextRoom(curr, option);
        curr = temp.cave;
        option = temp.option;
        distance--;
      } catch (Exception e) {
        this.message.append(e.getMessage());
        // hits the wall
        if (this.adventurer.getArrowCount() == 0) {
          this.message.append("- No arrows are left.\n");
          // remove the current player
          int[] location = this.getAdventurerLocation();
          Cave currCave = this.layout[location[0]][location[1]];
          removeThisAdventurer(currCave);
          this.finished = true;
        } else {
          this.message.append(
                  String.format("- You have %d arrows left.\n", this.adventurer.getArrowCount()));
        }

        return;
      }
    }

    this.message.append(String.format("- You have %d arrows left.\n",
            this.adventurer.getArrowCount()));

    // travel through the distance.
    if (curr.hasWumpus()) {
      curr.removeWumpus();
      this.message.append("- Great! You killed the wumpus.\n");
      this.finished = true;
    } else {
      // missed the wumpus
      this.message.append("- Your arrow missed the wumpus.\n");
      if (this.adventurer.getArrowCount() == 0) {
        this.message.append("- You don't have any arrows left.\n");
        // remove the current player
        int[] location = this.getAdventurerLocation();
        Cave currCave = this.layout[location[0]][location[1]];
        removeThisAdventurer(currCave);
        this.finished = true;
      }
    }
  }

  @Override
  public void setGameOver() {
    this.finished = true;
  }

  /*
    a temporary class to store the next room and the arrow flying direction
     */
  private static class RoomWithOption {
    private final Cave cave;
    private final String option;

    protected RoomWithOption(Cave cave, String option) {
      this.cave = cave;
      this.option = option;
    }
  }

  /*
  arrow flies to the next room, return the next room and the arrow flying direction
   */
  private RoomWithOption arrowToNextRoom(Cave curr, String option) {
    List<String> possibleOptions = curr.getOptions();
    boolean valid = false;
    for (String p : possibleOptions) {
      if (p.equals(option)) {
        valid = true;
        break;
      }
    }

    if (!valid) {
      throw new IllegalStateException("- Your arrow hits the wall.\n");
    }

    Cave next = getNextRoom(curr, option);
    String nextOption = option;

    // travel through the tunnel
    while (!next.isCave()) {
      // in the tunnel, find the next room
      List<String> nextOptions = next.getOptions();
      String prev;
      switch (nextOption) {
        case "up":
          prev = "down";
          break;
        case "down":
          prev = "up";
          break;
        case "left":
          prev = "right";
          break;
        default:
          prev = "left";
      }

      for (String n : nextOptions) {
        if (!n.equals(prev)) {
          nextOption = n;
        }
      }

      // get the next room, maybe still in the tunnel
      next = getNextRoom(next, nextOption);
    }

    return new RoomWithOption(next, nextOption);
  }

  /*
  get the next room in the specified direction
   */
  private Cave getNextRoom(Cave curr, String option) {
    switch (option) {
      case "up":
        return curr.getUp();
      case "down":
        return curr.getDown();
      case "left":
        return curr.getLeft();
      default:
        return curr.getRight();
    }
  }

  @Override
  public void moveAdventurer(String option) {
    if (this.isFinished()) {
      throw new IllegalStateException("- Game is already over.\n");
    }
    List<String> options = this.getMovementOptions();
    boolean valid = false;

    for (String o : options) {
      if (o.equals(option)) {
        valid = true;
        break;
      }
    }

    if (!valid) {
      throw new IllegalArgumentException(String.format("- You cannot move %s.\n", option));
    }

    // option is valid
    int[] location = this.getAdventurerLocation();
    Cave currCave = this.layout[location[0]][location[1]];
    Cave newCave;
    switch (option) {
      case "up":
        newCave = currCave.getUp();
        break;
      case "down":
        newCave = currCave.getDown();
        break;
      case "left":
        newCave = currCave.getLeft();
        break;
      default:
        newCave = currCave.getRight();
    }

    // remove current player
    removeThisAdventurer(currCave);
    newCave.setAdventurerToThisCave();
    // update player symbol
    newCave.setAdventurerSymbol(this.adventurerSymbol);
    // newRoom.setTouched();
    this.adventurerLocation = newCave.getLocation();
    this.updateStatus();
  }

  private void removeThisAdventurer(Cave currCave) {
    if (twoAdventurersInSameCave()) {
      if (currCave.getAdventurerSymbol().equals(this.adventurerSymbol)) {
        currCave.setAdventurerSymbol(currCave.getAdventurerSymbol().equals("*") ? "/" : "*");
      }
    } else {
      currCave.removeAdventurerFromCave();
    }
  }

  private boolean twoAdventurersInSameCave() {
    if (this.singlePlayer) {
      return false;
    } else {
      int count = 0;
      for (Cave[] caves : layout) {
        for (Cave r : caves) {
          if (r.doesCaveHaveAdventurer()) {
            count++;
          }
        }
      }
      return count == 1;
    }
  }

  /*
  update the game status based on the player's current position
   */
  private void updateStatus() {
    Cave curr = this.layout[this.adventurerLocation[0]][this.adventurerLocation[1]];
    // update the player symbol
    curr.setAdventurerSymbol(this.adventurerSymbol);
    // mark as travelled
    curr.setCaveToVisited();
    // in tunnel
    if (!curr.isCave()) {
      return;
    }
    // in a room
    int[] newLocation;
    if (curr.hasBats()) {
      newLocation = this.encounterSuperbats();

      // meet bats, and is carried to a new location
      if (!Arrays.equals(newLocation, this.adventurerLocation)) {
        this.message.append(
                String.format("- Super bats carried you to [%d, %d].\n",
                        newLocation[0], newLocation[1]));
        this.adventurerLocation = newLocation;
        if (twoAdventurersInSameCave()) {
          if (curr.getAdventurerSymbol().equals(this.adventurerSymbol)) {
            curr.setAdventurerSymbol(curr.getAdventurerSymbol().equals("*") ? "/" : "*");
          }
        } else {
          curr.removeAdventurerFromCave();
        }
        this.layout[newLocation[0]][newLocation[1]].setAdventurerToThisCave();
        this.updateStatus();
        return;
      }

      // if not, continue
      this.message.append("- Super bats missed you.\n");
    }

    if (curr.hasPit()) {
      this.finished = true;
      this.message.append("- You fall into the bottomless pit.\n");
      curr.removeAdventurerFromCave();
      return;
    }

    if (curr.hasWumpus()) {
      this.finished = true;
      curr.removeAdventurerFromCave();
      this.message.append("- You are swallowed by the wumpus.\n");
    }
  }

  @Override
  public String getHints() {
    if (isFinished()) {
      return "- Game over.\n";
    }

    Cave curr = this.layout[this.adventurerLocation[0]][this.adventurerLocation[1]];
    Cave adjacent;
    List<String> options = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
    boolean bat = false;
    boolean pit = false;
    boolean wumpus = false;

    for (String o : options) {
      try {
        adjacent = arrowToNextRoom(curr, o).cave;
      } catch (Exception e) {
        continue;
      }

      if (adjacent.hasWumpus()) {
        wumpus = true;
      }
      if (adjacent.hasPit()) {
        pit = true;
      }
      if (adjacent.hasBats()) {
        bat = true;
      }
    }

    // get hints
    StringBuilder hints = new StringBuilder();

    // add possible directions
    List<String> possibleOptions = getMovementOptions();
    hints.append("- You can move to [");

    for (String o : possibleOptions) {
      hints.append(o).append(", ");
    }

    hints.delete(hints.length() - 2, hints.length());
    hints.append("].\n");

    if (bat) {
      hints.append("- You hear flapping nearby.\n");
    }

    if (pit) {
      hints.append("- You feel a breeze nearby.\n");
    }

    if (wumpus) {
      hints.append("- You smell a wumpus nearby.\n");
    }

    return hints.toString();
  }

  /*
  when entering a room with bats, 50% the player will be carried away
   */
  private int[] encounterSuperbats() {
    // 1 means get carried to a new location
    // 2 means stay in the same position
    if (random.nextInt(2) == 1) {
      List<int[]> allLocations = new ArrayList<>();
      for (int i = 0; i < this.layout.length; i++) {
        for (int j = 0; j < this.layout[0].length; j++) {
          allLocations.add(new int[]{i, j});
        }
      }
      return allLocations.get(random.nextInt(allLocations.size()));
    }
    return this.adventurerLocation;
  }

  @Override
  public void locationRefresh() {
    Cave curr = this.layout[this.adventurerLocation[0]][this.adventurerLocation[1]];
    curr.setAdventurerToThisCave();
    curr.setAdventurerSymbol(this.adventurerSymbol);
  }

  @Override
  public String toString() {
    this.locationRefresh();
    return MazeVisualizer.visualizer(layout);
  }

  @Override
  public boolean isFinished() {
    return this.finished;
  }

  @Override
  public String getMessage() {
    String result = this.message.toString();
    this.message.setLength(0);
    return result;
  }

  @Override
  public void setSinglePlayer(boolean flag) {
    this.singlePlayer = flag;
  }

  @Override
  public Cave[][] copyDungeon() {
    int x = layout.length;
    int y = layout[0].length;
    Cave[][] newMaze = new Cave[x][y];
    // get a copy of each room
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        Cave originalCave = layout[i][j];
        newMaze[i][j] = originalCave.copy();
      }
    }
    // connect each room
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        Cave originalCave = layout[i][j];
        Cave newCave = newMaze[i][j];
        if (originalCave.getUp() != null) {
          int[] location = originalCave.getUp().getLocation();
          newCave.setUp(newMaze[location[0]][location[1]]);
        }
        if (originalCave.getDown() != null) {
          int[] location = originalCave.getDown().getLocation();
          newCave.setDown(newMaze[location[0]][location[1]]);
        }
        if (originalCave.getLeft() != null) {
          int[] location = originalCave.getLeft().getLocation();
          newCave.setLeft(newMaze[location[0]][location[1]]);
        }
        if (originalCave.getRight() != null) {
          int[] location = originalCave.getRight().getLocation();
          newCave.setRight(newMaze[location[0]][location[1]]);
        }
      }
    }

    return newMaze;
  }
}
