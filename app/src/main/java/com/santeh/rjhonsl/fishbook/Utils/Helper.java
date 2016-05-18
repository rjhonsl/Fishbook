package com.santeh.rjhonsl.fishbook.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.santeh.rjhonsl.fishbook.R;

import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//import com.google.maps.android.ui.IconGenerator;
//import com.santeh.rjhonsl.samplemap.Obj.CustInfoObject;
//import com.santeh.rjhonsl.samplemap.Obj.Var;
//import com.santeh.rjhonsl.samplemap.R;

/**
 * Created by rjhonsl on 7/24/2015.
 **/
public class Helper {


    public static class variables{
        public static String sourceAddress_goDaddy                  = "http://santeh-webservice.com/php/android_json_post/";
        public static String URL_PHP_INSERT_FEEDPOST                    = sourceAddress_goDaddy + "insertFeedContent.php";
        public static String URL_PHP_INSERT_FEEDPOST_PHOTO              = sourceAddress_goDaddy + "uploadimage.php";

    }



    public static class toast{

        public static void short_(Activity context, String msg){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setMaxLines(5);
                snackbar.show();
            }else{
                LayoutInflater inflater = context.getLayoutInflater();
                final View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setMargin(0, 0);
                toast.setView(layout);

                toast.show();
            }

        }

        public static void long_(Activity context, String msg){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setMaxLines(5);
                snackbar.show();

            }else{

                LayoutInflater inflater = context.getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setMargin(0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }
        }

        public static void indefinite(Activity context, String msg){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                final Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(context.getResources().getColor(R.color.gray_100));

                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(context.getResources().getColor(R.color.gray_100));
                tv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        snackbar.dismiss();
                        return false;
                    }
                });
                tv.setMaxLines(5);
                snackbar.show();
            }else{
                LayoutInflater inflater = context.getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) context.findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
                text.setTypeface(font);
                text.setText(msg);

                Toast toast = new Toast(context.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
                toast.setMargin(0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }


        }

    }



    public static class dialogBox{

        public static Dialog yesNo(Activity activity, String prompt, String title, String strButton1, String strButton2, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_material_themed_yesno);//Set the xml view of the dialog
            Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

            txtprompt.setText(prompt);
            txttitle.setText(title);
//        txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            btn1.setText(strButton1);
            btn2.setText(strButton2);


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = d.getWindow();
            lp.copyFrom(window.getAttributes());
            // This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            d.show();
            return d;
        }

        public static Dialog yesNoWithEditText(Activity activity, String prompt, String edtEntry, String title, String strButton1, String strButton2, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_material_themed_yesno_with_edittext);//Set the xml view of the dialog
            Button btn1 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt1);
            Button btn2 = (Button) d.findViewById(R.id.btn_dialog_yesno_opt2);
            EditText edt = (EditText) d.findViewById(R.id.dialog_edttext);
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_yesno_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_yesno_prompt);

            txtprompt.setText(prompt);
            edt.setText(edtEntry);
            txttitle.setText(title);
            txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            btn1.setText(strButton1);
            btn2.setText(strButton2);
            d.show();
            return d;
        }

        public static Dialog numberPicker(Activity activity, String dialogTitle, int minVal, int maxValue){
            final Dialog d = new Dialog(activity);//
            d.setContentView(R.layout.dialog_numberpicker);
            d.setTitle(dialogTitle);
            final NumberPicker nbp = (NumberPicker) d.findViewById(R.id.dialog_numberpicker);
            Button set = (Button) d.findViewById(R.id.btn_numberpicker_set);
            nbp.setMaxValue(maxValue);
            nbp.setMinValue(minVal);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            return d;
        }

        public static Dialog decimalPicker(Activity activity, String dialogTitle, int minVal, int maxValue){
            final Dialog d = new Dialog(activity);//

            d.setContentView(R.layout.dialog_decimalpicker);
            d.setTitle(dialogTitle);
            NumberPicker wholeNum = (NumberPicker) d.findViewById(R.id.dialog_decipicker_whole);
            wholeNum.setMaxValue(maxValue);
            wholeNum.setMinValue(0);

            NumberPicker decimal = (NumberPicker) d.findViewById(R.id.dialog_decipicker_deci);
            decimal.setMaxValue(maxValue);
            decimal.setMinValue(0);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            return d;
        }

        public static Dialog okOnly(Activity activity, String title, String prompt, String button){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_okonly);//Set the xml view of the dialog
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
            Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txtprompt.setText(prompt);
            txttitle.setText(title);
            txtok.setText(button);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = d.getWindow();
            lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            d.show();
            return d;
        }

        public static Dialog okOnly_Scrolling(Activity activity, String title, String prompt, String button, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_okonly_scrollview);//Set the xml view of the dialog
            TextView txttitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtprompt = (TextView) d.findViewById(R.id.dialog_okonly_prompt);
            Button txtok = (Button) d.findViewById(R.id.btn_dialog_okonly_OK);
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txttitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            txtprompt.setText(prompt);
            txttitle.setText(title);
            txtok.setText(button);
            d.show();
            return d;
        }

        public static Dialog list(Activity activity, String[] options, String title, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_list);//Set the xml view of the dialog

            ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
            listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listview.setAdapter(listViewAdapter);

            TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtTitle.setText(title);
            d.show();
            return d;
        }

        public static Dialog listWithPrompt(Activity activity, String[] options, String title, String message, int resIdColor){
            final Dialog d = new Dialog(activity);//
            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //notitle
            d.setContentView(R.layout.dialog_material_themed_list_with_prompt);//Set the xml view of the dialog

            ListView listview = (ListView) d.findViewById(R.id.dialog_list_listview);
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(activity, R.layout.select_dialog_item_material, options); //selected item will look like a spinner set from XML
            listViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listview.setAdapter(listViewAdapter);

            TextView txtTitle = (TextView) d.findViewById(R.id.dialog_okonly_title);
            TextView txtmessage = (TextView) d.findViewById(R.id.txt_message);

            txtTitle.setBackground(activity.getResources().getDrawable(resIdColor));
            txtTitle.setText(title);
            txtmessage.setText(message);

            d.show();
            return d;
        }

    }



    public static class convert{

        public static long DateToLong(int dd, int MM, int yyyy){
            long startDate=000000;
            try {
                String dateString = dd+"/"+MM+"/"+yyyy;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                startDate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return startDate;
        }

        public static String DatetoGregorian(int dd, int MM, int yyyy){
            String dateString_gregorian="";
            try {
                String dateString = dd+"/"+MM+"/"+yyyy;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateString);
                dateString_gregorian = LongToDate_Gregorian(date.getTime());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateString_gregorian;
        }

        public static long DateToMilis_DBFormat(String yyyymmdd){
            long startDate=000000;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(yyyymmdd);
                startDate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return startDate;
        }

        public static String LongtoDateString(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongtoDateString_DBFormat(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDate_ShortGregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDate_Gregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDateTime_Gregorian(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy hh:mmaa");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongToDateTime_DBFormat(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static String LongtoDate_DBFormat(long dateInMillis){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateInMillis);
            return formatter.format(calendar.getTime());
        }

        public static long DateDifferenceInDays(long date1, long date2){

            long diff = date1 - date2; //result in millis
            long days = diff / (24 * 60 * 60 * 1000);

            Log.d("DIFF", "days: "+days+"");
            return days;
        }

        public static int[] LongtoDateFormat(long dateinMilis) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(dateinMilis));
            String[] date = dateString.split("/");

            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);


            return new int[]{month,day,year};
        }

    }


    public static class ActivityAction {

        public static void startActivityClearStack(Activity currentActivity, Class nextActivity) {
            Intent intent = new Intent(currentActivity, nextActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            currentActivity.startActivity(intent);
        }
    }


    public static class getDeviceInfo{

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        public static String getMacAddress(Context context){
            String macaddress = "";
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
                try {
                    String interfaceName = "wlan0";
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)){
                            continue;
                        }

                        byte[] mac = intf.getHardwareAddress();
                        if (mac==null){
                            macaddress = "";
                        }

                        StringBuilder buf = new StringBuilder();
                        for (byte aMac : mac) {
                            buf.append(String.format("%02X:", aMac));
                        }
                        if (buf.length()>0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        macaddress = buf.toString();
                    }
                } catch (Exception ex) { } // for now eat exceptions
            }else{
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                macaddress = info.getMacAddress();
            }

            return macaddress;

        }

        public static String getIMEI(Context context){
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        }

    }

    public static class random{

        public static void moveCursortoEnd(Activity activity, int resId){
            EditText et = (EditText)activity.findViewById(resId);
            et.setSelection(et.getText().length());
        }

        public static void hideKeyboardOnLoad(Activity activity){
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        public static String trimFirstAndLast(String string){
            String trimmed = "";

            trimmed = string.substring(1,string.length() );
            return  trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
    
        public static int removeSuffix(String wholeValue, String unitsToRemove){
            int initialValue;
            if (wholeValue.equalsIgnoreCase("") || wholeValue.equalsIgnoreCase(null)) {
                initialValue = 1;
            }else {
                if (wholeValue.substring(wholeValue.length() - unitsToRemove.length(), wholeValue.length()) .equalsIgnoreCase(unitsToRemove)){
                    initialValue = Integer.parseInt(wholeValue.substring(0, wholeValue.length() - unitsToRemove.length()));
                }else{
                    initialValue = Integer.parseInt(wholeValue.toString());
                }
            }
            return  initialValue;
        }

        public static void setCursorOnEnd(EditText edt) {
            edt.setSelection(edt.getText().length());
        }

        public boolean isPackageInstalled(String packagename, Context context) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

    }

    public static class animate{

        // To animate view slide out from left to right
        public static void slideToRight(View view){
            TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        // To animate view slide out from right to left
        public static void slideToLeft(View view){
            TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        // To animate view slide out from top to bottom
        public static void slideToBottom(final View view, float height , final float alpha,final int vis){
            TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
            animate.setDuration(500);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.animate()
                            .alpha(alpha)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            });
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(vis);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animate);
        }

        // To animate view slide out from bottom to top
        public static void slideToTop(final View view, float height , final float alpha,final int vis){
            final TranslateAnimation animate = new TranslateAnimation(0,0,0,-height);
            animate.setDuration(500);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.animate()
                            .alpha(alpha)
                            .setDuration(200)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                }
                            });

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(vis);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animate);

        }
    }

    public static class fileInfo{

        public static String getSize(Intent returnIntent, Context context){
            Uri returnUri = returnIntent.getData();
            Cursor returnCursor =
                    context.getContentResolver().query(returnUri, null, null, null, null);
            assert returnCursor != null;
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            return Long.toString(returnCursor.getLong(sizeIndex));
        }


        public static String getName(Intent returnIntent, Context context){
            Uri returnUri = returnIntent.getData();
            Cursor returnCursor =
                    context.getContentResolver().query(returnUri, null, null, null, null);
            assert returnCursor != null;
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            return returnCursor.getString(nameIndex);
        }
    }


}//end of class
