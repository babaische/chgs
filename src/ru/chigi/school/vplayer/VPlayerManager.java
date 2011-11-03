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

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;

/**
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public class VPlayerManager {

    private volatile boolean locked = false;
    private static VPlayerManager instance = null;
    private final MediaPlayerFactory factory;

    /**
     * Request new player instance
     * @param parentFrame Parent frame for fullscreen mode or null
     * @return
     */
    public EmbeddedMediaPlayer request(JFrame parentFrame) {
        return factory.newMediaPlayer(parentFrame != null ? new DefaultFullScreenStrategy(parentFrame) : null);
    }

    /**
     * Append player into a pool of free players
     *
     * @param player
     */
    public synchronized void release(EmbeddedMediaPlayer player) {
        player.release();
    }

    /**
     * Synchronized play
     * @param player
     */
    public synchronized void play(EmbeddedMediaPlayer player) {
        dolock();
        player.addMediaPlayerEventListener(getPlayCB());
        player.play();
    }

    /**
     * Synchronized pause
     * @param player
     */
    public synchronized void pause(EmbeddedMediaPlayer player) {
        dolock();
        player.addMediaPlayerEventListener(getPauseCB());
        player.setPause(true);
    }

    /**
     * Synchronized stop
     * @param player
     */
    public synchronized void stop(EmbeddedMediaPlayer player) {
        dolock();
        player.addMediaPlayerEventListener(getStopCB());
        player.stop();
    }

    private VPlayerManager() {
        factory = new MediaPlayerFactory(new String[]{
                    "--no-video-title-show",
                    "--no-snapshot-preview"
                });
    }

    public static VPlayerManager getDefault() {
        if (instance == null) {
            instance = new VPlayerManager();
        }

        return instance;
    }

    private synchronized void dolock() {
        while (locked) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        locked = true;
    }

    private synchronized void dounlock() {
        if (!locked) {
            return;
        }

        locked = false;
        notifyAll();
    }

    private synchronized MediaPlayerEventAdapter getPlayCB() {
        return new MediaPlayerEventAdapter() {

            @Override
            public void playing(MediaPlayer mp) {
                dounlock();
                mp.removeMediaPlayerEventListener(this);
            }
        };
    }

    private synchronized MediaPlayerEventAdapter getPauseCB() {
        return new MediaPlayerEventAdapter() {

            @Override
            public void paused(MediaPlayer mp) {
                dounlock();
                mp.removeMediaPlayerEventListener(this);
            }
        };
    }

    private synchronized MediaPlayerEventAdapter getStopCB() {
        return new MediaPlayerEventAdapter() {

            @Override
            public void stopped(MediaPlayer mp) {
                dounlock();
                mp.removeMediaPlayerEventListener(this);
            }
        };
    }
}
