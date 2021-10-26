package com.example.calculadoralaplace

import android.text.Spanned

interface AccionesLaPlace {

    fun notificarResultado(resultado:String)
    fun notificarError(mensaje:String)
    fun notificarResultado(resultado:Spanned)
}