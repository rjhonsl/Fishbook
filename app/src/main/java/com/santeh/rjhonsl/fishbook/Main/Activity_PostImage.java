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
import android.util.Log;
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
import com.santeh.rjhonsl.fishbook.Utils.FusedLocation;
import com.santeh.rjhonsl.fishbook.Utils.Helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by rjhonsl on 5/11/2016.
 */
public class Activity_PostImage extends AppCompatActivity {


    RelativeLayout llAddImage;
    LinearLayout llPostImageNow, llTopPostImage;
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

    FusedLocation fusedLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postimage);
        activity = this;
        context = Activity_PostImage.this;

        fusedLocation = new FusedLocation(context, activity);
        fusedLocation.connectToApiClient();

        loading = new ProgressDialog(context);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setMessage("Uploading");

        imgPreview = (ImageView) findViewById(R.id.img_preview);
        llAddImage = (RelativeLayout) findViewById(R.id.llAddImage);
        llPostImageNow = (LinearLayout) findViewById(R.id.ll_postImageNow);
        llTopPostImage = (LinearLayout) findViewById(R.id.ll_top_postimage);
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

        llTopPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                String filesize = Helper.fileInfo.getSize(data, context);
                Helper.toast.indefinite(activity, "Length: "+Helper.fileInfo.getSize(data, context) + " " +data.getData().getPath().length()+ "\nName: " + Helper.fileInfo.getName(data, context));

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgPreview.setImageBitmap(bitmap);

                encodeImagetoString(bitmap, filesize);


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

    public void encodeImagetoString(final Bitmap bmp, final String filesize) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                double quality = 100; //quality in percentage
                int size = Integer.valueOf(filesize);
                double requiredSize = 300000;//max size in kb
                double reducedSize = size;

                while (reducedSize > requiredSize) {
                    reducedSize = size * (quality / 100);
                    quality = quality - 1;
                }
                Log.d("Image", "rsize: " + reducedSize + "  quality: " + quality);


                Double Dquality = Double.valueOf(quality);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, Dquality.intValue(), stream);
                byte[] imageBytes = stream.toByteArray();

                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

                params.put("content_type", MainActivity.CONTENT_IMAGE + "");

                params.put("content_fetchAt", System.currentTimeMillis() + "");


                String geoLocation =  Helper.LocationUtil.getAddress(context, fusedLocation.getLastKnowLocation().latitude+"", fusedLocation.getLastKnowLocation().longitude+"");
                String sqlString = "INSERT INTO `feed_main_` " +
                        "(`feed_main_id`, `feed_main_uid`, `feed_main_date`, `feed_main_loclat`, `feed_main_loclong`, `feed_main_fetch_at`, `feed_main_seen_state`) " +
                        "VALUES " +
                        "(NULL, '11', " +
                        "'"+System.currentTimeMillis()+"', " +
                        "'"+fusedLocation.getLastKnowLocation().latitude+"', " +
                        "'"+fusedLocation.getLastKnowLocation().longitude+"', '"+geoLocation+"', '0');";

                Log.d("SQL", sqlString);

                params.put("sql", sqlString);

            }
        }.execute(null, null, null);
    }


    public void makeHTTPCall() {
        params.put("content_desc", edtImageDesc.getText().toString() + "");
        final int DEFAULT_TIMEOUT = 20 * 1000;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);

        loading.setMessage("Uploading image...");
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://www.santeh-webservice.com/images/androidimageupload_fishbook/feedphoto.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        if (response.substring(1,2).equalsIgnoreCase("0")){
                            loading.hide();
                            Toast.makeText(context, "Upload Failed: "+response, Toast.LENGTH_SHORT).show();
                        }else{
                            loading.hide();

                            finish();
                            Toast.makeText(context, "Image has been uploaded", Toast.LENGTH_SHORT).show();
                            Helper.toast.long_(activity, "Image has been uploaded.");
                            Log.d("Response of upload", response);
                        }

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
                            Helper.toast.long_(activity, "Upload failed. Please try again.");
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Helper.toast.long_(activity, "Server didn't respond. Please try again.");
                        }
                        // When Http response code other than 404, 500
                        else {
                            Helper.toast.long_(activity, "Upload failed. Please try again.");
//                            Helper.dialogBox.okOnly_Scrolling(activity, "Upload Error", "Error:" + error.toString() + " ", "OK", R.color.red );
                        }
                    }
                });
    }


    public File getPath(Uri uri) {
        return new File(uri.getPath());
    }

}
