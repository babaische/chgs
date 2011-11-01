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
import java.awt.event.ActionEvent;

public class RoomAction extends AbstractAction {
    private Workspace workspace;
    private RoomInterface room;

    public RoomAction(RoomInterface room, Workspace workspace) {
        super(room.getRoomName(), room.getRoomIcon16());

        this.room = room;
        this.workspace = workspace;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        workspace.open(room);
    }
}
