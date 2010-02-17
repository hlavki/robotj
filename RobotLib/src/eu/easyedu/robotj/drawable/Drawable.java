/*
 * Drawable.java
 *
 * Created on Nedeľa, 2006, december 10, 16:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.drawable;

import java.awt.Graphics2D;

/**
 * Basic Drawable Interface. Every class with implemented Drawable interface can 
 * be drawn on {@link eu.easyedu.robotj.canvas.PaintCanvas} canvas.
 * @author hlavki
 */
public interface Drawable {

    /**
     * Draws 2D graphics to {@link eu.easyedu.robotj.canvas.PaintCanvas} canvas.
     * @param g Graphics where shape is drawn.
     */
    public void draw(Graphics2D g);

}
