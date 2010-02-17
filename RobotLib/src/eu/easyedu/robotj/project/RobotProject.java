/*
 * RobotProject.java
 *
 * Created on Sobota, 2005, november 19, 10:05
 *
 */

package eu.easyedu.robotj.project;

import eu.easyedu.robotj.utils.ExecuteTimer;
import eu.easyedu.robotj.event.RobotProjectEvent;
import eu.easyedu.robotj.event.RobotProjectEventListener;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This is base class for projects that use robots.
 * @author hlavki
 */
public abstract class RobotProject implements Runnable {
    
    /**
     * Name of project
     */
    protected String name;
    
    private Thread thread;
    private ExecuteTimer timer;
    protected static final Logger logger = Logger.getLogger(RobotProject.class.getName());
    private boolean isRunning = false;
    
    private List<RobotProjectEventListener> startListeners;
    private List<RobotProjectEventListener> stopListeners;
    
    /**
     * Creates a new instance of RobotProject
     * @param canvas canvas
     * @param name name of project
     */
    public RobotProject(String name) {
        this.name = name;
        timer = new ExecuteTimer();
        startListeners = new ArrayList<RobotProjectEventListener>();
        stopListeners = new ArrayList<RobotProjectEventListener>();
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for
     * the specified number of milliseconds.
     * @param millis the length of time to sleep in milliseconds.
     */
    protected void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch ( InterruptedException e ) {
            System.out.println( "awakened prematurely" );
        }
    }
    
    /**
     * Causes this project to begin execution
     */
    public final void start() {
        logger.info("Starting Robot Project: " + this);
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    
    /**
     * If this thread was constructed using a separate Runnable run object, then that
     * Runnable object's run method is called; otherwise, this method does nothing and returns.
     */
    public final void run() {
        isRunning = true;
        fireRobotProjectStart();
        timer.start();
//        canvas.resetFrameCounter();
        runProject();
//        int fps = canvas.getFrameCounter();
        timer.stop();
        logger.info("Time: " + timer);
//        if (timer.toValue() > 1000) {
//            logger.fine("FPS: " + fps/(timer.toValue()/1000));
//        }
        isRunning = false;
        logger.info("Stopping Robot Project: " + this);
        fireRobotProjectStop();
        //canvas.clearCursors();
    }
    
    /**
     * Causes this project to end execution. Only Stopable projects can be stopped.
     */
    public final synchronized void stop() {
        isRunning = false;
        thread = null;
    }
    
    /**
     * Returns if project is running or not.
     * @return true if project is running.
     */
    public final boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Returns name of project.
     * @return name of project.
     */
    public String toString() {
        return name;
    }
    
    /**
     * Here students write own code. This method is called through {@link #start()}.
     */
    protected abstract void runProject();
    
    /**
     * returns execution time of last running project in milliseconds.
     * @return time in millis.
     */
    public long getLastDuration() {
        return timer.toValue();
    }
    
    /**
     * returns project name
     * @return project name
     */
    public String getName() {
        return name;
    }
    
    private void fireRobotProjectStart() {
        for (RobotProjectEventListener eventListener: startListeners) {
            eventListener.projectStartEvent(new RobotProjectEvent(this));
        }
    }
    
    private void fireRobotProjectStop() {
        for (RobotProjectEventListener eventListener: stopListeners) {
            eventListener.projectStopEvent(new RobotProjectEvent(this));
        }
    }
    
    public void addRobotProjectStartListener( RobotProjectEventListener eventListener ) {
        startListeners.add( eventListener );
    }
    
    public void addRobotProjectStopListener( RobotProjectEventListener eventListener ) {
        stopListeners.add( eventListener );
    }
}
