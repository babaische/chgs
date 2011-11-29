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
import ru.chigi.school.course.CourseManager;

import javax.swing.*;
import java.awt.*;

public class ClassRoom extends AbstractRoom {
    private ImageIcon icon16 = new ImageIcon(getClass().getResource("/ru/chigi/school/classroom/resources/classroom16.png"));
    private ImageIcon icon32 = new ImageIcon(getClass().getResource("/ru/chigi/school/classroom/resources/classroom32.png"));

    public ClassRoom() {
        super();

        initComponents();
    }

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
        return icon16;
    }

    @Override
    public ImageIcon getRoomIcon32() {
        return icon32;
    }

    @Override
    public int getRoomPriority() {
        return 10;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel navigator = new JPanel();
        navigator.add(new JButton("Navigator"));

        JPanel workspace = new JPanel();
        workspace.setPreferredSize(new Dimension(200, 300));
        workspace.add(new JButton("AAAAA"));

        add(navigator, BorderLayout.LINE_START);
        add(workspace, BorderLayout.CENTER);
    }
}
