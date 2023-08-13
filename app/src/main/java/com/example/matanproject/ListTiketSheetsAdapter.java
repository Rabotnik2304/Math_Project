package com.example.matanproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ListTiketSheetsAdapter extends RecyclerView.Adapter<ListTiketSheetsAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Bitmap> photos;

    ListTiketSheetsAdapter(Context ctx, List<Bitmap> photos) {
        this.inflater = LayoutInflater.from(ctx);
        this.photos = photos;
    }
    @Override
    public ListTiketSheetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.tiket_sheet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTiketSheetsAdapter.ViewHolder holder, int position) {
        Bitmap photo = photos.get(position);
        holder.photo.setImageBitmap(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView photo;
        ViewHolder(View view){
            super(view);
            photo = view.findViewById(R.id.photo);
        }
    }
}
