package gui;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {
	
	/**
	 * Resizes an image located at the specified imagePath to the given width and height.
	 *
	 * @param imagePath The path to the image file.
	 * @param width     The desired width of the resized image.
	 * @param height    The desired height of the resized image.
	 * @return          The resized image as an Image object.
	 */
    public static Image resizeImage(String imagePath, int width, int height) {
        try {
            File file = new File(imagePath);
            BufferedImage originalImage = ImageIO.read(file);
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return scaledImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
