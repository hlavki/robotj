/*
 * Canvas.java
 * 
 * Created on 8.7.2007, 14:44:21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.easyedu.robotj.canvas;

import eu.easyedu.robotj.Robot;
import eu.easyedu.robotj.drawable.Drawable;
import java.awt.Color;
import java.util.Collection;

/**
 *
 * @author hlavki
 */
public interface Canvas {
    
    public void drawShape(Drawable shape);

    public int getWidth();
    
    public int getHeight();
    
    public void addRobot(Robot r);
    
    public void removeRobot(Robot r);
    
    public void removeRobot(String name);
    
    public Robot findRobot(String name);
    
    public void clearScreen();

    public void clearScreen(Color color);
    
    public void removeAllRobots();
    
    public Collection<Robot> getRobots();
}
