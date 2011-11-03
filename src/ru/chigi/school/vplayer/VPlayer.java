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

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.binding.LibX11;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public class VPlayer extends JPanel implements Serializable {
    private final static int x11threads = LibX11.INSTANCE.XInitThreads();
    private Canvas canvas;
    private RemotePlayer mediaPlayer;
    private JToolBar toolbar;
    private JSlider progressSlider;
    private JSlider volumeSlider;
    private JButton playButton;
    private JButton stopButton;
    private JButton fullScreenButton;
    private JButton muteButton;
    private JLabel progressLabel;
    private final Icon iconMuted = new ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/muted.png"));
    private final Icon iconNotMuted = new ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/not_muted.png"));
    private final Icon iconPlay = new ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/play.png"));
    private final Icon iconPause = new ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/pause.png"));
    private JFrame parentFrame;
    private FullScreenFrame fsFrame;
    private boolean allowFullscreen;
    private final String PLAY_TXT = "Play (SPACE)";
    private final String PAUSE_TXT = "Pause (SPACE)";
    private VPlayerCallback callback;
    private String mediaSource = null;
    private MediaPlayerEventAdapter mediaPlayerEvents;
    private boolean playerInited = false;

    public static class Builder {
        private String mediaSource = null;
        private JFrame parentFrame = null;
        private boolean allowFullscreen = true;
        private VPlayerCallback callback = null;

        public Builder(String src) {
            mediaSource = src;
        }

        public Builder parent(JFrame parent) {
            parentFrame = parent;
            return this;
        }

        public Builder fullscreen(boolean val) {
            allowFullscreen = val;
            return this;
        }

        public Builder callback(VPlayerCallback cb) {
            callback = cb;
            return this;
        }

        public VPlayer build() {
            return new VPlayer(this);
        }
    }

    public VPlayer(Builder builder) {
        super();

        mediaSource = builder.mediaSource;
        parentFrame = builder.parentFrame;
        allowFullscreen = builder.allowFullscreen;
        callback = builder.callback;
        canvas = RuntimeUtil.isWindows() ? new WindowsCanvas() : new Canvas();
        canvas.setBackground(Color.black);

        initToolbar();
        initInputMap();
        initProgressSlider();

        setLayoutOptions();
    }

    public void togglePlay() {
        if (mediaPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void setFullScreen(boolean fs) {
        mediaPlayer.setFullScreen(fs);
    }

    public String getMediaSource() {
        return mediaSource;
    }

    /**
     * Release player resources
     */
    public void release() {
        if (callback != null) {
            callback.released(this);
        }

        if (mediaPlayer != null) {
            mediaPlayer.close();
            mediaPlayer = null;
        }
    }

    /**
     * Return player state
     * @return
     */
    public VPlayerState getState() {
        VPlayerState state = new VPlayerState();

        state.setMediaSource(mediaSource);
        state.setPlaying(mediaPlayer.isPlaying());
        state.setTime(mediaPlayer.getTime());
        state.setVolume(mediaPlayer.getVolume());

        return state;
    }

    /**
     * Restore player state
     * @param state
     */
    public void setState(VPlayerState state) {
        MediaPlayerEventAdapter cb = new MediaPlayerEventAdapter() {

            @Override
            public void playing(MediaPlayer mp) {
                mp.removeMediaPlayerEventListener(this);
                pause();
            }
        };

        if (!state.isPlaying()) {
            //mediaPlayer.addMediaPlayerEventListener(cb);
        }

        play();

        mediaPlayer.setTime(state.getTime());
        mediaPlayer.setVolume(state.getVolume());
    }

    /******************************************************************/
    /**
     * Init toolbar and all its components
     */
    private void initToolbar() {
        final VPlayer current = this;

        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(true);
        toolbar.setAlignmentX(Component.LEFT_ALIGNMENT);

        playButton = new JButton();
        playButton.setIcon(iconPlay);
        playButton.setToolTipText(PLAY_TXT);
        playButton.setFocusable(false);
        playButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!playerInited) {
                    initPlayer();
                    playerInited = true;
                }

                togglePlay();
            }
        });

        stopButton = new JButton();
        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/stop.png"))); // NOI18N
        stopButton.setToolTipText("Stop");
        stopButton.setFocusable(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop();
            }
        });

        // Toggle fullscreen button
        fullScreenButton = new JButton();
        fullScreenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/chigi/school/vplayer/resources/fullscreen.png"))); // NOI18N
        fullScreenButton.setToolTipText("Toggle fullscreen mode");
        fullScreenButton.setFocusable(false);
        fullScreenButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VPlayerCallback cb = new VPlayerCallback() {

                    @Override
                    public void released(VPlayer vp) {
                    }

                    @Override
                    public void playing(VPlayer vp) {
                    }
                };

                VPlayerState state = getState();

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                fsFrame = new FullScreenFrame(state);
            }
        });
        fullScreenButton.setEnabled(allowFullscreen);

        // Toggle mute button
        muteButton = new JButton();
        muteButton.setIcon(iconNotMuted);
        muteButton.setToolTipText("Toggle mute");
        muteButton.setFocusable(false);
        muteButton.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediaPlayer.setMute(!mediaPlayer.getMute());
                muteButton.setIcon(mediaPlayer.getMute() ? iconMuted : iconNotMuted);
            }
        });

        volumeSlider = new JSlider();
        volumeSlider.setPreferredSize(new Dimension(10, 10));
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
        volumeSlider.setToolTipText("Adjust volume");
        volumeSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider src = (JSlider) e.getSource();

                if (!src.getValueIsAdjusting()) {
                    mediaPlayer.setVolume(src.getValue());
                }
            }
        });

        progressLabel = new JLabel("00:00:00");

        toolbar.add(playButton);
        toolbar.add(stopButton);
        toolbar.add(fullScreenButton);
        toolbar.addSeparator();
        toolbar.add(progressLabel);

        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(volumeSlider);
        toolbar.add(muteButton);
    }

    /**
     * Prepare media player instance
     */
    private void initPlayer() {
        final VPlayer current = this;

        mediaPlayerEvents = new MediaPlayerEventAdapter() {

            @Override
            public void playing(MediaPlayer mp) {
                volumeSlider.setValue(mediaPlayer.getVolume());
                playButton.setIcon(iconPause);
                playButton.setToolTipText(PAUSE_TXT);

                if (callback != null) {
                    callback.playing(current);
                }
            }

            @Override
            public void paused(MediaPlayer mp) {
                playButton.setIcon(iconPlay);
                playButton.setToolTipText(PLAY_TXT);
            }

            @Override
            public void stopped(MediaPlayer mp) {
                paused(mp);
            }

            @Override
            public void lengthChanged(MediaPlayer mp, long l) {
                String txt = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(l),
                        TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                progressLabel.setText(txt);
            }
        };

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        mediaPlayer = RemotePlayerFactory.getRemotePlayer(canvas);
        //mediaPlayer.addMediaPlayerEventListener(mediaPlayerEvents);
        //mediaPlayer.setVideoSurface(canvas);
        mediaPlayer.load(mediaSource);
    }

    private void initProgressSlider() {
        progressSlider = new JSlider();
        progressSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressSlider.setValue(0);
        progressSlider.setMinimum(0);
        progressSlider.setMaximum(100);
        progressSlider.setToolTipText("Progress time");
        progressSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider src = (JSlider) e.getSource();

                if (!src.getValueIsAdjusting()) {
                    mediaPlayer.setPosition(src.getValue() / 100F);
                }
            }
        });
    }

    private void initInputMap() {
        InputMap imap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap amap = getActionMap();

        imap.put(KeyStroke.getKeyStroke("SPACE"), "pause_resume");
        amap.put("pause_resume", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                togglePlay();
            }
        });
    }

    private void setLayoutOptions() {
        this.setLayout(new GridBagLayout());

        java.awt.GridBagConstraints opts;

        opts = new java.awt.GridBagConstraints();
        opts.gridx = 0;
        opts.gridy = 0;
        opts.anchor = java.awt.GridBagConstraints.PAGE_START;
        opts.insets = new java.awt.Insets(0, 0, 0, 0);
        opts.weightx = 1.0;
        opts.weighty = 1.0;
        opts.fill = java.awt.GridBagConstraints.BOTH;
        add(canvas, opts);

        opts = new java.awt.GridBagConstraints();
        opts.gridx = 0;
        opts.gridy = 1;
        opts.anchor = java.awt.GridBagConstraints.PAGE_START;
        opts.insets = new java.awt.Insets(5, 0, 5, 0);
        opts.fill = java.awt.GridBagConstraints.BOTH;
        add(progressSlider, opts);

        opts = new java.awt.GridBagConstraints();
        opts.gridx = 0;
        opts.gridy = 2;
        opts.anchor = java.awt.GridBagConstraints.PAGE_START;
        opts.insets = new java.awt.Insets(0, 0, 0, 0);
        opts.fill = java.awt.GridBagConstraints.BOTH;
        add(toolbar, opts);
    }
}
