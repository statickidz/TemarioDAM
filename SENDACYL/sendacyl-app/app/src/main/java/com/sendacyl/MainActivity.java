package com.sendacyl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.sendacyl.config.Config;
import com.sendacyl.menu.NavDrawerCallback;
import com.sendacyl.menu.ui.NavDrawerFragment;
import com.sendacyl.menu.model.NavItem;
import com.sendacyl.util.Helper;
import com.sendacyl.util.MyLocation;

public class MainActivity extends AppCompatActivity implements NavDrawerCallback {

    private Toolbar mToolbar;
    private NavDrawerFragment mNavigationDrawerFragment;
    
    public static String DATA = "transaction_data";
    
    SharedPreferences prefs;
    String mWebUrl = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set config context
        Config.setContext(getApplicationContext());

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.v("locazis", MyLocation.getLocationCoordinates(this));

        //Navigation drawer
        mNavigationDrawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.scrimInsetsFrameLayout, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        mNavigationDrawerFragment.getDrawerLayout().setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark_color));

        //Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		// New image loader
		Helper.initializeImageLoader(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.rss_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, NavItem item){
        Fragment fragment;
		try {
			fragment = item.getFragment().newInstance();
			if (fragment != null && null == mWebUrl) {
				//adding the data
		    	Bundle bundle = new Bundle();
		        String extra = item.getData();
		        bundle.putString(DATA, extra);
				fragment.setArguments(bundle);
				
				FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager.beginTransaction()
						.replace(R.id.container, fragment)
						.commit();
 
				setTitle(item.getText());
				
				if (null != MainActivity.this.getSupportActionBar() && null != MainActivity.this.getSupportActionBar().getCustomView()){
					MainActivity.this.getSupportActionBar().setDisplayOptions(
						    ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
				} 

			 } else {
		        // error in creating fragment
		        Log.e("MainActivity", "Error in creating fragment");
		     }
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void onBackPressed() {
    	Fragment webview = getSupportFragmentManager().findFragmentById(R.id.container);
    	
        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        } else {
        	super.onBackPressed();
        }
    }
}