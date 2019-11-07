package com.sendacyl.settings.ui;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.preference.PreferenceFragment;
import android.text.Html;
import android.widget.Toast;

import com.sendacyl.R;

/**
 * This fragment is used to show a settings page to the user
 */

public class SettingsFragment extends PreferenceFragment {

    String start;
    String menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

        //open play store page
        Preference preferencerate = findPreference("rate");
        preferencerate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Could not open Play Store", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return true;
            }
        });


        //open about dialog
        Preference preferenceabout = findPreference("about");
        preferenceabout.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder ab = null;
                ab = new AlertDialog.Builder(getActivity());
                ab.setMessage(Html.fromHtml(getResources().getString(R.string.about_text)));
                ab.setPositiveButton(getResources().getString(R.string.ok), null);
                ab.setTitle(getResources().getString(R.string.about_header));
                ab.show();
                return true;
            }
        });

    }

}