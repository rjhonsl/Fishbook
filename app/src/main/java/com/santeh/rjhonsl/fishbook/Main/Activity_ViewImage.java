package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.santeh.rjhonsl.fishbook.R;


/**
 * Created by rjhonsl on 4/15/2016.
 */
public class Activity_ViewImage extends FragmentActivity {
    SubsamplingScaleImageView imageView;
    Intent receivedIntent;
    String filename;
    private RequestQueue queue;
    Context context;
    LinearLayout llTopViewImage, llcomment;
    Activity activity;
    boolean isFullImageView = true;
    String baseImageFolder = "http://www.santeh-webservice.com/images/androidimageupload_fishbook/";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);
        context = Activity_ViewImage.this;
        activity = this;

        queue = Volley.newRequestQueue(context);
        if (getIntent()!= null){
            receivedIntent = getIntent();
        }

        llTopViewImage = (LinearLayout) findViewById(R.id.ll_top_postimage);
        llcomment = (LinearLayout) findViewById(R.id.ll_comments);
        imageView= (SubsamplingScaleImageView)findViewById(R.id.imageView);
        imageView.setZoomEnabled(true);
        imageView.setImage(ImageSource.resource(R.drawable.ic_photo_512x));

//        intent.putExtra("postid", newsFeedsObj.getContent_mainid());
//        intent.putExtra("commentcount", newsFeedsObj.getCommentCount());
        final String postid = receivedIntent.getStringExtra("postid");
        int commentcount = receivedIntent.getIntExtra("commentcount", 0);

        if (receivedIntent.hasExtra("imagename")){
            filename =  receivedIntent.getStringExtra("imagename");
            retrieveImage(baseImageFolder + filename);
//            Helper.toast.long_(activity, "Retrieving Image");
        }

        llTopViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Helper.toast.short_(activity, "comment was selected");
                Intent intent = new Intent(context, Activity_Comments.class);
                intent.putExtra("postid", postid+"");
                context.startActivity(intent);
            }
        });

        imageView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });



        imageView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    llcomment.setVisibility(View.GONE);
                    llTopViewImage.setVisibility(View.GONE);
                    isFullImageView = false;
                }else if(event.getActionMasked() == MotionEvent.ACTION_UP){
                    llcomment.setVisibility(View.VISIBLE);
                    llTopViewImage.setVisibility(View.VISIBLE);
                    isFullImageView = true;
                }

                return false;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isFullImageView){
                    llcomment.setVisibility(View.GONE);
                    llTopViewImage.setVisibility(View.GONE);
                    isFullImageView = false;
                }else{
                    llcomment.setVisibility(View.VISIBLE);
                    llTopViewImage.setVisibility(View.VISIBLE);
                    isFullImageView = true;
                }
                return false;
            }
        });
    }

    private void retrieveImage(String imageUrl){
        ImageRequest request = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(final Bitmap bitmap) {
                        imageView.setImage(ImageSource.bitmap(bitmap));
                    }
                },
                1024, imageView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        Log.d("ImageRequestAdapter", arg0.toString()+"");
                    }
                }

        );
        queue.add(request);
    }
}
