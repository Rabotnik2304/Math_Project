package com.example.matanproject;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;


public class ListAdapter extends BaseAdapter implements Filterable {
    private class Pair {
        Item item;
        int level;

        Pair (Item item, int level) {
            this.item = item;
            this.level = level;
        }
    }
    private LayoutInflater mLayoutInflater;
    private ArrayList<Pair> hierarchyArray;
    private ModelFilter filter;
    private ArrayList<Item> originalHierarchicalItems;
    private LinkedList<Item> openItems;
    private ArrayList<String> lowestHierarchicalItems;
    private String[] linksToPhotos;
    private boolean searchActivate = false;

    public ListAdapter (Context ctx, ArrayList<Item> itemsHierarchical, ArrayList<String> lowestHierarchicalItems, String[] linksToPhotos) {
        mLayoutInflater = LayoutInflater.from(ctx);
        originalHierarchicalItems = itemsHierarchical;

        hierarchyArray = new ArrayList<Pair>();
        openItems = new LinkedList<Item>();

        this.lowestHierarchicalItems = lowestHierarchicalItems;
        this.linksToPhotos = linksToPhotos;
        generateHierarchy();
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

        title.setAllCaps(false);
        title.setTypeface(null, Typeface.NORMAL);

        Pair pair = hierarchyArray.get(position);

        title.setText(pair.item.getTitle());
        title.setCompoundDrawablesWithIntrinsicBounds(pair.item.getIconResource(), 0, 0, 0);
        title.setPadding((pair.level+1) * 30, 0, 0, 0);

        if (!searchActivate) {
            if (pair.level == 0) {
                title.setAllCaps(true);
                title.setTypeface(null, Typeface.BOLD);
            } else if (pair.level == 1) {
                title.setTypeface(null, Typeface.BOLD);
            }
        }
        return convertView;
    }
    private void generateHierarchy() {
        hierarchyArray.clear();
        generateList(originalHierarchicalItems,0);
    }

    private void generateList(ArrayList<Item> items, int level) {
        for (Item i : items) {
            hierarchyArray.add(new Pair(i,level));
            if (openItems.contains(i))
                generateList(i.getChilds(),level+1);
        }
    }
    public void clickOnItem (int position) {

        if (searchActivate || lowestHierarchicalItems.contains(hierarchyArray.get(position).item)) {
            int pointPartNumber =  hierarchyArray.get(position).item.getTitle().charAt(0) -48;
            int pointTiketNumber = Integer.parseInt(hierarchyArray.get(position).item.getTitle().substring(2,4));

            int partNumber = 0;
            int tiketNumber = 0;
            boolean tiketFouned = false;
            for (String line:linksToPhotos) {
                if (line.contains("Билет 1")){
                    tiketNumber = 1;
                    partNumber ++;
                }
                if (partNumber == pointPartNumber&& tiketNumber==pointTiketNumber){
                    tiketFouned = true;
                }
                if (tiketFouned&&!line.contains("Билет")){

                }else{
                    break;
                }
            }
        } else {

            Item i = hierarchyArray.get(position).item;
            if (!closeItem(i))
                openItems.add(i);

            generateHierarchy();
            notifyDataSetChanged();
        }
    }
    private boolean closeItem (Item i) {
        if (openItems.remove(i)) {
            for (Item c : i.getChilds())
                 closeItem(c);
            return true;
        }
        return false;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new ModelFilter();
        }
        return filter;
    }

    private class ModelFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint.toString().length() > 0)
            {
                searchActivate = true;
                ArrayList<Pair> filteredItems = new ArrayList<Pair>();

                for(int i = 0; i < lowestHierarchicalItems.size(); i++)
                {
                    if(lowestHierarchicalItems.get(i).toLowerCase().contains(constraint))
                        filteredItems.add(new Pair(new ListItem(lowestHierarchicalItems.get(i)),0));
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }else{
                synchronized(this)
                {
                    searchActivate = false;
                    generateHierarchy();
                    result.values = hierarchyArray;
                    result.count = hierarchyArray.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hierarchyArray = (ArrayList<Pair>)results.values;
            notifyDataSetChanged();
        }
    }
}

