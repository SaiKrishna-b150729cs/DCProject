package com.dcproject.nodues;

/**
 * Created by M.Hemanth on 13-03-2018.
 */

public class Department {


    String name = null;
    boolean selected = false;

    public Department(String name, boolean selected) {
        super();

        this.name = name;
        this.selected = selected;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}