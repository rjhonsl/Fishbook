package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.fishbook.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.fishbook.Adapters.RecyclerViewAdapter;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.FusedLocation;
import com.santeh.rjhonsl.fishbook.Utils.Helper;
import com.santeh.rjhonsl.fishbook.Utils.NewsFeedsParser;
import com.santeh.rjhonsl.fishbook.Utils.VarFishBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity  extends AppCompatActivity {

    Activity activity;
    Context context;
    RecyclerView recyclerView;
    RecyclerViewAdapter rcAdapter;

    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_FILE = 2;

    ProgressDialog loading;
    FloatingActionButton fabAddPost;
    LinearLayout llbottomwrapper;

    FusedLocation fusedLocation;
    Boolean isBottomAnimating = false;
    Boolean isFabSelected = false;
    String currentUploadID = null;

    List<VarFishBook> newsFeedList;
    public static int CONTENT_TEXT = 0;
    public static int CONTENT_IMAGE = 1;
    public static int CONTENT_FILE = 2;
    public static int CONTENT_EVENT = 3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activity = this;
        context = MainActivity.this;

        loading = new ProgressDialog(context);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setMessage("Uploading");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        assert myToolbar != null;
        myToolbar.setBackgroundColor(getResources().getColor(R.color.blue_400));
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_search);

        ActionBar ab = getSupportActionBar();

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();



        fabAddPost = (FloatingActionButton) findViewById(R.id.fab_addpost);
        llbottomwrapper = (LinearLayout) findViewById(R.id.ll_wrapper_bottom);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        registerForContextMenu(recyclerView);

        //add ItemDecoration
        int VERTICAL_ITEM_SPACE = 40;
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
//        //or
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity));
//        //or
//        recyclerView.addItemDecoration(new DividerItemDecoration(activity, R.drawable.line_divider));

        LinearLayout llpostSomething = (LinearLayout) findViewById(R.id.ll_postSomething);
        LinearLayout lluploadPhoto = (LinearLayout) findViewById(R.id.ll_uploadPhoto);
        LinearLayout lluploadFile = (LinearLayout) findViewById(R.id.ll_uploadfile);


        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFabSelected = true;
                animateBottom();
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (llbottomwrapper.getVisibility() == View.VISIBLE) {
                    isFabSelected = false;
                    if (!isBottomAnimating){
                        animateBottom();
                    }
                }
                return false;
            }
        });

        assert lluploadPhoto != null;
        lluploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, Activity_PostImage.class));


            }
        });


        assert llpostSomething != null;
        llpostSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Activity_PostText.class);
                startActivity(intent);
            }
        });

        assert lluploadFile != null;
        lluploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                String[] mimetypes = {
                        "text/*",
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "application/vnd.ms-powerpoint",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                        "application/x-rar-compressed",
                        "application/zip"
                };
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select a file"), SELECT_FILE);

            }
        });



        requestDataFeedData();
    }


    private void requestDataFeedData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.sourceAddress_goDaddy+"FBselectNewsFeeds.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                            Log.d("ResponseAdapter", "0 Failed");

                        } else {
                            Log.d("ResponseAdapter", "1 Success: "+ response);
//                            Helper.dialogBox.okOnly_Scrolling(activity, "Response", response, "OK", R.color.blue_400);
                            newsFeedList = NewsFeedsParser.parseFeed(response, context);
                            rcAdapter = new RecyclerViewAdapter(newsFeedList, context);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(rcAdapter);
                            rcAdapter.notifyDataSetChanged();

//                            newsFeedList = new ArrayList<>();
//
//                            ImageAdapter adapter = new ImageAdapter(context, R.layout.cardview_postimage, newsFeedList);

//                            lvNewsFeeds.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.dialogBox.okOnly_Scrolling(activity,"Err" ,""+error, "OK", R.color.red);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));
                params.put("userid",  "11");
                params.put("userlvl", "4");

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }



    private void animateBottom(){
        Animation animatelayout;
        int visibility;

        if (isFabSelected){
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
            animatelayout.setDuration(300);
            animatelayout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    llbottomwrapper.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            visibility = View.VISIBLE;

            fabAddPost.setVisibility(View.GONE);
            llbottomwrapper.startAnimation(animatelayout);

        }
        else{
            animatelayout  = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
            animatelayout.setDuration(300);
            animatelayout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isBottomAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isBottomAnimating = false;
                    llbottomwrapper.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fabAddPost.setVisibility(View.VISIBLE);
            llbottomwrapper.startAnimation(animatelayout);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }

        };

        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.action_search);

        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            Helper.toast.long_(activity, "Refreshing Contents");
            requestDataFeedData();
        }
        return super.onOptionsItemSelected(item);

    }

    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) { //
//                Uri selectedFileUri = data.getData();
//                Uri filePath = data.getData();
//
//                File file = getPath(selectedFileUri);
//                selectedFilePath = file.getAbsoluteFile().getName();
//
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedFileUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                encodeImagetoString(bitmap);
//
//
//            }else if (requestCode == SELECT_FILE) {
//                Helper.toast.indefinite(activity, "path:"+ "" + "" +
////                        "\nfile name:"+ filename +"" +
////                        "\nfile type:"+ filetype +"" +
//                        "");
//            }
//
//
//
////            if(requestCode == SELECT_FILE){
////
////
////
//////                Uri uri = data.getData();
//////                fileURI = uri;
//////                String fullpath = FileUtils.getPath(context, uri);
//////                assert fullpath != null;
////////                String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
////////                String filetype = fullpath.substring(fullpath.lastIndexOf(".")+1);
////
////                Helper.indefinite(activity, "path:"+ FileUtils.getPath(context, uri) + "" +
//////                        "\nfile name:"+ filename +"" +
//////                        "\nfile type:"+ filetype +"" +
////                        "");
//////                new UploadFileAsync().execute("");
////
////            }
//        }
//
//
//    }





//    public void encodeImagetoString(final Bitmap bmp ) {
//        new AsyncTask<Void, Void, String>() {
//
//            protected void onPreExecute() {
//                loading.show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                byte[] imageBytes = stream.toByteArray();
//                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                return "";
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                loading.setMessage("Uploading...");
//                params = new RequestParams();
//                params.put("image", encodedImage);
//                params.put("imagename", selectedFilePath);
//                params.put("username", "tsraqua");
//                params.put("password", "tsraqua");
//                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));
//                params.put("userid",  "11");
//                params.put("userlvl", "4");
//                // Trigger Image upload
//                makeHTTPCall();
//            }
//        }.execute(null, null, null);
//    }
//
//
//    public void makeHTTPCall() {
//        loading.setMessage("Invoking Php");
//        AsyncHttpClient client = new AsyncHttpClient();
//        // Don't forget to change the IP address to your LAN address. Port no as well.
//        client.post("http://www.santeh-webservice.com/images/androidimageupload_fishbook/feedphoto.php",
//                params, new AsyncHttpResponseHandler() {
//                    // When the response returned by REST has Http
//                    // response code '200'
//                    @Override
//                    public void onSuccess(String response) {
//                        // Hide Progress Dialog
//                        loading.hide();
//                        Toast.makeText(getApplicationContext(), response,
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    // When the response returned by REST has Http
//                    // response code other than '200' such as '404',
//                    // '500' or '403' etc
//                    @Override
//                    public void onFailure(int statusCode, Throwable error,
//                                          String content) {
//                        // Hide Progress Dialog
//                        loading.hide();
//                        // When Http response code is '404'
//                        if (statusCode == 404) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Requested resource not found",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code is '500'
//                        else if (statusCode == 500) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Something went wrong at server end",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code other than 404, 500
//                        else {
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
//                                            + error.toString() + " "
//                                            + statusCode, Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    }
//                });
//    }
//
//
//    public File getPath(Uri uri) {
//        return new File(uri.getPath());
//    }




//    public class Initializer extends Application {
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            // setup the broadcast action namespace string which will
//            // be used to notify upload status.
//            // Gradle automatically generates proper variable as below.
//            UploadService.HTTP_STACK = new OkHttpStack();
//            UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
//        }
//    }


//    public void uploadMultipart(final Context context, String AbsFilePath) {
//        try {
//            currentUploadID =
//                    new MultipartUploadRequest(context, "http://www.santeh-webservice.com/uploadedfiles/")
//                            .addFileToUpload(selectedFilePath, "your-param-name")
//                            .addParameter("filename", selectedFile.getName())
//                            .addParameter("username", "tsraqua")
//                            .addParameter("password", "tsraqua")
//                            .addParameter("deviceid", Helper.getMacAddress(context))
//                            .addParameter("userid",  "11")
//                            .addParameter("userlvl", "4")
//                            .setNotificationConfig(new UploadNotificationConfig())
//                            .setMaxRetries(2)
//                            .startUpload();
//        } catch (Exception exc) {
//            Log.e("AndroidUploadService", exc.getMessage(), exc);
//        }
//    }


    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider =  ContextCompat.getDrawable(context,R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }

}
