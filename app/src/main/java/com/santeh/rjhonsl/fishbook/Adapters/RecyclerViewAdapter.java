package com.santeh.rjhonsl.fishbook.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.santeh.rjhonsl.fishbook.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.fishbook.Main.Activity_Comments;
import com.santeh.rjhonsl.fishbook.Main.Activity_ViewImage;
import com.santeh.rjhonsl.fishbook.Main.MainActivity;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.Helper;
import com.santeh.rjhonsl.fishbook.Utils.VarFishBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 5/18/2016.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<VarFishBook> newsFeedsList;
    private LruCache<Integer, Bitmap> imageCache;

    private RequestQueue queue;
    Context context1;
    ProgressDialog pd;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDescription, txtDateTime, txtLocation, txtFullName, txtComments;
        public ImageView imagePreview;
        public ImageButton btnContextMenu;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            txtDescription = (TextView) view.findViewById(R.id.txtDesc);
            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtLocation = (TextView) view.findViewById(R.id.txtlocation);
            txtComments = (TextView) view.findViewById(R.id.txtlinkcomments);
            txtFullName = (TextView) view.findViewById(R.id.txtfullname);
            btnContextMenu = (ImageButton) view.findViewById(R.id.btnPostOptions);

            imagePreview = (ImageView) view.findViewById(R.id.img_content);

            this.view = view;
        }
    }


    public RecyclerViewAdapter(List<VarFishBook> newsFeedsList, Context context) {
        this.newsFeedsList = newsFeedsList;
        context1 = context;

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VarFishBook newsFeedsObj = newsFeedsList.get(position);

        String commentLink = holder.txtComments.getText().toString();

        if (newsFeedsObj.getCommentCount() > 0){
            holder.txtComments.setText(commentLink+"("+newsFeedsObj.getCommentCount()+")");
        }else{
            holder.txtComments.setText(commentLink+"");
        }


        holder.txtComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, Activity_Comments.class);
                intent.putExtra("postid", newsFeedsObj.getMain_id()+"");
                context1.startActivity(intent);
            }
        });

        holder.btnContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context1, holder.btnContextMenu);
//                if (newsFeedsObj.getCurrentUserID().equalsIgnoreCase())
                popup.getMenuInflater().inflate(R.menu.contextmenu_owner, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.itmDelete) {
                            pd = new ProgressDialog(context1);
                            pd.setIndeterminate(false);
                            pd.setMessage("Removing Post...");
                            pd.setCancelable(false);
                            pd.show();
                          RemovePost(context1, newsFeedsObj.getMain_id(), position,
                                  newsFeedsObj.getContent_imageUrl());
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return true;
//            }
//        });

        if (newsFeedsObj.getContent_description().equalsIgnoreCase("")){
            holder.txtDescription.setVisibility(View.GONE);
        }else{
            holder.txtDescription.setText(newsFeedsObj.getContent_description());
            holder.txtDescription.setVisibility(View.VISIBLE);
        }


        if (!newsFeedsObj.getMain_fetchat().equalsIgnoreCase("")  && !newsFeedsObj.getMain_fetchat().trim().equalsIgnoreCase("0")){
            holder.txtLocation.setText(newsFeedsObj.getMain_fetchat());
        }else{
            holder.txtLocation.setText("");
        }

        holder.txtFullName.setText(newsFeedsObj.getCurrentUserFirstname() + " " + newsFeedsObj.getCurrentUserLastname());
        holder.txtDateTime.setText(Helper.convert.getDateTimePassed(newsFeedsObj.getMain_date()));
        final ImageView imageView = holder.imagePreview;


        if (newsFeedsObj.getContent_type().equalsIgnoreCase(MainActivity.CONTENT_IMAGE+"")){
            final Bitmap bitmap = imageCache.get(Integer.valueOf(newsFeedsObj.getContent_id()));
            imageView.setVisibility(View.VISIBLE);


            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            else {
                String imageUrl = "http://www.santeh-webservice.com/images/androidimageupload_fishbook/" + newsFeedsObj.getContent_imageUrl();
                ImageRequest request = new ImageRequest(imageUrl,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(final Bitmap arg0) {
                                imageView.setImageBitmap(arg0);
                                imageCache.put(Integer.valueOf(newsFeedsObj.getContent_id()), arg0);

                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context1, Activity_ViewImage.class);
                                        intent.putExtra("imagename", newsFeedsObj.getContent_imageUrl());
                                        intent.putExtra("postid", newsFeedsObj.getContent_mainid());
                                        intent.putExtra("commentcount", newsFeedsObj.getCommentCount());
                                        Log.d("ImageView", "Start ImageView");
                                        context1.startActivity(intent);
                                    }
                                });
                            }
                        },
                        1024, imageView.getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                Log.d("ImageRequestAdapter", arg0.getMessage());
                            }
                        }

                );
                queue.add(request);
            }
        }else{
            imageView.setVisibility(View.GONE);
        }


    }



    @Override
    public int getItemCount() {
        return newsFeedsList.size();
    }


    public void removeAt(int position) {
        newsFeedsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, newsFeedsList.size());
    }



    private void RemovePost(final Context context, final String rowID, final int position, final String imageName){
        StringRequest postRequest = new StringRequest(Request.Method.POST,"http://www.santeh-webservice.com/images/androidimageupload_fishbook/"+ "FBDeletePost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d("PHP DELETE", response);
                        pd.dismiss();
                        if (response.substring(1,2).equalsIgnoreCase("1")){
                            removeAt(position);

                            Toast.makeText(context, "Post has been removed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", rowID);
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("imagename", imageName);
                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }

}