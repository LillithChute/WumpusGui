package view.components;

import interfaces.Cave;
import interfaces.ControllerOperations;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.Map;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import view.ColorTheme;
import view.Images;

/**
 * The game display.
 */
public class GameView implements Game {

  private final JPanel main = new JPanel();
  private final JPanel game = new JPanel();
  private final JScrollPane mazeView = new JScrollPane();
  private final JTextArea mouseTurn = new JTextArea("");
  private final JTextArea logBodyP1 = new JTextArea("");
  private final JTextArea hintBodyP1 = new JTextArea("");
  private final JTextArea logBodyP2 = new JTextArea("");
  private final JTextArea hintBodyP2 = new JTextArea("");
  private final JButton restart = new JButton("Restart ");
  private final JButton newGame = new JButton("New Game");
  private final Images images = new Images();
  private final JTextArea errorP1 = new JTextArea("");
  private final JTextArea errorP2 = new JTextArea("");
  private int windowWidth = 0;
  private int windowHeight = 0;

  /**
   * Construct a game view.
   */
  public GameView() {
    constructView();
  }

  private void constructView() {
    main.setLayout(new BorderLayout());
    main.setSize(1600, 900);
    main.setBackground(Color.BLACK);
    // game view
    game.setBackground(ColorTheme.MAIN);
    main.add(game, BorderLayout.CENTER);

    // message view
    constructMessage();
  }

  private void constructMessage() {
    // use grid bag layout to construct the view
    // this is the right side bar
    int unitWidth = 265;
    JPanel message = new JPanel();
    message.setBackground(ColorTheme.SIDE);
    message.setLayout(new GridBagLayout());
    message.setBorder(new EmptyBorder(
            new Insets(10, 10, 10, 10)));
    GridBagConstraints gbc = new GridBagConstraints();

    int y = 0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JLabel mouseTitle = new JLabel("- Current Mouse Player:");
    ComponentUtils.setBoldFont(mouseTitle);
    message.add(mouseTitle, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 1;
    mouseTurn.setEditable(false);
    ComponentUtils.setFont(mouseTurn);
    ComponentUtils.setEmptyBorder(mouseTurn, 10);
    message.add(mouseTurn, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // log
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JLabel title = new JLabel("- Message: ");
    ComponentUtils.setBoldFont(title);
    message.add(title, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    message.add(Box.createHorizontalGlue(), gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // log body
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.BOTH;
    ComponentUtils.setFont(logBodyP1);
    ComponentUtils.setEmptyBorder(logBodyP1, 10);
    logBodyP1.setEditable(false);
    logBodyP1.setPreferredSize(new Dimension(unitWidth, 100));
    message.add(logBodyP1, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    logBodyP2.setEditable(false);
    logBodyP2.setPreferredSize(new Dimension(unitWidth, 100));
    ComponentUtils.setFont(logBodyP2);
    ComponentUtils.setEmptyBorder(logBodyP2, 10);
    message.add(logBodyP2, gbc);
    y += 5;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // hints
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    JLabel hint = new JLabel("- Hints: ");
    ComponentUtils.setBoldFont(hint);
    message.add(hint, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    message.add(Box.createHorizontalGlue(), gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    // hint body
    y++;
    ComponentUtils.setFont(hintBodyP1);
    ComponentUtils.setEmptyBorder(hintBodyP1, 10);
    ComponentUtils.setFont(hintBodyP2);
    ComponentUtils.setEmptyBorder(hintBodyP2, 10);
    hintBodyP1.setEditable(false);
    hintBodyP2.setEditable(false);
    hintBodyP1.setPreferredSize(new Dimension(unitWidth, 150));
    hintBodyP2.setPreferredSize(new Dimension(unitWidth, 150));
    gbc.gridx = 0;
    gbc.gridy = y;
    message.add(hintBodyP1, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    message.add(hintBodyP2, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // error
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.fill = GridBagConstraints.BOTH;
    JLabel errorTitle = new JLabel("- Error: ");
    ComponentUtils.setBoldFont(errorTitle);
    message.add(errorTitle, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 0, 0);
    errorP1.setEditable(false);
    errorP1.setPreferredSize(new Dimension(unitWidth, 40));
    ComponentUtils.setErrorMessage(errorP1);
    ComponentUtils.setEmptyBorder(errorP1, 10);
    message.add(errorP1, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    errorP2.setEditable(false);
    errorP2.setPreferredSize(new Dimension(unitWidth, 40));
    ComponentUtils.setErrorMessage(errorP2);
    ComponentUtils.setEmptyBorder(errorP2, 10);
    message.add(errorP2, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // buttons
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.insets = new Insets(0,0,0,10);
    message.add(restart, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    gbc.insets = new Insets(0,10,0,0);
    message.add(newGame, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.insets = new Insets(0, 0,0,0);
    JLabel ruleTitle = new JLabel("- Game Rules:");
    ComponentUtils.setBoldFont(ruleTitle);
    message.add(ruleTitle, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JTextArea rules = new JTextArea(
            new StringBuilder()
                    .append("- You need to kill the Wumpus! Remember, you only have 3 arrows.\n")
                    .append("- You will get hints about the adjacent rooms.\n")
                    .append("- Bats may take you to a random location.\n")
                    .append("- You fall into a pit, you die.").toString()
    );
    ComponentUtils.setFont(rules);
    ComponentUtils.setEmptyBorder(rules, 10);
    rules.setPreferredSize(new Dimension(2 * unitWidth, 100));
    message.add(rules, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    // legend
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 1;
    JLabel legend = new JLabel("- Legend:");
    ComponentUtils.setBoldFont(legend);
    message.add(legend, gbc);
    y++;
    ComponentUtils.createHorizontalGap(message, gbc, y, 10);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.BOTH;
    JButton p1 = new JButton(
            new ImageIcon(
                    images.getPlayer1().getImage().getScaledInstance(
                            40, 40, Image.SCALE_SMOOTH)));
    p1.setMargin(new Insets(0 ,0, 0, 0));
    ComponentUtils.setEmptyBorder(p1, 10);
    p1.setText("  - Player 1: (W, A, S, D) to move, (CTRL + W, A ,S, D) to shoot an arrow");
    ComponentUtils.setFont(p1);
    message.add(p1, gbc);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JButton p2 = new JButton(
            new ImageIcon(
                    images.getPlayer2().getImage().getScaledInstance(
                            40, 40, Image.SCALE_SMOOTH)));
    p2.setMargin(new Insets(0 ,0, 0, 0));
    ComponentUtils.setEmptyBorder(p2, 10);
    p2.setText("  - Player 2: (Arrow Key) to move, (CTRL + Arrow Key) to shoot an arrow");
    ComponentUtils.setFont(p2);
    message.add(p2, gbc);

    main.add(message, BorderLayout.EAST);
  }

  @Override
  public void setPlayerTurn(boolean player1Turn) {
    String message = player1Turn ? "- Player 1" : "- Player 2";
    mouseTurn.setText(message);
  }

  @Override
  public void setErrorMessage(String message, boolean isP1) {
    if (isP1) {
      errorP1.setText(message);
    } else {
      errorP2.setText(message);
    }
  }

  @Override
  public void clearErrorMessage(boolean isP1) {
    if (isP1) {
      errorP1.setText("");
    } else {
      errorP2.setText("");
    }
  }

  @Override
  public void updateInfo(Map<String, String> message, Map<String, String> hints) {
    // player 1
    if (message.get("player1") != null && message.get("player1").length() > 0) {
      this.logBodyP1.setText("- Player 1\n" + message.get("player1"));
    }
    if (hints.get("player1") != null && hints.get("player1").length() > 0) {
      this.hintBodyP1.setText("- Player 1\n" + hints.get("player1"));
    }
    // player 2
    if (message.get("player2") != null && message.get("player2").length() > 0) {
      this.logBodyP2.setText("- Player 2\n" + message.get("player2"));
    }
    if (hints.get("player2") != null && hints.get("player2").length() > 0) {
      this.hintBodyP2.setText("- Player 2\n" + hints.get("player2"));
    }
  }

  private void reset() {
    logBodyP1.setText("");
    logBodyP2.setText("");
    hintBodyP2.setText("");
    hintBodyP1.setText("");
    mouseTurn.setText("");
    clearErrorMessage(true);
    clearErrorMessage(false);
  }

  @Override
  public void addListener(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    this.newGame.addActionListener(l -> {
      reset();
      try {
        controllerOperations.startNewGame();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.restart.addActionListener(l -> {
      reset();
      try {
        controllerOperations.restartGame();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public JComponent getMainComponent() {
    return this.main;
  }

  @Override
  public void setVisible() {
    this.main.setVisible(true);
  }

  @Override
  public void setInvisible() {
    this.main.setVisible(false);
  }

  @Override
  public void initializeMaze(Cave[][] map, ControllerOperations controllerOperations) {
    if (map == null || map.length == 0 || map[0].length == 0) {
      throw new IllegalArgumentException("Null or invalid map dimension.");
    }
    reset();
    game.removeAll();
    int x = map.length;
    int y = map[0].length;
    if (windowHeight == 0) {
      windowHeight = (int) (game.getHeight() * 0.9);
      windowWidth = windowHeight;
    }
    JPanel maze = mazeButton(map, controllerOperations, x, y);

    mazeView.setViewportView(maze);
    GridBagConstraints gbc = new GridBagConstraints();
    mazeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
    mazeView.setMaximumSize(new Dimension(windowWidth, windowHeight));
    mazeView.setMinimumSize(new Dimension(windowWidth, windowHeight));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.BOTH;
    game.add(mazeView, gbc);
    game.setLayout(new GridBagLayout());
  }

  @Override
  public void updateMaze(Cave[][] map, ControllerOperations controllerOperations) {
    int x = map.length;
    int y = map[0].length;
    JPanel maze = mazeButton(map, controllerOperations, x, y);
    int h = mazeView.getHorizontalScrollBar().getValue();
    int v = mazeView.getVerticalScrollBar().getValue();
    mazeView.setViewportView(maze);
    mazeView.getHorizontalScrollBar().setValue(h);
    mazeView.getVerticalScrollBar().setValue(v);
  }

  private JPanel mazeButton(Cave[][] map, ControllerOperations controllerOperations, int x, int y) {
    if (map == null || map.length == 0 || map[0].length == 0) {
      throw new IllegalArgumentException("Null or invalid map dimension.");
    }
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    JPanel maze = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        gbc.gridx = j;
        gbc.gridy = i;
        int sideLength = 125;
        ImageIcon img = assignImageIcon(map[i][j]);
        Icon resizedImg = resizeIcon(img, sideLength, sideLength);
        JButton room = new JButton(resizedImg);
        room.setBackground(Color.WHITE);
        room.setActionCommand(String.format("%d,%d", i, j));
        room.addActionListener(l -> {
          String[] command = l.getActionCommand().split(",");
          int row = Integer.parseInt(command[0]);
          int col = Integer.parseInt(command[1]);
          try {
            clearErrorMessage(mouseTurn.getText().equals("- Player 1"));
            controllerOperations.moveAdventurer(row, col);
          } catch (IllegalArgumentException | IOException e) {
            if (mouseTurn.getText().equals("- Player 1")) {
              errorP1.setText(e.getMessage());
            } else {
              errorP2.setText(e.getMessage());
            }
          }
        });
        room.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
        room.setPreferredSize(new Dimension(sideLength - 1, sideLength - 1));
        maze.add(room, gbc);
      }
    }
    return maze;
  }

  private ImageIcon assignImageIcon(Cave cave) {
    if (cave == null) {
      throw new IllegalArgumentException("Null room.");
    }
    if (!cave.isCaveVisited()) {
      return images.getHiddenRoom();
    }
    // empty
    if (!cave.hasWumpus() && !cave.doesCaveHaveAdventurer()
            && !cave.hasBats() && !cave.hasPit()) {
      return emptyRoomIcon(cave);
    } else if (!cave.doesCaveHaveAdventurer()
            && !cave.hasBats() && !cave.hasPit()) {
      // only has wumpus
      return wumpusOnlyRoom(cave);
    } else if (!cave.hasWumpus() && !cave.doesCaveHaveAdventurer()
            && !cave.hasPit()) {
      // only has bats
      return batOnlyRoom(cave);
    } else if (!cave.hasWumpus() && !cave.doesCaveHaveAdventurer()
            && !cave.hasBats()) {
      // only has pit
      return pitOnlyRoom(cave);
    } else if (!cave.hasWumpus()
            && !cave.hasBats() && !cave.hasPit()) {
      // only has player
      return playerOnlyRoom(cave);
    } else if (!cave.hasBats() && !cave.hasPit()) {
      // wumpus + player
      return  playerOnlyRoom(cave);
    } else if (!cave.doesCaveHaveAdventurer() && !cave.hasPit()) {
      // wumpus + bats
      return wumpusBatRoom(cave);
    } else if (!cave.doesCaveHaveAdventurer() && !cave.hasBats()) {
      // wumpus + pit
      return wumpusPitRoom(cave);
    } else if (!cave.hasWumpus() && !cave.hasPit()) {
      // player + bat
      return playerOnlyRoom(cave);
    } else if (!cave.hasWumpus() && !cave.hasBats()) {
      // player + pit
      return pitOnlyRoom(cave);
    } else if (!cave.hasWumpus() && !cave.doesCaveHaveAdventurer()) {
      // bat + pit
      return batPitRoom(cave);
    } else if (!cave.doesCaveHaveAdventurer()) {
      // wumpus + pit + bat
      return wumpusBatPitRoom(cave);
    } else if (!cave.hasWumpus()) {
      // bat + pit + player
      return batPitRoom(cave);
    } else if (!cave.hasBats()) {
      // wumpus + pit + player
      return wumpusPitRoom(cave);
    } else {
      // wumpus + bat + player
      return wumpusBatPitRoom(cave);
    }
  }

  /*
  Methods below are to retrieve correct image for each room
   */
  private ImageIcon wumpusBatPitRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatPitWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomWumpusBatPitASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatPitWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatPitWSD();
    } else {
      return images.getRoomWumpusBatPitWAS();
    }
  }

  private ImageIcon batPitRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomBatPitWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomBatPitASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomBatPitWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomBatPitWSD();
    } else {
      return images.getRoomBatPitWAS();
    }
  }

  private ImageIcon wumpusPitRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusPitWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomWumpusPitASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusPitWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomWumpusPitWSD();
    } else {
      return images.getRoomWumpusPitWAS();
    }
  }

  private ImageIcon wumpusBatRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomWumpusBatASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomWumpusBatWSD();
    } else {
      return images.getRoomWumpusBatWAS();
    }
  }

  private ImageIcon playerOnlyRoom(Cave cave) {
    if (cave.getAdventurerSymbol().equals("*")) {
      if (cave.getUp() != null && cave.getDown() != null
              && cave.getLeft() != null && cave.getRight() != null) {
        return images.getRoomPlayer1WASD();
      } else if (cave.getUp() == null && cave.getRight() != null
              && cave.getLeft() != null && cave.getDown() != null) {
        return images.getRoomPlayer1ASD();
      } else if (cave.getLeft() == null && cave.getUp() != null
              && cave.getDown() != null && cave.getRight() != null) {
        return images.getRoomPlayer1WSD();
      } else if (cave.getDown() == null && cave.getUp() != null
              && cave.getLeft() != null && cave.getRight() != null) {
        return images.getRoomPlayer1WAD();
      } else if (cave.getRight() == null && cave.getUp() != null
              && cave.getLeft() != null && cave.getDown() != null) {
        return images.getRoomPlayer1WAS();
      } else if (cave.getUp() != null && cave.getLeft() != null
              && cave.getRight() == null && cave.getDown() == null) {
        return images.getRoomPlayer1WA();
      } else if (cave.getUp() != null && cave.getDown() != null
              && cave.getLeft() == null && cave.getRight() == null) {
        return images.getRoomPlayer1WS();
      } else if (cave.getUp() != null && cave.getRight() != null
              && cave.getLeft() == null && cave.getDown() == null) {
        return images.getRoomPlayer1WD();
      } else if (cave.getLeft() != null && cave.getDown() != null
              && cave.getRight() == null && cave.getUp() == null) {
        return images.getRoomPlayer1AS();
      } else if (cave.getLeft() != null && cave.getRight() != null
              && cave.getUp() == null && cave.getDown() == null) {
        return images.getRoomPlayer1AD();
      } else if (cave.getDown() != null && cave.getRight() != null
              && cave.getUp() == null && cave.getLeft() == null) {
        return images.getRoomPlayer1SD();
      } else if (cave.getUp() != null && cave.getLeft() == null
              && cave.getRight() == null && cave.getDown() == null) {
        return images.getRoomPlayer1W();
      } else if (cave.getLeft() != null && cave.getDown() == null
              && cave.getUp() == null && cave.getRight() == null) {
        return images.getRoomPlayer1A();
      } else if (cave.getDown() != null && cave.getUp() == null
              && cave.getLeft() == null && cave.getRight() == null) {
        return images.getRoomPlayer1S();
      } else {
        return images.getRoomPlayer1D();
      }
    } else {
      if (cave.getUp() != null && cave.getDown() != null
              && cave.getLeft() != null && cave.getRight() != null) {
        return images.getRoomPlayer2WASD();
      } else if (cave.getUp() == null && cave.getRight() != null
              && cave.getLeft() != null && cave.getDown() != null) {
        return images.getRoomPlayer2ASD();
      } else if (cave.getLeft() == null && cave.getUp() != null
              && cave.getDown() != null && cave.getRight() != null) {
        return images.getRoomPlayer2WSD();
      } else if (cave.getDown() == null && cave.getUp() != null
              && cave.getLeft() != null && cave.getRight() != null) {
        return images.getRoomPlayer2WAD();
      } else if (cave.getRight() == null && cave.getUp() != null
              && cave.getLeft() != null && cave.getDown() != null) {
        return images.getRoomPlayer2WAS();
      } else if (cave.getUp() != null && cave.getLeft() != null
              && cave.getRight() == null && cave.getDown() == null) {
        return images.getRoomPlayer2WA();
      } else if (cave.getUp() != null && cave.getDown() != null
              && cave.getLeft() == null && cave.getRight() == null) {
        return images.getRoomPlayer2WS();
      } else if (cave.getUp() != null && cave.getRight() != null
              && cave.getLeft() == null && cave.getDown() == null) {
        return images.getRoomPlayer2WD();
      } else if (cave.getLeft() != null && cave.getDown() != null
              && cave.getRight() == null && cave.getUp() == null) {
        return images.getRoomPlayer2AS();
      } else if (cave.getLeft() != null && cave.getRight() != null
              && cave.getUp() == null && cave.getDown() == null) {
        return images.getRoomPlayer2AD();
      } else if (cave.getDown() != null && cave.getRight() != null
              && cave.getUp() == null && cave.getLeft() == null) {
        return images.getRoomPlayer2SD();
      } else if (cave.getUp() != null && cave.getLeft() == null
              && cave.getRight() == null && cave.getDown() == null) {
        return images.getRoomPlayer2W();
      } else if (cave.getLeft() != null && cave.getDown() == null
              && cave.getUp() == null && cave.getRight() == null) {
        return images.getRoomPlayer2A();
      } else if (cave.getDown() != null && cave.getUp() == null
              && cave.getLeft() == null && cave.getRight() == null) {
        return images.getRoomPlayer2S();
      } else {
        return images.getRoomPlayer2D();
      }
    }
  }

  private ImageIcon emptyRoomIcon(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomEmptyWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomEmptyASD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomEmptyWSD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomEmptyWAD();
    } else if (cave.getRight() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomEmptyWAS();
    } else if (cave.getUp() != null && cave.getLeft() != null
            && cave.getRight() == null && cave.getDown() == null) {
      return images.getRoomEmptyWA();
    } else if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() == null && cave.getRight() == null) {
      return images.getRoomEmptyWS();
    } else if (cave.getUp() != null && cave.getRight() != null
            && cave.getLeft() == null && cave.getDown() == null) {
      return images.getRoomEmptyWD();
    } else if (cave.getLeft() != null && cave.getDown() != null
            && cave.getRight() == null && cave.getUp() == null) {
      return images.getRoomEmptyAS();
    } else if (cave.getLeft() != null && cave.getRight() != null
            && cave.getUp() == null && cave.getDown() == null) {
      return images.getRoomEmptyAD();
    } else if (cave.getDown() != null && cave.getRight() != null
            && cave.getUp() == null && cave.getLeft() == null) {
      return images.getRoomEmptySD();
    } else if (cave.getUp() != null && cave.getLeft() == null
            && cave.getRight() == null && cave.getDown() == null) {
      return images.getRoomEmptyW();
    } else if (cave.getLeft() != null && cave.getDown() == null
            && cave.getUp() == null && cave.getRight() == null) {
      return images.getRoomEmptyA();
    } else if (cave.getDown() != null && cave.getUp() == null
            && cave.getLeft() == null && cave.getRight() == null) {
      return images.getRoomEmptyS();
    } else {
      return images.getRoomEmptyD();
    }
  }

  private ImageIcon pitOnlyRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomPitWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomPitASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomPitWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomPitWSD();
    } else {
      return images.getRoomPitWAS();
    }
  }

  private ImageIcon wumpusOnlyRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomWumpusASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomWumpusWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomWumpusWSD();
    } else {
      return images.getRoomWumpusWAS();
    }
  }

  private ImageIcon batOnlyRoom(Cave cave) {
    if (cave.getUp() != null && cave.getDown() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomBatWASD();
    } else if (cave.getUp() == null && cave.getRight() != null
            && cave.getLeft() != null && cave.getDown() != null) {
      return images.getRoomBatASD();
    } else if (cave.getDown() == null && cave.getUp() != null
            && cave.getLeft() != null && cave.getRight() != null) {
      return images.getRoomBatWAD();
    } else if (cave.getLeft() == null && cave.getUp() != null
            && cave.getDown() != null && cave.getRight() != null) {
      return images.getRoomBatWSD();
    } else {
      return images.getRoomBatWAS();
    }
  }

  private Icon resizeIcon(ImageIcon icon, int width, int height) {
    if (width < 100 && height < 100) {
      width = 100;
      height = 100;
    }
    Image img = icon.getImage();
    Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImg);
  }
}
