package view.components;

import interfaces.ControllerOperations;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import view.ColorTheme;

/**
 * A game is over display.
 */
public class GameOver implements MazeComponent {

  private final JPanel overView;
  private final JTextArea player1 = new JTextArea("");
  private final JTextArea player2 = new JTextArea("");
  private final JButton restart = new JButton("Restart ");
  private final JButton newGame = new JButton("New Game");
  private final String player1Result;
  private final String player2Result;

  /**
   * Construct a game over view.
   *
   * @param player1Result the result for player 1
   * @param player2Result the result for player 2
   */
  public GameOver(String player1Result, String player2Result) {
    overView = new JPanel();
    overView.setBackground(ColorTheme.MAIN);
    overView.setLayout(new GridBagLayout());
    this.player1Result = player1Result;
    this.player2Result = player2Result;
    constructView();
  }

  private void constructView() {
    JPanel titleWrapper = new JPanel();
    titleWrapper.setBackground(Color.BLACK);
    titleWrapper.setBorder(new LineBorder(Color.WHITE, 4));
    JLabel title = new JLabel("- Game Over -", SwingConstants.CENTER);
    title.setForeground(Color.WHITE);
    title.setFont(new Font("", Font.BOLD, 40));
    title.setPreferredSize(new Dimension(800, 150));
    titleWrapper.add(title);

    int y = 0;
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    gbc.ipadx = 0;
    overView.add(titleWrapper, gbc);
    y++;
    ComponentUtils.createHorizontalGap(overView, gbc, y, 30);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    player1.setEditable(false);
    player1.setText("- Player 1\n" + "    " + player1Result);
    player1.setPreferredSize(new Dimension(400, 400));
    player1.setFont(new Font("", Font.PLAIN, 20));
    ComponentUtils.setEmptyBorder(player1, 20);
    overView.add(player1, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    player2.setEditable(false);
    player2.setText("- Player 2\n" + "    " + player2Result);
    player2.setPreferredSize(new Dimension(400, 400));
    player2.setFont(new Font("", Font.PLAIN, 20));
    ComponentUtils.setEmptyBorder(player2, 20);
    overView.add(player2, gbc);
    y++;
    ComponentUtils.createHorizontalGap(overView, gbc, y, 30);
    y++;
    gbc.gridx = 0;
    gbc.gridy = y;
    restart.setPreferredSize(new Dimension(150, 40));
    overView.add(restart, gbc);
    gbc.gridx = 1;
    gbc.gridy = y;
    newGame.setPreferredSize(new Dimension(150, 40));
    overView.add(newGame, gbc);
  }

  @Override
  public void addListener(ControllerOperations controllerOperations) {
    if (controllerOperations == null) {
      throw new IllegalArgumentException("Null controller.");
    }
    restart.addActionListener(l -> {
      try {
        controllerOperations.restartGame();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    newGame.addActionListener(l -> {
      try {
        controllerOperations.startNewGame();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public JComponent getMainComponent() {
    return overView;
  }

  @Override
  public void setVisible() {
    overView.setVisible(true);
  }

  @Override
  public void setInvisible() {
    overView.setVisible(false);
  }
}
