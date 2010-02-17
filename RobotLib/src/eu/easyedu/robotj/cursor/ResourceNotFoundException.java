/*
 * ResourceNotFoundException.java
 *
 * Created on Streda, 2006, december 13, 16:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.easyedu.robotj.cursor;

/**
 *
 * @author hlavki
 */
public class ResourceNotFoundException extends Exception {
    
    public ResourceNotFoundException() {
	super();
    }

    public ResourceNotFoundException(String message) {
	super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
    
}
