package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.santeh.rjhonsl.fishbook.APIs.MyVolleyAPI;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.FusedLocation;
import com.santeh.rjhonsl.fishbook.Utils.Helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjhonsl on 4/22/2016.
 */
public class Activity_PostText extends AppCompatActivity {

    Activity activity;
    Context context;
    EditText edtPostContent;
    FusedLocation fusedLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posttext);
        activity = this;
        context = Activity_PostText.this;
        Helper.random.hideKeyboard(this);

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();

        edtPostContent = (EditText) findViewById(R.id.edtPostSomethingContent);

        final LinearLayout llpostNow = (LinearLayout) findViewById(R.id.ll_postnow);
        llpostNow.setEnabled(false);
        LinearLayout lltop = (LinearLayout) findViewById(R.id.ll_top_postsomething);

        llpostNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.toast.short_(activity, edtPostContent.getText().toString());
                startPostSomethingToWeb();
            }
        });

        edtPostContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0){
                    llpostNow.setEnabled(true);
                }else {
                    llpostNow.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void startPostSomethingToWeb() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Helper.variables.URL_PHP_INSERT_FEEDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (!response.substring(1, 2).equalsIgnoreCase("0")) {
                            Helper.toast.indefinite(activity, "Post successful");
                            finish();
                        } else {

                            Helper.toast.short_(activity, "Posting failed. Please try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.toast.short_(activity, "Posting failed. Please try again.");
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));
                params.put("userid",  "11");
                params.put("userlvl", "4");

                params.put("content_type", 0 + "");
                params.put("content_desc", edtPostContent.getText().toString() + "");
                params.put("content_imgurl", "null");
                params.put("content_eventid", "null");
                params.put("content_fileurl",  "null");
                params.put("content_fetchAt", System.currentTimeMillis() + "");

                String geoLocation =  Helper.LocationUtil.getAddress(context, fusedLocation.getLastKnowLocation().latitude+"", fusedLocation.getLastKnowLocation().longitude+"");
                String sqlString = "INSERT INTO `feed_main_` " +
                        "(`feed_main_id`, `feed_main_uid`, `feed_main_date`, `feed_main_loclat`, `feed_main_loclong`, `feed_main_fetch_at`, `feed_main_seen_state`) " +
                        "VALUES " +
                        "(NULL, '11', " +
                        "'"+System.currentTimeMillis()+"', " +
                        "'"+fusedLocation.getLastKnowLocation().latitude+"', " +
                        "'"+fusedLocation.getLastKnowLocation().longitude+"', '"+geoLocation+"', '0');";


                params.put("sql", sqlString);


                return params;
            }
        };


        MyVolleyAPI api = new MyVolleyAPI();
        api.addToReqQueue(postRequest, context);
    }

}
