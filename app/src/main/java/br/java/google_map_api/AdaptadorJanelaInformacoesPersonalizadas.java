package br.java.google_map_api;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class AdaptadorJanelaInformacoesPersonalizadas implements GoogleMap.InfoWindowAdapter {

    private final View janela;
    private final View conteudo;

    public static Marker brisbane;
    public static Marker adelaide;
    public static boolean useJanelaInformacoesPadrao;


    public AdaptadorJanelaInformacoesPersonalizadas(Activity activity) {
        janela = activity.getLayoutInflater().inflate(R.layout.janela_informacoes_personalizadas, null);
        conteudo = activity.getLayoutInflater().inflate(R.layout.conteudo_informacoes_personalizadas, null);
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        if (useJanelaInformacoesPadrao) {

            // Isso significa que getInfoContents será chamado.
            return null;
        }
        renderizar(marker, janela);

        return janela;
    }
    public View getInfoContents(@NonNull Marker marker) {

        if (useJanelaInformacoesPadrao) {

            // Isso significa que o conteúdo de informações padrão será usado.
            return null;
        }
        renderizar(marker, conteudo);
        return conteudo;
    }

    public void renderizar(Marker marker, View view){

        int marcador;

        if (marker.equals(brisbane)) {
            marcador = R.drawable.brisbane;
        } else if (marker.equals(adelaide)) {
            marcador = R.drawable.adelaide;
        } else {
            marcador = 0;
        }
        ((ImageView) view.findViewById(R.id.marcador)).setImageResource(marcador);

        String titulo = marker.getTitle();
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        tvTitulo.setText(titulo);

        String trecho = marker.getSnippet();
        TextView tvTrecho = view.findViewById(R.id.tvTrecho);
        tvTrecho.setText(trecho);
    }
}
