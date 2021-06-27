package main;

import main.inner.ImageView;
import main.inner.TableView;
import main.menu.MyMenuBar;
import main.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {
    private boolean ready = true;
    public MainFrame(){
        super("Image Thinggyyy");
        setPreferredSize(new Dimension(800,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new MyMenuBar());
        add(ImageView.getImageView(),BorderLayout.CENTER);
        add(TableView.getTableView(), BorderLayout.EAST);
        addKeyListener(this);
        setFocusable(true);

        setVisible(true);
        pack();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(ready) {
            TableView.addRow(new String[]{Settings.iteator + "" ,Settings.mappedValues.get(e.getKeyCode()),e.getKeyCode() + ""});
            ImageView.getImageView().nextImage();
            Settings.iteator++;
            ready = false;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ready = true;
    }
}
