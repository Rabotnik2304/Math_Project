package com.example.matanproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityForTiketDemonstration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_tiket_demonstration);

        Bundle arguments = getIntent().getExtras();
        ArrayList<String> linksToPhotos  = arguments.getStringArrayList("tiketPhotos");
        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        for(String link:linksToPhotos)
        {
            photos.add(getBitmapFromAsset(this,link));
        }

        RecyclerView tiketSheets = findViewById(R.id.tiket_sheets);
        ListTiketSheetsAdapter adapter = new ListTiketSheetsAdapter(this, photos);

        tiketSheets.setAdapter(adapter);
    }
    public Bitmap getBitmapFromAsset(Context context, String filePath) {
        InputStream inputStream;
        Bitmap bitmap = null;
        try {
            inputStream=getAssets().open(filePath);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }
}