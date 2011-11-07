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

import ru.chigi.school.StatusBar;

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
        try {
            LogDB.insert(logRecord);

            Level level = logRecord.getLevel();
            StatusBar sb = StatusBar.getDefault();

            if(level.equals(Level.SEVERE))
                sb.setLogIcon(LogStatusBarIcon.Icon.ERROR);
            else if(level.equals(Level.WARNING))
                sb.setLogIcon(LogStatusBarIcon.Icon.WARNING);
            else if(level.equals(Level.INFO))
                sb.setLogIcon(LogStatusBarIcon.Icon.INFO);
        }
        catch (Exception e) {}
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
