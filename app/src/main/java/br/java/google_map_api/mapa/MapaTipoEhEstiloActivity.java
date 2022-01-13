package br.java.google_map_api.mapa;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;

import br.java.google_map_api.R;

public class MapaTipoEhEstiloActivity extends FragmentActivity implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener{

    private GoogleMap googleMap;
    private static  final int[] mapTipos = {MAP_TYPE_NORMAL, MAP_TYPE_SATELLITE, MAP_TYPE_HYBRID, MAP_TYPE_TERRAIN, MAP_TYPE_NONE};
    private static final Integer[] mapaEstilos = {R.raw.mapstyle_grayscale, R.raw.mapstyle_night, R.raw.mapstyle_retro, R.raw.mapstyle_transit, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_tipo_eh_estilo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Spinner sMapaTipo = findViewById(R.id.sMapType);
        Spinner sMapaEstilo = findViewById(R.id.sMapStyle);

        sMapaTipo.setOnItemSelectedListener(this);
        sMapaEstilo.setOnItemSelectedListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        googleMap = map;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.sMapType) {
            googleMap.setMapType(mapTipos[i]);
        } else if (adapterView.getId() == R.id.sMapStyle) {

            try {
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, mapaEstilos[i]));
            } catch (Exception exc) {
                googleMap.setMapStyle(null);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}