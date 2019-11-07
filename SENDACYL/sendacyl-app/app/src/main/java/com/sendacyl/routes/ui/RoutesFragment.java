package com.sendacyl.routes.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sendacyl.config.Config;
import com.sendacyl.util.Helper;
import com.sendacyl.MainActivity;
import com.sendacyl.R;
import com.sendacyl.routes.RoutesListAdapter;
import com.sendacyl.routes.model.RouteItem;
import com.sendacyl.util.MyLocation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * This activity is used to display a list of routes
 */

public class RoutesFragment extends Fragment {

    private ArrayList<RouteItem> routeList = null;
    private ListView feedListView = null;
    private View footerView;
    private Activity mAct;
    private RoutesListAdapter feedListAdapter = null;
    private Bundle savedInstanceState;
    private RoutesFragment routesFragment;
    private FilterDialog filterDialog;

    private LinearLayout ll;
    RelativeLayout pDialog;

    Integer pages;
    int perPage = 10;
    int start = 0;
    int end = 0;
    Integer currentPage = 1;

    String apiUrl;
    String baseUrl;
    String searchurl;
    String searchurlend;
    String pageUrl;

    Boolean isLoading = false;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        ll = (LinearLayout) inflater.inflate(R.layout.fragment_routes_list, container, false);
        setHasOptionsMenu(true);

        apiUrl = this.getArguments().getString(MainActivity.DATA);
        constructUrls();

        footerView = inflater.inflate(R.layout.listview_footer, null);
        feedListView = (ListView) ll.findViewById(R.id.custom_list);
        feedListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = feedListView.getItemAtPosition(position);
                RouteItem routeItem = (RouteItem) o;

                Intent intent = new Intent(mAct, RouteDetailActivity.class);
                intent.putExtra("route", routeItem);
                startActivity(intent);
            }
        });
        feedListView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (feedListAdapter == null)
                    return;

                if (feedListAdapter.getCount() == 0)
                    return;

                int l = visibleItemCount + firstVisibleItem;
                if (l >= totalItemCount && !isLoading && currentPage <= pages) {
                    new DownloadFilesTask(baseUrl, false).execute();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
        return ll;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAct = getActivity();

        //Setup filter dialog
        filterDialog = new FilterDialog(mAct);

        new DownloadFilesTask(baseUrl, true).execute();
    }

    public void constructUrls() {
        if (currentPage == 1)
            start = 0;
        else
            start = end;

        end = perPage * currentPage;
        String coordinates = MyLocation.getLocationCoordinates(this.getActivity());
        pageUrl = apiUrl + "?count=" + start + "%7C" + end + "&coords=" + coordinates;

        baseUrl = pageUrl;

        searchurl = Config.API_SEARCH_URL;
        searchurlend = "?count=" + start + "%7C" + end;

    }

    public void updateList(boolean initialload) {
        if (initialload) {
            feedListAdapter = new RoutesListAdapter(mAct, savedInstanceState, routesFragment, 0, routeList);
            feedListView.setAdapter(feedListAdapter);
        } else {
            feedListAdapter.addAll(routeList);
            feedListAdapter.notifyDataSetChanged();
        }
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, Void> {

        String url;
        boolean initialLoad;

        DownloadFilesTask(String url, boolean firstload) {
            this.url = url;
            this.initialLoad = firstload;
        }

        @Override
        protected void onPreExecute() {
            if (isLoading) {
                this.cancel(true);
            } else {
                isLoading = true;
            }
            if (initialLoad) {
                pDialog = (RelativeLayout) ll.findViewById(R.id.progressBarHolder);

                if (pDialog.getVisibility() == View.GONE) {
                    pDialog.setVisibility(View.VISIBLE);
                    feedListView.setVisibility(View.GONE);
                }

                currentPage = 1;

                if (null != routeList) {
                    routeList.clear();
                }
                if (null != feedListView) {
                    feedListView.setAdapter(null);
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    feedListView.addFooterView(footerView);
                }
            } else {
                feedListView.addFooterView(footerView);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            if (null != routeList) {
                updateList(initialLoad);
            } else {
                Helper.noConnection(mAct, true);
            }
            if (pDialog.getVisibility() == View.VISIBLE) {
                pDialog.setVisibility(View.GONE);
                //feedListView.setVisibility(View.VISIBLE);
                Helper.revealView(feedListView, ll);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    feedListView.removeFooterView(footerView);
                }
            } else {
                feedListView.removeFooterView(footerView);
            }
            isLoading = false;
        }

        @Override
        protected Void doInBackground(String... params) {
            //String url = params[0];
            //url = url + Integer.toString(curpage);
            currentPage = currentPage + 1;
            constructUrls();

            Log.v("INFO", "Step 0, started " + url);
            // getting JSON string from URL
            JSONObject json = getJSONFromUrl(url);
            Log.v("INFO", "Step 2, got JsonObjoct");
            //parsing json data
            parseJson(json);
            return null;
        }
    }


    public JSONObject getJSONFromUrl(String url) {
        /*InputStream is = null;
        JSONObject jObj = null;
        String json = null;

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.v("INFO", "Step 1, got Respons");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jObj = new JSONObject(json);
        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;*/

        JSONObject jObj = null;
        String json = null;

        // Making HTTP request
        try {
            URL requestUrl = new URL(url);
            URLConnection con = requestUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            int cp;
            while((cp = in.read())!=-1){
                sb.append((char)cp);
            }
            in.close();
            json = sb.toString();

            Log.v("INFO", "Step 1, got Response");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jObj = new JSONObject(json);
        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    public void parseJson(JSONObject json) {
        try {

            pages = json.getInt("pages");

            if (json.getString("status").equalsIgnoreCase("ok") || json.getString("status").equalsIgnoreCase("true")) {
                routeList = new ArrayList<RouteItem>();

                JSONArray routes = json.getJSONArray("routes");

                for (int i = 0; i < routes.length(); i++) {
                    RouteItem item = new RouteItem();
                    JSONObject route = routes.getJSONObject(i);

                    item.setId(route.getString("id"));
                    item.setName(Html.fromHtml(route.getString("equip_b_nombre")).toString());
                    item.setDate(route.getString("equip_a_fecha_declaracion"));
                    item.setDateState(route.getString("equip_a_estado_fecha"));
                    item.setUrl(Config.API_ROUTE_URL + route.getString("id"));
                    item.setDescription(route.getString("equip_a_observaciones"));
                    item.setDifficulty(route.getString("senda_dificultad"));
                    item.setDistance(route.getString("senda_longitud"));
                    item.setTime(route.getString("senda_tiempo_recorrido"));
                    item.setLocality(route.getString("gmaps_locality"));
                    item.setProvince(route.getString("gmaps_province"));
                    item.setStreetViewId(route.getString("gmaps_pano_id"));

                    String routeCode = route.getString("equip_a_codigo");
                    String routeImage = routeCode.substring(0, Math.min(routeCode.length(), 3));
                    item.setThumbnailUrl(Config.API_IMAGE_URL + routeImage + Config.API_IMAGE_EXT);
                    item.setKmlUrl(Config.API_KML_URL + route.getString("id") + Config.API_KML_EXT);

                    //Temporal fix for incomplete data
                    if (!route.getString("gmaps_locality").equals("") && !route.getString("gmaps_province").equals(""))
                        routeList.add(item);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);

        //set & get the search button in the actionbar
        final SearchView searchView = new SearchView(mAct);

        searchView.setQueryHint(getResources().getString(R.string.routes_search_hint));
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    query = URLEncoder.encode(query, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                searchView.clearFocus();

                baseUrl = searchurl + query + searchurlend;
                new DownloadFilesTask(baseUrl, true).execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


        searchView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                if (!isLoading) {
                    baseUrl = pageUrl;
                    new DownloadFilesTask(baseUrl, true).execute();
                }
            }

            @Override
            public void onViewAttachedToWindow(View arg0) {
                // search was opened
            }
        });

        //TODO make menu an xml item
        menu.add("search")
                .setIcon(R.drawable.ic_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        menu.add("filter")
                .setIcon(R.drawable.ic_filter)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        filterDialog.show();
                        return false;
                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:
                if (!isLoading) {
                    baseUrl = pageUrl;
                    new DownloadFilesTask(baseUrl, true).execute();
                } else {
                    Toast.makeText(mAct, getString(R.string.already_loading), Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
