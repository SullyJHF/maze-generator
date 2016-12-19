package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Surface extends JPanel {
  private final static int WIDTH = 400;
  private final static int HEIGHT = WIDTH;
  static final int CELL_SIZE = 40;
  final static int ROWS = WIDTH / CELL_SIZE;
  final static int COLS = HEIGHT / CELL_SIZE;

  static ArrayList<Cell> grid;

  private Cell current;

  private Color visitedColor = new Color(255, 0, 255, 90);
  private Color wallColor = new Color(230, 230, 230);

  public Surface() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    grid = new ArrayList<Cell>(ROWS * COLS);
    for (int j = 0; j < ROWS; j++) {
      for (int i = 0; i < COLS; i++) {
        Cell c = new Cell(i, j);
        grid.add(c);
      }
    }

    current = grid.get(0);
  }

  public void doDrawing(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.GRAY);
    g2d.fillRect(0, 0, WIDTH, HEIGHT);
    for (Cell c : grid) {
      g2d.setColor(visitedColor);
      if(c.visited) g2d.fillRect(c.getX(), c.getY(), CELL_SIZE, CELL_SIZE);
      g2d.setColor(wallColor);
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
    current.visited = true;
    Cell next = current.getNeighbour();
    if(next != null) {
      next.visited = true;
      current = next;
    } else {
      System.out.println("no neighbours");
    }
  }
}
