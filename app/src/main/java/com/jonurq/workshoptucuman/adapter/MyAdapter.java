package com.jonurq.workshoptucuman.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonurq.workshoptucuman.R;
import com.jonurq.workshoptucuman.model.Item;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Serializable {

    private ArrayList<Item> items;

    public MyAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        }

        TextView title = (TextView)view.findViewById(R.id.item_title);
        TextView amount = (TextView)view.findViewById(R.id.item_amount);
        TextView id = (TextView)view.findViewById(R.id.item_id);
        TextView fecha = (TextView)view.findViewById(R.id.item_fecha);
        TextView status = (TextView)view.findViewById(R.id.item_status);
        ImageView imagen = view.findViewById(R.id.item_imagen);



        title.setText(items.get(position).getTitle());
        amount.setText(items.get(position).getAmount());
        id.setText(items.get(position).getId());
        fecha.setText(items.get(position).getFecha());
        status.setText(items.get(position).getStatus());

        new DownloadImageTask((ImageView) view.findViewById(R.id.item_imagen))
                .execute(items.get(position).getImagen());


        return view;
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
