package view.components;

import controller.Arguments;
import interfaces.ControllerOperations;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.ColorTheme;

/** The display for setting up the game. */
public class Menu implements MazeComponent {

  private final JPanel menu = new JPanel();
  private final JTextField rowText = new JTextField();
  private final JTextField columnText = new JTextField();
  private final JTextField wallText = new JTextField();
  private final JTextField batText = new JTextField();
  private final JTextField pitText = new JTextField();
  private final JButton player1 = new JButton("Single Player");
  private final JButton player2 = new JButton("Multi Player");
  private final JLabel error = new JLabel(" ");

  /** Construct a menu view. */
  public Menu() {
    this.constructMenu();
  }

  private void constructMenu() {
    menu.setBounds(new Rectangle(1600, 900));
    menu.setBackground(ColorTheme.MAIN);
    menu.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    int y = 0;

    // title
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JLabel title = new JLabel("Hunt the Wumpus!");
    title.setFont(new Font("", Font.BOLD, 60));
    title.setForeground(Color.WHITE);
    menu.add(title, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 50);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    JLabel setting = new JLabel("Game Settings:");
    ComponentUtils.setFont(setting);
    menu.add(setting, gbc);
    y++;
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    // row
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 1;
    gbc.ipadx = 60;
    JLabel rowLabel = new JLabel("Number of Rows: ");
    rowLabel.setPreferredSize(new Dimension(200, 20));
    ComponentUtils.setFont(rowLabel);
    menu.add(rowLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    menu.add(rowText, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    // column
    gbc.gridx = 0;
    gbc.gridy = y;
    JLabel columnLabel = new JLabel("Number of Columns: ");
    ComponentUtils.setFont(columnLabel);
    menu.add(columnLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    menu.add(columnText, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    // wall
    gbc.gridx = 0;
    gbc.gridy = y;
    JLabel wallLabel = new JLabel("Number of walls in the dungeon: ");
    ComponentUtils.setFont(wallLabel);
    menu.add(wallLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    menu.add(wallText, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    // bat
    gbc.gridx = 0;
    gbc.gridy = y;
    JLabel batLabel = new JLabel("Percentage of superbats (between 0 and 1): ");
    ComponentUtils.setFont(batLabel);
    menu.add(batLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    menu.add(batText, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 5);
    y++;
    // pit
    gbc.gridx = 0;
    gbc.gridy = y;
    JLabel pitLabel = new JLabel("Percentage of pits (between 0 and 1): ");
    ComponentUtils.setFont(pitLabel);
    menu.add(pitLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    menu.add(pitText, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 40);
    y++;
    // player button
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.fill = GridBagConstraints.NONE;
    player1.setPreferredSize(new Dimension(180, 30));
    menu.add(player1, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    player2.setPreferredSize(new Dimension(180, 30));
    menu.add(player2, gbc);
    y++;
    // gap
    ComponentUtils.createHorizontalGap(menu, gbc, y, 10);
    y++;
    // error message
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    gbc.ipadx = 1;
    error.setForeground(Color.RED);
    menu.add(error, gbc);
  }

  private String getRow() {
    return this.rowText.getText();
  }

  private String getColumn() {
    return this.columnText.getText();
  }

  private String getWall() {
    return this.wallText.getText();
  }

  private String getBat() {
    return this.batText.getText();
  }

  private String getPit() {
    return this.pitText.getText();
  }

  @Override
  public void addListener(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    this.player1.addActionListener(
        l -> {
          Arguments arguments;
          try {
            arguments = getArguments(true);
            controllerOperations.createGame(arguments);
            // empty error message
            error.setText(" ");
            reset();
          } catch (Exception e) {
            error.setText("Error: " + e.getMessage());
          }
        });

    this.player2.addActionListener(
        l -> {
          Arguments arguments;
          try {
            arguments = getArguments(false);
            controllerOperations.createGame(arguments);
            // empty error message
            error.setText(" ");
            reset();
          } catch (Exception e) {
            error.setText("Error: " + e.getMessage());
          }
        });
  }

  private void reset() {
    this.rowText.setText("");
    this.columnText.setText("");
    this.wallText.setText("");
    this.batText.setText("");
    this.pitText.setText("");
  }

  private Arguments getArguments(boolean singlePlayer) {
    return new Arguments(
        this.getRow(),
        this.getColumn(),
        this.getWall(),
        this.getBat(),
        this.getPit(),
        singlePlayer);
  }

  @Override
  public JComponent getMainComponent() {
    return this.menu;
  }

  @Override
  public void setVisible() {
    this.menu.setVisible(true);
  }

  @Override
  public void setInvisible() {
    this.menu.setVisible(false);
  }
}
