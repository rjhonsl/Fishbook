package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.fishbook.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.fishbook.Adapters.CommentsAdapter;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.FusedLocation;
import com.santeh.rjhonsl.fishbook.Utils.Helper;
import com.santeh.rjhonsl.fishbook.Utils.NewsFeedsParser;
import com.santeh.rjhonsl.fishbook.Utils.VarFishBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjhonsl on 5/28/2016.
 */
public class Activity_Comments extends AppCompatActivity {

    private Activity activity;
    private Context context;

    private List<VarFishBook> newsFeedList;
    private RecyclerView rvComments;
    private CommentsAdapter rcAdapter;

    String postid;
    EditText edtAddComment;
    ImageView btnAddComment;
    FusedLocation fusedLocation;
    ProgressDialog pd;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        activity = this;
        context = Activity_Comments.this;

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();

        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setCancelable(true);

        rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        mLinearLayoutManager = new LinearLayoutManager(activity);
        rvComments.setLayoutManager(mLinearLayoutManager);
        registerForContextMenu(rvComments);

        postid = getIntent().getStringExtra("postid");



//        Helper.toast.short_(activity, postid);

        btnAddComment = (ImageView) findViewById(R.id.btnAddcomment);
        edtAddComment = (EditText) findViewById(R.id.edtAddComment);

        btnAddComment.setEnabled(false);

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocation.connectToApiClient();
                addCommentData();
                pd.setMessage("Posting comment...");
                pd.show();
            }
        });

        edtAddComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtAddComment.getText().toString().length() > 0){
                    btnAddComment.setEnabled(true);
                }else{
                    btnAddComment.setEnabled(false);
                }
            }
        });

        requestCommentsData();

    }



    private void requestCommentsData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.sourceAddress_goDaddy+"FBselectCommentByID.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                            Log.d("ResponseAdapter", "0 Failed"+response);
                            Helper.toast.snackbarWithAction(activity, "Can't connect...")
                                    .setActionTextColor(getResources().getColor(R.color.blue_200))
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            requestCommentsData();
                                        }
                                    });

                        } else {
                            Log.d("ResponseAdapter", "1 Success: "+ response);
                            newsFeedList = NewsFeedsParser.parseFeed(response, context);
                            if (newsFeedList != null){
                                rcAdapter = new CommentsAdapter(newsFeedList, context, activity);
//
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                rvComments.setLayoutManager(mLayoutManager);
                                rvComments.setItemAnimator(new DefaultItemAnimator());
                                rvComments.setAdapter(rcAdapter);
                                rcAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Helper.dialogBox.okOnly_Scrolling(activity,"Err" ,""+error, "OK", R.color.red);
                        Helper.toast.snackbarWithAction(activity, "Can't connect...")
                        .setActionTextColor(getResources().getColor(R.color.blue_200))
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestCommentsData();
                            }
                        });

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
                params.put("postid", postid);

                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }



    private void addCommentData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.sourceAddress_goDaddy+"FBInsertComment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (response.substring(1, 2).equalsIgnoreCase("0")) {
                            Log.d("ResponseAdapter", "0 Failed");
                            Helper.toast.snackbarWithAction(activity, "Can't connect...")
                                .setActionTextColor(getResources().getColor(R.color.blue_200))
                                .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addCommentData();
                                    pd.show();
                                }
                            });

                        } else {
                            Log.d("ResponseAdapter", "1 Success: "+ response);
                            edtAddComment.setText("");
                            Helper.random.hideKeyboard(activity);
                            requestCommentsData();
                            rvComments.scrollToPosition(newsFeedList.size()-1);
                            pd.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.toast.snackbarWithAction(activity, "Can't connect...")
                                .setActionTextColor(getResources().getColor(R.color.blue_200))
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addCommentData();
                                        pd.show();
                                    }
                                });
                        pd.dismiss();
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


                params.put("feedcomments_mainid", postid);
                params.put("feedcomments_uid", "11");
                params.put("feedcomments_content", edtAddComment.getText().toString());

                params.put("feedcomments_loclat",fusedLocation.getLastKnowLocation().latitude+"");
                params.put("feedcomments_loclong", fusedLocation.getLastKnowLocation().latitude+"");
                params.put("feedcomments_fetchat", "0");


                return params;
            }
        };

        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }



}
