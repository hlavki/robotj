package eu.easyedu.robotj.ide;

import eu.easyedu.robotj.canvas.Canvas;
import eu.easyedu.robotj.canvas.PaintCanvas;
import eu.easyedu.robotj.event.RobotProjectAdapter;
import eu.easyedu.robotj.event.RobotProjectEvent;
import eu.easyedu.robotj.project.RobotProject;
import eu.easyedu.robotj.project.Stopable;
import java.awt.Dimension;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import org.netbeans.api.java.classpath.ClassPath;
import org.openide.ErrorManager;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
final class PaintDesktopTopComponent extends TopComponent {

    private static PaintDesktopTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "eu/easyedu/robotj/ide/new_dektop_icon.png";
    private static final String PREFERRED_ID = "PaintDesktopTopComponent";
    private static final long serialVersionUID = 4693116117827945369L;
    private PaintCanvas canvas;
    private DefaultComboBoxModel imageSizesModel;
    private DefaultListModel projectListModel;

    private PaintDesktopTopComponent() {
        initComponents();
        canvas = new PaintCanvas(getDefaultImageDimension());
        drawPanel.add(canvas);
        setName(NbBundle.getMessage(PaintDesktopTopComponent.class, "CTL_PaintDesktopTopComponent"));
        setToolTipText(NbBundle.getMessage(PaintDesktopTopComponent.class, "HINT_PaintDesktopTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
    }

    /**
     * Add project to ProjectList.
     * @param project Added project
     */
    public void addProject(RobotProject project) {
        project.addRobotProjectStartListener(new RobotProjectAdapter() {

            @Override
            public void projectStartEvent(RobotProjectEvent evt) {
                projectStartPerformed(evt);
            }
        });
        project.addRobotProjectStopListener(new RobotProjectAdapter() {

            @Override
            public void projectStopEvent(RobotProjectEvent evt) {
                projectStopPerformed(evt);
            }
        });
        getProjectListModel().addElement(project);
    }

    public void projectStartPerformed(RobotProjectEvent evt) {
        startButton.setEnabled(!evt.getRobotProject().isRunning());
        stopButton.setEnabled(evt.getRobotProject().isRunning() && (evt.getRobotProject() instanceof Stopable));
    }

    public void projectStopPerformed(RobotProjectEvent evt) {
//        RobotProject project = (RobotProject)projectList.getSelectedValue();
        RobotProject project = evt.getRobotProject();
//        RobotProject stoppedProject = evt.getRobotProject();
        String timeStr = ResourceBundle.getBundle("eu/easyedu/robotj/ide/Bundle").getString("projectPanelTimeLabel");
//        timeLabel.setText(timeStr + ": " + stoppedProject.getLastDuration() + "ms");
        startButton.setEnabled(!project.isRunning() && getProjectListModel().getSize() > 0);
        stopButton.setEnabled(project.isRunning() && (project instanceof Stopable));
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        drawPanel = new javax.swing.JPanel();
        projectPanel = new javax.swing.JPanel();
        projectListPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        navigatePanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        imageSizeLabel = new javax.swing.JLabel();
        imageSizeComboBox = new javax.swing.JComboBox();
        clearButton = new javax.swing.JButton();
        clearRobotsButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        mainPanel.setLayout(new java.awt.BorderLayout());

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(PaintDesktopTopComponent.class, "mainPanelTitle"))); // NOI18N
        org.jdesktop.layout.GroupLayout drawPanelLayout = new org.jdesktop.layout.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 220, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 226, Short.MAX_VALUE)
        );
        mainPanel.add(drawPanel, java.awt.BorderLayout.CENTER);

        projectPanel.setLayout(new java.awt.BorderLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("eu/easyedu/robotj/ide/Bundle"); // NOI18N
        projectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("projectPanelTitle"))); // NOI18N
        projectPanel.setPreferredSize(new java.awt.Dimension(170, 10));
        projectListPanel.setLayout(new java.awt.BorderLayout());

        projectList.setModel(getProjectListModel());
        projectList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectListMouseClicked(evt);
            }
        });
        projectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                projectListValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(projectList);

        projectListPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        projectPanel.add(projectListPanel, java.awt.BorderLayout.CENTER);

        navigatePanel.setPreferredSize(new java.awt.Dimension(100, 60));
        org.openide.awt.Mnemonics.setLocalizedText(startButton, bundle.getString("projectPanelStartButton")); // NOI18N
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        navigatePanel.add(startButton);

        org.openide.awt.Mnemonics.setLocalizedText(stopButton, bundle.getString("projectPanelStopButton")); // NOI18N
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        navigatePanel.add(stopButton);

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, bundle.getString("projectPanelDeleteButton")); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        navigatePanel.add(deleteButton);

        projectPanel.add(navigatePanel, java.awt.BorderLayout.SOUTH);

        mainPanel.add(projectPanel, java.awt.BorderLayout.EAST);

        add(mainPanel, java.awt.BorderLayout.CENTER);

        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonPanel.setPreferredSize(new java.awt.Dimension(10, 50));
        org.openide.awt.Mnemonics.setLocalizedText(imageSizeLabel, bundle.getString("CTL_PaintDesktopImageSizeLabel")); // NOI18N
        buttonPanel.add(imageSizeLabel);

        imageSizeComboBox.setModel(getImageSizeComboBoxModel());
        imageSizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageSizeComboBoxActionPerformed(evt);
            }
        });

        buttonPanel.add(imageSizeComboBox);

        org.openide.awt.Mnemonics.setLocalizedText(clearButton, bundle.getString("projectPanelClearButton")); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(clearButton);

        org.openide.awt.Mnemonics.setLocalizedText(clearRobotsButton, org.openide.util.NbBundle.getMessage(PaintDesktopTopComponent.class, "projectPanelRemoveRobotsButton")); // NOI18N
        clearRobotsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearRobotsButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(clearRobotsButton);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

    private void clearRobotsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearRobotsButtonActionPerformed
        canvas.hideCursors();
    }//GEN-LAST:event_clearRobotsButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        canvas.clearScreen();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int idx = projectList.getSelectedIndex();
        RobotProject delProject = (RobotProject) getProjectListModel().get(idx);
        if (delProject instanceof Stopable) {
            delProject.stop();
        }
        getProjectListModel().remove(idx);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        RobotProject project = (RobotProject) projectList.getSelectedValue();
        project.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        RobotProject project = (RobotProject) projectList.getSelectedValue();
        project.start();
    }//GEN-LAST:event_startButtonActionPerformed

    private void projectListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectListMouseClicked
        if (evt.getClickCount() == 2) {
            RobotProject project = (RobotProject) projectList.getSelectedValue();
            if (!project.isRunning()) {
                project.start();
            } else if (project instanceof Stopable) {
                project.stop();
            }
        }
    }//GEN-LAST:event_projectListMouseClicked

    private void projectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_projectListValueChanged
        RobotProject project = (RobotProject) projectList.getSelectedValue();
        if (project != null) {
            startButton.setEnabled(!project.isRunning());
            stopButton.setEnabled(project.isRunning() && (project instanceof Stopable));
        }
    }//GEN-LAST:event_projectListValueChanged

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        canvas.stop();
    }//GEN-LAST:event_formComponentHidden

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        canvas.start();
    }//GEN-LAST:event_formComponentShown

    private void imageSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageSizeComboBoxActionPerformed
        canvas.setImageSize((Dimension) imageSizeComboBox.getSelectedItem());
    }//GEN-LAST:event_imageSizeComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearRobotsButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel drawPanel;
    private javax.swing.JComboBox imageSizeComboBox;
    private javax.swing.JLabel imageSizeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel navigatePanel;
    private javax.swing.JList projectList;
    private javax.swing.JPanel projectListPanel;
    private javax.swing.JPanel projectPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized PaintDesktopTopComponent getDefault() {
        if (instance == null) {
            instance = new PaintDesktopTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the PaintDesktopTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized PaintDesktopTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            ErrorManager.getDefault().log(ErrorManager.WARNING, "Cannot find PaintDesktop component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof PaintDesktopTopComponent) {
            return (PaintDesktopTopComponent) win;
        }
        ErrorManager.getDefault().log(ErrorManager.WARNING, "There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper((ImageDimension) imageSizeComboBox.getSelectedItem());
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;
        private final ImageDimension imageSize;

        ResolvableHelper(ImageDimension imageSize) {
            this.imageSize = imageSize;
        }

        public Object readResolve() {
            PaintDesktopTopComponent win = PaintDesktopTopComponent.getDefault();
            System.out.println(imageSize);
            if (imageSize != null) {
                win.imageSizeComboBox.setSelectedItem(imageSize);
            }
            return win;
        }
    }

    private synchronized DefaultComboBoxModel getImageSizeComboBoxModel() {
        if (imageSizesModel == null) {
            imageSizesModel = new DefaultComboBoxModel();
            imageSizesModel.addElement(new ImageDimension(400, 300));
            imageSizesModel.addElement(new ImageDimension(640, 480));
            imageSizesModel.addElement(new ImageDimension(800, 600));
            imageSizesModel.addElement(new ImageDimension(1024, 768));
        }
        imageSizesModel.setSelectedItem(getDefaultImageDimension());
        return imageSizesModel;
    }

    private synchronized DefaultListModel getProjectListModel() {
        if (projectListModel == null) {
            projectListModel = new DefaultListModel();
        }
        return projectListModel;
    }

    public synchronized ImageDimension getDefaultImageDimension() {
        return new ImageDimension(400, 300);
    }

    public RobotProject registerProject(ClassPath classPath, String className) {
        RobotProject result = null;
        try {
            // Convert File to a URL
//            URL url = file.toURL();
            URL[] urls = new URL[classPath.entries().size()];
            Iterator<ClassPath.Entry> it = classPath.entries().iterator();
            int i = 0;
            while (it.hasNext()) {
                urls[i++] = it.next().getURL();
            }

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls, getClass().getClassLoader());

            Class<?> clazz = cl.loadClass(className);
            for (Constructor<?> constructor : clazz.getConstructors()) {
                System.out.println(constructor);
            }
            Constructor<?> constructor = clazz.getConstructor(Canvas.class);
            RobotProject project = (RobotProject) constructor.newInstance(canvas);
            addProject(project);
            projectList.setSelectedValue(project, true);
            result = project;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("returing: " + result);
        return result;
    }
//    public Dimension new ImageDimension(int width, int height) {
//        return new Dimension(width, height) {
//            public String toString() {
//                return width + "x" + height;
//            }
//        };
//    }
}
