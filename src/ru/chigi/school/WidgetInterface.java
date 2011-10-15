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

public interface WidgetInterface extends Comparable<WidgetInterface> {
    /**
     * Get widget name
     * @return widget name
     */
    public String getWidgetName();

    /**
     * Get widget description
     * @return widget description
     */
    public String getWidgetDescription();

    /**
     * Get Widget 16x16 icon
     * @return 16x16 icon
     */
    public ImageIcon getWidgetIcon16();

    /**
     * Get Widget 32x32 icon
     * @return 32x32 icon
     */
    public ImageIcon getWidgetIcon32();

    /**
     * Whether to show this widget's icon in main menu
     * @return
     */
    public boolean showInMenu();

    /**
     * Whether to show this widget's icon on toolbar
     * @return
     */
    public boolean showOnToolbar();

    /**
     * Widget's priority determine its position in menu/toolbar
     * @return Priority index
     */
    public int getWidgetPriority();
}
