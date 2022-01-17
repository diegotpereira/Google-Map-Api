package br.java.google_map_api.mapa;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import br.java.google_map_api.R;

public class SnapshotActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap googleMap;
    private ImageView ivImageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicializar();
    }

    private  void inicializar() {
        Button btnTakeSnapshot = findViewById(R.id.bTakeSnapshot);
        Button btnClear = findViewById(R.id.bClear);
        ivImageHolder = findViewById(R.id.ivImageHolder);

        btnTakeSnapshot.setOnClickListener(this);
        btnClear.setOnClickListener(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        googleMap = map;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bTakeSnapshot:
                googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {

                    @Override
                    public void onSnapshotReady(@Nullable Bitmap bitmap) {
                        ivImageHolder.setImageBitmap(bitmap);
                    }
                });
                break;
            case R.id.bClear:
                ivImageHolder.setImageDrawable(null);
                break;
        }
    }


}