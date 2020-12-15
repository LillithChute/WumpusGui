package view.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import view.ColorTheme;

/**
 * A helper class.
 */
public class ComponentUtils {

  /**
   * Add a horizontal gap.
   *
   * @param mainComponent the target component
   * @param gbc           grid bag constraint
   * @param y             the y index
   * @param height        gap height
   */
  public static void createHorizontalGap(Container mainComponent, GridBagConstraints gbc,
                                         int y, int height) {
    if (mainComponent == null || gbc == null) {
      throw new IllegalArgumentException("Null input arguments.");
    }

    if (y < 0 || height < 0) {
      throw new IllegalArgumentException("Negative value is not allowed.");
    }

    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 2;
    mainComponent.add(Box.createRigidArea(new Dimension(height, height)), gbc);
    gbc.gridwidth = 1;
  }

  /**
   * Set the component's font and background color.
   *
   * @param component the target component
   */
  public static void setFont(JComponent component) {
    if (component == null) {
      throw new IllegalArgumentException("Null input component.");
    }

    component.setBackground(ColorTheme.MAIN);
    component.setForeground(ColorTheme.FONT);
    component.setFont(new Font("", Font.PLAIN, 14));
  }

  /**
   * Set the component's font as bold.
   *
   * @param component the target component
   */
  public static void setBoldFont(JComponent component) {
    if (component == null) {
      throw new IllegalArgumentException("Null input component.");
    }

    component.setBackground(ColorTheme.MAIN);
    component.setForeground(ColorTheme.FONT);
    component.setFont(new Font("", Font.BOLD, 14));
  }

  /**
   * Set the component's error message layout.
   *
   * @param component the target component
   */
  public static void setErrorMessage(JComponent component) {
    if (component == null) {
      throw new IllegalArgumentException("Null input component.");
    }

    component.setBackground(ColorTheme.MAIN);
    component.setForeground(ColorTheme.DANGER);
    component.setFont(new Font("", Font.PLAIN, 14));
  }

  /**
   * Set empty border for the target component.
   *
   * @param component the target component
   */
  public static void setEmptyBorder(JComponent component, int size) {
    if (component == null) {
      throw new IllegalArgumentException("Null input component.");
    }

    component.setBorder(new EmptyBorder(new Insets(size, size, size, size)));
  }
}
