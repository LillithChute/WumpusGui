package model;

import interfaces.Cave;
import java.util.ArrayList;
import java.util.List;

/** This is an implementation of a cave in the layout.*/
public class TheCave implements Cave {

  private Cave up;
  private Cave down;
  private Cave left;
  private Cave right;
  private final int[] location;
  private int identifier = 0;
  private boolean adventurer = false;
  private boolean isCave = true;
  private boolean hasSuperbats = false;
  private boolean hasPit = false;
  private boolean hasWumpus = false;
  private String adventurerImage;
  private boolean visited = false;

  /**
   * Constructor.
   *
   * @param location the location of the cave
   */
  public TheCave(int[] location) {
    if (location[0] < 0 || location[1] < 0) {
      throw new IllegalArgumentException("You can't have a negative location.");
    }

    this.up = null;
    this.down = null;
    this.left = null;
    this.right = null;
    this.location = location;
  }

  @Override
  public Cave copy() {
    Cave newCave = new TheCave(location);
    newCave.setIdentifier(identifier);

    if (adventurer) {
      newCave.setAdventurerToThisCave();
    }

    if (!isCave) {
      newCave.changeToTunnel();
    }

    if (hasSuperbats) {
      newCave.setBats();
    }

    if (hasPit) {
      newCave.setPit();
    }

    if (hasWumpus) {
      newCave.setWumpus();
    }

    if (adventurerImage != null) {
      newCave.setAdventurerSymbol(adventurerImage);
    }

    if (visited) {
      newCave.setCaveToVisited();
    }

    return newCave;
  }

  @Override
  public boolean isCaveVisited() {
    return visited;
  }

  @Override
  public void setCaveToVisited() {
    visited = true;
  }

  @Override
  public void setAdventurerToThisCave() {
    this.adventurer = true;
  }

  @Override
  public void removeAdventurerFromCave() {
    this.adventurer = false;
  }

  @Override
  public boolean doesCaveHaveAdventurer() {
    return this.adventurer;
  }

  @Override
  public String getAdventurerSymbol() {
    return this.adventurerImage;
  }

  @Override
  public void setAdventurerSymbol(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Null symbol.");
    }
    if (symbol.length() != 1) {
      throw new IllegalArgumentException("Invalid adventurer symbol.");
    }
    this.adventurerImage = symbol;
  }

  @Override
  public void setBats() {
    this.hasSuperbats = true;
  }

  @Override
  public boolean hasBats() {
    return this.hasSuperbats;
  }

  @Override
  public void setPit() {
    this.hasPit = true;
  }

  @Override
  public boolean hasPit() {
    return this.hasPit;
  }

  @Override
  public void setWumpus() {
    this.hasWumpus = true;
  }

  @Override
  public boolean hasWumpus() {
    return this.hasWumpus;
  }

  @Override
  public void changeToTunnel() {
    this.isCave = false;
  }

  @Override
  public void removeWumpus() {
    this.hasWumpus = false;
  }

  @Override
  public boolean isCave() {
    return this.isCave;
  }

  @Override
  public int[] getLocation() {
    return this.location;
  }

  @Override
  public Cave getUp() {
    return this.up;
  }

  @Override
  public Cave getDown() {
    return this.down;
  }

  @Override
  public Cave getLeft() {
    return this.left;
  }

  @Override
  public Cave getRight() {
    return this.right;
  }

  @Override
  public int getIdentifier() {
    return this.identifier;
  }

  @Override
  public void setIdentifier(int newValue) {
    if (newValue < 0) {
      throw new IllegalArgumentException("ID can't be a negative value.");
    }

    this.identifier = newValue;
  }

  @Override
  public void setUp(Cave up) {
    if (up == null) {
      throw new IllegalArgumentException("Cave can't be null.");
    }

    this.up = up;
  }

  @Override
  public void setDown(Cave down) {
    if (down == null) {
      throw new IllegalArgumentException("Cave can't be null.");
    }

    this.down = down;
  }

  @Override
  public void setLeft(Cave left) {
    if (left == null) {
      throw new IllegalArgumentException("Cave can't be null.");
    }

    this.left = left;
  }

  @Override
  public void setRight(Cave right) {
    if (right == null) {
      throw new IllegalArgumentException("Cave can't be null.");
    }

    this.right = right;
  }

  @Override
  public List<String> getOptions() {
    ArrayList<String> result = new ArrayList<>();

    if (this.up != null) {
      result.add("up");
    }

    if (this.down != null) {
      result.add("down");
    }

    if (this.left != null) {
      result.add("left");
    }

    if (this.right != null) {
      result.add("right");
    }

    return result;
  }

  @Override
  public String toString() {
    String result = "";
    if (this.up == null) {
      result += "UUUUU\n";
    } else {
      result += "U   U\n";
    }

    if (this.left == null) {
      if (this.adventurer) {
        result += String.format("U%s%s%s",
                this.adventurerImage, this.adventurerImage, this.adventurerImage);
      } else if (this.hasWumpus && this.hasPit && this.hasSuperbats) {
        result += "UBWP";
      } else if (this.hasSuperbats && this.hasPit) {
        result += "UB P";
      } else if (this.hasWumpus && this.hasPit) {
        result += "U WP";
      } else if (this.hasWumpus && this.hasSuperbats) {
        result += "UBW ";
      } else if (this.hasSuperbats) {
        result += "U B ";
      } else if (this.hasPit) {
        result += "U P ";
      } else if (this.hasWumpus) {
        result += "U W ";
      } else {
        result += "U   ";
      }
    } else {
      if (this.adventurer) {
        result += String.format(" %s%s%s", this.adventurerImage,
                this.adventurerImage, this.adventurerImage);
      } else if (this.hasWumpus && this.hasPit && this.hasSuperbats) {
        result += " BWP";
      } else if (this.hasSuperbats && this.hasPit) {
        result += " B P";
      } else if (this.hasWumpus && this.hasPit) {
        result += "  WP";
      } else if (this.hasWumpus && this.hasSuperbats) {
        result += " BW ";
      } else if (this.hasSuperbats) {
        result += "  B ";
      } else if (this.hasPit) {
        result += "  P ";
      } else if (this.hasWumpus) {
        result += "  W ";
      } else {
        result += "    ";
      }
    }

    if (this.right == null) {
      result += "U\n";
    } else {
      result += " \n";
    }
    if (this.down == null) {
      result += "UUUUU";
    } else {
      result += "U   U";
    }

    return result;
  }
}
