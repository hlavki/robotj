/*
 * RobotAdapter.java
 *
 * Created on Utorok, 2006, január 24, 8:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

/**
 *
 * @author hlavki
 */
public abstract class RobotAdapter implements RobotEventListener {
    
    public void robotMovesEvent(RobotEvent robotEvent) {}

    public void robotchangesColorEvent(RobotEvent robotEvent) {}

    public void robotchangesHeadingEvent(RobotEvent robotEvent) {}
    
}
