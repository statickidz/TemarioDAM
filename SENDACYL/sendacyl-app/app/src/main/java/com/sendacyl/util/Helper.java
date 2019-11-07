package com.sendacyl.util;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sendacyl.R;

public class Helper {

    public static void noConnection(final Context context, boolean calledFromFragment) {

        AlertDialog.Builder ab = null;
        ab = new AlertDialog.Builder(context);

        if (isOnline(context, false, false)) {
            ab.setMessage(context.getResources().getString(R.string.dialog_connection_description));
            ab.setPositiveButton(context.getResources().getString(R.string.ok), null);
            ab.setTitle(context.getResources().getString(R.string.dialog_connection_name));
        } else {
            ab.setMessage(context.getResources().getString(R.string.dialog_internet_description));
            ab.setPositiveButton(context.getResources().getString(R.string.ok), null);
            ab.setTitle(context.getResources().getString(R.string.dialog_internet_name));
        }

        ab.show();
    }

    public static boolean isOnline(Context c, boolean calledFromFragment, boolean showDialog) {
        ConnectivityManager cm = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else if (showDialog) {
            noConnection(c, calledFromFragment);
        }
        return false;
    }

    public static ImageLoader initializeImageLoader(Context c) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            //creating a configuration for imageloader
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            //set the configuration for imageloader
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
                    .defaultDisplayImageOptions(options)
                    .build();
            imageLoader.init(config);
        }
        return imageLoader;
    }

    @SuppressLint("NewApi")
    public static void revealView(View toBeRevealed, View frame) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // get the center for the clipping circle
                int cx = (frame.getLeft() + frame.getRight()) / 2;
                int cy = (frame.getTop() + frame.getBottom()) / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(frame.getWidth(), frame.getHeight());
                Log.v("INFO", "Radius: " + finalRadius);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(
                        toBeRevealed, cx, cy, 0, finalRadius);

                // make the view visible and start the animation
                toBeRevealed.setVisibility(View.VISIBLE);
                anim.start();
            } else {
                toBeRevealed.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public static void setStatusBarColor(Activity mActivity, int color) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean resourceExists(String URLName){
        /*try {
            HttpClient client= new DefaultHttpClient();
            HttpHead headMethod = new HttpHead(URLName);
            HttpResponse response = client.execute(headMethod);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return false;
            } else {
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

    /**
     * The calculateKilometers method displays the kilometers that are equivalent to
     * a specified number of meters.
     *
     * @param meters
     * @return the number of kilometers
     */
    public static double calculateKilometers(double meters) {
        double kilometers = meters * 0.001;
        return kilometers;
    }

}
