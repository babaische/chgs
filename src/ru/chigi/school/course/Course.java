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
package ru.chigi.school.course;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A course type
 */
public class Course {
    private String id;
    private Map<String, String> descriptions = new HashMap<String, String>();
    private List<Author> authors;

    private List<LessonPointer> lessons;
    private String url;
    private final String defaultLang = "default";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription(String lang) {
        String val = descriptions.get(lang);

        return val == null ? descriptions.get(defaultLang) : val;
    }

    public void setDescription(String lang, String description) {
        this.descriptions.put(lang, description);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<LessonPointer> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonPointer> lessons) {
        this.lessons = lessons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("<Course instance: id=%s>", id);
    }
}
