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

public class Version {
    private static final String strVersion = "0.1";
    private static final int intVersion = 1;
    private static final String appName = "chgs";
    private static final String appDescription = "A multimedia platform for 4igi guitar school";
    private static final String homepage =  "http://school.4igi.ru";

    public static String getStrVersion() {
        return strVersion;
    }

    public static int getIntVersion() {
        return intVersion;
    }

    public static String getAppName() {
        return appName;
    }

    public static String getAppDescription() {
        return appDescription;
    }

    public static String getHomepage() {
        return homepage;
    }
}
