package com.example.calculadoralaplace

import android.os.Build
import android.text.Html
import android.text.Spanned

class Transversal {

    companion object ObtenerTransversales {

        @SuppressWarnings("deprecation")
         fun obtenerHtmlFuncion(valorFuncion: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(valorFuncion, Html.FROM_HTML_MODE_LEGACY);
            } else {
                Html.fromHtml(valorFuncion);
            }
        }
    }
}