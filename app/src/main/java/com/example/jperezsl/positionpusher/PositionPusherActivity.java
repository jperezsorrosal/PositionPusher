package com.example.jperezsl.positionpusher;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class PositionPusherActivity extends AppCompatActivity {

//    final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//
//    final String tmDevice, tmSerial, androidId;
//    tmDevice = "" + tm.getDeviceId();
//    tmSerial = "" + tm.getSimSerialNumber();
//    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
//    String deviceId = deviceUuid.toString();

//    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//    Criteria criteria = new Criteria();
//    criteria.setAccuracy()
//    Location location = lm.getBestProvider(new Criteria(), false); //getLastKnownLocation(LocationManager.GPS_PROVIDER);
//    double longitude = location.getLongitude();
//    double latitude = location.getLatitude();
//
//
    double longitude = 0.0;
    double latitude = 0.0;

//    private final LocationListener locationListener = new LocationListener() {
//
//        @Override
//        public void onLocationChanged(Location location) {
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
//
//    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_pusher);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.chkPushPosition);
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        CheckBox checkbox = (CheckBox) view;
        boolean checked = checkbox.isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.chkPushPosition:
                if (checked) {
                    System.out.println("Checked.");
                    AsyncT asyncT = new AsyncT();
                    asyncT.execute();
                }

                // Put some meat on the sandwich
                else {
                    System.out.println("Not Checked.");
                }

                // Remove the meat
                break;
            // TODO: Veggie sandwich
        }
    }


    /* Inner class to get response */
    class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            postData();
            return null;
        }

    }


    private void postData() {

        URL url = null;
        try {
            url = new URL("http://192.168.5.104:9999");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {

                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);


                JSONObject position = new JSONObject();
                JSONObject coordinates = new JSONObject();

                coordinates.put("longitude", longitude);
                coordinates.put("latitude", latitude);
                position.put("deviceID", "aaxlo23323d");
                position.put("coordinates", coordinates);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                //writeStream(out);
                System.out.println(position.toString());
                out.write(position.toString().getBytes());

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //readStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}
