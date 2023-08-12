package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.gui.io.OpenImageFileDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;

public class ActionMap {
    public static final String UNDO = "Undo";
    public static final String REDO = "Redo";
    public static final String SAVE = "Save";
    public static final String SAVEAS = "Save as";
    public static final String OPEN = "Open";

    private final MainFrame frame;

    private static class ActionAdapter extends AbstractAction {
        private final Runnable runnable;

        private ActionAdapter(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.runnable.run();
        }
    }

    public ActionMap(final MainFrame frame) {
        this.frame = frame;

        registerAction(OPEN, () -> {
            final OpenImageFileDialog od = new OpenImageFileDialog(frame, null);
            final File choosenFile = od.getFile();
            if(choosenFile != null) {
                final WorkspaceTab t = frame.createWorkspace(choosenFile);
                t.getWorkspace().setWorkingFile(choosenFile);
            }
        });
        registerAction(UNDO, () -> frame.getCurrentWorkspaceTab().getWorkspace().undo());
        registerAction(REDO, () -> frame.getCurrentWorkspaceTab().getWorkspace().redo());
        registerAction(SAVE, () -> frame.getSaver().trySave(frame.getCurrentWorkspaceTab().getWorkspace()));
        registerAction(SAVEAS, () -> frame.getSaver().trySaveAs(frame.getCurrentWorkspaceTab().getWorkspace()));
    }

    public void registerAction(final String id, final Runnable action) {
        if (frame.getRootPane().getActionMap().get(id) != null) {
            throw new IllegalArgumentException("Given id \"" + id + "\" already exists!");
        }

        frame.getRootPane().getActionMap().put(id, new ActionAdapter(action));
    }

    public boolean runAction(final String id) {
        final Action r = frame.getRootPane().getActionMap().get(id);
        if (r == null) return false;
        r.accept(null);
        return true;
    }
}
