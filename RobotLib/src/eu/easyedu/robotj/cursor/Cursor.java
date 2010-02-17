/*
 * Cursor.java
 *
 * Created on Nedeľa, 2005, november 20, 11:38
 *
 */

package eu.easyedu.robotj.cursor;

import eu.easyedu.robotj.drawable.Drawable;
import java.awt.geom.Point2D;
import java.util.logging.Logger;


/**
 * This abstract class covers all visual cursors of {@link eu.easyedu.robotj.Robot}.<br/>
 * There are some predefined implementations of cursor:<br/><br/>
 * There is 1..n relationship between Cursor and {@link eu.easyedu.robotj.Robot}.
 * <B>Many</B> robots can have <B>one</B> Cursor.
 * Example:
 * <CODE>Cursor cursor = new BitmapCursor(canvas, "turtle.png");<br/>
 * Robot r1 = new Robot(canvas, 100, 100);<br/>
 * Robot r2 = new Robot(canvas, 100, 200);<br/>
 * r1.setCursor(cursor);<br/>
 * r2.setCursor(cursor);<br/>
 * r1.setCursorVisible(true);<br/>
 * r2.setCursorVisible(true);
 * </CODE>
 * @author hlavki
 */
public abstract class Cursor implements Drawable {

    /**
     * Center point of cursor. This point assign {@link eu.easyedu.robotj.Robot} position.
     * This point is center of drawing. It is also center of rotation.
     */
    protected Point2D center;
    /**
     * Direction of Robot in degrees.
     */
    protected double heading;
    
    /**
     * Default logger class used for internal testing.
     */
    protected static final Logger logger = Logger.getLogger(Cursor.class.getName());

    /**
     * set center of Cursor.
     * @param position Position of center.
     * @param heading Direction of cursor.
     */
    public void setCenter(Point2D position, double heading) {
        this.center = position;
        this.heading = heading;
    }

    /**
     * returns center point of Cursor.
     * @return Center of cursor
     */
    public Point2D getCenter() {
        return center;
    }

    /**
     * Returns direction of cursor in degrees.
     * @return direction of cursor in degrees.
     */
    public double getHeading() {
        return heading;
    }

    /**
     * This method calculates coordinates and rotation of cursor. There is no need to 
     * calculate this in draw method. This method has optimalisation purposes.
     * It fires only when {@link eu.easyedu.robotj.Robot} moves or changes heading.
     */
    public abstract void calculate();
}
