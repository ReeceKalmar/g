package g;
/**
 * 
 * @author Reece Kalmar
 * @version 3/17/2023
 * This class creates a new pixel object in which the red green and blue amounts of information for a pixel are stored
 */
public class Pixel {
	
	private int red, green, blue; 
	/**
	 * This is the constructor for the pixel class, creating the object and storing the rgb information
	 * @param redAmount the red in a pixel
	 * @param greenAmount the green in a pixel
	 * @param blueAmount the blue in a pixel
	 */
	public Pixel(int redAmount, int greenAmount, int blueAmount) {
		if (redAmount >= 0 && greenAmount >= 0 && blueAmount >= 0 && redAmount <= 255 && greenAmount <= 255 && blueAmount <= 255) {
			this.red = redAmount;
			this.green = greenAmount;
			this.blue = blueAmount;
		}
		else {
			throw  new IllegalArgumentException("rgb value not in range");
		}
	/**
	 * This getter meathod gets the red amount in a pixel
	 * @return red the red in a pixel
	 */
	}
	public int getRedAmount() {
		return this.red;
	}
	/**
	 * This getter meathod gets the green amount in a pixel
	 * @return red the green in a pixel
	 */
	public int getGreenAmount() {
		return this.green;
	}
	/**
	 * This getter meathod gets the blue amount in a pixel
	 * @return red the blue in a pixel
	 */
	public int getBlueAmount() {
		return this.blue;
	}
	/**
	 * This meathod packs all the rgb information into a single 4 byte integer
	 * @return the 4 byte packed rbg information
	 */
	public int getPackedRGB() {
		int packedRGB = (red << 16) | (green << 8) | blue;
	     return packedRGB;
	}
}
