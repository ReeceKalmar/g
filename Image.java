package g;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class represents an image as a two-dimensional array of pixels and
 * provides a number
 * of image filters (via instance methods) for changing the appearance of the
 * image.
 * Application of multiple filters is cumulative; e.g., obj.redBlueSwapFilter()
 * followed by
 * obj.rotateClockwiseFilter() results in an image altered both in color and
 * orientation.
 * 
 * Note:
 * - The pixel in the northwest corner of the image is stored in the first row,
 * first column.
 * - The pixel in the northeast corner of the image is stored in the first row,
 * last column.
 * - The pixel in the southeast corner of the image is stored in the last row,
 * last column.
 * - The pixel in the southwest corner of the image is stored in the last row,
 * first column.
 * 
 * @author Prof. Martin and Reece Kalmar
 * @version 3/17/2023
 */
public class Image {

	private Pixel[][] imageArray;

	/**
	 * Creates a new Image object by reading the image file with the given filename.
	 * 
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param filename - name of the given image file to read
	 * @throws IOException if file does not exist or cannot be read
	 */
	public Image(String filename) {
		BufferedImage imageInput = null;
		try {
			imageInput = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("Image file " + filename + " does not exist or cannot be read.");
		}

		imageArray = new Pixel[imageInput.getHeight()][imageInput.getWidth()];
		for (int i = 0; i < imageArray.length; i++)
			for (int j = 0; j < imageArray[0].length; j++) {
				int rgb = imageInput.getRGB(j, i);
				imageArray[i][j] = new Pixel((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
			}
	}

	/**
	 * Create a new "default" Image object, whose purpose is to be used in testing.
	 * 
	 * The orientation of this image:
	 * cyan red
	 * green magenta
	 * yellow blue
	 *
	 * DO NOT MODIFY THIS METHOD
	 */
	public Image() {
		imageArray = new Pixel[3][2];
		imageArray[0][0] = new Pixel(0, 255, 255); // cyan
		imageArray[0][1] = new Pixel(255, 0, 0); // red
		imageArray[1][0] = new Pixel(0, 255, 0); // green
		imageArray[1][1] = new Pixel(255, 0, 255); // magenta
		imageArray[2][0] = new Pixel(255, 255, 0); // yellow
		imageArray[2][1] = new Pixel(0, 0, 255); // blue
	}

	/**
	 * Gets the pixel at the specified row and column indexes.
	 * 
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param rowIndex    - given row index
	 * @param columnIndex - given column index
	 * @return the pixel at the given row index and column index
	 * @throws IndexOutOfBoundsException if row or column index is out of bounds
	 */
	public Pixel getPixel(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= imageArray.length)
			throw new IndexOutOfBoundsException("rowIndex must be in range 0-" + (imageArray.length - 1));

		if (columnIndex < 0 || columnIndex >= imageArray[0].length)
			throw new IndexOutOfBoundsException("columnIndex must be in range 0-" + (imageArray[0].length - 1));

		return imageArray[rowIndex][columnIndex];
	}

	/**
	 * Writes the image represented by this object to file.
	 * Does nothing if the image length is 0.
	 * 
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param filename - name of image file to write
	 * @throws IOException if file does cannot be written
	 */
	public void writeImage(String filename) {
		if (imageArray.length > 0) {
			BufferedImage imageOutput = new BufferedImage(imageArray[0].length,
					imageArray.length, BufferedImage.TYPE_INT_RGB);

			for (int i = 0; i < imageArray.length; i++)
				for (int j = 0; j < imageArray[0].length; j++)
					imageOutput.setRGB(j, i, imageArray[i][j].getPackedRGB());

			try {
				ImageIO.write(imageOutput, "png", new File(filename));
			} catch (IOException e) {
				System.out.println("The image cannot be written to file " + filename);
			}
		}
	}

	/**
	 * Meathod swaps the red and blue values of a pixel in the image
	 */
	public void redBlueSwapFilter() {
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[0].length; j++) {
				Pixel color = new Pixel(imageArray[i][j].getBlueAmount(), imageArray[i][j].getGreenAmount(),
						imageArray[i][j].getRedAmount());
				imageArray[i][j] = color;
			}
		}
	}

	/**
	 * Meathod changes the the photo to be black and white by setting the rgb values
	 * to be the average of the three.
	 */
	public void blackAndWhiteFilter() {
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[0].length; j++) {
				int greyScaledColor = (imageArray[i][j].getRedAmount() + imageArray[i][j].getGreenAmount()
						+ imageArray[i][j].getBlueAmount()) / 3;
				Pixel color = new Pixel(greyScaledColor, greyScaledColor, greyScaledColor);
				imageArray[i][j] = color;
			}
		}
	}

	/**
	 * Meathod rotates the image 90 deg clockwise
	 */
	public void rotateClockwiseFilter() {
		int rows = imageArray.length;
		int cols = imageArray[0].length;
		Pixel[][] rotatedArray = new Pixel[cols][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rotatedArray[j][rows - i - 1] = imageArray[i][j];
			}
		}
		imageArray = rotatedArray;
	}

	/**
	 * Meathod swaps the green and blue values of a pixel
	 */
	public void greenBlueSwapFilter() {
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[0].length; j++) {
				Pixel color = new Pixel(imageArray[i][j].getRedAmount(), imageArray[i][j].getBlueAmount(),
						imageArray[i][j].getGreenAmount());
				imageArray[i][j] = color;
			}
		}
	}

	public void brightnessFilter(int brightness) {
		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[0].length; j++) {
				int rgb = imageArray[i][j].getPackedRGB();
				int red = ((rgb >> 16) & 255) + brightness;
				int green = ((rgb >> 8) & 255) + brightness;
				int blue = (rgb & 255) + brightness;
				if (red > 255) {
					red = 255;
				} else if (red < 0) {
					red = 0;
				}

				if (green > 255) {
					green = 255;
				} else if (green < 0) {
					green = 0;
				}

				if (blue > 255) {
					blue = 255;
				} else if (blue < 0) {
					blue = 0;
				}

				imageArray[i][j] = new Pixel(red, green, blue);
			}
		}
	}

	public void cropFilter(int x1, int y1, int x2, int y2) {
		int cropWidth = x2 - x1 + 1;
		int cropHeight = y2 - y1 + 1;
		Pixel[][] croppedImage = new Pixel[cropHeight][cropWidth];

		if (x1 < 0 || y1 < 0 || x2 >= imageArray[0].length || y2 >= imageArray.length) {
			throw new IllegalArgumentException("Invalid crop coordinates.");
		}

		for (int i = y1; i <= y2; i++) {
			for (int j = x1; j <= x2; j++) {
				croppedImage[i - y1][j - x1] = imageArray[i][j];
			}
		}
		imageArray = croppedImage;
	}

	/**
	 * Applies a simple blur filter to the image by replacing each pixel color with
	 * the
	 * average color of the surrounding pixels.
	 */
	public void blurFilter() {
		Pixel[][] newImageArray = new Pixel[imageArray.length][imageArray[0].length];

		for (int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[0].length; j++) {
				int redSum = 0, greenSum = 0, blueSum = 0;
				int count = 0;
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						if (i + di >= 0 && i + di < imageArray.length && j + dj >= 0 && j + dj < imageArray[0].length) {
							Pixel p = imageArray[i + di][j + dj];
							redSum += p.getRedAmount();
							greenSum += p.getGreenAmount();
							blueSum += p.getBlueAmount();
							count++;
						}
					}
				}
				int newRed = redSum / count;
				int newGreen = greenSum / count;
				int newBlue = blueSum / count;

				newImageArray[i][j] = new Pixel(newRed, newGreen, newBlue);
			}
		}

		imageArray = newImageArray;
	}

	public int getNumberOfRows() {
		return this.imageArray.length;
	}

	public int getNumberOfColumns() {
		if (this.imageArray.length == 0)
			return 0;
		return this.imageArray[0].length;
	}
}
