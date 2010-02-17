/*
 * RobotEvent.java
 *
 * Created on Pondelok, 2006, január 23, 16:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class RobotEvent extends EventObject {

    private final Point2D oldPosition;
    private final Point2D newPosition;

    private final Color oldColor;
    private final Color newColor;

    private final double oldHeading;
    private final double newHeading;

    private final boolean penDown;
    private final int penWidth;
    

    /** Creates a new instance of RobotEvent */
    public RobotEvent(Object source, Point2D oldPosition, Point2D newPosition, 
            Color oldColor, Color newColor, double oldHeading, double newHeading, 
            boolean penDown, int penWidth) {
        super(source);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.oldColor = oldColor;
        this.newColor = newColor;
        this.oldHeading = oldHeading;
        this.newHeading = newHeading;
        this.penDown = penDown;
        this.penWidth = penWidth;
    }

    /** Creates a new instance of RobotEvent */
    public RobotEvent(Object source, Point2D oldPosition, Point2D newPosition, 
            Color color, double heading, boolean penDown, int penWidth) {
        this(source, oldPosition, newPosition, color, color, heading, heading, penDown, penWidth);
    }

    /** Creates a new instance of RobotEvent */
    public RobotEvent(Object source, Point2D position, Color oldColor, Color newColor, 
            double heading, boolean penDown, int penWidth) {
        this(source, position, position, oldColor, newColor, heading, heading,penDown, penWidth);
    }

    /** Creates a new instance of RobotEvent */
    public RobotEvent(Object source, Point2D position, Color color, double oldHeading, 
            double newHeading, boolean penDown, int penWidth) {
        this(source, position, position, color, color, oldHeading, newHeading, penDown, penWidth);
    }

    /**
     * Returns the original position of the robot before it moves relative to the
     * source component.
     *
     * @return position relative to the component
     */
    public Point2D getOldPosition() {
        return oldPosition;
    }

    /**
     * Returns the new position of the robot before it moves relative to the
     * source component.
     *
     * @return position relative to the component
     */
    public Point2D getNewPosition() {
        return newPosition;
    }

    public Color getNewColor() {
        return newColor;
    }

    public double getNewHeading() {
        return newHeading;
    }

    public Color getOldColor() {
        return oldColor;
    }

    public double getOldHeading() {
        return oldHeading;
    }

    public boolean isPenDown() {
        return penDown;
    }

    public int getPenWidth() {
        return penWidth;
    }

}
