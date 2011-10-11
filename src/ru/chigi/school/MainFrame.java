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

    public MainFrame() {
        setTitle("Chigi guitar school");

        setLayout(new BorderLayout());
        setJMenuBar(initMenu());
        add(initToolbar(), BorderLayout.PAGE_START);
        add(initWorkspace(), BorderLayout.CENTER);
        add(new StatusBar(), BorderLayout.PAGE_END);

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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

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
                return;
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

        JMenuItem hall = new JMenuItem("Hall",
                new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/hall16.png")));

        JMenuItem classroom = new JMenuItem("Classroom",
                new ImageIcon(getClass().getResource("/ru/chigi/school/classroom/resources/classroom16.png")));

        JMenuItem nt = new JMenuItem("Note trainer",
                new ImageIcon(getClass().getResource("/ru/chigi/school/nt/resources/nt16.png")));

        roomsMenu.add(hall);
        roomsMenu.add(classroom);
        roomsMenu.add(nt);
        menuBar.add(roomsMenu);

        /** Widgets menu **/
        JMenu widgetsMenu = new JMenu("Widgets");
        widgetsMenu.setMnemonic(KeyEvent.VK_W);

        JMenuItem news = new JMenuItem("News",
                new ImageIcon(getClass().getResource("/ru/chigi/school/news/resources/news16.png")));

        JMenuItem metronome = new JMenuItem("Metronome",
                new ImageIcon(getClass().getResource("/ru/chigi/school/metronome/resources/metronome16.png")));

        JMenuItem log = new JMenuItem("Messages",
                new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/log16.png")));

        widgetsMenu.add(news);
        widgetsMenu.add(metronome);
        widgetsMenu.addSeparator();
        widgetsMenu.add(log);
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

        JButton hall = new JButton(new ImageIcon(getClass().getResource("/ru/chigi/school/hall/resources/hall32.png")));
        hall.setToolTipText("Hall");

        JButton rooms = new JButton(new ImageIcon(getClass()
                .getResource("/ru/chigi/school/classroom/resources/classroom32.png")));
        rooms.setToolTipText("Classroom");

        JButton nt = new JButton(new ImageIcon(getClass().getResource("/ru/chigi/school/nt/resources/nt32.png")));
        rooms.setToolTipText("Note trainer");

        tb.add(hall);
        tb.add(rooms);
        tb.add(nt);
        tb.addSeparator();

        JButton news = new JButton(new ImageIcon(getClass().getResource("/ru/chigi/school/news/resources/news32.png")));
        news.setToolTipText("News");

        JButton metronome = new JButton(new ImageIcon(getClass()
                .getResource("/ru/chigi/school/metronome/resources/metronome32.png")));
        metronome.setToolTipText("Metronome");

        JButton log = new JButton(new ImageIcon(getClass().getResource("/ru/chigi/school/log/resources/log32.png")));
        log.setToolTipText("Log");

        tb.add(news);
        tb.add(metronome);
        tb.add(log);

        return tb;
    }

    private JTabbedPane initWorkspace() {
        return new JTabbedPane();
    }
}


