package com.santeh.rjhonsl.fishbook.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.Helper;
import com.santeh.rjhonsl.fishbook.Utils.VarFishBook;

import java.util.List;

/**
 * Created by rjhonsl on 5/18/2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<VarFishBook> newsFeedsList;
    private LruCache<Integer, Bitmap> imageCache;

    private RequestQueue queue;
    Context context1;
    private Activity activity1;
    ProgressDialog pd;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDateTime, txtFullName, txtComment;
        public ImageView imagePreview;
        public ImageButton btnContextMenu;
        public View view;

        public MyViewHolder(View view) {
            super(view);

            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtFullName = (TextView) view.findViewById(R.id.txtfullname);
            txtComment = (TextView) view.findViewById(R.id.txtComment);
            imagePreview = (ImageView) view.findViewById(R.id.img_userpic);
            btnContextMenu = (ImageButton) view.findViewById(R.id.btnOptions);

            this.view = view;
        }
    }


    public CommentsAdapter(List<VarFishBook> newsFeedsList, Context context, Activity activity) {
        this.newsFeedsList = newsFeedsList;
        context1 = context;
        activity1 = activity;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);

        queue = Volley.newRequestQueue(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comments, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VarFishBook newsFeedsObj = newsFeedsList.get(position);
        int difInHour = Helper.convert.getDateDifferenceHour(System.currentTimeMillis(), Long.valueOf(newsFeedsObj.getComment_dateCommented()));
        int difInMinutes = Helper.convert.getDateDifferenceMinute(System.currentTimeMillis(), Long.valueOf(newsFeedsObj.getComment_dateCommented()));
        String time = "";

        if (difInMinutes  == 0 ){
            time = "Just now";
        }else if (difInMinutes  < 2 ){
            time = difInMinutes + " minute ago";
        }else if(difInMinutes < 60){
            time = difInHour +  " minutes ago";
        }else if(difInHour == 1){
            time = difInHour + " hour ago";
        }else if(difInHour < 24){
            time = difInHour + " hours ago";
        }
        else if (difInHour < 48){
            time = "Yesterday "+ Helper.convert.LongToTime12Hour(Long.valueOf(newsFeedsObj.getComment_dateCommented()));
        }else {
            time = Helper.convert.LongToDate_ShortGregorian(Long.valueOf(newsFeedsObj.getComment_dateCommented()));
        }

        holder.txtDateTime.setText(time);

        holder.txtFullName.setText(newsFeedsObj.getCurrentUserFirstname() + " " + newsFeedsObj.getCurrentUserLastname());

        holder.txtComment.setText(newsFeedsObj.getComment_content());
        holder.btnContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context1, holder.btnContextMenu);
                popup.getMenuInflater().inflate(R.menu.contextmenu_owner, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId() == R.id.itmDelete) {
//                            pd = new ProgressDialog(context1);
//                            pd.setIndeterminate(false);
//                            pd.setMessage("Removing Post...");
//                            pd.setCancelable(false);
//                            pd.show();
//                        }
                        return true;
                    }
                });
                popup.show();

            }
        });
//
//        txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
//        txtFullName = (TextView) view.findViewById(R.id.txtfullname);
//        txtComment = (TextView) view.findViewById(R.id.txtComment);
//        imagePreview = (ImageView) view.findViewById(R.id.img_userpic);
//        btnContextMenu = (ImageButton) view.findViewById(R.id.btnOptions);

        /**
//        PopupMenu popup = new PopupMenu(context1, holder.btnContextMenu);
////                if (newsFeedsObj.getCurrentUserID().equalsIgnoreCase())
//        popup.getMenuInflater().inflate(R.menu.contextmenu_owner, popup.getMenu());
//
//        //registering popup with OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.itmDelete) {
//                    pd = new ProgressDialog(context1);
//                    pd.setIndeterminate(false);
//                    pd.setMessage("Removing Post...");
//                    pd.setCancelable(false);
//                    pd.show();
//                    RemovePost(context1, newsFeedsObj.getMain_id(), position,
//                            newsFeedsObj.getContent_imageUrl());
//                }
//                return true;
//            }
//        });
//        popup.show();

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


//        final ImageView imageView = holder.imagePreview;
//
//        if (newsFeedsObj.getContent_type().equalsIgnoreCase( MainActivity.CONTENT_IMAGE+"")){
//            final Bitmap bitmap = imageCache.get(Integer.valueOf(newsFeedsObj.getContent_id()));
//            imageView.setVisibility(View.VISIBLE);
//
//
//            if (bitmap != null) {
//                imageView.setImageBitmap(bitmap);
//            }
//            else {
//                String imageUrl = "http://www.santeh-webservice.com/images/androidimageupload_fishbook/" + newsFeedsObj.getContent_imageUrl();
//                ImageRequest request = new ImageRequest(imageUrl,
//                        new Response.Listener<Bitmap>() {
//                            @Override
//                            public void onResponse(final Bitmap arg0) {
//                                imageView.setImageBitmap(arg0);
//                                imageCache.put(Integer.valueOf(newsFeedsObj.getContent_id()), arg0);
//
//                                imageView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(context1, Activity_ViewImage.class);
//                                        intent.putExtra("imagename", newsFeedsObj.getContent_imageUrl());
//                                        Log.d("ImageView", "Start ImageView");
//                                        context1.startActivity(intent);
//                                    }
//                                });
//                            }
//                        },
//                        1024, imageView.getMeasuredHeight(),
//                        Bitmap.Config.ARGB_8888,
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError arg0) {
//                                Log.d("ImageRequestAdapter", arg0.getMessage());
//                            }
//                        }
//
//                );
//                queue.add(request);
//            }
//        }else{
//            imageView.setVisibility(View.GONE);
//        }
**/

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



//    private void RemovePost(final Context context, final String rowID, final int position, final String imageName){
//        StringRequest postRequest = new StringRequest(Request.Method.POST,"http://www.santeh-webservice.com/images/androidimageupload_fishbook/"+ "FBDeletePost.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(final String response) {
//                        Log.d("PHP DELETE", response);
//                        pd.dismiss();
//                        if (response.substring(1,2).equalsIgnoreCase("1")){
//                            removeAt(position);
//
//                            Toast.makeText(context, "Post has been removed", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", rowID);
//                params.put("username", "tsraqua");
//                params.put("password", "tsraqua");
//                params.put("imagename", imageName);
//                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));
//
//                return params;
//            }
//        };
//
//        MyVolleyAPI api = new MyVolleyAPI();
//        api.addToReqQueue(postRequest, context);
//    }

}