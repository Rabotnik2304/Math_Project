package com.example.matanproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Item> items; // 1
    ListAdapter adapter; // 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = generateSomeHierarchy(); // 3

        adapter = new ListAdapter(this, items); // 4

        ListView mList = (ListView) this.findViewById(R.id.list_item); // 5
        mList.setAdapter(adapter); // 6
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //7
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.clickOnItem(position); //8
            }
        });
    }

    private ArrayList<Item> generateSomeHierarchy() { // 9
        items = new ArrayList<Item>();

        ListItem li1 = new ListItem("Item 1");
        ListItem li2 = new ListItem("Item 2");
        ListItem li3 = new ListItem("Item 3");

        items.add(li1);
        items.add(li2);
        items.add(li3);

        ListItem li11 = new ListItem("Item 1.1");
        ListItem li12 = new ListItem("Item 1.2");
        ListItem li13 = new ListItem("Item 1.3");

        li1.addChild(li11);
        li1.addChild(li12);
        li1.addChild(li13);

        ListItem li21 = new ListItem("Item 2.1");
        ListItem li22 = new ListItem("Item 2.2");

        li2.addChild(li21);
        li2.addChild(li22);

        ListItem li211 = new ListItem("Item 2.1.1");
        ListItem li212 = new ListItem("Item 2.1.2");

        li21.addChild(li211);
        li21.addChild(li212);

        return items;
    }
}