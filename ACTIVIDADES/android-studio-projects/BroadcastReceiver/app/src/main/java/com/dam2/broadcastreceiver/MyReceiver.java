package com.dam2.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    private static final String AIRPLANE_MODE = "android.intent.action.AIRPLANE_MODE";
    private static final String NEW_PICTURE = "com.android.camera.NEW_PICTURE";
    private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if(intent.getAction().equals(AIRPLANE_MODE)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            editor.putString("lastChanged", dateFormat.format(date));
            editor.apply();
            Toast.makeText(context, "Modo avión cambiado", Toast.LENGTH_LONG).show();
        }

        if(intent.getAction().equals(NEW_PICTURE)) {
            Cursor cursor = context.getContentResolver().query(intent.getData(), null, null, null, null);
            if (cursor != null) {
                    cursor.moveToFirst();

                    String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                    Toast.makeText(context, "Foto: " + imagePath, Toast.LENGTH_LONG).show();

                    editor.putString("lastPhoto", imagePath);
                    editor.apply();

                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
            }
        }

        if(intent.getAction().equals(PHONE_STATE)) {
            Toast.makeText(context, "llamando tu madre", Toast.LENGTH_LONG).show();
            Log.v("llamando", "a tu madre");
        }


    }

}
