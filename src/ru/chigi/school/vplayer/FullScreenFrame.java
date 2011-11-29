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
package ru.chigi.school.vplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Frame used for fullscreen playback
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
class FullScreenFrame extends JFrame {

    private VPlayer player;

    public FullScreenFrame(VPlayerState state, VPlayerCallback cb) {
        super("VPlayer - full screen mode");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                player.release();
            }
        });

        pack();
        setVisible(true);

        player = new VPlayer.Builder(state.getMediaSource()).fullscreen(false).callback(cb).build();
        player.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        initMap();

        add(player, BorderLayout.CENTER);
        pack();

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);

        player.setState(state);
    }

    private void initMap() {
        InputMap imap = player.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap amap = player.getActionMap();
        final JFrame self = this;

        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        amap.put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                self.dispatchEvent(new WindowEvent(self, WindowEvent.WINDOW_CLOSING));
                dispose();
            }
        });
    }
}