package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private BottomNavigationView bottomNavigationView;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private MediaPlayer mp1 ,mp2, mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.wifiOn:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    textView.setText("Nincs jogosultság a wifi állapot módosítására");
                                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                                    startActivityForResult(panelIntent, 0);
                                }else {
                                    wifiManager.setWifiEnabled(true);
                                    textView.setText("Wifi bekapcsolva");
                                    mp1.start();
                                }
                                break;
                            case R.id.wifiOff:

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    textView.setText("Nincs jogosultság a wifi állapot módosítására");
                                    Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                                    startActivityForResult(panelIntent, 0);
                                }else {
                                    wifiManager.setWifiEnabled(false);
                                    textView.setText("Wifi kikapcsolva");
                                }
                                mp2.start();
                                break;
                            case R.id.wifiInfo:
                                ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                                if (netInfo.isConnected()){
                                    int ip_number = wifiInfo.getIpAddress();
                                    String ip = Formatter.formatIpAddress(ip_number);
                                    textView.setText("IP cim: "+ip);
                                } else {
                                    textView.setText("Nem csatlakoztál wifi hálózatra");
                                }

                                mp3.start();
                                break;
                        }
                        return true;
                    }
                });

    }

    private void init() {

        textView = findViewById(R.id.textVew);
        bottomNavigationView = findViewById(R.id.bottomNavi);

        mp1 = MediaPlayer.create(this,R.raw.wifi_on);
        mp2 = MediaPlayer.create(this,R.raw.wifi_off);
        mp3 = MediaPlayer.create(this,R.raw.wifi_info);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

    }
}