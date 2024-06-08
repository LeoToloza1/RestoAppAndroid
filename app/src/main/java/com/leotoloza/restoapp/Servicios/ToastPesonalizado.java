package com.leotoloza.restoapp.Servicios;

import android.content.Context;

public class ToastPesonalizado {
    private static Context context = null;
    public static void mostrarMensaje(Context context, String mensaje) {
        android.widget.Toast.makeText(context, mensaje, android.widget.Toast.LENGTH_SHORT).show();
    }
}

