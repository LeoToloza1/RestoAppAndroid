package com.leotoloza.restoapp.Servicios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.leotoloza.restoapp.R;

public class Dialogo extends DialogFragment {
    public static void mostrarDialogoConfirmacion(Context contexto, String titulo, String mensaje, DialogInterface.OnClickListener listenerPositivo) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        constructorDialogo.setMessage(mensaje);
        constructorDialogo.setPositiveButton("Sí", listenerPositivo);
        constructorDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }
    public static void mostrarDialogoConEntrada(Context contexto, String titulo, CambioContraseñaListener cambioContraseñaListener) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        View vistaDialogo = LayoutInflater.from(contexto).inflate(R.layout.dialogo_custom, null);
        constructorDialogo.setView(vistaDialogo);

        EditText password = vistaDialogo.findViewById(R.id.password1);
        EditText passwordConfirm = vistaDialogo.findViewById(R.id.password2);

        constructorDialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String nuevaContraseña = password.getText().toString();
                String passworConfirmada = passwordConfirm.getText().toString();
                if (cambioContraseñaListener != null) {
                    if (nuevaContraseña.equals(passworConfirmada)) {
                        cambioContraseñaListener.onAceptar(nuevaContraseña);
                    } else {
                        mostrarDialogoInformativo(contexto, "Contraseñas diferentes", "Por favor, escriba las contraseñas nuevamente.");
                    }
                }
            }
        });

        constructorDialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }
    public static void mostrarDialogoInformativo(Context contexto, String titulo, String mensaje) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        constructorDialogo.setMessage(mensaje);
        constructorDialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }
    //interface
    public interface CambioContraseñaListener {
        void onAceptar(String nuevaContraseña);
    }
}
