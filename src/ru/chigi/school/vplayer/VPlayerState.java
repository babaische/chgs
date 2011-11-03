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

/**
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */

/**
 * Player state description
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public class VPlayerState {
    private String mediaSource = "";
    private boolean playing = false;
    private int volume = 0;
    private long time = 0;

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean isPlaying) {
        this.playing = isPlaying;
    }

    public String getMediaSource() {
        return mediaSource;
    }

    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
