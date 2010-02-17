/*
 * RobotProjectAdapter.java
 *
 * Created on Utorok, 2006, január 24, 9:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

/**
 *
 * @author hlavki
 */
public abstract class RobotProjectAdapter implements RobotProjectEventListener {
    
    public void projectStartEvent(RobotProjectEvent robotProjectEvent) {}

    public void projectStopEvent(RobotProjectEvent robotProjectEvent) {}
    
}
