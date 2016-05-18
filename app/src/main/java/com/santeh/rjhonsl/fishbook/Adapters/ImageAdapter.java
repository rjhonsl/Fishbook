package com.santeh.rjhonsl.fishbook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class ImageAdapter extends ArrayAdapter<VarFishbook> {

	private Context context;
	private List<VarFishbook> feedList;
	
	private LruCache<Integer, Bitmap> imageCache;
	
	private RequestQueue queue;

	public ImageAdapter(Context context, int resource, List<VarFishbook> objects) {
		super(context, resource, objects);
		this.context = context;
		this.feedList = objects;
		
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
		final int cacheSize = maxMemory / 8;
		imageCache = new LruCache<>(cacheSize);
		
		queue = Volley.newRequestQueue(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.cardview_postimage, parent, false);

		//Display flower name in the TextView widget
		final VarFishbook newsFeeds = feedList.get(position);
		TextView tv = (TextView) view.findViewById(R.id.txtDesc);
		tv.setText(newsFeeds.getContent_description());

		//Display flower photo in ImageView widget
		Bitmap bitmap = imageCache.get(Integer.valueOf(newsFeeds.getContent_id()));
		final ImageView image = (ImageView) view.findViewById(R.id.img_content);
		if (bitmap != null) {
			image.setImageBitmap(bitmap);
		}
		else {

			String imageUrl = "http://www.santeh-webservice.com/images/androidimageupload_fishbook/" + newsFeeds.getContent_imageUrl();
			ImageRequest request = new ImageRequest(imageUrl,
					new Response.Listener<Bitmap>() {

						@Override
						public void onResponse(Bitmap arg0) {
							image.setImageBitmap(arg0);
							imageCache.put(Integer.valueOf(newsFeeds.getContent_id()), arg0);
						}
					},
					512, 256,
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

		return view;
	}

}
