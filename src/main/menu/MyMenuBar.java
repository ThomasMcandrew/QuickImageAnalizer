package main.menu;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar(){
        JMenu file = new JMenu("File");
        JMenuItem openVideo = new JMenuItem("Open new Video");
        JMenuItem openPreviousSession = new JMenuItem("Open Previous Session");
        JMenuItem saveCsv = new JMenuItem("Save CSV");
        JMenuItem saveSession = new JMenuItem("Save Session");
        file.add(openVideo);
        file.add(openPreviousSession);
        file.add(saveCsv);
        file.add(saveSession);

        add(file);
    }

}
