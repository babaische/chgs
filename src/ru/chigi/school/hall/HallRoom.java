/*
 * Copyright (C) 2011 Max E. Kuznecov <mek@mek.uz.ua>
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
package ru.chigi.school.hall;

import ru.chigi.school.RoomInterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Top component which displays something.
 */

public final class HallRoom extends JPanel implements RoomInterface {
    private javax.swing.JScrollPane WelcomeScroll;
    private javax.swing.JTextPane WelcomeTextPane;
    //private ru.chigi.school.vplayer.VPlayer player;
    private JPanel player;

    public HallRoom() {
        initComponents();
        URL welcomeText = getClass().getResource("/ru/chigi/school/hall/resources/welcome_ru.html");

        try {
            WelcomeTextPane.setPage(welcomeText);
        } catch (IOException ex) {
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        WelcomeScroll = new javax.swing.JScrollPane();
        WelcomeTextPane = new javax.swing.JTextPane();
        JLabel logo = new JLabel();
        //player = new VPlayer.Builder("/tmp/test.avi").build();
        player = new JPanel();

        setBackground(java.awt.Color.white);
        setLayout(new java.awt.GridBagLayout());

        // Logo
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        logo.setIcon(new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/logo.png")));
        add(logo, gridBagConstraints);

        // Player
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        player.setPreferredSize(new Dimension(384, 288));
        add(player, gridBagConstraints);

        // Text
        WelcomeScroll.setFont(new java.awt.Font("Verdana", 0, 12));
        WelcomeTextPane.setBackground(java.awt.SystemColor.control);
        WelcomeTextPane.setEditable(false);
        WelcomeTextPane.setFont(new java.awt.Font("Verdana", 0, 12));
        WelcomeTextPane.setFocusable(false);
        WelcomeTextPane.setMinimumSize(new java.awt.Dimension(6, 300));
        WelcomeTextPane.setPreferredSize(new java.awt.Dimension(6, 300));
        WelcomeScroll.setViewportView(WelcomeTextPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(WelcomeScroll, gridBagConstraints);
    }

    @Override
    public String getRoomName() {
        return "Hall";
    }

    @Override
    public String getRoomDescription() {
        return "Hall room";
    }

    @Override
    public ImageIcon getRoomIcon16() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/hall16.png"));
    }

    @Override
    public ImageIcon getRoomIcon32() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/hall32.png"));
    }

    @Override
    public int getRoomPriority() {
        return 0;
    }

    @Override
    public int compareTo(RoomInterface other) {
        return this.getRoomPriority() - other.getRoomPriority();
    }
}
