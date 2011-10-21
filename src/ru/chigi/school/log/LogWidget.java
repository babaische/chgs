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

package ru.chigi.school.log;

import ru.chigi.school.AbstractWidget;
import ru.chigi.school.WidgetInterface;

import javax.swing.*;

public class LogWidget extends AbstractWidget {
    private LogTable table;

    public LogWidget() {
        super();

        table = new LogTable();
        setTitle("Log messages");

        add(table);

        pack();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(getRootPane());
    }

    @Override
    public String getWidgetName() {
        return "Log";
    }

    @Override
    public String getWidgetDescription() {
        return "Log messages";
    }

    @Override
    public ImageIcon getWidgetIcon16() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/log16.png"));
    }

    @Override
    public ImageIcon getWidgetIcon32() {
        return new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/log32.png"));
    }

    @Override
    public boolean showOnToolbar() {
        return false;
    }

    @Override
    public int getWidgetPriority() {
        return 20;
    }
}
