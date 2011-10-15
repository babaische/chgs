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
package ru.chigi.school.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Central application logger
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */

public class Log {
    private static Log instance = null;
    private static Logger logger;

    /**
     * Return Log instance
     * @return Log instance
     */
    public static Log getDefault() {
        if(instance == null)
            instance = new Log();

        logger.addHandler(new UIHandler());
        return instance;
    }

    /**
     * Log severe message
     * @param message Message string
     */
    public void severe(String message) {
        severe(message, "");
    }

    /**
     * Log severe message with formatting
     * @param fmt Format string
     * @param args Format params
     */
    public void severe(String fmt, Object... args) {
        logger.severe(String.format(fmt, args));
    }

    /**
     * Log warning message
     * @param message Message string
     */
    public void warning(String message) {
        warning(message, "");
    }

    /**
     * Log warning message with formatting
     * @param fmt Format string
     * @param args Format params
     */
    public void warning(String fmt, Object... args) {
        logger.warning(String.format(fmt, args));
    }

    /**
     * Log info message
     * @param message Message string
     */
    public void info(String message) {
        info(message, "");
    }

    /**
     * Log info message with formatting
     * @param fmt Format string
     * @param args Format params
     */
    public void info(String fmt, Object... args) {
        logger.info(String.format(fmt, args));
    }

    /**
     * Log config message
     * @param message Message string
     */
    public void config(String message) {
        config(message, "");
    }

    /**
     * Log config message with formatting
     * @param fmt Format string
     * @param args Format params
     */
    public void config(String fmt, Object... args) {
        logger.config(String.format(fmt, args));
    }

    private Log() {
        logger = Logger.getLogger(ru.chigi.school.Version.getAppName());
        logger.setLevel(Level.CONFIG);
    }
}
