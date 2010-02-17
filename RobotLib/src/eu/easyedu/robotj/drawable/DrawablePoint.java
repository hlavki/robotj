/*
 * DrawablePoint.java
 *
 * Created on Nedeľa, 2006, december 10, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Draw point on {@link eu.easyedu.robotj.canvas.PaintCanvas}. Point has circle shape.
 * @author hlavki
 */
public class DrawablePoint implements Drawable {
    
    /**
     * Point color
     */
    protected Color color;
    /**
     * Point radius
     */
    protected double radius;
    /**
     * Point class from Java API
     */
    protected Ellipse2D point;
    
    /**
     * Creates a new instance of DrawablePoint
     * @param position Position of point.
     * @param radius Point radius.
     * @param color Point Color.
     */
    public DrawablePoint(Point2D position, double radius, Color color) {
        point = new Ellipse2D.Double(position.getX()-(radius / 2), position.getY()-radius, radius, radius);
        this.color = color;
    }
    
    /**
     * Draws point on {@link eu.easyedu.robotj.canvas.PaintCanvas}.
     * @param g Graphics where shape is drawn.
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(point);
    }
    
    /**
     * Returns point.
     * @return point
     */
    public Ellipse2D getPoint() {
        return point;
    }
}

