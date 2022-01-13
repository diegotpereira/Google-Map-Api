package br.java.google_map_api.mapa;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.java.google_map_api.R;


public class MapaCameraEventosActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
             GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
             GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener{

    public static String TAG = "MapaActivity";
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_camera_eventos);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        googleMap.setOnMapLoadedCallback(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);

        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraMoveStartedListener(this);
        googleMap.setOnCameraMoveListener(this);
        googleMap.setOnCameraMoveCanceledListener(this);
    }

    @Override
    public void onMapLoaded() {

        Log.d(TAG, "onMapLoaded() chamada");
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        googleMap.stopAnimation();
        Log.d(TAG, "onMapClick() chamada com: latLng = [" + latLng.latitude + ", " + latLng.longitude +  "]");
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        LatLng sydney = new LatLng(-33.87365, 151.20689);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        Log.d(TAG, "onMapLongClick() chamada com: latLng =  [" + latLng.latitude + ", " + latLng.longitude + "] ");

    }

    @Override
    public void onCameraIdle() {
        Log.d(TAG, "onCameraIdle() chamada");
    }

    @Override
    public void onCameraMoveStarted(int i) {
        Log.d(TAG, "onCameraMoveStarted() chamada com: i = ["+ i +"]");
    }

    @Override
    public void onCameraMove() {
        Log.d(TAG, "onCameraMove() chamada");
    }
    @Override
    public void onCameraMoveCanceled() {
        Log.d(TAG, "onCameraMoveCanceled() chamada");
    }
}