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

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * UI logger handler.
 * Sends messages to visual table
 */
public class UIHandler extends Handler {
    @Override
    public void publish(LogRecord logRecord) {
        LogDB.insert(logRecord);

        Level level = logRecord.getLevel();

        LogStatusBarIcon.getInstance().setToolTipText("New messages have appeared");

        if(level.equals(Level.SEVERE))
            LogStatusBarIcon.getInstance().setIcon(LogStatusBarIcon.Icon.ERROR);
        else if(level.equals(Level.WARNING))
            LogStatusBarIcon.getInstance().setIcon(LogStatusBarIcon.Icon.WARNING);
        else if(level.equals(Level.INFO))
            LogStatusBarIcon.getInstance().setIcon(LogStatusBarIcon.Icon.INFO);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
