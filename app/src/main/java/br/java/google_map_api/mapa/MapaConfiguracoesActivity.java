package br.java.google_map_api.mapa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.java.google_map_api.R;

public class MapaConfiguracoesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private LatLng TORRE_EIFFEL = new LatLng(48.8582667, 2.2944489);
    private LatLng MADISON_SQUARE_GARDEN = new LatLng(40.7503388, -73.9934577);

    private static final int CODIGO_SOLICITACAO_PERMISSAO_LOCAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_configuracoes);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        googleMap = map;

        UiSettings uiConfig = googleMap.getUiSettings();

        ativarMeuLocal();
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);

        uiConfig.setCompassEnabled(true);
        uiConfig.setMyLocationButtonEnabled(true);
        uiConfig.setZoomControlsEnabled(true);
        uiConfig.setMapToolbarEnabled(true);
        uiConfig.setIndoorLevelPickerEnabled(true);

        uiConfig.setRotateGesturesEnabled(true);
        uiConfig.setScrollGesturesEnabled(true);
        uiConfig.setTiltGesturesEnabled(true);
        uiConfig.setZoomGesturesEnabled(true);
        uiConfig.setAllGesturesEnabled(true);

        googleMap.addMarker(new MarkerOptions().position(MADISON_SQUARE_GARDEN));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MADISON_SQUARE_GARDEN, 17));
    }

    // precisa para android 6 e superior
    private void ativarMeuLocal() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
    }
}