package com.sendacyl.config;

import android.content.Context;

import com.sendacyl.R;
import com.sendacyl.settings.ui.SettingsFragment;
import com.sendacyl.menu.model.NavItem;
import com.sendacyl.routes.ui.RoutesFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static Context context;

    public static final String API_ROUTES_URL = "https://sendacyl.statickidz.com/api/routes/";
    public static final String API_ROUTE_URL = "https://sendacyl.statickidz.com/api/routes/view/";
    public static final String API_PROVINCE_URL = "https://sendacyl.statickidz.com/api/routes/province/";
    public static final String API_FILTER_URL = "https://sendacyl.statickidz.com/api/routes/filter/";
    public static final String API_SEARCH_URL = "https://sendacyl.statickidz.com/api/routes/search/";

    public static final String API_IMAGE_URL = "https://sendacyl.statickidz.com/public/img/";
    public static final String API_KML_URL = "https://sendacyl.statickidz.com/public/kml/";

    public static final String API_KML_EXT = ".kml";
    public static final String API_IMAGE_EXT = ".jpg";

	public static List<NavItem> configuration() {
		
		List<NavItem> i = new ArrayList<NavItem>();

		i.add(new NavItem(context.getString(R.string.app_name), NavItem.SECTION));
        i.add(new NavItem(context.getString(R.string.explore), R.drawable.ic_details, NavItem.ITEM, RoutesFragment.class, API_ROUTES_URL));

        i.add(new NavItem(context.getString(R.string.routes), NavItem.SECTION));
        String[] provinces = context.getResources().getStringArray(R.array.provinces);
        for(String province : provinces) {
            try {
                i.add(new NavItem(province, R.drawable.ic_details, NavItem.ITEM, RoutesFragment.class,
                        API_PROVINCE_URL + URLEncoder.encode(province, "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        i.add(new NavItem(context.getString(R.string.config), NavItem.SECTION));
        i.add(new NavItem(context.getString(R.string.options), R.drawable.ic_action_settings, NavItem.EXTRA, SettingsFragment.class, null));

        return i;
			
	}

    public static void setContext(Context context) {
        Config.context = context;
    }
}