package maze;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class FileHandler {
  Pattern p = Pattern.compile("(\\d+)");
  String path = "src/screenshots/";
  int largest;
  public FileHandler() {
    largest = 0;
  }

  public void screenshot(BufferedImage image) {
    try {
      System.out.println("--- Saving file --- ");
      String filename = path + "maze" + getNumber() + ".png";
      ImageIO.write(image, "PNG", new File(filename));
      System.out.println("--- Saved file " + filename + " ---");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getNumber() {
    String output = "";
    try {
      Stream<Path> paths = Files.walk(Paths.get("src/screenshots"));
      paths.map(path -> {
        Matcher m = p.matcher(path.toString());
        if(m.find()) {
          return m.group(0);
        }
        return "0";
//        p.matcher(path.toString()).find() ? p.matcher(path.toString()).group(0) : "0");
      }).forEach(this::setLargest);
      largest++;
      return largest < 10 ? "0" + String.valueOf(largest) : String.valueOf(largest);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return output;
  }

  private void setLargest(String input) {
    if(Integer.valueOf(input) > largest) largest = Integer.valueOf(input);
  }
}
