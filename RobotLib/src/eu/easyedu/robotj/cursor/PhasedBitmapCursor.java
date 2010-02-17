/*
 * PhasedBitmapCursor.java
 *
 * Created on Utorok, 2005, november 22, 8:11
 *
 */

package eu.easyedu.robotj.cursor;

import eu.easyedu.robotj.canvas.PaintCanvas;
import eu.easyedu.robotj.canvas.PaintCanvas;
import eu.easyedu.robotj.canvas.PaintCanvas;
import eu.easyedu.robotj.utils.ImageRotator;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Creates Bitmap cursor with phases. Phases identify direction of cursor.
 * Phases are created on creating PhasedBitmapCursor instance.
 * This class is alternative to BitmapCursor. It has higher performance but worst look.
 * @author hlavki
 */
public class PhasedBitmapCursor extends Cursor {
    
    private BufferedImage[] phases;
    private int phasesCount;
    private Image image;
    private int imageWidth, imageHeight;
    private int currentPhase = 0;
    
    /**
     * Creates a new instance of PhasedBitmapCursor
     * @param canvas Canvas where Cursor is drawn.
     * @param imageName Name of image. For example "turtle.png"
     * @param phasesCount Number of phases. For example 36 phases means that there will be 
     * created 36 images with 10 degree distinction.
     */
    public PhasedBitmapCursor(PaintCanvas canvas, String imageName, int phasesCount) {
        URL imgUrl = getClass().getResource("/eu/easyedu/robot/resources/" + imageName);
        this.image = Toolkit.getDefaultToolkit().getImage(imgUrl);
        
        MediaTracker mediaTracker = new MediaTracker(canvas);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException ie) {
            System.err.println(ie);
            System.exit(1);
        }
        this.imageWidth = image.getWidth(null);
        this.imageHeight = image.getHeight(null);
        
        logger.fine("Cursor Image ("+ imageWidth + " x " + imageHeight + ") loaded...");
        
        this.phasesCount = phasesCount;
        phases = new BufferedImage[phasesCount];
        makePhases();
        logger.fine("Phases are maked.");
    }
    
    /**
     * Calculates position and phase index.
     */
    public void calculate() {
        float delta = 360 / phasesCount;
        currentPhase = Math.round((float)heading / delta) % phasesCount;
    }
    
    /**
     * Draws image.
     * @param g Graphics where image is drawn.
     */
    public void draw(Graphics2D g) {
        BufferedImage rotatedImage = phases[currentPhase];
        g.drawImage(rotatedImage, null, (int)(center.getX() - rotatedImage.getWidth()/2), 
                (int)(center.getY()-rotatedImage.getHeight()/2));
    }
    
    private void makePhases() {
        double delta = 360 / phasesCount;
        for (int i=0; i<phasesCount; i++) {
            phases[i] = ImageRotator.rotateDegrees(image, imageWidth, imageHeight, 
                    i*delta, ImageRotator.EXACT_BOUNDING_BOX, null);
        }
    }
}
