/*
 * EllipseCursor.java
 *
 * Created on Nedeľa, 2005, november 20, 23:41
 *
 */

package eu.easyedu.robotj.cursor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Creates cursor with elliptic shape.
 * @author hlavki
 */
public class EllipseCursor extends Cursor {

    double sideLength;
    double x, y;

    /**
     * Creates a new instance of EllipseCursor. For now there is only circle shape.
     * @param sideLength Radius of circle
     */
    public EllipseCursor(double sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * Calculates position of cursor.
     */
    public void calculate() {
        x = center.getX() - sideLength / 2;
        y = center.getY() - sideLength / 2;
    }

    /**
     * Draws image.
     * @param g Graphics where image is drawn.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.draw(new Ellipse2D.Double(x, y, sideLength, sideLength));
    }
    
}
