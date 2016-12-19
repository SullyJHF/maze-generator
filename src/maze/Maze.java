package maze;

import javax.swing.JFrame;

public class Maze extends JFrame implements Runnable {
  private Thread thread;
  private boolean running = false;

  private final int UPS = 5;

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
    while (running) {
      // convert the time to seconds
      double currTime = System.nanoTime() / 1000000000.0;
      if ((currTime - nextTime) > maxTimeDiff) nextTime = currTime;
      if (currTime >= nextTime) {
        // assign the time for the next update
        nextTime += delta;
        surface.tick();
        if(surface.finished) stop();
        if ((currTime < nextTime) || (skippedFrames > maxSkippedFrames)) {
          surface.render();
          skippedFrames = 1;
        } else {
          skippedFrames++;
        }
      } else {
        // calculate the time to sleep
        int sleepTime = (int) (1000.0 * (nextTime - currTime));
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
  }

}
