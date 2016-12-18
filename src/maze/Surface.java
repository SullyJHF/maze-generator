package maze;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Surface extends JPanel {
  private final int WIDTH = 400;
  private final int HEIGHT = WIDTH;
  public Surface() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
  }
}
