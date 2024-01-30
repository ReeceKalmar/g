package g;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * This class represents a GUI component for displaying an image.
 *
 * @author Prof. Martin
 * @version April 6, 2023
 */
public class ImagePanel extends JPanel {
  MouseHandler mouseHandler = new MouseHandler();
  private BufferedImage bufferedImg;
  public static int x1;
  public static int y1;
  public static int x2;
  public static int y2;
  public int width;
  public int height;

  /**
   * Creates a new ImagePanel to display the given image.
   *
   * @param img - the given image
   */
  public ImagePanel(Image img) {
    int rowCount = img.getNumberOfRows();
    int colCount = img.getNumberOfColumns();
    this.bufferedImg = new BufferedImage(colCount, rowCount,
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < rowCount; i++)
      for (int j = 0; j < colCount; j++)
        this.bufferedImg.setRGB(j, i, img.getPixel(i,
            j).getPackedRGB());
    this.setPreferredSize(new Dimension(colCount, rowCount));
    this.addMouseListener(mouseHandler);
  }

  public ImagePanel(Image img, ImageProcessorFrame frame) {
    int rowCount = img.getNumberOfRows();
    int colCount = img.getNumberOfColumns();
    this.bufferedImg = new BufferedImage(colCount, rowCount,
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < rowCount; i++)
      for (int j = 0; j < colCount; j++)
        this.bufferedImg.setRGB(j, i, img.getPixel(i,
            j).getPackedRGB());
    this.setPreferredSize(new Dimension(colCount, rowCount));

    this.addMouseListener(mouseHandler);
  }

  /**
   * This method is called by the system when a component needs to be painted.
   * Which can be at one of three times:
   * --when the component first appears
   * --when the size of the component changes (including resizing by the
   * user)
   * --when repaint() is called
   *
   * Partially overrides the paintComponent method of JPanel.
   *
   * @param g -- graphics context onto which we can draw
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.bufferedImg, 0, 0, this);
    g.setColor(new Color(105, 105, 105, 125));
    int startX = Math.min(x1, x2);
    int startY = Math.min(y1, y2);
    g.fillRect(startX, startY, Math.abs(x2 - x1), Math.abs(y2 - y1));
  }

  class MouseHandler extends MouseAdapter {

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
      x1 = e.getX();
      y1 = e.getY();

      repaint();
    }

    public void mouseReleased(MouseEvent e) {
      x2 = e.getX();
      y2 = e.getY();
      width = Math.abs(x2 - x1);
      height = Math.abs(y2 - y1);
      repaint();
    }
  }

  // Required by a serializable class (ignore for now)
  private static final long serialVersionUID = 1L;
}
