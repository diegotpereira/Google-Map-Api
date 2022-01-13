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
import com.google.android.gms.maps.model.LatLngBounds;

import br.java.google_map_api.R;

public class MapaNavigacaoActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final float ROLAR_POR_PX = 100;
    private GoogleMap googleMap;

    public  static final CameraPosition TORRES = new CameraPosition.Builder()
            .target(new LatLng(-29.345405, -49.728500)).zoom(15.5f).bearing(300).tilt(25).build();

    private static final CameraPosition CAPAO = new CameraPosition.Builder()
            .target(new LatLng(-29.757171, -50.017498)).zoom(12.0f).bearing(0).tilt(0).build();

    private static final CameraPosition TRAMANDAI = new CameraPosition.Builder()
            .target(new LatLng(-29.977433, -50.123233)).zoom(12.0f).bearing(0).tilt(0).build();

    private static final LatLngBounds CAPAO_LIMITES =
            new LatLngBounds(new LatLng(-29.7, -50.01), new LatLng(-29.76, -50.01));

    private static final LatLngBounds TRAMANDAI_LIMITES =
            new LatLngBounds(new LatLng(-29.97, -50.12), new LatLng(-30.01, -50.13));


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

        Button btnRolarParaCima = findViewById(R.id.btnRolarParaCima);
        Button btnRolarParaBaixo = findViewById(R.id.btnRolarParaBaixo);

        Button btnRodarAnimacao = findViewById(R.id.btnRodarAnimacao);
        Button btnPararAnimacao = findViewById(R.id.btnPararAnimacao);
        Button btnRolarParaEsquerda = findViewById(R.id.btnRolarParaEsquerda);
        Button btnRolarParaDireita = findViewById(R.id.btnRolarParaDireita);

        btnInclinarMais.setOnClickListener(this);
        btnInclinarMenos.setOnClickListener(this);

        btnZoomIn.setOnClickListener(this);
        btnZoomOut.setOnClickListener(this);

        btnRodarAnimacao.setOnClickListener(this);
        btnPararAnimacao.setOnClickListener(this);

        btnRolarParaCima.setOnClickListener(this);
        btnRolarParaBaixo.setOnClickListener(this);
        btnRolarParaEsquerda.setOnClickListener(this);
        btnRolarParaDireita.setOnClickListener(this);

        // Carregar mapas
        Button btnIrMapaUm = findViewById(R.id.btnIrMapaUm);
        Button btnIrMapaDois = findViewById(R.id.btnIrMapaDois);
        Button btnIrMapaTres = findViewById(R.id.btnIrMapaTres);
        Button bClampToKawakami = findViewById(R.id.bClampToKawakami);
        Button bClampToAdelaide = findViewById(R.id.bClampToAdelaide);

        btnIrMapaUm.setOnClickListener(this);
        btnIrMapaDois.setOnClickListener(this);
        btnIrMapaTres.setOnClickListener(this);
        bClampToKawakami.setOnClickListener(this);
        bClampToAdelaide.setOnClickListener(this);
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
                break;

            case R.id.btnRodarAnimacao:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(TORRES));
                break;

            case R.id.btnPararAnimacao:
                googleMap.stopAnimation();
                break;



            case R.id.btnRolarParaCima:
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, -ROLAR_POR_PX));
                break;

            case R.id.btnRolarParaBaixo:
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, ROLAR_POR_PX));
                break;

            case R.id.btnRolarParaEsquerda:
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(-ROLAR_POR_PX, 0));
                break;

            case R.id.btnRolarParaDireita:
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(ROLAR_POR_PX, 0));


            case R.id.btnIrMapaUm:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(TORRES));
                break;

            case R.id.btnIrMapaDois:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CAPAO));
                break;

            case R.id.btnIrMapaTres:
                googleMap.setLatLngBoundsForCameraTarget(null);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(TRAMANDAI));
                break;

            case R.id.bClampToKawakami:
                googleMap.setLatLngBoundsForCameraTarget(TRAMANDAI_LIMITES);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(TRAMANDAI));
                break;

            case R.id.bClampToAdelaide:
                googleMap.setLatLngBoundsForCameraTarget(CAPAO_LIMITES);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CAPAO));
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