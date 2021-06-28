package main.inner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class TableView extends JPanel {

    private static TableView tableView;
    private static int iterator;
    private static DefaultTableModel model;
    public static void addRowToModel(String[] v){
        model.addRow(v);
    }

    public static TableView getTableView(){
        if(tableView == null){
            tableView = new TableView();
        }
        return tableView;
    }
    private TableView(){
        setPreferredSize(new Dimension(300,500));
        model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(250,400));
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });        add(scrollPane);
        iterator = 0;
    }

    public static void addRow(String[] inupt){
        for(int i = 0; i < inupt.length;i++){
            if(i >= model.getColumnCount()){
                model.addColumn("i");
            }
        }
        model.addRow(inupt);
        iterator++;
    }
    public static String getCsvData(){
        String data = "";
        for(int i = 0; i < model.getRowCount(); i++){
            for (int j = 0; j < model.getColumnCount(); j++) {
                data += model.getValueAt(i,j) + ",";
            }
            data += "\n";
        }
        return data;
    }

}
