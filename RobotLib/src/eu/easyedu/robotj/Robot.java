/*
 * Robot.java
 *
 * Created on Nedeľa, 2006, december 10, 16:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj;

import eu.easyedu.robotj.canvas.Canvas;
import eu.easyedu.robotj.cursor.Cursor;
import eu.easyedu.robotj.cursor.TriangleCursor;
import eu.easyedu.robotj.drawable.Drawable;
import eu.easyedu.robotj.drawable.DrawableLine;
import eu.easyedu.robotj.drawable.DrawablePoint;
import eu.easyedu.robotj.event.DrawEvent;
import eu.easyedu.robotj.event.DrawEventListener;
import eu.easyedu.robotj.event.RobotEventListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import eu.easyedu.robotj.event.RobotEvent;
import java.io.Serializable;

/**
 * Robot is main class of this lightweight framework. Robot live on desktop that
 * is represented by {@link eu.easyedu.robotj.canvas.Canvas} component.
 * Robot move on canvas and draw lines, point, change direction and shape.
 * There is 1..n relationship between Canvas and Robot. One Canvas may have many Robots.
 * On the other side Robot can live only on one Canvas.
 * @author hlavki
 */
public class Robot implements Drawable, Serializable {

    private static final Logger log = Logger.getLogger(Robot.class.getName());
    private double heading;
    private Point2D position;
    private boolean penDown = true;
    private Color color = Color.BLACK;
    private int penWidth = 1;
    private Cursor cursor = null;
    private boolean calculateCursor = true;
    private boolean visible = false;
    private String name;
    private List<RobotEventListener> robotMoveListeners;
    private List<RobotEventListener> robotChangeColorListeners;
    private List<RobotEventListener> robotChangeHeadingListeners;
    private List<DrawEventListener> robotDrawEventListener;

    /**
     * Creates a new instance of Robot
     * @param canvas Canvas where Robot lives.
     * @param x X coordinate of start position
     * @param y Y coordinate of start position
     * @param heading Starting direction
     */
    public Robot(String name, double x, double y, double heading) {
        this(name, new Point2D.Double(x, y), heading);
    }

    /**
     * Creates a new instance of Robot
     * @param canvas Canvas where Robot lives.
     * @param position Starting position
     * @param heading Starting direction
     */
    public Robot(String name, Point2D position, double heading) {
        this.name = name;
        this.position = position;
        this.heading = heading;
        this.cursor = new TriangleCursor(15);
        this.robotMoveListeners = new ArrayList<RobotEventListener>();
        this.robotChangeColorListeners = new ArrayList<RobotEventListener>();
        this.robotChangeHeadingListeners = new ArrayList<RobotEventListener>();
        this.robotDrawEventListener = new ArrayList<DrawEventListener>();
    }

    /**
     * Creates a new instance of Robot. Default Direction is set to 0.
     * @param canvas Canvas where Robot lives.
     * @param x X coordinate of start position
     * @param y X coordinate of start position
     */
    public Robot(String name, double x, double y) {
        this(name, x, y, 0);
    }

    public Robot(String name, Canvas canvas) {
        this(name, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.addRobot(this);
    }

    public Robot(String name, Canvas canvas, double x, double y) {
        this(name, x, y, 0);
        canvas.addRobot(this);
    }

//    /**
//     * Creates a new instance of Robot. Default Direction is set to 0 and position
//     * is set to centre of desktop
//     *
//     * @param canvas
//     */
//    public Robot(String name) {
//        this(name, canvas.getWidth() / 2, canvas.getHeight() / 2, 0);
//    }
    /**
     * Returns X coordinate of current position
     * @return x coordinate of current position
     * @deprecated replaced by {@link #getPosition()}
     */
    @Deprecated
    public double getX() {
        return position.getX();
    }

    /**
     * Returns Y coordinate of current position
     * @return Returns Y coordinate of current position
     * @deprecated replaced by {@link #getPosition()}
     */
    @Deprecated
    public double getY() {
        return position.getY();
    }

    /**
     * Returns current heading
     * @return current heading
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Returns current color
     * @return current color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns current pen width
     * @return current pen width
     */
    public int getPenWidth() {
        return penWidth;
    }

//    /**
//     * Returns Canvas
//     * @return canvas.
//     */
//    public Canvas getCanvas() {
//        if (canvas == null) {
//            throw new NoCanvasAssignedException("No canvas is assigned to robot ");
//        } else {
//            return canvas;
//        }
//    }
    /**
     * Sets robot's position.
     * @param position new position
     */
    public void setPosition(Point2D position) {
        Point2D oldPosition = this.position;
        this.position = position;
        calculateCursor = true;
        fireRobotMove(oldPosition, this.position);
    }

    /**
     * Sets new heading.
     * @param heading new heading
     */
    public void setHeading(double heading) {
        double oldHeading = this.heading;
        this.heading = heading;
        while (this.heading < 0) {
            this.heading += 360;
        }
        while (this.heading >= 360) {
            this.heading -= 360;
        }
        calculateCursor = true;
        fireRobotChangeHeading(oldHeading, this.heading);
    }

    /**
     * Sets new color.
     * @param color new color
     */
    public void setColor(Color color) {
        Color oldColor = this.color;
        this.color = color;
        fireRobotChangeColor(oldColor, this.color);
    }

    /**
     * Sets new pen Width
     * @param penWidth new pen Width
     */
    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

//    /**
//     * Change Canvas.
//     * @param canvas new Canvas
//     */
//    public void setCanvas(Canvas canvas) {
//        this.canvas = canvas;
//    }
    /**
     * Change direction of robot in clockwise direction.
     * @param value additive angle
     */
    public void right(double value) {
        setHeading(this.heading + value);
        calculateCursor = true;
    }

    /**
     * Change direction of robot in anti-clockwise direction.
     * @param value additive angle
     */
    public void left(double value) {
        setHeading(this.heading - value);
        calculateCursor = true;
    }

    /**
     * Moves robot. If Robot has pen down, draws line with color, penWidth.
     * @param value step length.
     */
    public void forward(double value) {
        setXY(position.getX() + Math.sin(Math.toRadians(heading)) * value, position.getY() -
                Math.cos(Math.toRadians(heading)) * value);
    }

    /**
     * Sets X, Y coordinates of robot. If there is pen down, robot draws line.
     * @param x new X coordinate
     * @param y new Y coordinate
     */
    public void setXY(double x, double y) {
        if (penDown) {
            fireDrawShapeEvent(new DrawableLine(position.getX(), position.getY(), x, y, color, penWidth));
//            getCanvas().drawShape(new DrawableLine(position.getX(), position.getY(), x, y, color, penWidth));
        }
        moveXY(x, y);
    }

    /**
     * Moves robot to new coordinate. Robot never draws line.
     * @param x new X coordinate
     * @param y new Y coordinate
     */
    public void moveXY(double x, double y) {
        this.setPosition(new Point2D.Double(x, y));
    }

    /**
     * Sets pen up. Where robot has pen up, never draws line.
     */
    public void penUp() {
        this.penDown = false;
    }

    /**
     * Sets pen down.
     */
    public void penDown() {
        this.penDown = true;
    }

    /**
     * Robot draws Point.
     * @param radius radius of point
     */
    public void drawPoint(double radius) {
        if (penDown) {
            radius = Math.round(Math.abs(radius));
            if (radius == 0) {
                radius = penWidth;
            }
            //            int r1 = ((int)radius + 1) / 2;
            //            int r2 = (int)radius - r1 + 1;
            fireDrawShapeEvent(new DrawablePoint(position, radius, color));
//            getCanvas().drawShape(new DrawablePoint(position, radius, color));
        }
    }

    /**
     * Calculates distance between robot and point..
     * @param x X coordinate of point
     * @param y Y coordinate of point
     * @return distance between robot and point.
     */
    public double dist(double x, double y) {
        return Math.sqrt(Math.pow(x - position.getX(), 2) + Math.pow(y - position.getY(), 2));
    }

    /**
     * Calculates distance between robot and point..
     * @param point point
     * @return distance between robot and point.
     */
    public double dist(Point2D point) {
        return dist(point.getX(), point.getY());
    }

    /**
     * Calculates distance between 2 robots.
     * @param robot Robot
     * @return distance between robot and point.
     */
    public double dist(Robot robot) {
        return dist(robot.getPosition());
    }

    /**
     * Calculates if 2 robots are too close.
     * @param robot Robot
     * @return true if robot is close to another robot.
     */
    public boolean isNear(Robot robot) {
        return isNear(robot.getPosition());
    }

    /**
     * Calculates if robot is too close to another position.
     * @param robot Robot
     * @return true if robot is close to another position.
     */
    public boolean isNear(Point2D position) {
        return isNear(position, 30);
    }

    /**
     * Calculates if robot is too close to another position.
     * @param robot Robot
     * @return true if robot is close to another position.
     */
    public boolean isNear(Point2D position, double radius) {
        return dist(position) < radius;
    }

    /**
     * Chnages direction of robot to point.
     * @param x X coordinate of point
     * @param y Y coordinate of point
     */
    public void towards(double x, double y) {
        double a;
        x = x - position.getX();
        y = position.getY() - y;
        if (y == 0) {
            if (x == 0) {
                a = 0;
            } else if (x < 0) {
                a = 270;
            } else {
                a = 90;
            }
        } else if (y > 0) {
            if (x >= 0) {
                a = Math.toDegrees(Math.atan(x / y));
            } else {
                a = 360 - Math.toDegrees(Math.atan(-x / y));
            }
        } else {
            a = 180 + Math.toDegrees(Math.atan(x / y));
        }
        setHeading(a);
    }

    /**
     * Chnages direction of robot to point.
     * @param point point
     */
    public void towards(Point2D point) {
        towards(point.getX(), point.getY());
    }

    /**
     * Chnages direction of robot to another robot.
     *
     * @param robot robot
     */
    public void towards(Robot robot) {
        towards(robot.getPosition());
    }

    /**
     * Set Visible cursor of Robot.
     *
     * @param visible True if cursor is visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
//        if (visible && cursor != null) {
//            getCanvas().addCursor(this);
//        } else {
//            getCanvas().removeCursor(this);
//        }
    }

    /**
     * Check if robot's cursor is visible or not
     * @return true if cursor is visible
     */
    public boolean isVisible() {
        return visible;
//        return getCanvas().containsCursor(this);
    }

    /**
     * Returns current cursor.
     * @return cursor
     */
    public Cursor getCursor() {
        cursor.setCenter(position, heading);
        if (calculateCursor) {
            cursor.calculate();
            calculateCursor = false;
        }
        return cursor;
    }

    /**
     * Set new cursor.
     * @param cursor new cursor
     */
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    /**
     * Returns position of robot.
     * @return position of robot.
     */
    public Point2D getPosition() {
        return position;
    }

    public boolean isPenDown() {
        return penDown;
    }

    private void fireRobotMove(Point2D oldPosition, Point2D newPosition) {
        RobotEvent event = null;
        for (RobotEventListener robotEventListener : robotMoveListeners) {
            if (event == null) {
                event = new RobotEvent(this, oldPosition, newPosition, getColor(), getHeading(), isPenDown(), getPenWidth());
            }
            robotEventListener.robotMovesEvent(event);
        }
    }

    private void fireRobotChangeColor(Color oldColor, Color newColor) {
        RobotEvent event = null;
        for (RobotEventListener robotEventListener : robotChangeColorListeners) {
            if (event == null) {
                event = new RobotEvent(this, getPosition(), oldColor, newColor, getHeading(), isPenDown(), getPenWidth());
            }
            robotEventListener.robotchangesColorEvent(event);
        }
    }

    private void fireRobotChangeHeading(double oldHeading, double newHeading) {
        RobotEvent event = null;
        for (RobotEventListener robotEventListener : robotChangeHeadingListeners) {
            if (event == null) {
                event = new RobotEvent(this, getPosition(), getColor(), oldHeading, newHeading, isPenDown(), getPenWidth());
            }
            robotEventListener.robotchangesHeadingEvent(event);
        }
    }

    private void fireDrawShapeEvent(Drawable shape) {
        DrawEvent event = null;
        for (DrawEventListener listener : robotDrawEventListener) {
            if (event == null) {
                event = new DrawEvent(this, shape);
            }
            listener.drawEvent(event);
        }
    }

    /**
     * Add listener that listen on robot moves.
     * @param robotEventListener
     */
    public void addRobotMoveListener(RobotEventListener robotEventListener) {
        robotMoveListeners.add(robotEventListener);
    }

    /**
     * Add listener that listen on robot color changes.
     * @param robotEventListener
     */
    public void addRobotChangeColorListener(RobotEventListener robotEventListener) {
        robotChangeColorListeners.add(robotEventListener);
    }

    /**
     * Add listener that listen on change robot heading.
     * @param robotEventListener
     */
    public void addRobotChangeHeadingListener(RobotEventListener robotEventListener) {
        robotChangeHeadingListeners.add(robotEventListener);
    }

    /**
     * Add listener that listen on change robot heading.
     * @param robotEventListener
     */
    public void addDrawEventListener(DrawEventListener listener) {
        robotDrawEventListener.add(listener);
    }

    /**
     * Remove listener that listen when robot draw shapes.
     * @param listener
     */
    public void removeDrawEventListener(DrawEventListener listener) {
        robotDrawEventListener.remove(listener);
    }

    @Override
    public String toString() {
        return super.toString() + "[" + getPosition() + "]" + "[" + getPenWidth() + "]";
    }

    public void draw(Graphics2D g) {
        getCursor().draw(g);
    }

    public String getName() {
        return name;
    }
}