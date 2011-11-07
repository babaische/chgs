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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.LogRecord;

/**
 * A static class holding log messages for further processing
 */
public class LogDB {
    private final static int size = 100;
    private static ArrayDeque<LogRecord> db = new ArrayDeque<LogRecord>(size);
    private static Set<UpdateSubscriber> subscribers = new HashSet<UpdateSubscriber>();

    /**
     * Insert a new record into db
     * @param record
     */
    public static synchronized void insert(LogRecord record) {
        if(db.size() == size)
            db.removeLast();

        db.addFirst(record);

        for(UpdateSubscriber sub : subscribers)
            sub.notifyUpdate();
    }

    /**
     * DB size
     * @return size
     */
    public static int size() {
        return db.size();
    }

    /**
     * Get messages
     * @return Messages as a collection
     */
    public static Collection<LogRecord> getMessages() {
        return db;
    }

    /**
     * Clear messages in db
     */
    public static void clear() {
        db.clear();
    }

    /**
     * Subscribe to DB updates
     * @param sub UpdateSubscriber instance
     */
    public static void subscribe(UpdateSubscriber sub) {
        if(!subscribers.contains(sub))
            subscribers.add(sub);
    }
}
