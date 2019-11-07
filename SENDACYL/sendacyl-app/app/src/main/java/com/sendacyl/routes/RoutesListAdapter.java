package com.sendacyl.routes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlGeometry;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlLineString;
import com.google.maps.android.kml.KmlMultiGeometry;
import com.google.maps.android.kml.KmlPlacemark;
import com.sendacyl.util.Helper;
import com.sendacyl.R;
import com.sendacyl.routes.model.RouteItem;
import com.sendacyl.routes.ui.RouteDetailActivity;
import com.sendacyl.routes.ui.RoutesFragment;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RoutesListAdapter extends ArrayAdapter<RouteItem> {

    private Bundle savedInstanceState;

    private RoutesFragment routesFragment;

    private ArrayList<RouteItem> listData;

    private LayoutInflater layoutInflater;

    private Context mContext;

    private MapView mapView;

    private GoogleMap googleMap;

    private KmlLayer kmlLayer;

    private RelativeLayout pDialog;

    private String TAG_TOP = "TOP";

    public RoutesListAdapter(Context context, Bundle savedInstanceState, RoutesFragment routesFragment, Integer something, ArrayList<RouteItem> listData) {
        super(context, something, listData);
        this.listData = listData;
        this.savedInstanceState = savedInstanceState;
        this.routesFragment = routesFragment;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public RouteItem getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        final RouteItem routeItem = (RouteItem) listData.get(position);

        //if it is the first item, give a special treatment.
        if (position == 0) {
            convertView = layoutInflater.inflate(R.layout.listview_highlight, null);

            //Loading
            pDialog = (RelativeLayout) convertView.findViewById(R.id.progressBarHolder);

            //Init map
            mapView = (MapView) convertView.findViewById(R.id.mapViewHighlight);
            mapView.onCreate(savedInstanceState);
            MapsInitializer.initialize(mContext);
            googleMap = mapView.getMap();
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            uiSettings.setZoomGesturesEnabled(false);
            uiSettings.setMapToolbarEnabled(false);
            mapView.setClickable(false);
            mapView.setFocusable(false);
            mapView.setDuplicateParentStateEnabled(false);
            mapView.setEnabled(false);

            //Override on click method to scape GMaps default action
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    Intent intent = new Intent(mContext, RouteDetailActivity.class);
                    intent.putExtra("route", routeItem);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

            //Load KML
            new DownloadKmlFile(routeItem.getKmlUrl()).execute();

            //Set route name
            ((TextView) convertView.findViewById(R.id.nameHighlight)).setText(routeItem.getName());

            //Set route data
            ((TextView) convertView
                    .findViewById(R.id.dataHighlight))
                    .setText(routeItem.getLocality() + " - " + routeItem.getProvince());


            convertView.setTag(TAG_TOP);
            return convertView;
        }

        ViewHolder holder;

        if (convertView == null || convertView.getTag().equals(TAG_TOP)) {
            convertView = layoutInflater.inflate(R.layout.fragment_routes_list_row, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.dataView = (TextView) convertView.findViewById(R.id.data);
            holder.imageView = convertView.findViewById(R.id.thumbImage);
            holder.distanceView = (TextView) convertView.findViewById(R.id.distance);
            holder.timeView = (TextView) convertView.findViewById(R.id.time);
            holder.distanceContainer = (LinearLayout) convertView.findViewById(R.id.distanceContainer);
            holder.timeContainer = (LinearLayout) convertView.findViewById(R.id.timeContainer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            //holder.imageView.setImageBitmap(null);
        }

        holder.nameView.setText(routeItem.getName());

        String data = "";

        String provinceLetter = String.valueOf(routeItem.getProvince().charAt(0)).toUpperCase()
                + String.valueOf(routeItem.getProvince().charAt(1)).toUpperCase();
        TextDrawable drawable;

        //Set route difficulty color
        switch (routeItem.getDifficulty()) {
            case "1":
                data += mContext.getResources().getString(R.string.medium);
                drawable = TextDrawable.builder()
                        .buildRect(provinceLetter, getContext().getResources().getColor(R.color.route_diff_medium));
                //holder.imageView.setBackgroundResource(R.drawable.route_diff_medium);
                break;
            case "2":
                //holder.imageView.setBackgroundResource(R.drawable.route_diff_default);
                drawable = TextDrawable.builder()
                        .buildRect(provinceLetter, getContext().getResources().getColor(R.color.route_diff_default));
                data += mContext.getResources().getString(R.string.normal);
                break;
            case "3":
                data += mContext.getResources().getString(R.string.hard);
                drawable = TextDrawable.builder()
                        .buildRect(provinceLetter, getContext().getResources().getColor(R.color.route_diff_hard));
                //holder.imageView.setBackgroundResource(R.drawable.route_diff_hard);
                break;
            case "4":
                data += mContext.getResources().getString(R.string.easy);
                drawable = TextDrawable.builder()
                        .buildRect(provinceLetter, getContext().getResources().getColor(R.color.route_diff_low));
                //holder.imageView.setBackgroundResource(R.drawable.route_diff_low);
                break;
            default:
                data += mContext.getResources().getString(R.string.normal);
                drawable = TextDrawable.builder()
                        .buildRect(provinceLetter, getContext().getResources().getColor(R.color.route_diff_default));
                holder.imageView.setBackgroundResource(R.drawable.route_diff_default);
                break;
        }
        holder.imageView.setBackgroundDrawable(drawable);

        data += " - " + routeItem.getLocality() + " - " + routeItem.getProvince();
        holder.dataView.setText(Html.fromHtml(data));

        //Set route time and distance
        //if (routeItem.getTime().equals("0")) holder.timeContainer.setVisibility(View.GONE);
        //if (routeItem.getDistance().equals("0")) holder.distanceContainer.setVisibility(View.GONE);
        double meters = Double.parseDouble(routeItem.getDistance());
        double kilometers = Helper.calculateKilometers(meters);
        DecimalFormat numberFormat = new DecimalFormat("#.##");
        String formatKilometers = numberFormat.format(kilometers);
        if(formatKilometers.equals("0")) {
            holder.distanceView.setText("N/A");
        } else {
            holder.distanceView.setText(formatKilometers + " km");
        }
        if(routeItem.getTime().equals("0")) {
            holder.timeView.setText("N/A");
        } else {
            holder.timeView.setText(routeItem.getTime() + " min");
        }


        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView dataView;
        View imageView;
        TextView distanceView;
        TextView timeView;
        LinearLayout distanceContainer;
        LinearLayout timeContainer;
    }

    private void moveCameraToKml(KmlLayer kmlLayer) {
        //Retrieve the first container in the KML layer
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        //Retrieve a nested container within the first container
        container = container.getContainers().iterator().next();
        //Retrieve the first placemark in the nested container
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
        //Create LatLngBounds of the outer coordinates of the polygon
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        KmlLineString lineString;
        KmlMultiGeometry multiGeometry;
        //Check for geometry type and get bounds
        switch (placemark.getGeometry().getGeometryType()) {
            case "LineString":
                lineString = (KmlLineString) placemark.getGeometry();
                for (LatLng latLng : lineString.getGeometryObject()) {
                    builder.include(latLng);
                }
                break;
            case "MultiGeometry":
                multiGeometry = (KmlMultiGeometry) placemark.getGeometry();
                KmlGeometry geo = multiGeometry.getGeometryObject().iterator().next();
                for (LatLng latLng : (ArrayList<LatLng>) geo.getGeometryObject()) {
                    builder.include(latLng);
                }
                break;
            default:
                lineString = (KmlLineString) placemark.getGeometry();
                for (LatLng latLng : lineString.getGeometryObject()) {
                    builder.include(latLng);
                }
                break;
        }
        //Move camera to map
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(builder.build(), 1);
        googleMap.moveCamera(update);
        googleMap.animateCamera(update, 2000, null);
    }


    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFile(String url) {
            mUrl = url;
        }

        @Override
        protected byte[] doInBackground(String... params) {
            try {
                InputStream is = new URL(mUrl).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (pDialog.getVisibility() == View.GONE) {
                pDialog.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(byte[] byteArr) {
            try {
                kmlLayer = new KmlLayer(googleMap, new ByteArrayInputStream(byteArr), mContext);
                kmlLayer.addLayerToMap();
                moveCameraToKml(kmlLayer);
                if (pDialog.getVisibility() == View.VISIBLE) {
                    pDialog.setVisibility(View.GONE);
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}
