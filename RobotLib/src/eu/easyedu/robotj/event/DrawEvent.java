/*
 * DrawEvent.java
 * 
 * Created on 8.7.2007, 15:41:15
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

import eu.easyedu.robotj.drawable.Drawable;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class DrawEvent extends EventObject {

    private final Drawable shape;
    
    public DrawEvent(Object source, Drawable shape) {
        super(source);
        this.shape = shape;
    }

    public Drawable getShape() {
        return shape;
    }

}
