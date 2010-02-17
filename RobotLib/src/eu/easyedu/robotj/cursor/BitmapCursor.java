/*
 * BitmapCursor.java
 *
 * Created on Pondelok, 2005, november 21, 9:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.cursor;

import eu.easyedu.robotj.canvas.PaintCanvas;
import eu.easyedu.robotj.cursor.ResourceNotFoundException;
import eu.easyedu.robotj.utils.ImageRotator;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Creates cursor from external bitmap image. Supported are .png, .jpg, .bmp formats.
 * This implmentation of Cursor rotates image at runtime. It could have some performance 
 * issues with many visible robots. There is also {@link PhasedBitmapCursor} implementation, which 
 * creates phases at the creating instance.
 * @author hlavki
 */
public final class BitmapCursor extends Cursor {
    
    private Image image;
    private BufferedImage rotatedImage;
    private int imageWidth, imageHeight;
    
    /**
     * Creates a new instance of BitmapCursor.
     * @param canvas Canvas where Cursor will be painted.
     * @param imageName name of image. Images are located in uniba.fmph.edi.robot package.
     */
    public BitmapCursor(java.awt.Component component, String imageName) {
        URL imgUrl = getClass().getResource("/eu/easyedu/robotj/resources/" + imageName);
        image = Toolkit.getDefaultToolkit().getImage(imgUrl);
        System.out.println("IMAGE: " + image);

        MediaTracker mediaTracker = new MediaTracker(component);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException ie) {
            System.err.println(ie);
            System.exit(1);
        }
        this.imageWidth = image.getWidth(null);
        this.imageHeight = image.getHeight(null);
    }
    
    /**
     * Calculates position and rotation of image depends on center and heading.
     */
    public void calculate() {
        rotatedImage = ImageRotator.rotateDegrees(image, imageWidth, imageHeight, heading, ImageRotator.EXACT_BOUNDING_BOX, null);
    }
    
    /**
     * Draws image.
     * @param g Graphics where image is drawn.
     */
    public void draw(Graphics2D g) {
        g.drawImage(rotatedImage, null, (int)(center.getX()-rotatedImage.getWidth()/2), (int)(center.getY()-rotatedImage.getHeight()/2));
    }
}
