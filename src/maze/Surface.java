package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Surface extends JPanel {
  private final int WIDTH = 400;
  private final int HEIGHT = WIDTH;
  protected static final int CELL_SIZE = 40;
  private final int ROWS = WIDTH / CELL_SIZE;
  private final int COLS = HEIGHT / CELL_SIZE;

  private ArrayList<Cell> grid;

  public Surface() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    grid = new ArrayList<Cell>(ROWS * COLS);
    for (int j = 0; j < ROWS; j++) {
      for (int i = 0; i < COLS; i++) {
        Cell c = new Cell(i, j);
        grid.add(c);
      }
    }
  }

  public void doDrawing(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.GRAY);
    g2d.fillRect(0, 0, WIDTH, HEIGHT);
    g2d.setColor(Color.WHITE);
    for (Cell c : grid) {
      g2d.draw(c.getTop());
      g2d.draw(c.getRight());
      g2d.draw(c.getBottom());
      g2d.draw(c.getLeft());
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    doDrawing(g);
  }

  public void render() {
    this.paintImmediately(new Rectangle(0, 0, WIDTH, HEIGHT));
  }

  public void tick() {

  }
}
