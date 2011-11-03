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
 * A callback interface used to notify about VPlayer events
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public interface VPlayerCallback {
    /**
     * Called when player is about to be released
     * @param player
     */
    public void released(VPlayer player);
    public void playing(VPlayer player);
}
