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

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class LogTable extends JPanel implements UpdateSubscriber {
    private JTable table;
    private TableModel model;

    public LogTable() {
        final int levelColWidth = 50;
        final int dateColWidth = 250;

        TableColumn levelCol = new TableColumn(0);
        levelCol.setHeaderValue("Level");
        levelCol.setMinWidth(levelColWidth);
        levelCol.setMaxWidth(levelColWidth);
        levelCol.setPreferredWidth(levelColWidth);

        TableColumn dateCol = new TableColumn(1);
        dateCol.setHeaderValue("Date");
        dateCol.setPreferredWidth(dateColWidth);
        dateCol.setMinWidth(dateColWidth);

        TableColumn msgCol = new TableColumn(2);
        msgCol.setHeaderValue("Message");

        final TableColumnModel colModel = new DefaultTableColumnModel();
        colModel.addColumn(levelCol);
        colModel.addColumn(dateCol);
        colModel.addColumn(msgCol);

        model = new AbstractTableModel() {
            private Object[][] db2Array() {
                Object[][] array = new Object[getRowCount()][getColumnCount()];
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

                int r = 0;

                for(LogRecord rec : LogDB.getMessages()) {
                    array[r][0] = LogStatusBarIcon.getIcon(rec.getLevel());
                    array[r][1] = format.format(new Date(rec.getMillis()));
                    array[r][2] = rec.getMessage();

                    r++;
                }

                return array;
            };

            @Override
            public int getRowCount() {
                return LogDB.size();
            }

            @Override
            public int getColumnCount() {
                return colModel.getColumnCount();
            }

            @Override
            public Object getValueAt(int i, int i1) {
                return db2Array()[i][i1];
            }

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

            @Override
            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setColumnModel(colModel);
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        LogDB.subscribe(this);
    }

    public void notifyUpdate() {
        ((AbstractTableModel)table.getModel()).fireTableDataChanged();
        table.revalidate();
        table.repaint();
    }
}
