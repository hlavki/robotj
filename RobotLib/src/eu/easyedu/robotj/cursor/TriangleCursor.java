/*
 * TriangleCursor.java
 *
 * Created on Nedeľa, 2005, november 20, 11:46
 *
 */

package eu.easyedu.robotj.cursor;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Robot cursor with triangle shape.
 * @author hlavki
 */
public class TriangleCursor extends Cursor {

    double sideLength;
    int[] xPoints = new int[3];
    int[] yPoints = new int[3];
    
    /**
     * Creates a new instance of TriangleCursor. Shape is equilateral triangle with 
     * user defined side length.
     * @param sideLength side length of triangle.
     */
    public TriangleCursor(double sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * Draws image.
     * @param g Graphics where image is drawn.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.drawPolygon(xPoints, yPoints, 3);
    }

    /**
     * Calculates position and rotation of image depends on center and heading.
     */
    public void calculate() {
        double height = sideLength / 2.5 * Math.sin(Math.toRadians(heading));
        double width = sideLength / 2.5 * Math.cos(Math.toRadians(heading));
        double angle90 = Math.toRadians(heading-90);
        
        xPoints[0] = (int)(center.getX() - width);
        yPoints[0] = (int)(center.getY() - height);
        xPoints[1] = (int)(center.getX() + width);
        yPoints[1] = (int)(center.getY() + height);
        xPoints[2] = (int)(center.getX() + ((sideLength) * Math.cos(angle90)));
        yPoints[2] = (int)(center.getY() + ((sideLength) * Math.sin(angle90)));
    }
    
}
