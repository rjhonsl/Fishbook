package com.santeh.rjhonsl.fishbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.VarFishbook;

import java.util.List;

/**
 * Created by rjhonsl on 5/18/2016.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<VarFishbook> newsFeedsList;
    private LruCache<Integer, Bitmap> imageCache;

    private RequestQueue queue;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDescription;
        public ImageView imagePreview;

        public MyViewHolder(View view) {
            super(view);
            txtDescription = (TextView) view.findViewById(R.id.txtDesc);
            imagePreview = (ImageView) view.findViewById(R.id.img_content);
        }
    }


    public RecyclerViewAdapter(List<VarFishbook> newsFeedsList, Context context) {
        this.newsFeedsList = newsFeedsList;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        queue = Volley.newRequestQueue(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_postimage, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VarFishbook newsFeedsObj = newsFeedsList.get(position);
        holder.txtDescription.setText(newsFeedsObj.getContent_description() + "hey");

        //Display flower photo in ImageView widget
        Bitmap bitmap = imageCache.get(Integer.valueOf(newsFeedsObj.getContent_id()));
        final ImageView image = holder.imagePreview;
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        }
        else {

            String imageUrl = "http://www.santeh-webservice.com/images/androidimageupload_fishbook/" + newsFeedsObj.getContent_imageUrl();
            ImageRequest request = new ImageRequest(imageUrl,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap arg0) {
                            image.setImageBitmap(arg0);
                            imageCache.put(Integer.valueOf(newsFeedsObj.getContent_id()), arg0);
                        }
                    },
                    512, 320,
                    Bitmap.Config.ARGB_8888,

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            Log.d("FlowerAdapter", arg0.getMessage());
                        }
                    }

            );
            queue.add(request);
        }

    }

    @Override
    public int getItemCount() {
        return newsFeedsList.size();
    }
}