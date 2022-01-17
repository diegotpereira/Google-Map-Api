package br.java.google_map_api.exibirRua;

import static com.google.android.gms.maps.CameraUpdateFactory.zoomIn;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomOut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import br.java.google_map_api.R;

public class StreetViewNavigationActivity extends FragmentActivity implements
        OnStreetViewPanoramaReadyCallback, View.OnClickListener {

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng SAN_FRANCISCO = new LatLng(37.769263, -122.450727);
    private static final int PAN_BY_DEG = 30;
    private static final float ZOOM_BY = 0.5f;
    private StreetViewPanorama streetViewPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_navigation);

        SupportStreetViewPanoramaFragment fragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.street_view);
        fragment.getStreetViewPanoramaAsync(this);

        inicializar();
    }

    private void inicializar() {

        Button bPanUp = findViewById(R.id.bPanUp);
        Button bPanDown = findViewById(R.id.bPanDown);
        Button bPanLeft = findViewById(R.id.bPanLeft);
        Button bPanRight = findViewById(R.id.bPanRight);
        Button bZoomIn = findViewById(R.id.bZoomIn);
        Button bZoomOut = findViewById(R.id.bZoomOut);
        Button bWalk = findViewById(R.id.bWalk);
        Button bPosition = findViewById(R.id.bPosition);
        Button bGoToSydney = findViewById(R.id.bGoToSydney);
        Button bGoToSanFrancisco = findViewById(R.id.bGoToSanFrancisco);

        bPanUp.setOnClickListener(this);
        bPanDown.setOnClickListener(this);
        bPanLeft.setOnClickListener(this);
        bPanRight.setOnClickListener(this);
        bZoomIn.setOnClickListener(this);
        bZoomOut.setOnClickListener(this);
        bWalk.setOnClickListener(this);
        bPosition.setOnClickListener(this);
        bGoToSydney.setOnClickListener(this);
        bGoToSanFrancisco.setOnClickListener(this);
    }

    @Override
    public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {
        streetViewPanorama = streetViewPanorama;
        streetViewPanorama.setPosition(SYDNEY);
    }

    @Override
    public void onClick(View view) {

        if (streetViewPanorama == null) {

            Toast.makeText(StreetViewNavigationActivity.this, "O Street View não está pronto.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (view.getId())
        {
            case R.id.bPanUp:
                panUp();
                break;
            case R.id.bPanDown:
                panDown();
                break;
            case R.id.bPanRight:
                panRight();
                break;
            case R.id.bPanLeft:
                panLeft();
                break;
            case R.id.bZoomIn:
                zoomIn();
                break;
            case R.id.bZoomOut:
                zoomOut();
                break;
            case R.id.bWalk:
                walk();
                break;
            case R.id.bPosition:
                Toast.makeText(view.getContext(), streetViewPanorama.getLocation().position.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.bGoToSydney:
                streetViewPanorama.setPosition(SYDNEY);
                break;
            case R.id.bGoToSanFrancisco:
                streetViewPanorama.setPosition(SAN_FRANCISCO);
                break;
        }

    }

    private void panUp() {

        float currentTilt = streetViewPanorama.getPanoramaCamera().tilt;
        float newTilt = currentTilt + PAN_BY_DEG;
        newTilt = (newTilt > 90) ? 90 : newTilt;

        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                .tilt(newTilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing)
                .build(), 1000);
    }
    private void panDown() {
        float currentTilt = streetViewPanorama.getPanoramaCamera().tilt;
        float newTilt = currentTilt - PAN_BY_DEG;
        newTilt = (newTilt < -90) ? -90 : newTilt;

        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                .tilt(newTilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing)
                .build(), 1000);
    }

    private void walk() {

        StreetViewPanoramaLocation location = streetViewPanorama.getLocation();
        StreetViewPanoramaCamera camera = streetViewPanorama.getPanoramaCamera();
        if (location != null && location.links != null)
        {
            StreetViewPanoramaLink link = findClosestLinkToBearing(location.links, camera.bearing);
            streetViewPanorama.setPosition(link.panoId);
        }
    }

    private StreetViewPanoramaLink findClosestLinkToBearing(StreetViewPanoramaLink[] links, float bearing) {
        float minBearingDiff = 360;
        StreetViewPanoramaLink closestLink = links[0];
        for (StreetViewPanoramaLink link : links)
        {
            if (minBearingDiff > findNormalizedDifference(bearing, link.bearing))
            {
                minBearingDiff = findNormalizedDifference(bearing, link.bearing);
                closestLink = link;
            }
        }
        return closestLink;

    }

    private float findNormalizedDifference(float a, float b) {

        float diff = a - b;
        float normalizedDiff = diff - (float) (360 * Math.floor(diff / 360.0f));
        return (normalizedDiff < 180.0f) ? normalizedDiff : 360.0f - normalizedDiff;
    }

    private void panLeft() {

        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing - PAN_BY_DEG)
                .build(), 1000);
    }

    private void panRight() {

        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing + PAN_BY_DEG)
                .build(), 1000);
    }

    public void zoomIn()
    {
        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom + ZOOM_BY)
                .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing)
                .build(), 1000);
    }

    public void zoomOut()
    {
        streetViewPanorama.animateTo(new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom - ZOOM_BY)
                .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing)
                .build(), 1000);
    }
}