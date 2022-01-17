package br.java.google_map_api.exibirRua;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import br.java.google_map_api.R;


public class StreetViewBasicActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private StreetViewPanorama streetViewPanorama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_basic);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.street_view);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

    }
    @Override
    public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {

        streetViewPanorama = streetViewPanorama;
        streetViewPanorama.setPosition(SYDNEY);

    }
}