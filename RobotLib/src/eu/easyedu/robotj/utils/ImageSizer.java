/*
 * ImageSizer.java
 *
 * Created on Pondelok, 2005, november 21, 15:20
 *
 */

package eu.easyedu.robotj.utils;

/**
 *
 * @author hlavki
 */
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

@Deprecated
public class ImageSizer {
    private Dimension size = new Dimension(-1, -1);
    private boolean incomplete;
    
    private ImageObserver obs = new ImageObserver() {
        public synchronized boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            if ((infoflags & WIDTH) != 0)
                size.width = width;
            if ((infoflags & HEIGHT) != 0)
                size.height = height;
            if ((infoflags & (ERROR | ABORT)) != 0)
                incomplete = true;
            boolean done = resultKnown();
            if (done)
                notifyAll();
            return !done;
        }
    };
    
    public ImageSizer(Image image) {
        size.width = image.getWidth(obs);
        size.height = image.getHeight(obs);
    }
    
    private boolean resultKnown() {
        return size.width != -1 && size.height != -1 || incomplete;
    }
    
    //returns null iff error or abort
    public Dimension getImageSize() throws InterruptedException {
        synchronized (obs) {
            while (!resultKnown())
                obs.wait();
            return incomplete ? null : new Dimension(size);
        }
    }

}

