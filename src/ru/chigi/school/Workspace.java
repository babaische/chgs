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

import javax.swing.*;
import java.awt.*;

public class Workspace extends JTabbedPane {
    public Workspace() {
        super();

        setPreferredSize(new Dimension(800, 600));
    }

    public void open(RoomInterface room) {
        // Check if already open, then just focus
        int index = indexOfComponent(room.getRoom());

        if(index != -1)
            setSelectedIndex(index);
        else {
            addTab("", room.getRoomIcon16(), room.getRoom(), room.getRoomDescription());
            setTabComponentAt(getTabCount() - 1, new TabButtonComponent(this, room));
        }
    }
}
