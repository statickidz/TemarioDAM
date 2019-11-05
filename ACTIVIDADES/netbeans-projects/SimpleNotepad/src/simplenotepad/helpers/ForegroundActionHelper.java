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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class ForegroundActionHelper extends StyledEditorKit.StyledTextAction {

  JColorChooser colorChooser = new JColorChooser();

  JDialog dialog = new JDialog();

  boolean noChange = false;

  boolean cancelled = false;

  public ForegroundActionHelper() {
    super("foreground");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JTextPane editor = (JTextPane) getEditor(e);

    if (editor == null) {
      JOptionPane.showMessageDialog(null,
          "You need to select the editor pane before you can change the color.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    int p0 = editor.getSelectionStart();
    StyledDocument doc = getStyledDocument(editor);
    Element paragraph = doc.getCharacterElement(p0);
    AttributeSet as = paragraph.getAttributes();
    fg = StyleConstants.getForeground(as);
    if (fg == null) {
      fg = Color.BLACK;
    }
    colorChooser.setColor(fg);

    JButton accept = new JButton("OK");
    accept.addActionListener((ActionEvent ae) -> {
        fg = colorChooser.getColor();
        dialog.dispose();
    });

    JButton cancel = new JButton("Cancel");
    cancel.addActionListener((ActionEvent ae) -> {
        cancelled = true;
        dialog.dispose();
    });

    JButton none = new JButton("None");
    none.addActionListener((ActionEvent ae) -> {
        noChange = true;
        dialog.dispose();
    });

    JPanel buttons = new JPanel();
    buttons.add(accept);
    buttons.add(none);
    buttons.add(cancel);

    dialog.getContentPane().setLayout(new BorderLayout());
    dialog.getContentPane().add(colorChooser, BorderLayout.CENTER);
    dialog.getContentPane().add(buttons, BorderLayout.SOUTH);
    dialog.setModal(true);
    dialog.pack();
    dialog.setVisible(true);

    if (!cancelled) {

      MutableAttributeSet attr = null;
      if (editor != null) {
        if (fg != null && !noChange) {
          attr = new SimpleAttributeSet();
          StyleConstants.setForeground(attr, fg);
          setCharacterAttributes(editor, attr, false);
        }
      }
    }// end if color != null
    noChange = false;
    cancelled = false;
  }

  private Color fg;
}
