package eu.easyedu.robotj.ide;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows PaintDesktop component.
 */
public class PaintDesktopAction extends AbstractAction {

    private static final long serialVersionUID = -3049314311116046253L;

    public PaintDesktopAction() {
        super(NbBundle.getMessage(PaintDesktopAction.class, "CTL_PaintDesktopAction"));
        putValue(SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(PaintDesktopTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = PaintDesktopTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
