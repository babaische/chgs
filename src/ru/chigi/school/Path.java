/**
 chgs - A multimedia platform for 4igi guitar school (http://school.4igi.ru)
 Copyright (C) 2011 Max E. Kuznecov <mek@mek.uz.ua>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.chigi.school;

public class Path {
    /**
     * Return user's home dir
     * @return
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Get user's data dir
     * @return
     */
    public static String getUserDataDir() {
        String sep = System.getProperty("file.separator");

        return String.format("%s%s.chgs%s%s", getUserHome(), sep, sep, "user.conf");
    }
}
