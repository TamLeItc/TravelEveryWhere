package com.example.tamle.traveleverywhere.view.path;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamle.traveleverywhere.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by tamle on 28/12/2017.
 */

public class PathActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    public static final String TAG = PathActivity.class.getSimpleName();
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Polyline mPolyline;
    private boolean mIsAutoUpdateLocation = true;
    private boolean mIsMoveCamera = false;

    private SupportMapFragment mFrgMap;
    private GoogleMap mMap;

    private String mPlaceStartId;
    private String mPlaceEndId;
    private String mStringLastLocation;
    private LatLng mEndLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path);
        getDataFromIntent();

        initSupportMapFragment();

        if(!checkGoogleService()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Device does not support Google Play services");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    else{
                        finish();
                    }
                }
            });
        }
    }

    private void getDataFromIntent(){
        Intent intent = getIntent();
        this.mPlaceEndId = intent.getStringExtra("place_end_id");
    }

    private void initSupportMapFragment(){
        this.mFrgMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frg_map);
        this.mFrgMap.getMapAsync(this);
    }

    //Kiểm tra máy có hỗ trợ dịch vụ Google không
    private boolean checkGoogleService(){
        if(isPlayServicesAvailable()){
            setUpLocationClientIfNeeded();
            buildLocationRequest();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPlayServicesAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
                == ConnectionResult.SUCCESS;
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void buildLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    //Kiểm tra xem GPS có đang bậc hay không
    private boolean isGpsOn() {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void updateUi() {
        //Kiểm tra GPS có bật chưa
        if(!isGpsOn()){
            Toast.makeText(this, "GPS is off", Toast.LENGTH_SHORT).show();
        }

        //Kiểm tra xem có quyền cập quyền truy cập vị trí hiện tại hay không
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Tiến hành cập nhật lại vị trí hiện tại
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            mLastLocation = lastLocation;
        }

        if (mLastLocation != null) {
            //Khởi tạo nên một vị trí mới
            LatLng position = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            this.mStringLastLocation = position.latitude + "," + position.longitude;

            getPlaceIdByLatLng();

            //Duy chuyển tới vị trí hiện tại
            if(!mIsMoveCamera){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 11));
                this.mIsMoveCamera = true;
            }
        }
    }

    //Thiết lập việc cập nhật vị trí tự động
    protected void startLocationUpdates() {

        //Kiểm tra xem có quyền cập quyền truy cập vị trí hiện tại hay không
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Cập nhật vị trí tự động
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    //Tắt tự động cập nhật ví trí
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            mLastLocation = lastLocation;
        }

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, String.format(Locale.getDefault(), "onLocationChanged : %f, %f",
                location.getLatitude(), location.getLongitude()));

        mLastLocation = location;

        //Update lại ví trí tự động
        if (mIsAutoUpdateLocation) {
            updateUi();
        }
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient != null
                && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        Log.d(TAG, "onDestroy LocationService");
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //Kiểm tra có quyền truy cập vị trí chưa
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Kiểm tra GPS có bật chưa
            if(!isGpsOn()){
                Toast.makeText(this, "GPS is off", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        mMap.setMyLocationEnabled(true);
    }

    //Lấy place_id thông qua vị trí
    private void getPlaceIdByLatLng(){
        String urlGetPlaceId = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + this.mStringLastLocation +
                "&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

        RequestQueue requestQueue = Volley.newRequestQueue(PathActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetPlaceId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArrayResult = jsonObject.getJSONArray("results");

                            if(jsonArrayResult.length() > 1){
                                JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);

                                mPlaceStartId = jsonObjectResult.getString("place_id");

                                getDirections();
                            }
                            else{
                                Toast.makeText(PathActivity.this, "Data error", Toast.LENGTH_SHORT);
                            }

                        } catch (JSONException e) {
                            Log.d(TAG, e.toString());
                            Toast.makeText(PathActivity.this, "Data error", Toast.LENGTH_SHORT);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        Toast.makeText(PathActivity.this, "Please check internet connect", Toast.LENGTH_SHORT);
                    }
                });
        requestQueue.add(stringRequest);
    }

    //Vẽ đường đi từ vị trí hiện tại đến vị trí cần tới
    private void getDirections(){

        String urlGetPath = "https://maps.googleapis.com/maps/api/directions/json?origin=place_id:" +
                this.mPlaceStartId + "&destination=place_id:" + this.mPlaceEndId
                + "&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

        Log.d(TAG, urlGetPath);

        RequestQueue requestQueue = Volley.newRequestQueue(PathActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetPath,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            createPath(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void createPath(String data) throws JSONException, Exception {

        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArrayRoutes = jsonObject.getJSONArray("routes");
        JSONObject jsonObjectRoute = jsonArrayRoutes.getJSONObject(0);
        JSONArray jsonArrayLegs = jsonObjectRoute.getJSONArray("legs");
        JSONObject jsonObjectLeg = jsonArrayLegs.getJSONObject(0);
        JSONObject jsonObjectDistance = jsonObjectLeg.getJSONObject("distance");
        JSONObject jsonObjectDuration = jsonObjectLeg.getJSONObject("duration");
        JSONObject jsonObjectEndLocation = jsonObjectLeg.getJSONObject("end_location");
        JSONObject jsonObjectOverviewPolyline = jsonObjectRoute.getJSONObject("overview_polyline");

        String distance = jsonObjectDistance.getString("text");
        String duration = jsonObjectDuration.getString("text");
        this.mEndLocation = new LatLng(Double.parseDouble(jsonObjectEndLocation.getString("lat"))
                , Double.parseDouble(jsonObjectEndLocation.getString("lng")));
        String points = jsonObjectOverviewPolyline.getString("points");
        List<LatLng> latLngList = decodePolyLines(points);

        PolylineOptions polylineOptions = new PolylineOptions();
        for(LatLng latLng : latLngList){
            polylineOptions.add(latLng);
        }

        mMap.addMarker(new MarkerOptions()
                        .position(this.mEndLocation));

        if(this.mPolyline != null){
            mPolyline.remove();
        }
        mPolyline = mMap.addPolyline(polylineOptions);

    }

    //Decode polylines
    private List<LatLng> decodePolyLines(String poly){
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len){
            int b;
            int shift = 0;
            int result = 0;
            do{
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >>1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d,
                    lng / 100000d
            ));
        }
        return decoded;
    }
}
