package maze;

import java.awt.EventQueue;

public class Main {
  public void createAndShowGUI() {
    Maze maze = new Maze();
    maze.start();
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      Main m = new Main();
      m.createAndShowGUI();
    });
  }
}
