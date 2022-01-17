package br.java.google_map_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "google_map_api";

    private String[] activityNomes = {
            "MapaBasico", "MapaNavigacao", "MapaCameraEventos", "Marker", "Shape",
            "MapaTipoEhEstilo", "MapaConfiguracoes", "Snapshot", "MyLocation", "LocationSource",
            "Basic", "Navigation", "Events", "Settings", "AndMap"
    };

    private Spinner sActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sActivity = findViewById(R.id.sActivity);
        Button bStart = findViewById(R.id.bStart);
        bStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int activityId  = (int) sActivity.getSelectedItemId();

        String activityParaIniciar;

        if (activityId < 10)
            activityParaIniciar = "br.java.google_map_api.mapa." + activityNomes[activityId] + "Activity";
        else
            activityParaIniciar = "br.java.google_map_api.exibirRua.StreetView" + activityNomes[activityId] + "Activity";

        try {
            Class<?> c = Class.forName(activityParaIniciar);

            Intent intent = new Intent(this, c);
            startActivity(intent);
        } catch (ClassNotFoundException ignored) {

            Toast.makeText(MainActivity.this, "Classe não encontrada, nome de classe ou pacote incorreto.", Toast.LENGTH_SHORT).show();
        }
    }
}