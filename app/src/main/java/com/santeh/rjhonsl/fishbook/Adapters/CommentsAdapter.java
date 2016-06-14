package com.santeh.rjhonsl.fishbook.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.santeh.rjhonsl.fishbook.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.Helper;
import com.santeh.rjhonsl.fishbook.Utils.VarFishBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        public LinearLayout ll_commentContents;
        public View view;

        public MyViewHolder(View view) {
            super(view);

            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtFullName = (TextView) view.findViewById(R.id.txtfullname);
            txtComment = (TextView) view.findViewById(R.id.txtComment);
            imagePreview = (ImageView) view.findViewById(R.id.img_userpic);
            btnContextMenu = (ImageButton) view.findViewById(R.id.btnOptions);
            ll_commentContents = (LinearLayout) view.findViewById(R.id.ll_item_comments);

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

        holder.txtDateTime.setText(Helper.convert.getDateTimePassed(newsFeedsObj.getComment_dateCommented()));

        holder.txtFullName.setText(newsFeedsObj.getCurrentUserFirstname() + " " + newsFeedsObj.getCurrentUserLastname());
        holder.txtFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.toast.short_(activity1, newsFeedsObj.getCurrentUserFirstname() + " " + newsFeedsObj.getCurrentUserLastname());
            }
        });

        holder.txtComment.setText(newsFeedsObj.getComment_content());
        holder.ll_commentContents.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(context1, holder.txtComment);
                popup.getMenuInflater().inflate(R.menu.contextmenu_comment_owner, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.itmDelete) {
                            pd = new ProgressDialog(context1);
                            pd.setIndeterminate(false);
                            pd.setMessage("Removing comment...");
                            pd.setCancelable(false);
                            pd.show();
                            removeComment(context1,newsFeedsObj.getComment_Id(), position);
                        }else if (item.getItemId() == R.id.itmEdit) {
//                            pd = new ProgressDialog(context1);
//                            pd.setIndeterminate(false);
//                            pd.setMessage("Removing Post...");
//                            pd.setCancelable(false);
//                            pd.show();
                        }
                        return true;
                    }
                });
                popup.show();
                return false;
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



    private void removeComment(final Context context, final String rowID, final int position){
        StringRequest postRequest = new StringRequest(Request.Method.POST,Helper.variables.sourceAddress_goDaddy+ "FBDeleteComment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d("PHP DELETE", response);
                        pd.dismiss();
                        if (response.substring(1,2).equalsIgnoreCase("1")){
                            removeAt(position);
                            Toast.makeText(context, "Comment has been removed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error removing", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PHP DELETE", error.toString());
                        Toast.makeText(context, "Error removing", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", rowID);
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }

}