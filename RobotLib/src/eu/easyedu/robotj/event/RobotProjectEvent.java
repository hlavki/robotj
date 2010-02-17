/*
 * ProjectEvent.java
 *
 * Created on Utorok, 2006, január 24, 9:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.event;

import eu.easyedu.robotj.project.RobotProject;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class RobotProjectEvent extends EventObject {
    
    /** Creates a new instance of ProjectEvent */
    public RobotProjectEvent(RobotProject source) {
        super(source);
    }

    public RobotProject getRobotProject() {
        return (RobotProject)source;
    }
}
