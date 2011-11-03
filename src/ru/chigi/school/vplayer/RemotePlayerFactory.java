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

import com.sun.jna.Native;

import java.awt.*;
import java.net.URL;

/**
 * From http://berry120.blogspot.com/2011/07/using-vlcj-for-video-reliably-with-out.html
 */
public class RemotePlayerFactory {
    public static RemotePlayer getRemotePlayer(Canvas canvas) {
        try {
            long drawable = Native.getComponentID(canvas);
            StreamWrapper wrapper = startSecondJVM(drawable);
            final RemotePlayer player = new RemotePlayer(wrapper);

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    player.close();
                }
            });
            return player;
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create remote player", ex);
        }
    }

    private static StreamWrapper startSecondJVM(long drawable) throws Exception {
        String separator = System.getProperty("file.separator");
        String classpath = System.getProperty("java.class.path");
        String path = System.getProperty("java.home") + separator + "bin" + separator + "java";

        ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath,
                "-Djna.library.path=" + System.getProperty("jna.platform.library.path"),
                OutOfProcessPlayer.class.getName(), Long.toString(drawable));
        Process process = processBuilder.start();

        return new StreamWrapper(process.getInputStream(), process.getOutputStream());
    }
}