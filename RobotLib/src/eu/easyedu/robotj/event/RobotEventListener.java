/*
 * RobotEventListener.java
 *
 * Created on Pondelok, 2006, január 23, 16:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

import java.util.EventListener;

/**
 *
 * @author hlavki
 */
public interface RobotEventListener extends EventListener {

    public void robotMovesEvent( RobotEvent robotEvent );
    
    public void robotchangesColorEvent( RobotEvent robotEvent );
    
    public void robotchangesHeadingEvent( RobotEvent robotEvent );
    
}
