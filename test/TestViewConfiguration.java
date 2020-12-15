import static org.junit.Assert.assertEquals;

import controller.Arguments;
import org.junit.Test;

public class TestViewConfiguration {

  @Test
  public void stringConstruct() {
    Arguments test = new Arguments("4", "5", "6", "0.5", "0.6", true);
    assertEquals("x = 4, y = 5, walls = 6, bat = 0.5, pit = 0.6, singlePlayer = true.",
            test.toString());
  }

  @Test
  public void numericConstruct() {
    Arguments test = new Arguments(4, 5, 6, 0.5, 0.6, true);
    assertEquals("x = 4, y = 5, walls = 6, bat = 0.5, pit = 0.6, singlePlayer = true.",
            test.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullX() {
    Arguments test = new Arguments(null, "5", "6", "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullY() {
    Arguments test = new Arguments("4", null, "6", "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullWall() {
    Arguments test = new Arguments("4", "5", null, "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullBat() {
    Arguments test = new Arguments("4", "5", "6", null, "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullPit() {
    Arguments test = new Arguments("4", "5", "6", "0.5", null, true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void zeroLengthX() {
    Arguments test = new Arguments("", "5", "6", "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void zeroLengthY() {
    Arguments test = new Arguments("4", "", "6", "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void zeroLengthWall() {
    Arguments test = new Arguments("4", "5", "", "0.5", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void zeroLengthBat() {
    Arguments test = new Arguments("4", "5", "6", "", "0.6", true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void zeroLengthPit() {
    Arguments test = new Arguments("4", "5", "6", "0.5", "", true);
  }
}
