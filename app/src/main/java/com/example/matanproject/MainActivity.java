package com.example.matanproject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Item> itemsHierarchical;
    ListAdapter adapter;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] matanTikets = getResources().getStringArray(R.array.matan_tikets);
        itemsHierarchical = generateSomeHierarchy(matanTikets);

        adapter = new ListAdapter(this, itemsHierarchical, matanTikets);

        ListView mList = (ListView) this.findViewById(R.id.list_item);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.clickOnItem(position);
            }
        });

        search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    private ArrayList<Item> generateSomeHierarchy(String[] matanTikets) {
        itemsHierarchical = new ArrayList<Item>();
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
            itemsHierarchical.add(partTikets);
        }
        return itemsHierarchical;
    }
}