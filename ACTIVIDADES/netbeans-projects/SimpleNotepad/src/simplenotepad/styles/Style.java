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

package simplenotepad.styles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public final class Style {
    
    public static void app(JFrame view) {
        view.setIconImage(
                new ImageIcon(view.getClass().getResource("/simplenotepad/assets/simple-notepad-icon.png")).getImage());
    }
    
    public static void button(JButton button) {
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusable(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.WHITE);
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBackground(Color.decode("#D5E1F2"));
                button.setForeground(Color.decode("#D5E1F2"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.decode("#A3BDE3"));
                button.setForeground(Color.decode("#A3BDE3"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#D5E1F2"));
                button.setForeground(Color.decode("#D5E1F2"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.WHITE);
            }
        };
        button.addMouseListener(mouseListener);
    }
    
    public static void menuBar(JMenuBar menuBar) {
        menuBar.setUI(new BasicMenuBarUI(){
            @Override
            public void paint(Graphics g, JComponent c ){
               g.setColor(Color.WHITE);
               g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#DDDDDD")));
    }
    
    public static void actionsPanel(JPanel actionsPanel) {
        actionsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#DDDDDD")));
    }
    
    public static void textPane(JTextPane textPane) {
        textPane.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);
        ((HTMLEditorKit) textPane.getEditorKit()).setDefaultCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    }

    

}
