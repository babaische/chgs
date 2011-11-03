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

import com.sun.jna.Pointer;
import uk.co.caprica.vlcj.binding.LibVlcFactory;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_player_t;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.linux.LinuxEmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.mac.MacEmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.WindowsEmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sits out of process so as not to crash the primary VM.
 * From http://berry120.blogspot.com/2011/07/using-vlcj-for-video-reliably-with-out.html
 */
public class OutOfProcessPlayer {
    private static OutOfProcessPlayer instance = null;

    public static void main(String[] args) {
        try {
            instance = new OutOfProcessPlayer(Integer.parseInt(args[0]));
        } catch (Exception ex) {
            Logger.getLogger(OutOfProcessPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public OutOfProcessPlayer(final long canvasId) throws Exception {

        //Lifted pretty much out of the VLCJ code
        EmbeddedMediaPlayer player;

        System.out.format("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");
        String[] playerArgs = new String[]{"--no-video-title", "--no-snapshot-preview"};

        if (RuntimeUtil.isNix()) {
            player = new LinuxEmbeddedMediaPlayer(LibVlcFactory.factory().synchronise().log().create().libvlc_new(1, playerArgs), null) {

                @Override
                protected void nativeSetVideoSurface(libvlc_media_player_t mediaPlayerInstance, Canvas videoSurface) {
                    libvlc.libvlc_media_player_set_xwindow(mediaPlayerInstance, (int) canvasId);
                }
            };
        } else if (RuntimeUtil.isWindows()) {
            player = new WindowsEmbeddedMediaPlayer(LibVlcFactory.factory().synchronise().log().create().libvlc_new(1, playerArgs), null) {

                @Override
                protected void nativeSetVideoSurface(libvlc_media_player_t mediaPlayerInstance, Canvas videoSurface) {
                    Pointer ptr = Pointer.createConstant(canvasId);
                    libvlc.libvlc_media_player_set_hwnd(mediaPlayerInstance, ptr);
                }
            };
        } else if (RuntimeUtil.isMac()) {
            player = new MacEmbeddedMediaPlayer(LibVlcFactory.factory().synchronise().log().create().libvlc_new(2, playerArgs), null) {

                @Override
                protected void nativeSetVideoSurface(libvlc_media_player_t mediaPlayerInstance, Canvas videoSurface) {
                    Pointer ptr = Pointer.createConstant(canvasId);
                    libvlc.libvlc_media_player_set_nsobject(mediaPlayerInstance, ptr);
                }
            };
        } else {
            player = null;
            // TODO
            System.exit(1);
        }

        player.setVideoSurface(new Canvas());

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;
        //Process the input - I know this isn't very OO but it works for now...

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("open ")) {
                inputLine = inputLine.substring("open ".length());
                player.prepareMedia(inputLine);
            } else if (inputLine.equalsIgnoreCase("play")) {
                player.play();
            } else if (inputLine.equalsIgnoreCase("pause")) {
                player.pause();
            } else if (inputLine.equalsIgnoreCase("stop")) {
                player.stop();
            } else if (inputLine.equalsIgnoreCase("playable?")) {
                System.out.println(player.isPlayable());
            } else if (inputLine.startsWith("setTime ")) {
                inputLine = inputLine.substring("setTime ".length());
                player.setTime(Long.parseLong(inputLine));
            } else if (inputLine.startsWith("setMute ")) {
                inputLine = inputLine.substring("setMute ".length());
                player.mute(Boolean.parseBoolean(inputLine));
            } else if (inputLine.startsWith("setFullScreen ")) {
                inputLine = inputLine.substring("setFullScreen ".length());
                player.setFullScreen(Boolean.parseBoolean(inputLine));
            } else if (inputLine.startsWith("setPosition ")) {
                inputLine = inputLine.substring("setPosition ".length());
                player.setPosition(Float.parseFloat(inputLine));
            } else if (inputLine.startsWith("setVolume ")) {
                inputLine = inputLine.substring("setVolume ".length());
                player.setVolume(Integer.parseInt(inputLine));
            } else if (inputLine.equalsIgnoreCase("mute?")) {
                boolean mute = player.isMute();
                System.out.println(mute);
            } else if (inputLine.equalsIgnoreCase("length?")) {
                long length = player.getLength();
                System.out.println(length);
            } else if (inputLine.equalsIgnoreCase("time?")) {
                long time = player.getTime();
                System.out.println(time);
            } else if (inputLine.equalsIgnoreCase("volume?")) {
                System.out.println(player.getVolume());
            } else if (inputLine.equalsIgnoreCase("close")) {
                System.exit(0);
            } else {
                System.out.println("unknown command: ." + inputLine + ".");
            }
        }
    }
}