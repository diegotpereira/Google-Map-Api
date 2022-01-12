package br.java.google_map_api.mapa.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public abstract class PermissaoUtils {

    public static void requisitarPermissao(FragmentActivity activity, int requisicaoId, String permissao, boolean finalizarActivity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissao)) {

            // Exiba uma caixa de diálogo com justificativa.
            PermissaoUtils.RacionalDialogo.novaInstancia(requisicaoId, finalizarActivity).show(activity.getSupportFragmentManager(), "dialog");
        } else {
            // A permissão de localização ainda não foi concedida, solicite-a.
            ActivityCompat.requestPermissions(activity, new String[]{ permissao}, requisicaoId);
        }
    }

    public static boolean ehPermissaoConcedida(String[] concederPermissoes, int[] resultadosConcessao, String permissao) {

        for(int index = 0; index < concederPermissoes.length; index++) {

            if (permissao.equals(concederPermissoes[index])) {

                return resultadosConcessao[index] == PackageManager.PERMISSION_GRANTED;

            }
        }

        return false;
    }

    public static class PermissaoNegadaDialog extends DialogFragment {

        private static final String ARGUMENTO_FINALIZAR_ACTIVITY = "finalizar";
        private boolean mFinalizarActivity = false;

        public static PermissaoNegadaDialog novaInstancia(boolean finalizarActivity) {

            Bundle argumentos = new Bundle();
            argumentos.putBoolean(ARGUMENTO_FINALIZAR_ACTIVITY, finalizarActivity);

            PermissaoNegadaDialog dialogo = new PermissaoNegadaDialog();
            dialogo.setArguments(argumentos);

            return dialogo;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            mFinalizarActivity = getArguments().getBoolean(ARGUMENTO_FINALIZAR_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                    .setMessage("Esta amostra requer permissão de localização para habilitar o \\ 'minha localização\\' camada. Tente novamente e conceda acesso para usar o local.\\nSe a permissão foi negada permanentemente, ele pode ser ativado nas configurações do sistema &gt; Apps &gt; \\'Google Maps API Demos\\'.")
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
            if (mFinalizarActivity) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    public static class RacionalDialogo extends DialogFragment {

        public static RacionalDialogo novaInstancia(int requisitarCodigo, boolean finalizarActivity) {

            Bundle argumentos = new Bundle();
            RacionalDialogo dialogo = new RacionalDialogo();
            dialogo.setArguments(argumentos);
            return dialogo;
        }
    }
}