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

import ru.chigi.school.log.Log;
import sun.jdbc.odbc.JdbcOdbcPreparedStatement;
import sun.plugin2.ipc.Event;

import java.io.*;

/**
 * Controls an OutOfProcessPlayer via input / output process streams.
 * From http://berry120.blogspot.com/2011/07/using-vlcj-for-video-reliably-with-out.html
 */

public class RemotePlayer implements Runnable {
    private BufferedReader in;
    private BufferedReader notify;
    private BufferedWriter out;
    private Thread notifyListener = null;
    private EventsHandler eh = null;

    RemotePlayer(StreamWrapper wrapper, EventsHandler eh) {
        out = new BufferedWriter(new OutputStreamWriter(wrapper.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(wrapper.getInputStream()));
        notify = new BufferedReader(new InputStreamReader(wrapper.getErrStream()));
        this.eh = eh;

        // Start listener thread
        if(eh != null) {
            notifyListener = new Thread(this);
            notifyListener.start();
        }
    }

    /**
     * Send command to oop player
     * @param command Command string
     */
    private void send(String command) {
        try {
            out.write(command + "\n");
            out.flush();
        }
        catch (IOException ex) {
            Log.getDefault().severe(ex);
        }
    }

    /**
     * Receive command from oop player
     * @return Command read
     */
    private String receive() {
        try {
            return in.readLine();
        }
        catch (IOException ex) {
            Log.getDefault().severe(ex);
            return null;
        }
    }

    public void load(String path) {
        send("open " + path);
    }

    public void play() {
        send("play");
    }

    public void pause() {
        send("pause");
    }

    public void stop() {
        send("stop");
    }

    public boolean isPlayable() {
        send("playable?");
        return Boolean.parseBoolean(receive());
    }

    public long getLength() {
        send("length?");
        return Long.parseLong(receive());
    }

    public long getTime() {
        send("time?");
        return Long.parseLong(receive());
    }

    public int getVolume() {
        send("volume?");
        return Integer.parseInt(receive());
    }

    public void setVolume(int volume) {
        send("setVolume " + volume);
    }

    public void setTime(long time) {
        send("setTime " + time);
    }

    public void setPosition(float pos) {
        send("setPosition " + pos);
    }

    public boolean getMute() {
        send("mute?");
        return Boolean.parseBoolean(receive());
    }

    public void setMute(boolean mute) {
        send("setMute " + mute);
    }

    public void setFullScreen(boolean fs) {
        send("setFullScreen " + fs);
    }

    /**
     * Terminate the OutOfProcessPlayer. MUST be called before closing, otherwise
     * the player won't quit!
     */
    public void close() {
        send("close");
    }

    /**
     * Determine whether the remote player is playing.
     * @return true if its playing, false otherwise.
     */
    public boolean isPlaying() {
        send("playing?");
        return Boolean.parseBoolean(receive());
    }

    /**
     * Read oop player notifications from stderr
     */
    @Override
    public void run() {
        String line;

        while(true) {
            try {
                line = notify.readLine();

                if (line.startsWith("NOTIFY ")) {
                    line = line.substring("NOTIFY ".length());

                    if(line.equals("playing"))
                        eh.playing();
                    else if(line.equals("paused"))
                        eh.paused();
                    else if(line.equals("stopped"))
                        eh.stopped();
                    else if(line.startsWith("lengthChanged ")) {
                        eh.lengthChanged(Long.parseLong(line.substring("lengthChanged ".length())));
                    }
                }
                // If not notification, then print in to stderr
                else
                    System.err.println(line);
            }
            catch (IOException ex) {
                Log.getDefault().severe(ex);
            }
        }
    }
}
