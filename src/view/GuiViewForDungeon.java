package view;

import interfaces.Cave;
import interfaces.ControllerOperations;
import interfaces.ViewForDungeon;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.components.Game;
import view.components.GameOver;
import view.components.GameView;
import view.components.MazeComponent;
import view.components.Menu;

/**
 * A GUI implementation for the wumpus game.
 */
public class GuiViewForDungeon extends JFrame implements ViewForDungeon {

  private final MazeComponent menu = new Menu();
  private final Game game = new GameView();
  private MazeComponent gameOver = new GameOver("", "");
  private boolean moving = true;
  private boolean shootFlag = false;
  private String shotDirection = "";
  private boolean adventurerOneShot = true;
  private boolean adventurerOneFinish = false;
  private boolean adventurerTwoFinish = false;

  /**
   * Construct a GUI view.
   */
  public GuiViewForDungeon() {
    super();

    add(menu.getMainComponent());
    add(game.getMainComponent());

    configureOptions();
    setPreferredSize(new Dimension(1600, 900));
    setVisible(true);
    setLayout(new CardLayout());
    this.pack();
  }

  @Override
  public void addTheController(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    menu.addListener(controllerOperations);
    game.addListener(controllerOperations);
    gameOver.addListener(controllerOperations);
  }

  @Override
  public void configureOptions() {
    resetStatus();
    gameOver.setInvisible();
    game.setInvisible();
    menu.setVisible();
  }

  @Override
  public void startGame(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    gameOver.setInvisible();
    menu.setInvisible();
    game.setVisible();
  }

  @Override
  public void gameOver(String player1Result, String player2Result,
                       ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    gameOver = new GameOver(player1Result, player2Result);
    gameOver.addListener(controllerOperations);
    add(gameOver.getMainComponent());
    menu.setInvisible();
    game.setInvisible();
    gameOver.setVisible();
  }

  @Override
  public void initializeDungeon(Cave[][] layout, ControllerOperations controllerOperations) {
    if (layout == null || layout.length == 0 || layout[0].length == 0) {
      throw new IllegalArgumentException("Null or invalid map dimension.");
    }
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    game.initializeMaze(layout, controllerOperations);
    if (this.getKeyListeners().length != 0) {
      for (KeyListener k : this.getKeyListeners()) {
        this.removeKeyListener(k);
      }
    }
    addKeyBoardMovingFeature(controllerOperations);
    resetFocus();
    this.pack();
  }

  private void addKeyBoardMovingFeature(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // no need to implement
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // for keyboard action
        try {
          // move
          switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
              if (moving) {
                controllerOperations.moveAdventurerTwo("left");
                game.clearErrorMessage(false);
              }
              adventurerOneShot = false;
              shotDirection = "left";
              break;
            case KeyEvent.VK_UP:
              if (moving) {
                controllerOperations.moveAdventurerTwo("up");
                game.clearErrorMessage(false);
              }
              adventurerOneShot = false;
              shotDirection = "up";
              break;
            case KeyEvent.VK_DOWN:
              if (moving) {
                controllerOperations.moveAdventurerTwo("down");
                game.clearErrorMessage(false);
              }
              adventurerOneShot = false;
              shotDirection = "down";
              break;
            case KeyEvent.VK_RIGHT:
              if (moving) {
                controllerOperations.moveAdventurerTwo("right");
                game.clearErrorMessage(false);
              }
              adventurerOneShot = false;
              shotDirection = "right";
              break;
            default:
              // no action needed
          }
        } catch (Exception error) {
          game.setErrorMessage(error.getMessage(), false);
        }

        try {
          // move
          switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
              if (moving) {
                controllerOperations.moveAdventurerOne("up");
                game.clearErrorMessage(true);
              }
              adventurerOneShot = true;
              shotDirection = "up";
              break;
            case KeyEvent.VK_A:
              if (moving) {
                controllerOperations.moveAdventurerOne("left");
                game.clearErrorMessage(true);
              }
              adventurerOneShot = true;
              shotDirection = "left";
              break;
            case KeyEvent.VK_S:
              if (moving) {
                controllerOperations.moveAdventurerOne("down");
                game.clearErrorMessage(true);
              }
              adventurerOneShot = true;
              shotDirection = "down";
              break;
            case KeyEvent.VK_D:
              if (moving) {
                controllerOperations.moveAdventurerOne("right");
                game.clearErrorMessage(true);
              }
              adventurerOneShot = true;
              shotDirection = "right";
              break;
            default:
              // no action needed
          }
        } catch (Exception error) {
          game.setErrorMessage(error.getMessage(), true);
        }

        // prepare shooting
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          moving = false;
          shootFlag = true;
        }

        // for shooting
        shootingArrow(controllerOperations);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // shooting ends or aborts
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
          shootFlag = false;
          moving = true;
        } else {
          shotDirection = "";
        }
      }
    });
  }

  @Override
  public void setAdventurerTurn(boolean firstAdventurerTurn) {
    game.setPlayerTurn(firstAdventurerTurn);
  }

  private void shootingArrow(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    if (shootFlag && shotDirection.length() != 0) {
      int distance;
      while (true) {
        try {
          if ((adventurerOneFinish && adventurerOneShot) || (adventurerTwoFinish
                  && !adventurerOneShot)) {
            break;
          }
          distance = Integer.parseInt(
                  JOptionPane.showInputDialog(this, "Enter Distance:"));
          if (adventurerOneShot) {
            controllerOperations.adventurerOneFireArrow(shotDirection, distance);
          } else {
            controllerOperations.adventurerTwoFireArrow(shotDirection, distance);
          }
          break;
        } catch (Exception e) {
          if (e.getMessage().equals("null")) {
            break;
          }
          JOptionPane.showMessageDialog(this, "Invalid distance value.");
        }
      }
      // reset
      shootFlag = false;
      moving = true;
      shotDirection = "";
    }
  }

  @Override
  public void firstAdventurerGameOver(boolean finished) {
    adventurerOneFinish = finished;
  }

  @Override
  public void secondAdventurerGameOver(boolean finished) {
    adventurerTwoFinish = finished;
  }

  @Override
  public void updateLayout(Cave[][] layout, ControllerOperations controllerOperations) {
    if (layout == null || layout.length == 0 || layout[0].length == 0) {
      throw new IllegalArgumentException("Null or invalid map dimension.");
    }
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    game.updateMaze(layout, controllerOperations);
    resetFocus();
    this.pack();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void updateInfo(Map<String, String> message, Map<String, String> hints) {
    game.updateInfo(message, hints);
  }

  @Override
  public void resetStatus() {
    moving = true;
    shootFlag = false;
    shotDirection = "";
    adventurerOneShot = true;
    adventurerOneFinish = false;
    adventurerTwoFinish = false;
  }
}
