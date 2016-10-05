package com.archer.recipeupload.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.util.Date;


/**
 * Created by udit on 11/17/2015.
 */
public class CheflingUtils {


    public static final String PHOTO_PATH = "pPath";
    public static final String CAMERA_DIR = "/dcim/";

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    private static ProgressDialog progressBar;

    public static void setBackground(View view, int drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setBackground(view.getContext().getResources().getDrawable(drawable, null));
        } else {
            view.setBackgroundDrawable(view.getContext().getResources().getDrawable(drawable));
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static int getColor(Context context, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getColor(color, null);
        } else {
            return context.getResources().getColor(color);
        }
    }



    public static void showProgressBar(Context context) {
        try
        {
            progressBar = ProgressDialog.show(context,"","Processing..");
        }
        catch (Exception ex) {}
    }

    public static void closeProgressBar() {
        try {
            if (progressBar != null)
                progressBar.dismiss();
        }catch (Exception ex) {}
    }

    public static void showAlertMessage(String title, String message,
                                        Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static String getEndTime(String prefix,Date endDate) {
        Date now = new Date();
        long diff = endDate.getTime() - now.getTime();

        long oneDay = 1000 * 60 * 60 * 24;
        int daysLeft = (int) (diff/oneDay);
        if(daysLeft>1)
            return prefix + String.valueOf(daysLeft) + " days";

        long oneHour = 1000 * 60 * 60;
        int hoursLeft = (int) (diff/oneHour);
        if(hoursLeft>1)
            return prefix + String.valueOf(hoursLeft) + " hours";

        long oneMin = 1000 * 60;
        int minsLeft = (int) (diff/oneMin);
        if(minsLeft>1)
            return prefix + String.valueOf(minsLeft) + " mins";

        return prefix + String.valueOf(diff/1000) + " secs";
    }

    public static int statusBarHeight(Resources res) {
        return (int) (24 * res.getDisplayMetrics().density);
    }

    public static int ConvertToPx(Context c,int dip)
    {
        Resources r = c.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    public static Point getWindowSize(Display defaultDisplay)
    {
        Point windowSize = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            defaultDisplay.getSize(windowSize);
        }
        else
        {
            windowSize.x = defaultDisplay.getWidth();
            windowSize.y = defaultDisplay.getHeight();
        }
        SCREEN_WIDTH = windowSize.x;
        SCREEN_HEIGHT = windowSize.y;
        return windowSize;
    }

    public static int getScreenWidth(Display defaultDisplay)
    {
        if(SCREEN_WIDTH>0)
            return SCREEN_WIDTH;
        return getWindowSize(defaultDisplay).x;
    }

    public static int getScreenHeight(Display defaultDisplay)
    {
        if(SCREEN_HEIGHT>0)
            return SCREEN_HEIGHT;
        return getWindowSize(defaultDisplay).y;
    }

    public static void setVisibilityGone(int id, View Parent)
    {
        Parent.findViewById(id).setVisibility(View.GONE);
    }
    public static void setVisibilityVisible(int id, View Parent)
    {
        Parent.findViewById(id).setVisibility(View.GONE);
    }

    public static String encodeBitmapToBase64(Bitmap bmp)
    {
        byte[] image_base64;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 99, baos);
        image_base64 = baos.toByteArray();
        return Base64.encodeToString(image_base64, Base64.DEFAULT);
    }

    public static void bringViewToFocus(View v)
    {
        v.getParent().requestChildFocus(v, v);
    }

    /**
     * Checking device has camera hardware or not
     * */
    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    public static String getMonthName(int month) {
        switch (month)
        {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "";
    }


}
