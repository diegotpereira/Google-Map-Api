package br.java.google_map_api.mapa;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import br.java.google_map_api.R;

public class MapaNavigacaoActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final float SCROLL_BY_PX = 100;
    private GoogleMap googleMap;

    public  static final CameraPosition TORRES = new CameraPosition.Builder()
            .target(new LatLng(-29.345405, -49.728500)).zoom(15.5f).bearing(300).tilt(25).build();

    public static final CameraPosition CAPAO = new CameraPosition.Builder()
            .target(new LatLng(-29.757171, -50.017498)).zoom(15.5f).bearing(0).tilt(25).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_navigacao);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicialize();
    }

    private void inicialize() {

        // Carregar Funções
        Button btnInclinarMais = findViewById(R.id.btnInclinarMais);
        Button btnInclinarMenos = findViewById(R.id.btnInclinarMenos);

        Button btnZoomIn = findViewById(R.id.btnZoomIn);
        Button btnZoomOut = findViewById(R.id.btnZoomOut);

        Button btnRodarAnimacao = findViewById(R.id.btnRodarAnimacao);
        Button btnPararAnimacao = findViewById(R.id.btnPararAnimacao);

        btnInclinarMais.setOnClickListener(this);
        btnInclinarMenos.setOnClickListener(this);

        btnZoomIn.setOnClickListener(this);
        btnZoomOut.setOnClickListener(this);

        btnRodarAnimacao.setOnClickListener(this);
        btnPararAnimacao.setOnClickListener(this);

        // Carregar mapas
        Button btnMapaUm = findViewById(R.id.btnMapaUm);
        Button btnMapaDois = findViewById(R.id.btnMapaDois);

        btnMapaUm.setOnClickListener(this);
        btnMapaDois.setOnClickListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnInclinarMais:
                inclinarMais();
                break;

            case R.id.btnInclinarMenos:
                inclinarMenos();
                break;

            case R.id.btnZoomIn:
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.btnZoomOut:
                googleMap.moveCamera(CameraUpdateFactory.zoomOut());



            case R.id.btnMapaUm:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(TORRES));
                break;

            case R.id.btnMapaDois:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CAPAO));


            case R.id.btnRodarAnimacao:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(TORRES));
                break;

            case R.id.btnPararAnimacao:
                googleMap.stopAnimation();
                break;
        }
    }
    public void inclinarMais() {
        CameraPosition atualCameraPosicao = googleMap.getCameraPosition();

        float atualInclinacao = atualCameraPosicao.tilt;
        float novoInclinar = atualInclinacao + 10;
        novoInclinar = (novoInclinar > 90) ? 90 : novoInclinar;

        CameraPosition cameraPosicao = new CameraPosition.Builder(atualCameraPosicao).tilt(novoInclinar).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosicao));
    }

    public void inclinarMenos() {

        CameraPosition atualCameraPosicao = googleMap.getCameraPosition();

        float atualInclinacao = atualCameraPosicao.tilt;
        float novoInclinar = atualInclinacao - 10;
        novoInclinar = (novoInclinar > 0) ? novoInclinar : 0;

        CameraPosition cameraPosicao = new CameraPosition.Builder(atualCameraPosicao).tilt(novoInclinar).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosicao));
    }
}