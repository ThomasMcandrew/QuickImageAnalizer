package main.utils;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Dict extends Dictionary<Integer,String> {
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.size() == 0;
    }

    @Override
    public Enumeration<Integer> keys() {
        return null;
    }

    @Override
    public Enumeration<String> elements() {
        return null;
    }
    public String[] getRow(int i){
        return new String[]{keys.get(i) + "(" + (char) (int) (keys.get(i)) +")",values.get(i)};

    }
    public String getSaveString(){
        String val = "";
        for (int i = 0; i < keys.size(); i++) {
            val += keys.get(i) + ","+values.get(i) + "\n";
        }
        return val;
    }
    @Override
    public String get(Object key) {
        Integer i = (Integer) key;
        for (int j = 0; j < keys.size(); j++) {
            if(keys.get(j).compareTo(i) == 0){
                return values.get(j);
            }
        }
        return null;
    }

    @Override
    public String put(Integer key, String value) {
        keys.add(key);
        values.add(value);
        return null;
    }

    @Override
    public String remove(Object key) {
        return null;
    }
}
