/*
 * DrawableLine.java
 *
 * Created on Nedeľa, 2006, december 10, 16:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Draw line on {@link eu.easyedu.robotj.canvas.PaintCanvas}
 * @author hlavki
 */
public class DrawableLine implements Drawable {
    
    /**
     * line color
     */
    protected Color color;
    /**
     * line width
     */
    protected int width;
    /**
     * line class from Java API
     */
    protected Line2D line;
    
    /**
     * Creates instance of DrawableLine
     * @param line Line class from Java API
     * @param color Line color
     * @param width Line width
     */
    public DrawableLine(Line2D line, Color color, int width) {
        this.line = line;
        this.color = color;
        this.width = width;
    }
    
    /**
     * Creates instance of DrawableLine from 2 points defined by their coordinates.
     * @param x1 x coordinate of first point
     * @param y1 y coordinate of first point
     * @param x2 x coordinate of second point
     * @param y2 y coordinate of second point
     * @param color line color
     * @param width line width
     */
    public DrawableLine(double x1, double y1, double x2, double y2, Color color, int width) {
        this.line = new Line2D.Double(x1, y1, x2, y2);
        this.color = color;
        this.width = width;
    }
    
    /**
     * Creates instance of DrawableLine from 2 points defined by Point2D class.
     * @param point1 first point
     * @param point2 second Point
     * @param color line color
     * @param width line width
     */
    public DrawableLine(Point2D point1, Point2D point2, Color color, int width) {
        this.line = new Line2D.Double(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        this.color = color;
        this.width = width;
    }

    /**
     * Draw line on {@link eu.easyedu.robotj.canvas.PaintCanvas}.
     * @param g Graphics where shape is drawn.
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, 1));
        g.draw(line);
    }
}

