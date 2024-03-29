package ru.chigi.school;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

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

/**
 * Dispatch widgets
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public class WidgetManager {
    private List<WidgetInterface> widgets = new ArrayList<WidgetInterface>();
    private static WidgetManager instance = null;

    public static WidgetManager getDefault() {
        if(instance == null)
            instance = new WidgetManager();

        return instance;
    }

    /**
     * Get registered widgets
     *
     * @return List of widgets
     */
    public List<WidgetInterface> getWidgets() {
        return widgets;
    }

    /**
     * Get widget instance by class
     * @param widgetClass Widget class
     * @return Widget instance or null
     */

    public WidgetInterface getWidget(Class widgetClass) {
        for(WidgetInterface w : widgets) {
            if(w.getClass() == widgetClass)
                return w;
        }

        return null;
    }

    private WidgetManager() {
        for(WidgetInterface i : ServiceLoader.load(WidgetInterface.class)) {
            widgets.add(i);
        }

        Collections.sort(widgets);
    }
}
