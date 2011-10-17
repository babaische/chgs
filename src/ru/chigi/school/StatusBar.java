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
package ru.chigi.school;

import ru.chigi.school.log.LogStatusBarIcon;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class StatusBar extends JPanel {
    private final JLabel msg = new JLabel("");
    private final LogStatusBarIcon logIcon;
    private static StatusBar instance = null;

    /**
     * Static constructor
     * @return StatusBar instance
     */
    public static StatusBar getDefault() {
        if(instance == null)
            instance = new StatusBar();

        return instance;
    }

    public void setLogIcon(LogStatusBarIcon.Icon icon) {
        logIcon.setIcon(icon);
    }

    private StatusBar() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        logIcon = new LogStatusBarIcon();

        add(msg);
        add(Box.createHorizontalGlue());
        add(logIcon);
    }
}
