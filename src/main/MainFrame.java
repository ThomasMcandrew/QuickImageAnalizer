package main;

import main.inner.ImageView;
import main.inner.TableView;
import main.menu.MyMenuBar;
import main.settings.Settings;
import main.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Settings.saveProgress();
                System.exit(0);
            }
        });
        try{
            Settings.loadPrevious();
        }catch (Exception e){

        }
        setVisible(true);
        pack();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(ready && Utils.captureReady()) {
            TableView.addRow(new String[]{Settings.iteator + "" ,Settings.mappedValues.get(e.getKeyCode())});
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
