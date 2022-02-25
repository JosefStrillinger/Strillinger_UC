package at.jstrillinger.strillinger_google_maps;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple demo for using google Maps. It shows how to...
 * ...generate a Map (as part of the whole screen)
 * ...set a marker
 * ...react on clicking on a marker or on the whole map
 *  To make it work, generate your own key in res/values/google_maps_api.xml The file has the following structure:
 *  <resources>
 *     <!--
 *     TODO: Before you run your application, you need a Google Maps API key.
 *
 *     To get one, follow this link, follow the directions and press "Create" at the end:
 *
 *     https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=5B:EA:4C:AF:07:45:CD:3C:4A:D9:AD:40:51:0F:83:B0:56:A1:C3:4D%3Bat.ac.htlinn.androidexamples.googlemaps
 *
 *     You can also add your credentials to an existing key, using these values:
 *
 *     Package name:
 *     at.ac.htlinn.androidexamples.googlemaps
 *
 *     SHA-1 certificate fingerprint:
 *     5B:EA:4C:AF:07:45:CD:3C:4A:D9:AD:40:51:0F:83:B0:56:A1:C3:4D
 *
 *     Alternatively, follow the directions here:
 *     https://developers.google.com/maps/documentation/android/start#get-key
 *
 *     Once you have your key (it starts with "AIza"), replace the "google_maps_key"
 *     string in this file.
 *     -->
 *     <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">ADD YOUR KEY HERE</string>
 * </resources>
 *
 */
public class SimpleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private TextView feedback;
    Button getLocations;
    private int markerCnt = 0;
    private boolean check = false;
    private String appKey = "";
    private String url = "";
    private String url2 = "";
    private double latitude = 47.259659;
    private double longitude = 11.400375;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("MissingPermission")   //ignore the warning
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_maps);
        appKey = getString(R.string.app_key);
        url = getString(R.string.url);
        url2 = getString(R.string.url2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocation();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Innsbruck, Austria.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    class Todo {
        private int id;
        @Getter
        private String name;
        @Getter
        private float loong;
        @Getter
        private float lat;
        private String message;
        private String when;

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }

        public float getLat() {
            return lat;
        }

        public float getLoong(){
            return loong;
        }

        public String getMessage(){
            return message;
        }

        public String getWhen(){
            return when;
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        //Settings how frequently updates should be fetched
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000); //this is the lowest rate at which update is called
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //this callback is periodically called
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                TextView locationText = findViewById(R.id.feedback);
                Log.d("LOCATION", "calling callback");
                if (locationResult == null) {
                    Log.d("LOCATION", "locationResult is null");
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null && !check) {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Innsbruck"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,10)); //Values from 2 to 21 possible
                    check = true;
                }

                return;


            }
        };
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        feedback = findViewById(R.id.feedback);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Innsbruck and move the camera
        LatLng ibk = new LatLng(47.259659,11.400375);



        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ibk));

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        feedback.setText(marker.getTitle());
        return true; //event is consumed
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(String.format("Marker %d", ++markerCnt)));
        longitude = latLng.longitude;
        latitude = latLng.latitude;
        PutServerAsyncTask task = new PutServerAsyncTask();
        task.execute(url + "0");
        feedback.setText("New Marker created!");
    }

    public void getRequest(View view) {

        GetServerAsyncTask task = new GetServerAsyncTask();

        task.execute(url2);

    }

    private class GetServerAsyncTask extends AsyncTask<String, Void, String> {
        @lombok.SneakyThrows  //to avoid exception handling
        @Override
        protected String doInBackground(String... urls) {
            //find more information on https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Download failed";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            result = result.replaceAll("long", "loong");
            Log.d("result", result);
            //we already know Gson for JSON Processing
            Gson gson = new Gson();
            Todo[] todo = gson.fromJson(result, Todo[].class);
            for(int i = 0; i < todo.length; i++) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(todo[i].getLat(), todo[i].getLoong())).title(String.format("Marker %d", ++markerCnt)));
            }


        }
    }

    private class PutServerAsyncTask extends AsyncTask<String, Void, String> {
        @lombok.SneakyThrows  //to avoid exception handling
        @Override
        protected String doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("info", new JSONObject()
                        .put("lat", latitude)
                        .put("long", longitude)
                        .put("message", "test")
                        .put("name", "Josef Strillinger"));
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(jsonObject);
                Log.d("JSON", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .addHeader("Accept", "application/json")
                            .addHeader("appkey", appKey)
                            .put(body)
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Upload failed";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            feedback.setText(result);
            //we already know Gson for JSON Processing
            Gson gson = new Gson();
            Todo todo = gson.fromJson(result, Todo.class);
            feedback.append("\n");
            feedback.append(String.format("TITLE: %s\n",todo.getName()));
        }
    }


}
