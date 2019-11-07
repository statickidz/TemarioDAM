package com.sendacyl.routes.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.sendacyl.R;
import com.sendacyl.routes.model.RouteItem;

/**
 *  This activity is used to display street view panorama based on route.
 */
public class StreetViewFragment extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    private Bundle savedInstanceState;

    private RouteItem routeItem;
    private String streetViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_street_view);

        routeItem = (RouteItem) this.getIntent().getSerializableExtra("route");
        streetViewId = routeItem.getStreetViewId();

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.street_view_panorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        streetViewPanoramaFragment.setUserVisibleHint(false);
        streetViewPanoramaFragment.setHasOptionsMenu(false);
        streetViewPanoramaFragment.setMenuVisibility(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        // Set the panorama location on startup, when no
        // panoramas have been loaded.
        if (savedInstanceState == null) {
            panorama.setPosition(streetViewId);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                decorView
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}