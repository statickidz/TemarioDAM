package com.sendacyl.routes.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sendacyl.util.Helper;
import com.sendacyl.R;
import com.sendacyl.routes.model.RouteItem;
import com.sendacyl.util.CustomMapView;
import com.sendacyl.util.TrackingScrollView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 *  This activity is used to display a single route details
 */

public class RouteDetailActivity extends AppCompatActivity {

	private RouteItem routeItem;
    private Toolbar mToolbar;
	
	ImageLoader imageLoader; 

	private MapView mapView;
	private GoogleMap googleMap;
    private KmlLayer kmlLayer;

    private FloatingActionButton fabTop;
    private FloatingActionButton fabBottom;

	private int mImageHeight;
	boolean FadeBar = true;
	int latestAlpha;
	
	String id;
	String link;
    String name;
    String location;
    String difficulty;
    String distance;
	String dateauthor;
	String content;

    private TextView rName;
    private TextView rLocation;
    private TextView rDistance;
    private View rDifficulty;
    private ImageView rImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_details_tests);

		//Init toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Init image loader
        imageLoader = Helper.initializeImageLoader(this);

        //Init maps
		mapView = (CustomMapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
		MapsInitializer.initialize(this);
		googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Init fab
        fabTop = (FloatingActionButton) findViewById(R.id.fabTop);
        fabBottom = (FloatingActionButton) findViewById(R.id.fabBottom);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setZoomGesturesEnabled(true);
        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setDuplicateParentStateEnabled(false);

        //Init route
        routeItem = (RouteItem) this.getIntent().getSerializableExtra("route");
        if (routeItem != null) {

            //Map parallax
            mImageHeight = mapView.getLayoutParams().height;
            ((TrackingScrollView) findViewById(R.id.scroller)).setOnScrollChangedListener(
                new TrackingScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(TrackingScrollView source, int l, int t, int oldl, int oldt) {
                        handleScroll(source, t);
                    }
                }
            );

            //Set route details
            id = routeItem.getId();
            link = routeItem.getUrl();

            name = routeItem.getName();
            rName = (TextView) findViewById(R.id.name);
            rName.setText(name);

            location = routeItem.getProvince() + ", " + routeItem.getLocality();
            rLocation = (TextView) findViewById(R.id.location);
            rLocation.setText(location);

            distance = routeItem.getDistance();
            rDistance = (TextView) findViewById(R.id.distance);
            rDistance.setText(distance);

            //Set route image
            rImage = (ImageView) findViewById(R.id.image);
            imageLoader.displayImage(routeItem.getThumbnailUrl(), rImage);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            rImage.setColorFilter(filter);

            //Set route difficulty color
            rDifficulty = findViewById(R.id.difficulty);
            switch(routeItem.getDifficulty()) {
                case "1":
                    difficulty = getResources().getString(R.string.medium);
                    rDifficulty.setBackgroundResource(R.drawable.route_diff_medium);
                    break;
                case "2":
                    difficulty = getResources().getString(R.string.normal);
                    rDifficulty.setBackgroundResource(R.drawable.route_diff_default);
                    break;
                case "3":
                    difficulty = getResources().getString(R.string.hard);
                    rDifficulty.setBackgroundResource(R.drawable.route_diff_hard);
                    break;
                case "4":
                    difficulty = getResources().getString(R.string.easy);
                    rDifficulty.setBackgroundResource(R.drawable.route_diff_low);
                    break;
                default:
                    difficulty = getResources().getString(R.string.normal);
                    rDifficulty.setBackgroundResource(R.drawable.route_diff_default);
                    break;
            }

            //Load KML File
            new DownloadKmlFile(routeItem.getKmlUrl()).execute();
        }


        if (FadeBar){
    		mToolbar.getBackground().setAlpha(0);
    		Helper.setStatusBarColor(RouteDetailActivity.this, getResources().getColor(R.color.black));
    		getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


	}

	private void handleScroll(TrackingScrollView source, int top) {
		int scrolledImageHeight = Math.min(mImageHeight, Math.max(0, top));

		ViewGroup.MarginLayoutParams imageParams = (ViewGroup.MarginLayoutParams) mapView.getLayoutParams();
		int newImageHeight = mImageHeight - scrolledImageHeight;
		if (imageParams.height != newImageHeight) {
			// Transfer image height to margin top
			imageParams.height = newImageHeight;
			imageParams.topMargin = scrolledImageHeight;

			// Invalidate view
			mapView.setLayoutParams(imageParams);
		}
		
        if (FadeBar){
        	final int imageHeaderHeight = mapView.getHeight() - getSupportActionBar().getHeight();
        	//t=how far you scrolled
        	//ratio is from 0,0.1,0.2,...1
        	final float ratio = (float) Math.min(Math.max(top, 0.2), imageHeaderHeight) / imageHeaderHeight;
        	//setting the new alpha value from 0-255 or transparent to opaque
        	final int newAlpha = (int) (ratio * 255);
        	if (newAlpha != latestAlpha){
        		mToolbar.getBackground().setAlpha(newAlpha);
        		Helper.setStatusBarColor(RouteDetailActivity.this, blendColors(ratio, this));
        	}

            if(top < 10 || newAlpha < 0) {
                fabTop.show();
                fabBottom.hide();
            } else {
                fabTop.hide();
                fabBottom.show();
            }

            //Log.v("TRELERE", "top: " + top  + " imageHeaderHeight:" + imageHeaderHeight + " newAlpha:" + newAlpha);
        	
        	latestAlpha = newAlpha;

        }
	}

    	
    @Override
    public void onPause(){
        super.onPause();
        mToolbar.getBackground().setAlpha(255);
        mapView.onPause();
    }
    	
    @Override
    public void onResume(){
        super.onResume();
        if (FadeBar)
        mToolbar.getBackground().setAlpha(latestAlpha);
        mapView.onResume();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public final void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mapView.onSaveInstanceState(savedInstanceState);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.route_detail_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		 	case android.R.id.home:
	            finish();
	            return true;
	        case R.id.menu_share:
	        	shareContent();
	            return true;
	        case R.id.street_view:
                Intent intent = new Intent(getApplicationContext(), StreetViewFragment.class);
                intent.putExtra("route", routeItem);
                startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void shareContent() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, name + "\n" + link);
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getString(R.string.share_using)));
	}
	
	private int getActionBarHeight() {
	    int actionBarHeight = getSupportActionBar().getHeight();
	    if (actionBarHeight != 0)
	        return actionBarHeight;
	    final TypedValue tv = new TypedValue();
	    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
	         actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
	    return actionBarHeight;
	}
	
	private static int blendColors(float ratio, Context c) {
		int color1 = c.getResources().getColor(R.color.primary_dark_color);
		int color2 = c.getResources().getColor(R.color.black);
	    final float inverseRation = 1f - ratio;
	    float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
	    float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
	    float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
	    return Color.rgb((int) r, (int) g, (int) b);
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
        switch(placemark.getGeometry().getGeometryType()) {
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 1));
    }

    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFile(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {
            try {
                InputStream is =  new URL(mUrl).openStream();
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

        protected void onPostExecute(byte[] byteArr) {
            try {
                kmlLayer = new KmlLayer(googleMap, new ByteArrayInputStream(byteArr),
                        getApplicationContext());
                kmlLayer.addLayerToMap();
                moveCameraToKml(kmlLayer);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
