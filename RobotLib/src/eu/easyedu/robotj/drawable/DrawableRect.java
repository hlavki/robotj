/*
 * DrawableRect.java
 *
 * Created on Nedeľa, 2006, december 10, 16:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Draw rectangle on {@link eu.easyedu.robotj.canvas.PaintCanvas}.
 * Rectangle is defined with one point, width and height.
 * @author hlavki
 */
public class DrawableRect implements Drawable {
    
    /**
     * rectangle color
     */
    protected Color color;
    /**
     * Rectangle class from Java API
     */
    protected Rectangle2D rect;
    /**
     * Flag if rectangle is filled or not.
     */
    protected boolean filled = false;
    
    /**
     * Creates a new instance of DrawableRect.
     * @param position Position of rectangle.
     * @param w Rectangle width.
     * @param h Rectangle height.
     * @param color Rectangle color.
     */
    public DrawableRect(Point2D position, double w, double h, Color color) {
        this.rect = new Rectangle2D.Double(position.getX(), position.getY(), w, h);
        this.color = color;
    }
    
    /**
     * Sets if rectangle is filled or not.
     * @param filled Filled rectangle or not
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    /**
     * Draws rectangle on {@link eu.easyedu.robotj.canvas.PaintCanvas}.
     * @param g Graphics where shape is drawn.
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        if (filled) g.fill(rect);
        else g.draw(rect);
    }
    
    /**
     * Returns rectangle object.
     * @return Rectangle object.
     */
    public Rectangle2D getRect() {
        return rect;
    }
}

