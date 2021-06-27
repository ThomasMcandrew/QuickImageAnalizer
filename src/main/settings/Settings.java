package main.settings;

import main.inner.TableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;

public class Settings {
    private static File tempFolder = new File(System.getProperty("user.home") + File.separator + ".video");
    public static File getTempFolder(){
        if(!tempFolder.exists() || !tempFolder.isDirectory()){
            tempFolder.mkdir();
        }
        return tempFolder;
    }
    public static Dictionary<Integer,String> mappedValues;
    public static File videoFile;
    public static File csvFile;
    public static int iteator;

    public static void openSettings(){
        JFrame frame = new JFrame("Settings");
        frame.setPreferredSize(new Dimension(300,300));
        
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void saveProgress(){
        String fileData = "";
        fileData += videoFile.getAbsolutePath() + "\n";
        fileData += csvFile.getAbsolutePath() + "\n";
        fileData += iteator + "\n";
        try {
            FileWriter writer = new FileWriter(new File(getTempFolder() + File.separator + "temp.txt"));
            writer.write(fileData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter csv = new FileWriter(csvFile);
            csv.write(TableView.getCsvData());
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void loadPrevious(){

    }

}
