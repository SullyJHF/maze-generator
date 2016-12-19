package maze;

import java.awt.Shape;
import java.awt.geom.Line2D;

public class Cell {
  private int i;
  private int j;
  private boolean[] walls = { true, true, true, true };
  private int w = Surface.CELL_SIZE;
  private boolean visited = false;

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
}
