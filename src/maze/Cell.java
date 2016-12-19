package maze;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
  private int i;
  private int j;
  private boolean[] walls = { true, true, true, true };
  private int w = Surface.CELL_SIZE;
  boolean visited = false;
  private ArrayList<Cell> grid = Surface.grid;
  private int cols = Surface.COLS;
  private int rows = Surface.ROWS;
  private Random r = new Random();

  public Cell(int i, int j) {
    this.i = i;
    this.j = j;
  }

  public Shape getTop() {
    Line2D l = new Line2D.Double();
    if (!walls[0]) return l;
    l.setLine(i * w, j * w, i * w + w, j * w);
    return l;
  }

  public Shape getRight() {
    Line2D l = new Line2D.Double();
    if (!walls[1]) return l;
    l.setLine(i * w + w, j * w, i * w + w, j * w + w);
    return l;
  }

  public Shape getBottom() {
    Line2D l = new Line2D.Double();
    if (!walls[2]) return l;
    l.setLine(i * w + w, j * w + w, i * w, j * w + w);
    return l;
  }

  public Shape getLeft() {
    Line2D l = new Line2D.Double();
    if (!walls[3]) return l;
    l.setLine(i * w, j * w + w, i * w, j * w);
    return l;
  }

  public int getX() {
    return i * w;
  }

  public int getY() {
    return j * w;
  }

  public Cell getNeighbour() {
    ArrayList<Cell> neighbours = new ArrayList<Cell>();
    int topIndex = index(i, j - 1);
    int rightIndex = index(i + 1, j);
    int bottomIndex = index(i, j + 1);
    int leftIndex = index(i - 1, j);
//    System.out.println(topIndex);

    if(topIndex != -1 && !grid.get(topIndex).visited) neighbours.add(grid.get(topIndex));
    if(rightIndex != -1 && !grid.get(rightIndex).visited) neighbours.add(grid.get(rightIndex));
    if(bottomIndex != -1 && !grid.get(bottomIndex).visited) neighbours.add(grid.get(bottomIndex));
    if(leftIndex != -1 && !grid.get(leftIndex).visited) neighbours.add(grid.get(leftIndex));
    if(neighbours.size() > 0) {
      int rand = r.nextInt(neighbours.size());
      return neighbours.get(rand);
    }
    return null;
  }

  private int index(int i, int j) {
    if(i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
    return i + j * cols;
  }
}
