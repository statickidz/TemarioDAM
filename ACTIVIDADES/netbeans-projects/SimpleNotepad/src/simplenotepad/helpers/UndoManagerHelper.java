/*
 * Copyright (C) 2015 StaticKidz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simplenotepad.helpers;

/**
 *
 * @author doubleshow
 * @github
 * https://github.com/doubleshow/sikuliguide/blob/master/src/main/java/org/sikuli/ui/UndoManagerHelper.java
 */
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoManagerHelper {

    public static Action getUndoAction(UndoManager manager, String label) {
        return new UndoAction(manager, label);
    }

    public static Action getUndoAction(UndoManager manager) {
        return new UndoAction(manager, "Undo");
    }

    public static Action getRedoAction(UndoManager manager, String label) {
        return new RedoAction(manager, label);
    }

    public static Action getRedoAction(UndoManager manager) {
        return new RedoAction(manager, "Redo");
    }

    private abstract static class UndoRedoAction extends AbstractAction {

        UndoManager undoManager = new UndoManager();

        String errorMessage = "Cannot undo";

        String errorTitle = "Undo Problem";

        protected UndoRedoAction(UndoManager manager, String name) {
            super(name);
            undoManager = manager;
        }

        public void setErrorMessage(String newValue) {
            errorMessage = newValue;
        }

        public void setErrorTitle(String newValue) {
            errorTitle = newValue;
        }

        protected void showMessage(Object source) {
            /*if (source instanceof Component) {
                JOptionPane.showMessageDialog((Component) source, errorMessage,
                        errorTitle, JOptionPane.WARNING_MESSAGE);
            } else {
                System.err.println(errorMessage);
            }*/
        }
    }

    public static class UndoAction extends UndoRedoAction {

        public UndoAction(UndoManager manager, String name) {
            super(manager, name);
            setErrorMessage("Cannot undo");
            setErrorTitle("Undo Problem");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.undo();
            } catch (CannotUndoException cannotUndoException) {
                showMessage(actionEvent.getSource());
            }
            //update();
        }
        
        protected void update() {
            if(undoManager.canUndo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    public static class RedoAction extends UndoRedoAction {

        String errorMessage = "Cannot redo";

        String errorTitle = "Redo Problem";

        public RedoAction(UndoManager manager, String name) {
            super(manager, name);
            setErrorMessage("Cannot redo");
            setErrorTitle("Redo Problem");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.redo();
            } catch (CannotRedoException cannotRedoException) {
                showMessage(actionEvent.getSource());
            }
            //update();
        }
        
        protected void update() {
            if(undoManager.canRedo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }
}
