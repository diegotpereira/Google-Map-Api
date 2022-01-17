package br.java.google_map_api.exibirRua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import br.java.google_map_api.R;

public class StreetViewAndMapActivity extends FragmentActivity implements
        OnStreetViewPanoramaReadyCallback, OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
        StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private StreetViewPanorama streetViewPanorama;
    private GoogleMap googleMap;
    private Marker marker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_and_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SupportStreetViewPanoramaFragment fragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.street_view);
        fragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        googleMap.setOnMarkerDragListener(this);

        marker = googleMap.addMarker(new MarkerOptions()
        .position(SYDNEY)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pegman))
        .draggable(true));
    }

    @Override
    public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {

        streetViewPanorama = streetViewPanorama;

        streetViewPanorama.setOnStreetViewPanoramaChangeListener(this);
        streetViewPanorama.setPosition(SYDNEY);
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

        streetViewPanorama.setPosition(marker.getPosition());
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }



    @Override
    public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation) {

        if (streetViewPanoramaLocation != null) {
            marker.setPosition(streetViewPanoramaLocation.position);
        }
    }
}