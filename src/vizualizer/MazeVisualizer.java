package vizualizer;

import interfaces.Cave;

public class MazeVisualizer {

  /**
   * Visualizes a maze map in text mode.
   *
   * @param map the maze map
   * @return the maze visualization as a string
   */
  public static String visualizer(Cave[][] map) {
    if (map == null || map.length == 0 || map[0].length == 0) {
      throw new IllegalArgumentException("Null or invalid map dimension.");
    }
    int x = map[0].length;

    StringBuilder result1 = new StringBuilder();
    StringBuilder result2 = new StringBuilder();
    StringBuilder result3 = new StringBuilder();
    StringBuilder result = new StringBuilder();

    for (Cave[] caves : map) {
      for (int j = 0; j < x; j++) {
        Cave curr = caves[j];
        if (curr.isCaveVisited()) {
          String[] temp = curr.toString().split("\n");
          result1.append(temp[0]);
          result2.append(temp[1]);
          result3.append(temp[2]);
        } else {
          String blank = "     ";
          result1.append(blank);
          result2.append(blank);
          result3.append(blank);
        }
      }
      result.append(result1);
      result.append("\n");
      result.append(result2);
      result.append("\n");
      result.append(result3);
      result.append("\n");
      result1 = new StringBuilder();
      result2 = new StringBuilder();
      result3 = new StringBuilder();
    }
    return result.toString();
  }
}
