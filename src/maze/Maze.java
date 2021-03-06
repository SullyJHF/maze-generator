package maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Maze extends JFrame implements Runnable, MouseListener, KeyListener {
  private Thread thread;
  private boolean running = false;

  private final int UPS = 60;

  private double startTime;

  private Surface surface;

  private FileHandler fh;

  public Maze() {
    super("Maze");
    fh = new FileHandler();
    surface = new Surface();
    add(surface);
    addMouseListener(this);
    addKeyListener(this);
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
    System.out.println("Started!");
  }

  public synchronized void stop() {
    if (!running) return;
    running = false;
    try {
      System.out.println("Stopped!");
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    System.out.println("Running!");
    running = true;

    double delta = 1.0 / UPS;

    // convert the time to seconds
    double nextTime = System.nanoTime() / 1000000000.0;
    double maxTimeDiff = 0.5;
    int skippedFrames = 1;
    int maxSkippedFrames = 5;
    startTime = System.nanoTime() / 1000000000.0;
    while (running) {
      // convert the time to seconds
      double curTime = System.nanoTime() / 1000000000.0;
      if ((curTime - nextTime) > maxTimeDiff) nextTime = curTime;
      if (curTime >= nextTime) {
        // assign the time for the next update
        nextTime += delta;
        surface.tick();
        if (surface.finished) running = false;
        if ((curTime < nextTime) || (skippedFrames > maxSkippedFrames)) {
          surface.render();
          skippedFrames = 1;
        } else {
          skippedFrames++;
        }
      } else {
        // calculate the time to sleep
        int sleepTime = (int) (1000.0 * (nextTime - curTime));
        // sanity check
        if (sleepTime > 0) {
          // sleep until the next update
          try {
            Thread.sleep(sleepTime);
          } catch (InterruptedException e) {
            // do nothing
          }
        }
      }
    }
    System.out.println("Generated in: " + (System.nanoTime() / 1000000000.0 - startTime) + " seconds");
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {}

  @Override
  public void mouseEntered(MouseEvent arg0) {}

  @Override
  public void mouseExited(MouseEvent arg0) {}

  @Override
  public void mousePressed(MouseEvent arg0) {
    if (running) {
      stop();
      return;
    }
    surface = new Surface();
    add(surface);
    repaint();
    revalidate();
    start();
  }

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_SPACE) {
      fh.screenshot(surface.getImage());
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void keyTyped(KeyEvent e) {}
}
