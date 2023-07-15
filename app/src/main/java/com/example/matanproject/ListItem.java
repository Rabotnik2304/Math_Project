package com.example.matanproject;

import java.util.ArrayList;

public class ListItem implements Item {

    private String title;
    private ArrayList<Item> childs;

    public ListItem (String title) {
        this.title = title;
        childs = new ArrayList<Item>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ArrayList<Item> getChilds() {
        return childs;
    }

    @Override
    public int getIconResource() {
        if (childs.size() == 0)
            return R.drawable.paper;
        if (childs.get(0).getChilds().size() == 0)
            return R.drawable.book;
        return R.drawable.library;
    }

    public void addChild (Item item) {
        childs.add(item);
    }
}
