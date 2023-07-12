package com.example.matanproject;

import java.util.ArrayList;

public interface Item {
    public String getTitle();
    public int getIconResource();
    public ArrayList<Item> getChilds();
}
