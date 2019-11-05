package com.example.ilm.demoservicio;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ilm on 19/11/2014.
 */
public class WirelessTester extends Service {

    final String tag="Demo Servicio";
    public boolean enEjecucion=false;
    public boolean wifi_activo=false;
    public boolean tresg_activo=false;
    public int cont;
    private Tester tester;
    SharedPreferences sp;


    /** Llamado cuando se crea el servicio. */
    @Override
    public void onCreate() {
        Log.i(tag, "Servicio WirelessTester creado!");
        SharedPreferences sp = getSharedPreferences("WirelessTester", Activity.MODE_PRIVATE);
        cont = sp.getInt("contador", 0);
        tester=new Tester();
    }

    /** El servicio se arranca mediante una llamada startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!enEjecucion) {
            enEjecucion = true;
            tester.start();
            Log.i(tag, "Servicio WirelessTester arrancado!");
        }
        else {
            Log.i(tag, "El servicio WirelessTester ya estaba arrancado!");
        }

        return START_STICKY;
    }

    /** un cliente se vincula cuando llama a bindService()
     *  Como es un servicio no vinculado, devolvemos null */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Llamado cuando se destruye el servicio */
    @Override
    public void onDestroy() {
        Log.i(tag, "Servicio WirelessTester destruido!");
        if(enEjecucion)
            tester.interrupt();
    }


    private class Tester extends Thread{

        @Override
        public void run() {
            while(enEjecucion) {
                sp = getSharedPreferences("WirelessTester", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("contador", cont);
                editor.apply();
                cont++;
                Log.i(tag, "contador... " + cont);
                try {
                    Log.i(tag, "servicio ejecutándose....");
                    if(wifi_activo!=CompruebaConexion(ConnectivityManager.TYPE_WIFI)){
                        wifi_activo=!wifi_activo; //Cambio de estado
                        if(wifi_activo)
                            Log.i(tag,"Conexión WIFI activada");
                        else
                            Log.i(tag,"Conexión WIFI desactivada");
                    }


                    if(tresg_activo!=CompruebaConexion(ConnectivityManager.TYPE_MOBILE)){
                        tresg_activo=!tresg_activo; //Cambio de estado
                        if(tresg_activo)
                            Log.i(tag,"Conexión 3G activada");
                        else
                            Log.i(tag,"Conexión 3G desactivada");
                    }

                    this.sleep(3000);
                } catch (InterruptedException e) {
                    enEjecucion=false;
                    Log.i(tag, "hilo del servicio interrumpido....");
                }
            }

        }

        public boolean CompruebaConexion(int type){
            //crea un objeto ConnectivityManager que nos da información de la red
            ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                //Objtener información de la red: acceso vía WIFI
                NetworkInfo info = connectivity.getNetworkInfo(type);
                if (info != null) {
                    //Mirar si el dispositivo está conectado por WIFI
                    if (info.isConnected()) {
                        return true; //hay conexión
                    }
                }
            }
            return false; //no hay conexión
        }


    }

}
