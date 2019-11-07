package com.sendacyl.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.view.View;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebHelper {
	
	@SuppressLint("NewApi")
	public static String docToBetterHTML(Document doc, Context c){
		try {
			doc.select("img[src]").removeAttr("width");
		} catch (Exception e){
			e.printStackTrace();
		} try {
			doc.select("a[href]").removeAttr("style");
		} catch (Exception e){
			e.printStackTrace();
		} try {	
			doc.select("div").removeAttr("style");
		} catch (Exception e){
			e.printStackTrace();
		} try {	
			doc.select("iframe").attr("width", "100%");
		} catch (Exception e){
			e.printStackTrace();
		}
		
		String rtl;
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		Configuration config = c.getResources().getConfiguration();
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL){
			rtl =  "direction:RTL; unicode-bidi:embed;";
		} else {
			rtl = "";
		}
		
		String style =   "<style>" +
						"img{" +
							"max-width: 100%; " +
							"width: auto; height: auto;" +
						"} p{" +
							"max-width: 100%; " +
							"width:auto; " +
							"height: auto;" +
						"}" +
						"@font-face {" +
								  "font-family: 'Currents-Light-Sans';" +
								  "font-style: normal;" +
								  "font-weight: normal;" +
								  "src: local('sans-serif-light'), url('file:///android_asset/fonts/Roboto-Light.ttf') format('truetype');" +
						"} body p {  " +
							"font-family: 'Currents-Light-Sans', serif;"+
						"} body {  " +
							"max-width: 100% !important;" + 
							"font-family: 'Currents-Light-Sans', serif;" +
							"margin: 0;"+
							"padding: 0;"+
							rtl +
						"}"+
						"</style>";
		
		Element header = doc.head();
		header.append(style);
		
        String html = doc.toString();
        return html;
	}
	
	public static int getWebViewFontSize(Context c){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
		String prefList = sharedPreferences.getString("fontSize", "16");
		return Integer.parseInt(prefList);
	}
}