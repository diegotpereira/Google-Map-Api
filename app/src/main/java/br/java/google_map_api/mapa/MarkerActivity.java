package br.java.google_map_api.mapa;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.java.google_map_api.AdaptadorJanelaInformacoesPersonalizadas;
import br.java.google_map_api.MainActivity;
import br.java.google_map_api.R;

public class MarkerActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnInfoWindowLongClickListener {

    private GoogleMap googleMap;
    private static final LatLng SYDNEY = new LatLng(-34, 151);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicializar();
    }

    private void inicializar() {

        Button btnMarcaSimple = findViewById(R.id.btnMarcaSimple);
        Button btnMarcaOpcao = findViewById(R.id.btnMarcaOpcao);
        Button btnMarcaCustomizada = findViewById(R.id.btnMarcaCustomizada);

        btnMarcaSimple.setOnClickListener(this);
        btnMarcaOpcao.setOnClickListener(this);
        btnMarcaCustomizada.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnInfoWindowCloseListener(this);
        googleMap.setOnInfoWindowLongClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnMarcaSimple:
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(SYDNEY).title("Sydney"));
                break;

            case R.id.btnMarcaOpcao:
                googleMap.clear();
                AdaptadorJanelaInformacoesPersonalizadas.useJanelaInformacoesPadrao = true;
                adicionarMarcadoresAoMapa();
                googleMap.setInfoWindowAdapter(new AdaptadorJanelaInformacoesPersonalizadas(MarkerActivity.this));
                break;

            case R.id.btnMarcaCustomizada:
                googleMap.clear();
                AdaptadorJanelaInformacoesPersonalizadas.useJanelaInformacoesPadrao = false;
                adicionarMarcadoresAoMapa();
                googleMap.setInfoWindowAdapter(new AdaptadorJanelaInformacoesPersonalizadas(MarkerActivity.this));
                break;

        }
    }

    private void adicionarMarcadoresAoMapa() {
        MarkerOptions opcoes = new MarkerOptions();
        opcoes.position(BRISBANE);
        opcoes.title("brisbane");
        opcoes.snippet("População: 2,544,634");
        opcoes.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        opcoes.draggable(true);
        AdaptadorJanelaInformacoesPersonalizadas.brisbane = googleMap.addMarker(opcoes);

        MarkerOptions opcao = new MarkerOptions();
        opcao.position(ADELAIDE);
        opcao.title("adelaide");
        opcao.snippet("População: 3,543,222");
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person_pin_circle_black_24dp);
        opcao.icon(converterDrawableParaBitmap(drawable));
        opcao.draggable(true);
        AdaptadorJanelaInformacoesPersonalizadas.adelaide = googleMap.addMarker(opcao);
    }

    private BitmapDescriptor converterDrawableParaBitmap(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Log.d(MainActivity.TAG, "onMarkerClick() chamada com: marker = [" + marker.getTitle() + "]");

        return false;
    }
    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Log.d(MainActivity.TAG, "onInfoWindowClick() chamada com: marker = [" + marker.getTitle() + "]");
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

        Log.d(MainActivity.TAG, "onMarkerDragStart() chamada com: marker = [" + marker.getTitle() + "]");
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

        Log.d(MainActivity.TAG, "onMarkerDrag() chamada com: marker = [" + marker.getTitle() + "]");
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

        Log.d(MainActivity.TAG, "onMarkerDragEnd() chamada com: marker = [" + marker.getTitle() + "]");
    }

    @Override
    public void onInfoWindowClose(@NonNull Marker marker) {

        Log.d(MainActivity.TAG, "onInfoWindowClose() chamada com: marker = [" + marker.getTitle() + "]");
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {

        Log.d(MainActivity.TAG, "onInfoWindowLongClick() chamada com: marker = [" + marker.getTitle() + "]");
    }
}