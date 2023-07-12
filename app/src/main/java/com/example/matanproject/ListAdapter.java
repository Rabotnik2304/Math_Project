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

    private LayoutInflater mLayoutInflater; // 1
    private ArrayList<Item> hierarchyArray; // 2

    private ArrayList<Item> originalItems; // 3
    private LinkedList<Item> openItems; // 4

    public ListAdapter (Context ctx, ArrayList<Item> items) {
        mLayoutInflater = LayoutInflater.from(ctx);
        originalItems = items;

        hierarchyArray = new ArrayList<Item>();
        openItems = new LinkedList<Item>();

        generateHierarchy(); // 5
    }

    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position);
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

        Item item = hierarchyArray.get(position);

        title.setText(item.getTitle());
        title.setCompoundDrawablesWithIntrinsicBounds(item.getIconResource(), 0, 0, 0); // 6
        return convertView;
    }
    private void generateHierarchy() {
        hierarchyArray.clear(); // 1
        generateList(originalItems); // 2
    }

    private void generateList(ArrayList<Item> items) { // 3
        for (Item i : items) {
            hierarchyArray.add(i);
            if (openItems.contains(i))
                generateList(i.getChilds());
        }
    }
    public void clickOnItem (int position) {

        Item i = hierarchyArray.get(position);
        if (!openItems.remove(i))
            openItems.add(i);

        generateHierarchy();
        notifyDataSetChanged();
    }
}
