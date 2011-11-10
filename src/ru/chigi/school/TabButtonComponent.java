/**
 chgs - A multimedia platform for 4igi guitar school (http://school.4igi.ru)
 Copyright (C) 2011 Max E. Kuznecov <mek@mek.uz.ua>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.chigi.school;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Provide a tab with closing button
 * Based on http://download.oracle.com/javase/tutorial/uiswing/examples/components/TabComponentsDemoProject/src/components/ButtonTabComponent.java
 *
 */
public class TabButtonComponent extends JPanel {
    private final Workspace workspace;
    private final RoomInterface room;

    public TabButtonComponent(Workspace ws, RoomInterface r) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.workspace = ws;
        this.room = r;

        setOpaque(false);

        JLabel icon = new JLabel(room.getRoomIcon16());
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(icon);

        JLabel label = new JLabel(room.getRoomName());
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(label);

        JButton button = new TabButton();
        add(button);

        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;

            setPreferredSize(new Dimension(size, size));
            setToolTipText("Close");

            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());

            //Make it transparent
            setContentAreaFilled(false);

            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);

            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);

            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = workspace.indexOfTabComponent(TabButtonComponent.this);

            if (i != -1) {
                ((RoomInterface) workspace.getComponentAt(i)).closeRoom();

                workspace.remove(i);
            }
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            //shift the image for pressed buttons
            if (getModel().isPressed())
                g2.translate(1, 1);

            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);

            if (getModel().isRollover())
                g2.setColor(Color.BLUE);

            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();

            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();

            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
