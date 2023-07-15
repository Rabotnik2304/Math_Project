package com.example.matanproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Item> items;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = generateSomeHierarchy();

        adapter = new ListAdapter(this, items);

        ListView mList = (ListView) this.findViewById(R.id.list_item);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.clickOnItem(position);
            }
        });
    }

    private ArrayList<Item> generateSomeHierarchy() {

        String[] matanTikets = getResources().getStringArray(R.array.matan_tikets);

        items = new ArrayList<Item>();
        int i=0;

        while (i<matanTikets.length && matanTikets[i].indexOf('|')==2){
            ListItem partTikets = new ListItem(matanTikets[i]);
            i++;
            while (i<matanTikets.length && matanTikets[i].indexOf('|')==5) {
                ListItem tiket = new ListItem(matanTikets[i]);
                i++;
                while (i<matanTikets.length && matanTikets[i].indexOf('|')==8) {
                    tiket.addChild(new ListItem(matanTikets[i]));
                    i++;
                }
                partTikets.addChild(tiket);
            }
            items.add(partTikets);
        }
        return items;
    }
}