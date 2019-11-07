package com.sendacyl.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.util.List;

public class MyLocation {

    public static String getLocationCoordinates(Activity activity) {
        Geocoder geocoder;
        String bestProvider;
        List<Address> user;
        double lat;
        double lng;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "0%7C0";
        } else {
            Location location = lm.getLastKnownLocation(bestProvider);

            if (location == null) {
                return "0%7C0";
            } else {
                geocoder = new Geocoder(activity);
                try {
                    user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    lat = user.get(0).getLatitude();
                    lng = user.get(0).getLongitude();
                    return lat + "%7C" + lng;

                } catch (Exception e) {
                    e.printStackTrace();
                    return "0%7C0";
                }
            }
        }

    }

}