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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for courses index - list.xml file
 */
public class ListParser {
    private List<Course> courses = new ArrayList<Course>();
    private Course course = null;
    private String textTag = null;
    private Attributes attrs = null;

    public List<Course> parse(String listXML) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);

        try {
            InputStream input = new FileInputStream(listXML);
            SAXParser parser = factory.newSAXParser();

            ListParseHandler handler = new ListParseHandler();

            parser.parse(input, handler);
        }
        catch (FileNotFoundException e) {
            courses = null;
        }
        catch (ParserConfigurationException e) {
            courses = null;
        }
        catch (SAXException e) {
            courses = null;
        }
        catch (IOException e) {
            courses = null;
        }

        return courses;
    }

    private class ListParseHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localname, String qname, Attributes attributes) throws SAXException {
            if(qname.equalsIgnoreCase(Tags.COURSE_TAG)) {
                course = new Course();
                course.setId(attributes.getValue("id"));
            }
            else {
                textTag = qname;
                attrs = attributes;
            }
        }

        @Override
        public void endElement(String uri, String local, String qname) throws SAXException {
            if(qname.equalsIgnoreCase(Tags.COURSE_TAG)) {
                if(course != null) {
                    courses.add(course);
                    course = null;
                }
            }

            textTag = null;
            attrs = null;
        }

        @Override
        public void characters(char[] chars, int start, int length) throws SAXException {
            String text = new String(chars, start, length);

            if(textTag == Tags.DESCRIPTION_TAG)
                course.setDescription(attrs.getValue("lang"), text);

            else if(textTag == Tags.URL_TAG)
                course.setUrl(text);

            return;
        }
    }
}
