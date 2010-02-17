/*
 * ImageDimension.java
 *
 * Created on Štvrtok, 2006, december 28, 18:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.ide;

import java.awt.Dimension;

/**
 *
 * @author hlavki
 */
public class ImageDimension extends Dimension {
    
    static final long serialVersionUID = -5704239568659152093L;
    
    public ImageDimension(Dimension d) {
        this(d.width, d.height);
    }
    
    public ImageDimension(int width, int height) {
        super(width, height);
    }
    
    public String toString() {
        return width + "x" + height;
    }
    
}
