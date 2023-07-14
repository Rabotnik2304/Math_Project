package com.example.matanproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.matanproject.Item;
import com.example.matanproject.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListAdapter extends BaseAdapter {
    private class Pair {
        Item item;
        int level;

        Pair (Item item, int level) {
            this.item = item;
            this.level = level;
        }
    }
    private LayoutInflater mLayoutInflater; // 1
    private ArrayList<Pair> hierarchyArray; // 2

    private ArrayList<Item> originalItems; // 3
    private LinkedList<Item> openItems; // 4

    public ListAdapter (Context ctx, ArrayList<Item> items) {
        mLayoutInflater = LayoutInflater.from(ctx);
        originalItems = items;

        hierarchyArray = new ArrayList<Pair>();
        openItems = new LinkedList<Item>();

        generateHierarchy(); // 5
    }

    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.row, null);

        TextView title = (TextView)convertView.findViewById(R.id.title);

        Pair pair = hierarchyArray.get(position);

        title.setText(pair.item.getTitle());
        title.setCompoundDrawablesWithIntrinsicBounds(pair.item.getIconResource(), 0, 0, 0);
        title.setPadding((pair.level+1) * 20, 0, 0, 0);
        return convertView;
    }
    private void generateHierarchy() {
        hierarchyArray.clear();
        generateList(originalItems,0);
    }

    private void generateList(ArrayList<Item> items, int level) {
        for (Item i : items) {
            hierarchyArray.add(new Pair(i,level));
            if (openItems.contains(i))
                generateList(i.getChilds(),level+1);
        }
    }
    public void clickOnItem (int position) {

        Item i = hierarchyArray.get(position).item;
        if (!closeItem(i))
            openItems.add(i);

        generateHierarchy();
        notifyDataSetChanged();
    }

    private boolean closeItem (Item i) {
        if (openItems.remove(i)) {
            for (Item c : i.getChilds())
                 closeItem(c);
            return true;
        }
        return false;
    }
}
