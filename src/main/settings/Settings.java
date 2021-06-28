package main.settings;

import main.inner.ImageView;
import main.inner.TableView;
import main.utils.Dict;
import main.utils.Utils;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Settings {
    private static File tempFolder = new File(System.getProperty("user.home") + File.separator + ".video");
    public static File getTempFolder(){
        if(!tempFolder.exists() || !tempFolder.isDirectory()){
            tempFolder.mkdir();
        }
        return tempFolder;
    }
    public static Dict mappedValues;
    public static File videoFile;
    public static File csvFile;
    public static int iteator;
    public static int tempKeyChar = -1;
    public static DefaultTableModel model;
    private static JFrame frame;

    public static void openSettings(){
        frame = new JFrame("Settings");
        if(mappedValues == null) {
            mappedValues = new Dict();
        }
        frame.setPreferredSize(new Dimension(385,220));
        frame.add(makeSettingsPanel());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    public static JPanel makeSettingsPanel(){
        JPanel panel = new JPanel();
        JPanel left = new JPanel();
        JButton setVideo = new JButton("Open Video");
        setVideo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnval = fc.showOpenDialog(frame);
                if(returnval == JFileChooser.APPROVE_OPTION);
                videoFile = fc.getSelectedFile();
                Utils.initVideoCapture(videoFile);
                ImageView.getImageView().nextImage();
                ImageView.getImageView().repaint();
                model.setValueAt(videoFile.getAbsolutePath(),0,1);
            }
        });
        JButton saveCSV = new JButton("Save Csv File");
        saveCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnval = fc.showSaveDialog(frame);
                if(returnval == JFileChooser.APPROVE_OPTION);
                csvFile = fc.getSelectedFile();
                model.setValueAt(csvFile.getAbsolutePath(),1,1);
            }
        });
        JButton getKeyCode = new JButton("Key");
        getKeyCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(frame,"Press a key",true);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        super.keyPressed(e);
                        getKeyCode.setText(e.getKeyChar()+"");
                        tempKeyChar = e.getKeyCode();
                        dialog.dispose();
                    }
                });
                dialog.pack();
                dialog.setVisible(true);

            }
        });
        JTextField val = new JTextField(5);
        JButton map = new JButton("Map Key Text");
        map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tempKeyChar == -1 || val.getText() == ""){
//                    JDialog dialog = new JDialog(frame,"Please input a key and a value to map",true);
//                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//                    dialog.setSize(200,100);
//
//                    dialog.pack();
//                    dialog.setVisible(true);
                }
                model.addRow(new String[]{(char)tempKeyChar + "", val.getText()});
                mappedValues.put(tempKeyChar,val.getText());
                tempKeyChar = -1;
                getKeyCode.setText("Key");
                val.setText("");
            }
        });
        JButton reset = new JButton("Reset program");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new File(getTempFolder() + File.separator + "temp.txt").delete();
                System.exit(0);
            }
        });
        model = new DefaultTableModel();

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200,170));
        model.addColumn(1);
        model.addColumn(2);
        model.addRow(new String[]{"Video File",".."});
        model.addRow(new String[]{"CSV File",".."});
        model.addRow(new String[]{"Values Mapped",".."});
        if(videoFile != null){
            model.setValueAt(videoFile.getAbsolutePath(),0,1);
        }
        if(csvFile != null){
            model.setValueAt(csvFile.getAbsolutePath(),1,1);
        }
        for (int i = 0; i < mappedValues.size(); i++) {
            model.addRow(mappedValues.getRow(i));
        }
        GridBagConstraints c = new GridBagConstraints();
        left.setLayout(new GridBagLayout());
        c.anchor = GridBagConstraints.WEST;
        c.fill = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        left.add(setVideo,c);
        c.gridy++;
        left.add(saveCSV,c);
        c.gridy++;
        c.gridwidth = 1;
        left.add(getKeyCode,c);
        c.gridx++;
        left.add(val,c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth=2;
        left.add(map,c);
        c.gridy++;
        left.add(reset,c);
        panel.add(left,BorderLayout.CENTER);
        panel.add(scrollPane,BorderLayout.EAST);
        return panel;
    }
    public static void saveProgress(){
        if(videoFile == null || csvFile == null){
            return;
        }
        String fileData = "";
        fileData += videoFile.getAbsolutePath() + "\n";
        fileData += csvFile.getAbsolutePath() + "\n";
        fileData += iteator + "\n";
        fileData += mappedValues.getSaveString();
        try {
            FileWriter writer = new FileWriter(new File(getTempFolder() + File.separator + "temp.txt"),false);
            writer.write(fileData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter csv = new FileWriter(csvFile,false);
            csv.write(TableView.getCsvData());
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadPrevious(){
        try {
            Scanner scan = new Scanner(new File(getTempFolder() + File.separator + "temp.txt"));
            String temp = scan.nextLine();
            videoFile = new File(temp);
            Utils.initVideoCapture(videoFile);
            Utils.nextImage();
            temp = scan.nextLine();
            csvFile = new File(temp);
            temp = scan.nextLine();
            iteator = Integer.parseInt(temp);
            for (int i = 0; i < iteator; i++) {
                ImageView.getImageView().nextImage();
            }
            Scanner csvScanner = new Scanner(csvFile);
            while (csvScanner.hasNext()){
                String[] ar = csvScanner.nextLine().split(",");
                TableView.getTableView().addRow(ar);
            }
            int k;
            String v;
            if(mappedValues == null){
                mappedValues = new Dict();
            }
            while (scan.hasNext()){
                temp = scan.nextLine();
                k = Integer.parseInt(temp.split(",")[0]);
                v = temp.split(",")[1];
                mappedValues.put(k,v);
            }
            TableView.getTableView().repaint();
            ImageView.getImageView().repaint();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
