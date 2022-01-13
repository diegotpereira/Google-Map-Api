package br.java.google_map_api.mapa;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

import br.java.google_map_api.R;


public class ShapeActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnCircleClickListener, GoogleMap.OnPolygonClickListener,
        GoogleMap.OnPolylineClickListener, GoogleMap.OnGroundOverlayClickListener {

    private GoogleMap googleMap;
    private static final int PATTERN_DASH_LENGTH_PX = 100;
    private static final int PATTERN_GAP_LENGTH_PX = 200;
    private static final Dot DOT = new Dot();
    private static final Dash DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
    private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
    private static final List<PatternItem> PATTERN_MIXED = Arrays.asList(DOT, GAP, DOT, DASH, GAP);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicializar();
    }

    private void inicializar() {

        Button btnCirculo = findViewById(R.id.btnCirculo);
        Button btnPoligono = findViewById(R.id.btnPoligono);
        Button btnPolilinha = findViewById(R.id.btnPolilinha);
        Button btnSobreposicoes = findViewById(R.id.btnSobreposicoes);

        btnCirculo.setOnClickListener(this);
        btnPoligono.setOnClickListener(this);
        btnPolilinha.setOnClickListener(this);
        btnSobreposicoes.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setOnCircleClickListener(this);
        googleMap.setOnPolygonClickListener(this);
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnGroundOverlayClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnCirculo:
                addCirculo();
                break;

            case R.id.btnPoligono:
                addPoligono();
                break;

            case R.id.btnPolilinha:
                addPolilinha();
                break;

            case R.id.btnSobreposicoes:
                addSobreposicoes();
                break;
        }
    }
    private void addCirculo() {

        googleMap.clear();
        CircleOptions opcoesCirculo = new CircleOptions();
        opcoesCirculo.center(new LatLng(37, 67));
        opcoesCirculo.radius(100000);
        opcoesCirculo.strokeColor(Color.RED);
        opcoesCirculo.strokeWidth(10f);
        opcoesCirculo.strokePattern(PATTERN_MIXED);
        opcoesCirculo.clickable(true);
        opcoesCirculo.fillColor(Color.GREEN);

        googleMap.addCircle(opcoesCirculo).setTag(new EtiquetaPersonalizada("circulo"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37, 67)));
    }

    private void addPoligono() {
        googleMap.clear();
        PolygonOptions opcoesPoligono = new PolygonOptions();
        opcoesPoligono.add(new LatLng(0, 0));
        opcoesPoligono.add(new LatLng(-3, 2.5));
        opcoesPoligono.add(new LatLng(0, 5));
        opcoesPoligono.add(new LatLng(3, 5));
        opcoesPoligono.add(new LatLng(3, 0));
        opcoesPoligono.add(new LatLng(0, 0));
        opcoesPoligono.clickable(true);
        opcoesPoligono.fillColor(Color.BLACK);

        googleMap.addPolygon(opcoesPoligono).setTag(new EtiquetaPersonalizada("poligono"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0 ,0)));
    }

    private void addPolilinha() {

        googleMap.clear();
        PolylineOptions opcoesPolilinha = new PolylineOptions();
        opcoesPolilinha.add(new LatLng(37.35, -122.0));
        opcoesPolilinha.add(new LatLng(37.45, -122.0));
        opcoesPolilinha.add(new LatLng(37.45, -122.2));
        opcoesPolilinha.add(new LatLng(37.35, -122.2));
        opcoesPolilinha.clickable(true);
        opcoesPolilinha.color(Color.CYAN);

        googleMap.addPolyline(opcoesPolilinha).setTag(new EtiquetaPersonalizada("polilinha"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));

    }
    private void addSobreposicoes() {
        googleMap.clear();
        GroundOverlayOptions opcoesSobreposicao = new GroundOverlayOptions();
        opcoesSobreposicao.image(BitmapDescriptorFactory.fromResource(R.drawable.city)).anchor(0, 1);
        opcoesSobreposicao.position(new LatLng(40, -74), 8600f, 6500f);
        opcoesSobreposicao.bearing(0);
        opcoesSobreposicao.clickable(true);

        googleMap.addGroundOverlay(opcoesSobreposicao).setTag(new EtiquetaPersonalizada("sobreposição de solo"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40, -74)));
    }

    @Override
    public void onCircleClick(@NonNull Circle circle) {
        onClick((EtiquetaPersonalizada) circle.getTag());
    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {

        onClick((EtiquetaPersonalizada) polygon.getTag());
    }
    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {

        onClick((EtiquetaPersonalizada) polyline.getTag());
    }


    @Override
    public void onGroundOverlayClick(@NonNull GroundOverlay groundOverlay) {

        onClick((EtiquetaPersonalizada) groundOverlay.getTag());
    }

    private void onClick(EtiquetaPersonalizada tag) {

        tag.incrementarCliqueContar();

        Toast.makeText(ShapeActivity.this, tag.toString(), Toast.LENGTH_SHORT).show();
    }




    private static class EtiquetaPersonalizada {

        private final String descricao;
        private int cliqueContar;

        public EtiquetaPersonalizada(String descricao) {
            this.descricao = descricao;
            this.cliqueContar = 0;
        }

        void incrementarCliqueContar() {

            cliqueContar++;
        }

        @Override
        public String toString() {
            return "A " + descricao + " foi clicado " + cliqueContar + " vezes.";
        }
    }
}