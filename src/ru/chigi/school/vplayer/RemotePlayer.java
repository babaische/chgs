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

import java.io.*;

/**
 * Controls an OutOfProcessPlayer via input / output process streams.
 * From http://berry120.blogspot.com/2011/07/using-vlcj-for-video-reliably-with-out.html
 */

public class RemotePlayer {

    private BufferedReader in;
    private BufferedWriter out;
    private boolean open;
    private boolean playing;
    private boolean paused;

    /**
     * Internal use only.
     */
    RemotePlayer(StreamWrapper wrapper) {
        out = new BufferedWriter(new OutputStreamWriter(wrapper.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(wrapper.getInputStream()));
        playing = false;
        open = true;
    }

    private void writeOut(String command) {
        if (!open) {
            throw new IllegalArgumentException("This remote player has been closed!");
        }
        try {
            out.write(command + "\n");
            out.flush();
        }
        catch (IOException ex) {
            throw new RuntimeException("Couldn't perform operation", ex);
        }
    }

    private String getInput() {
        try {
            return in.readLine();
        }
        catch (IOException ex) {
            throw new RuntimeException("Couldn't perform operation", ex);
        }
    }

    public void load(String path) {
        writeOut("open " + path);
    }

    public void play() {
        writeOut("play");
        playing = true;
        paused = false;
    }

    public void pause() {
        if(!paused) {
            writeOut("pause");
            playing = false;
            paused = true;
        }
    }

    public void stop() {
        writeOut("stop");
        playing = false;
        paused = false;
    }

    public boolean isPlayable() {
        writeOut("playable?");
        return Boolean.parseBoolean(getInput());
    }

    public long getLength() {
        writeOut("length?");
        return Long.parseLong(getInput());
    }

    public long getTime() {
        writeOut("time?");
        return Long.parseLong(getInput());
    }

    public int getVolume() {
        writeOut("volume?");
        return Integer.parseInt(getInput());
    }

    public void setVolume(int volume) {
        writeOut("setVolume " + volume);
    }

    public void setTime(long time) {
        writeOut("setTime " + time);
    }

    public void setPosition(float pos) {
        writeOut("setPosition " + pos);
    }

    public boolean getMute() {
        writeOut("mute?");
        return Boolean.parseBoolean(getInput());
    }

    public void setMute(boolean mute) {
        writeOut("setMute " + mute);
    }

    public void setFullScreen(boolean fs) {
        writeOut("setFullScreen " + fs);
    }

    /**
     * Terminate the OutOfProcessPlayer. MUST be called before closing, otherwise
     * the player won't quit!
     */
    public void close() {
        if (open) {
            writeOut("close");
            playing = false;
            open = false;
        }
    }

    /**
     * Determine whether the remote player is playing.
     * @return true if its playing, false otherwise.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Determine whether the remote player is paused.
     * @return true if its paused, false otherwise.
     */
    public boolean isPaused() {
        return paused;
    }
}
