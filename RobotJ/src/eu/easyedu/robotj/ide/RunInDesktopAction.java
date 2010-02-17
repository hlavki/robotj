package eu.easyedu.robotj.ide;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePathScanner;
import eu.easyedu.robotj.project.RobotProject;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.support.ant.GeneratedFilesHelper;
import org.openide.awt.DynamicMenuContent;
import org.openide.awt.Mnemonics;
import org.openide.execution.ExecutorTask;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 *
 * @author hlavki
 */
public final class RunInDesktopAction extends AbstractAction implements ContextAwareAction, LookupListener {

    private static final long serialVersionUID = -5112242366043264557L;

//    private Lookup ctx;
//    private Lookup.Result result;
    public String getLabelFor(DataObject obj) {
        return NbBundle.getMessage(RunInDesktopAction.class, "CTL_RunInDesktop",
                new Object[]{obj != null ? obj.getName() : ""});
    }

    /**
     * Returns icon resource.
     * @return icon resource
     */
    protected String iconResource() {
        return "eu/easyedu/robotj/ide/run-in-desktop.png";
    }

    /**
     * Returns help context.
     * @return help context
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    /**
     * 
     * @return
     */
    protected boolean asynchronous() {
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        assert false : "Action should never be called without a context";
    }

    /**
     *
     * @param lookup
     * @return
     */
    public Action createContextAwareInstance(Lookup lookup) {
        return new ContextAction(lookup);
    }

    /**
     *
     * @param lookupEvent
     */
    public void resultChanged(LookupEvent lookupEvent) {
    }

    private boolean enable(DataObject obj) {
        assert obj != null;
        boolean result = false;

        JavaSource js = JavaSource.forFileObject(obj.getPrimaryFile());
        MyCancelableTask task = new MyCancelableTask();

        try {
            js.runUserActionTask(task, true);
            result = task.getElement().getSuperclass().toString().equals(RobotProject.class.getName());
        } catch (IOException e) {
            Logger.getLogger("global").log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    private void perform(DataObject obj) {
        assert obj != null;
//        PaintDesktopTopComponent desktop = PaintDesktopTopComponent.getDefault();
        FileObject sourceFile = obj.getPrimaryFile();
        if (compile(sourceFile)) {
            ClassPath classPath = ClassPath.getClassPath(sourceFile, ClassPath.EXECUTE);
            PaintDesktopTopComponent win = PaintDesktopTopComponent.findInstance();
            JavaSource js = JavaSource.forFileObject(obj.getPrimaryFile());
            MyCancelableTask task = new MyCancelableTask();
            try {
                js.runUserActionTask(task, true);
            } catch (IOException e) {
                Logger.getLogger("global").log(Level.SEVERE, e.getMessage(), e);
            }

            String className = task.getElement().getQualifiedName().toString();
            RobotProject project = win.registerProject(classPath, className);
            win.open();
            win.requestActive();
            project.start();
        }
    }

    private final class ContextAction extends AbstractAction implements Presenter.Popup {

        private static final long serialVersionUID = -324677085101802575L;
        private final DataObject dataObject;

        public ContextAction(Lookup context) {
            DataObject obj = context.lookup(DataObject.class);
            dataObject = (obj != null && enable(obj)) ? obj : null;
        }

        public void actionPerformed(ActionEvent e) {
            perform(dataObject);
        }

        public JMenuItem getPopupPresenter() {
            class Presenter extends JMenuItem implements DynamicMenuContent {

                private static final long serialVersionUID = 4661292887354073469L;

                public Presenter() {
                    super(ContextAction.this);
                }

                public JComponent[] getMenuPresenters() {
                    if (dataObject != null) {
                        Mnemonics.setLocalizedText(this, getLabelFor(dataObject));
                        return new JComponent[]{this};
                    } else {
                        return new JComponent[0];
                    }
                }

                public JComponent[] synchMenuPresenters(JComponent[] items) {
                    return getMenuPresenters();
                }
            }
            return new Presenter();
        }
    }

    /**
     *
     * @param file
     * @return
     */
    public boolean compile(FileObject file) {
        boolean result = false;
        try {
            Project project = FileOwnerQuery.getOwner(file);
            Properties p = new Properties();
            FileObject[] files = new FileObject[]{file};
            SourceGroup srcGroup = getSourceGroup(project, file);
            FileObject sourceDir = (srcGroup != null) ? srcGroup.getRootFolder() : null;
            p.setProperty("javac.includes", ActionUtils.antIncludesList(files, sourceDir));
            FileObject buildXml = findBuildXml(project);
            ExecutorTask task = ActionUtils.runTarget(buildXml, new String[]{"compile-single"}, p);
            result = task.result() == 0;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private FileObject findBuildXml(Project project) {
        return project.getProjectDirectory().getFileObject(GeneratedFilesHelper.BUILD_XML_PATH);
    }

    private SourceGroup getSourceGroup(Project project, FileObject file) {
        Sources src = ProjectUtils.getSources(project);
        SourceGroup[] groups = src.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        for (int i = 0; i < groups.length; i++) {
            if (groups[i].getRootFolder().equals(file) || FileUtil.isParentOf(groups[i].getRootFolder(), file)) {
                return groups[i];
            }
        }
        return null;
    }

    private static class SuperClassVisitor extends TreePathScanner<TypeElement, Void> {

        private CompilationInfo info;

        public SuperClassVisitor(CompilationInfo info) {
            this.info = info;
        }

        @Override
        public TypeElement visitClass(ClassTree t, Void v) {
            Element el = info.getTrees().getElement(getCurrentPath());
            if (el == null) {
                System.err.println("Cannot resolve class!");
            } else {
                return (TypeElement) el;
            }
            return null;
        }
    }

    private static class MyCancelableTask implements CancellableTask<CompilationController> {

        TypeElement element;

        public void cancel() {
        }

        public void run(CompilationController parameter) throws IOException {
            parameter.toPhase(Phase.ELEMENTS_RESOLVED);
            element = new SuperClassVisitor(parameter).scan(parameter.getCompilationUnit(), null);
        }

        public TypeElement getElement() {
            return element;
        }
    }
}

