package com.example.photoapp160;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyPhotos extends AppCompatActivity {

    private ListView listView;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);
        listView = findViewById(R.id.listView);

        listView.setAdapter(new CustomAdapter());
    }

    private class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.image_adapter, null);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView name = convertView.findViewById(R.id.name);
            TextView disc = convertView.findViewById(R.id.disc);

            return convertView;
        }
    }
}