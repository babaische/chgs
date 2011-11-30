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
/*
 * MainFrame.java
 *
 * Created on 7 oct. 2011, 19:54:48
 */
package ru.chigi.school;

import ru.chigi.school.course.ListParser;
import ru.chigi.school.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Application entry point
 *
 * @author Max E. Kuznecov <mek@mek.uz.ua>
 */
public class MainFrame extends JFrame {
    private final RoomManager roomManager = new RoomManager();
    private Workspace workspace;

    public MainFrame() {
        setTitle("Chigi guitar school");

        workspace = new Workspace();
        setLayout(new BorderLayout());
        setJMenuBar(initMenu());

        add(initToolbar(), BorderLayout.PAGE_START);
        add(workspace, BorderLayout.CENTER);
        add(StatusBar.getDefault(), BorderLayout.PAGE_END);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(getRootPane());

        setVisible(true);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Log.getDefault().warning(ex);
        } catch (InstantiationException ex) {
            Log.getDefault().warning(ex);
        } catch (IllegalAccessException ex) {
            Log.getDefault().warning(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Log.getDefault().warning(ex);
        }
        */

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    /**
     * Initialize menu
     * @return Menubar instance
     */
    private JMenuBar initMenu() {
        JMenuBar menuBar = new JMenuBar();

        /** File menu **/
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem settings = new JMenuItem("Settings",
                new ImageIcon(getClass().getResource("/ru/chigi/school/settings/resources/settings16.png")));
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Log.getDefault().warning("TEST MESSAGE");
            }
        });

        JMenuItem exit = new JMenuItem("Exit",
                new ImageIcon(getClass().getResource("/ru/chigi/school/resources/quit16.png")));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        fileMenu.add(settings);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        /** Rooms menu **/
        JMenu roomsMenu = new JMenu("Rooms");
        roomsMenu.setMnemonic(KeyEvent.VK_R);

        for(RoomInterface room : roomManager.getRooms()) {
            if(!room.showInMenu())
                continue;

            JMenuItem item = new JMenuItem(room.getRoomName(), room.getRoomIcon16());
            item.setAction(new RoomAction(room, workspace, true));

            roomsMenu.add(item);
        }

        menuBar.add(roomsMenu);

        /** Widgets menu **/
        JMenu widgetsMenu = new JMenu("Widgets");
        widgetsMenu.setMnemonic(KeyEvent.VK_W);

        for(WidgetInterface widget : WidgetManager.getDefault().getWidgets()) {
            if(!widget.showInMenu())
                continue;

            JMenuItem item = new JMenuItem(widget.getWidgetName(), widget.getWidgetIcon16());
            widgetsMenu.add(item);
        }

        menuBar.add(widgetsMenu);

        /** Help menu **/
        JMenu helpMenu = new JMenu("Help");
        widgetsMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About",
                new ImageIcon(getClass().getResource("/ru/chigi/school/about/resources/about16.png")));

        helpMenu.add(about);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JToolBar initToolbar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        // Add rooms
        for(RoomInterface room : roomManager.getRooms()) {
            if(!room.showOnToolbar())
                continue;

            JButton button = new JButton(room.getRoomIcon32());
            button.setToolTipText(room.getRoomDescription());
            button.setAction(new RoomAction(room, workspace, false));
            button.setText("");
            tb.add(button);
        }

        tb.addSeparator();

        // Add widgets
        for(WidgetInterface widget : WidgetManager.getDefault().getWidgets()) {
            if(!widget.showOnToolbar())
                continue;

            JButton button = new JButton(widget.getWidgetIcon32());
            button.setToolTipText(widget.getWidgetDescription());
            tb.add(button);
        }

        return tb;
    }
}
