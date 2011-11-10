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

import ru.chigi.school.AbstractRoom;
import ru.chigi.school.vplayer.VPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Hall room widget
 */

public final class HallRoom extends AbstractRoom {
    private javax.swing.JTextPane WelcomeTextPane;
    private ru.chigi.school.vplayer.VPlayer player;

    public HallRoom() {
        initComponents();
        URL welcomeText = getClass().getResource("/ru/chigi/school/hall/resources/welcome_ru.html");

        try {
            WelcomeTextPane.setPage(welcomeText);
        } catch (IOException ex) {}
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Internal panel
        JPanel ip = new JPanel();
        ip.setLayout(new BoxLayout(ip, BoxLayout.Y_AXIS));

        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/logo.png")));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ip.add(logo);
        ip.add(Box.createRigidArea(new Dimension(0, 20)));

        // Player
        Dimension playerDim = new Dimension(450, 354);
        player = new VPlayer.Builder("/tmp/test.avi").build();
        player.setPreferredSize(playerDim);
        player.setMaximumSize(playerDim);
        player.setMinimumSize(playerDim);
        player.setAlignmentX(Component.CENTER_ALIGNMENT);
        ip.add(player);
        ip.add(Box.createRigidArea(new Dimension(0, 10)));

        // Text
        WelcomeTextPane = new javax.swing.JTextPane();
        WelcomeTextPane.setBackground(java.awt.SystemColor.control);
        WelcomeTextPane.setEditable(false);
        WelcomeTextPane.setFont(new java.awt.Font("Verdana", 0, 12));
        WelcomeTextPane.setFocusable(false);
        WelcomeTextPane.setMinimumSize(new java.awt.Dimension(6, 300));
        WelcomeTextPane.setPreferredSize(new java.awt.Dimension(6, 300));
        WelcomeTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        ip.add(WelcomeTextPane);

        ScrollPane scroll = new ScrollPane();
        scroll.add(ip);

        add(scroll, BorderLayout.CENTER);
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
    public void closeRoom() {
        player.release();
    }
}
