package maze;

import javax.swing.JFrame;

public class Maze extends JFrame implements Runnable {
  private Thread thread;
  private boolean running = false;

  private Surface surface;

  public Maze() {
    super("Maze");
    surface = new Surface();
    add(surface);
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public synchronized void start() {
    if (running) return;
    running = true;
    thread = new Thread(this);
    thread.start();
  }

  public synchronized void stop() {
    if (!running) return;
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    System.out.println("running!");
  }

}
