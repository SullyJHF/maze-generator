package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

public class Surface extends JPanel {
  private final static int WIDTH = 800;
  private final static int HEIGHT = WIDTH;
  static final int CELL_SIZE = 20;
  final static int ROWS = HEIGHT / CELL_SIZE;
  final static int COLS = WIDTH / CELL_SIZE;

  static ArrayList<Cell> grid;

  private Cell current;

  private Color currentColor = new Color(50, 230, 70);
  private Color visitedColor = new Color(100, 75, 167);
  private Color stackColor = new Color(120, 100, 200);
  private Color wallColor = new Color(230, 230, 230);

  private Color startColor = new Color(65, 255, 150, 200);
  private Color endColor = new Color(255, 80, 180, 200);

  private Color solutionColor = new Color(0, 0, 0, 100);

  private Stack<Cell> stack = new Stack<Cell>();

  private Stack<Cell> solution = new Stack<Cell>();
  private Cell fork;

  private Cell start;
  private Cell end;

  private int biggest = stack.size();

  boolean finished = false;

  boolean showSolution = true;

  boolean skipVisited = true;

  public Surface() {
    System.out.println("NEW SURFACE");
    finished = false;
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    grid = new ArrayList<Cell>(ROWS * COLS);
    for (int j = 0; j < ROWS; j++) {
      for (int i = 0; i < COLS; i++) {
        Cell c = new Cell(i, j);
        grid.add(c);
      }
    }

    current = grid.get(0);
    start = current;
    fork = null;
    stack.push(current);
  }

  public void doDrawing(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.GRAY);
    g2d.fillRect(0, 0, WIDTH, HEIGHT);
    for (Cell c : grid) {
      Rectangle r = new Rectangle(c.getX(), c.getY(), CELL_SIZE, CELL_SIZE);
      g2d.setColor(visitedColor);
      if (c.visited && !c.equals(current))
        g2d.fill(r);

      g2d.setColor(stackColor);
      if (stack.contains(c))
        g2d.fill(r);

      g2d.setColor(solutionColor);
      if (showSolution && solution.contains(c))
        g2d.fill(r);

      g2d.setColor(startColor);
      if (c.equals(start))
        g2d.fill(r);

      g2d.setColor(endColor);
      if (c.equals(end))
        g2d.fill(r);

      g2d.setColor(wallColor);
      g2d.draw(c.getTop());
      g2d.draw(c.getRight());
      g2d.draw(c.getBottom());
      g2d.draw(c.getLeft());
    }
    if (!finished) {
      g2d.setColor(currentColor);
      g2d.fillRect(current.getX(), current.getY(), CELL_SIZE, CELL_SIZE);
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    doDrawing(g);
  }

  public void render() {
    repaint();
  }

  public void tick() {
    if (stack.isEmpty()) {
      finished = true;
      render();
      return;
    }
    current.distFromStart = stack.size();
    if (current.distFromStart > biggest) {
      biggest = current.distFromStart;
      end = current;
      if (showSolution) {
        solution.clear();
        fork = null;
      }
    }
    current.visited = true;
    Cell next = current.getNeighbour();
    if (next != null) {
      next.visited = true;

      stack.push(current);

      removeWalls(current, next);

      current = next;
    } else if (!stack.isEmpty()) {
      while (skipVisited && current.getNeighbour() == null) {
        if(stack.isEmpty()) break;
        current = stack.pop();

        fillSolution();
      }

      if(!skipVisited) {
        current = stack.pop();
        fillSolution();
      }
    }
  }

  private void fillSolution() {
    if (showSolution) {
      if (fork == null && current.getNeighbour() == null) {
        solution.push(current);
      } else if (fork == null && current.getNeighbour() != null) {
        fork = current;
      } else if (fork != null && current.equals(fork)) {
        solution.push(current);
        fork = null;
      }
    }
  }

  private void removeWalls(Cell a, Cell b) {
    int x = a.i - b.i;
    if (x == 1) {
      a.walls[3] = false;
      b.walls[1] = false;
    } else if (x == -1) {
      a.walls[1] = false;
      b.walls[3] = false;
    }

    int y = a.j - b.j;
    if (y == 1) {
      a.walls[0] = false;
      b.walls[2] = false;
    } else if (y == -1) {
      a.walls[2] = false;
      b.walls[0] = false;
    }
  }
}
