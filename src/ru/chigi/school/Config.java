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

import ru.chigi.school.log.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration parameters
 */
public class Config {
    private final String systemConfPath = "/system.conf";
    private final String userConfPath = Path.getUserDataDir();
    private final Properties systemConf;
    private final Properties userConf;

    public Config() {
        systemConf = new Properties();

        try {
            systemConf.load(getClass().getResourceAsStream(systemConfPath));
        }
        catch (IOException e) {
            Log.getDefault().severe("Unable to open system config: %s", e);
        }

        userConf = new Properties(systemConf);

        try {
            userConf.load(new FileInputStream(userConfPath));
        }
        catch (IOException e) {}
    }

    /**
     * Get key's value
     * @param key Key
     * @return Value
     */
    public String get(String key) {
        return userConf.getProperty(key);
    }

    /**
     * Get key's value or default
     * @param key Key
     * @param def Default value
     * @return Value
     */
    public String get(String key, String def) {
        return userConf.getProperty(key, def);
    }
}
