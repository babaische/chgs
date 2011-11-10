/**
 * chgs - A multimedia platform for 4igi guitar school (http://school.4igi.ru)
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

package ru.chigi.school.classroom;

import ru.chigi.school.AbstractRoom;

import javax.swing.*;

public class ClassRoom extends AbstractRoom {
    @Override
    public String getRoomName() {
        return "Classroom";
    }

    @Override
    public String getRoomDescription() {
        return "Classroom";
    }

    @Override
    public ImageIcon getRoomIcon16() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/classroom/resources/classroom16.png"));
    }

    @Override
    public ImageIcon getRoomIcon32() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/classroom/resources/classroom32.png"));
    }

    @Override
    public int getRoomPriority() {
        return 10;
    }
}
