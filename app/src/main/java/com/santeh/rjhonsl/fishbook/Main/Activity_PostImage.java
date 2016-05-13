package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.santeh.rjhonsl.fishbook.R;
import com.santeh.rjhonsl.fishbook.Utils.Helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by rjhonsl on 5/11/2016.
 */
public class Activity_PostImage extends AppCompatActivity {


    RelativeLayout llAddImage;
    LinearLayout llPostImageNow;
    int SELECT_PICTURE = 0;
    ProgressDialog loading;
    String encodedImage;

    RequestParams params;
    Activity activity;
    Context context;
    String selectedFilePath;
    Bitmap bitmap;
    ImageView imgPreview;
    ImageButton btnRemoveImage;
    EditText edtImageDesc;
    boolean isPictureSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postimage);
        activity = this;
        context = Activity_PostImage.this;


        loading = new ProgressDialog(context);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setMessage("Uploading");

        imgPreview = (ImageView) findViewById(R.id.img_preview);
        llAddImage = (RelativeLayout) findViewById(R.id.llAddImage);
        llPostImageNow = (LinearLayout) findViewById(R.id.ll_postImageNow);
        llPostImageNow.setEnabled(false);
        btnRemoveImage = (ImageButton) findViewById(R.id.btn_removeImage);
        edtImageDesc = (EditText) findViewById(R.id.edtImageDescription);

        setRemoveImageButton();

        edtImageDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isPictureSelected && count> 0){
                    llPostImageNow.setEnabled(true);
                }else{
                    llPostImageNow.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        assert imgPreview != null;
        llAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

        btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPictureSelected = false;
                imgPreview.setImageResource(R.drawable.addimage);
                setRemoveImageButton();
                llPostImageNow.setEnabled(false);
            }
        });

        llPostImageNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger Image upload
                loading.show();
                loading.setMessage("Uploading...");
                makeHTTPCall();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) { //


                isPictureSelected = true;
                setRemoveImageButton();
                if (isPictureSelected && edtImageDesc.getText().toString().length() > 0){
                    llPostImageNow.setEnabled(true);
                }else{
                    llPostImageNow.setEnabled(false);
                }
                Uri selectedFileUri = data.getData();
                Uri filePath = data.getData();

//                imgPreview.setImageURI(selectedFileUri);

                File file = getPath(selectedFileUri);
                selectedFilePath = file.getAbsoluteFile().getName();
                Helper.toast.indefinite(activity, ""+data.getData().getPath().length() + " " + data.getData().getPath().);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgPreview.setImageBitmap(bitmap);

                encodeImagetoString(bitmap);


            }

        }


    }

    private void setRemoveImageButton() {
        if (isPictureSelected) {
            btnRemoveImage.setVisibility(View.VISIBLE);
        }else{
            btnRemoveImage.setVisibility(View.GONE);
        }
    }



    public void encodeImagetoString(final Bitmap bmp ) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                return "";
            }


            @Override
            protected void onPostExecute(String msg) {
                params = new RequestParams();
                params.put("image", encodedImage);
                params.put("imagename", selectedFilePath);
                params.put("username", "tsraqua");
                params.put("password", "tsraqua");
                params.put("deviceid", Helper.getDeviceInfo.getMacAddress(context));
                params.put("userid",  "11");
                params.put("userlvl", "4");

            }
        }.execute(null, null, null);
    }


    public void makeHTTPCall() {
        loading.setMessage("Uploading image...");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://www.santeh-webservice.com/images/androidimageupload_fishbook/feedphoto.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        loading.hide();
                        Toast.makeText(getApplicationContext(), "Upload successful: " + response,
                                Toast.LENGTH_LONG).show();
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        loading.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                                            + error.toString() + " "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    public File getPath(Uri uri) {
        return new File(uri.getPath());
    }

}
