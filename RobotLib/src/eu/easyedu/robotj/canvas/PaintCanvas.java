/*
 * PaintCanvas.java
 *
 * Created on Nedeľa, 2006, december 10, 16:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package eu.easyedu.robotj.canvas;

import eu.easyedu.robotj.Robot;
import eu.easyedu.robotj.drawable.Drawable;
import eu.easyedu.robotj.drawable.DrawableRect;
import eu.easyedu.robotj.event.DrawEvent;
import eu.easyedu.robotj.event.DrawEventListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author hlavki
 */
public class PaintCanvas extends JComponent implements Canvas, MouseListener,
        MouseMotionListener, DrawEventListener, Runnable {

    private static final long serialVersionUID = -3189791220306034777L;
    /**
     * Variables to store the current figure info
     */
//    private Drawable shape = null;
    /**
     * BufferedImage to store the underlying saved painting.
     * Will be initialized first time paintComponent is called.
     */
    private BufferedImage image = null;
    private Thread thread;
    private boolean painting = false;
    private Set<Robot> robots;
    final JPopupMenu menu = new JPopupMenu();
    /**
     * private fps counter
     */
    private int frameCounter = 0;
    /**
     * Default log for private testing.
     */
    private static final Logger log = Logger.getLogger(PaintCanvas.class.getName());

    /**
     * Creates instance of PaintCanvas class.
     * @param prefferedSize Preffered size.
     * @param backgroundColor Background color.
     */
    public PaintCanvas(Dimension prefferedSize, Color backgroundColor) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        setPreferredSize(prefferedSize);
        robots = new HashSet<Robot>();
        initComponents();
        setBackground(backgroundColor);
        setSize(prefferedSize);
        // Create and add a menu item
        JMenuItem item = new JMenuItem("Robot properties");
        menu.add(item);
        item.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                robotPropertiesClicked(e, new Point2D.Double(100, 100));
            }
        });
        addMouseListener(this);
    }

    /**
     * Creates instance of PaintCanvas class.
     *
     * @param size canvas size
     */
    public PaintCanvas(Dimension size) {
        this(size, Color.WHITE);
    }

    /**
     * Draws {@link eu.easyedu.robotj.drawable.Drawable} objects.
     * @param shape Drawable shape to draw.
     */
    public synchronized void drawShape(final Drawable shape) {
        shape.draw((Graphics2D) image.getGraphics());
    }

    /**
     * Clear screen using background color.
     * @param color Background color.
     */
    public void clearScreen(Color color) {
        DrawableRect rect = new DrawableRect(new Point(0, 0), getWidth(), getHeight(), color);
        rect.setFilled(true);
        drawShape(rect);
    }

    /**
     * Clear screen using white background color.
     */
    public void clearScreen() {
        clearScreen(Color.WHITE);
    }

    /**
     * Paints all graphics to canvas.
     * @param g Default graphics.
     */
    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; // downcast to Graphics2D
        g2.drawImage(image, null, 0, 0); // draw previous shapes
        for (Robot robot : robots) {
            if (robot.isVisible()) {
                robot.draw(g2);
            }
        }
        frameCounter++;
    }

    /**
     * Starts painting on canvas.
     */
    public final void start() {
        log.info("Starting Drawing Thread...");
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        painting = true;
        thread.start();
    }

    /**
     * Stops painting on canvas.
     */
    public synchronized final void stop() {
        log.info("Stopping Drawing Thread...");
        painting = false;
        thread = null;
    }

    /**
     * This method paints graphics on cavas in loop.
     */
    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                break;
            }
        }
        thread = null;
    }

    /**
     * Returns flag if PaintCanvas is painting (running).
     * @return True if PaintCanvas is painting.
     */
    public boolean isPainting() {
        return painting;
    }

    /**
     * Add {@link eu.easyedu.robotj.Robot} to draw its {@link eu.easyedu.robotj.cursor.Cursor}.
     * @param cursor Robot
     */
    public synchronized void addRobot(Robot robot) {
        robots.add(robot);
        robot.addDrawEventListener(this);
    }

    /**
     * Hide all cursors for paint.
     */
    public synchronized void hideCursors() {
//        robots.clear(); 
        for (Robot robot : robots) {
            robot.setVisible(false);
        }
    }

    public void removeRobot(Robot robot) {
        robot.removeDrawEventListener(this);
        robots.remove(robot);
    }

    public void removeRobot(String name) {
        robots.remove(findRobot(name));
    }

    public Robot findRobot(String name) {
        for (Robot robot : robots) {
            if (name.equals(robot.getName())) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Romove one {@link eu.easyedu.robotj.Robot} from painting cursors.
     * @param cursor Robot
     */
    public synchronized void removeCursor(Robot cursor) {
        robots.remove(cursor);
    }

    public boolean containsCursor(Robot cursor) {
        return robots.contains(cursor);
    }

    private void initComponents() {
        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
    }

    private void formComponentResized(java.awt.event.ComponentEvent evt) {
        //resizeBufferedImage(getSize());
        //log.debug("PaintCanvas resized to: " + image.getWidth() + " x " + image.getHeight());
    }

    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        int newImageHeight = size.height - 5;
        int newImageWidth = size.width - 5;
        BufferedImage backupImage = image;
        image = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gc = image.createGraphics();
        if (backupImage == null) {
            gc.fillRect(0, 0, newImageWidth, newImageHeight); // fill in background
        } else {
            clearScreen();
            gc.drawImage(backupImage, 0, 0, backupImage.getWidth(), backupImage.getHeight(), null); // fill in background
        }
        repaint();
        gc.dispose();
        log.info("Paint desktop resized to: " + image.getWidth() + " x " + image.getHeight());
    }

    public void setImageSize(Dimension size) {
        super.setSize(size.width + 5, size.height + 5);
        BufferedImage backupImage = image;
        image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gc = image.createGraphics();
        if (backupImage == null) {
            gc.fillRect(0, 0, size.width, size.height); // fill in background
        } else {
            clearScreen();
            gc.drawImage(backupImage, 0, 0, backupImage.getWidth(), backupImage.getHeight(), null); // fill in background
        }
        repaint();
        gc.dispose();
        log.info("Paint desktop resized to: " + image.getWidth() + " x " + image.getHeight());
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    public void resetFrameCounter() {
        frameCounter = 0;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
        Set<Robot> robotsNear = getRobotsNear(event.getPoint());
        Robot robot = robotsNear.size() > 0 ? robotsNear.iterator().next() : null;
        if (event.isPopupTrigger() && robot != null) {
            menu.show(event.getComponent(), event.getX(), event.getY());
        }
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mouseDragged(MouseEvent event) {
    }

    public void mouseMoved(MouseEvent event) {
    }

    public void drawEvent(DrawEvent event) {
        drawShape(event.getShape());
    }

    public Set<Robot> getRobotsNear(Point2D position) {
        Set<Robot> result = new HashSet<Robot>();
        for (Robot robot : robots) {
            if (robot.isNear(position)) {
                result.add(robot);
            }
        }
        return result;
    }

    public void robotPropertiesClicked(java.awt.event.ActionEvent evt, Point2D position) {
        Set<Robot> nr = getRobotsNear(position);
    }

    public void removeAllRobots() {
        robots.clear();
    }

    public Collection<Robot> getRobots() {
        return Collections.unmodifiableSet(robots);
    }
}
