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

import ru.chigi.school.WidgetManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogStatusBarIcon extends JLabel {
    private ImageIcon icon;
    private final ImageIcon inactive = new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/inactive.png"));
    private final ImageIcon error = new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/error.png"));
    private final ImageIcon warning = new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/warning.png"));
    private final ImageIcon info = new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/info.png"));

    public static enum Icon {
        INACTIVE,
        ERROR,
        WARNING,
        INFO
    };

    public LogStatusBarIcon() {
        super();

        // Default icon
        icon = inactive;

        setIcon(icon);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                deactivate();

                WidgetManager.getDefault().getWidget(LogWidget.class).showWidget();
            }
        });
    }

    /**
     * Change icon
     * @param newIcon
     */
    public void setIcon(Icon newIcon) {
        switch (newIcon) {
            case ERROR:
                icon = error;
                break;
            case WARNING:
                icon = warning;
                break;
            case INFO:
                icon = info;
                break;
            case INACTIVE:
            default:
                icon = inactive;
                break;
        }

        setToolTipText("New messages have appeared");
        setIcon(icon);
    }

    /**
     * Deactivate icon
     */
    public void deactivate() {
        setToolTipText("");
        setIcon(Icon.INACTIVE);
    }
}
