package com.example.calculadoralaplace

open class LaPlaceFactory(private val accionesLaPlace: AccionesLaPlace) {

    private lateinit var valorOperacion: String
    private lateinit var factorial: String
    private lateinit var potencia: String
    private var valorFuncion: Int = 0

    fun calcularOperacion(operacionLaPlace: OperacionLaPlace, valorOperacion: String) {
        this.valorOperacion = valorOperacion
        obtenerValorFuncion()
        when (operacionLaPlace) {
            OperacionLaPlace.PrimerCaso -> primerCaso()
            OperacionLaPlace.SegundoCaso -> segundoCaso()
            OperacionLaPlace.TercerCaso -> tercerCaso()
            OperacionLaPlace.CuartoCaso -> cuartoCaso()
            OperacionLaPlace.QuintoCaso -> quintoCaso()
            OperacionLaPlace.SextoCaso -> sextoCaso()
            OperacionLaPlace.SeptimoCaso -> septimoCaso()
            OperacionLaPlace.OctavoCaso -> octavoCaso()
            OperacionLaPlace.NovenoCaso -> novenoCaso()
            OperacionLaPlace.DecimoCaso -> decimoCaso()
        }
    }


    private fun primerCaso() {
        if (Integer.parseInt(valorOperacion) > 0) {
            accionesLaPlace.notificarResultado("$valorOperacion \n ___________ \n s")
        } else {
            accionesLaPlace.notificarError("El valor debe ser superior a 0")
        }
    }

    private fun segundoCaso() {
        accionesLaPlace.notificarResultado(
            "1 \n ___________ \n s - $valorFuncion"
        )
    }

    private fun tercerCaso() {
        obtenerFactorial(valorFuncion)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$factorial \n ___________ \n s<sup>$valorFuncion + 1 </sup>"))
    }

    private fun cuartoCaso() {
        obtenerPotenciaNumero(valorFuncion)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$valorFuncion \n ___________ \n s<sup>2</sup>  + s<sup>$potencia</sup> "))
    }

    private fun quintoCaso() {
        obtenerPotenciaNumero(valorFuncion)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("s \n ___________ \n s<sup>2</sup>  + s<sup>$potencia</sup> "))
    }

    private fun sextoCaso() {
        obtenerPotenciaNumero(valorFuncion)
        accionesLaPlace.notificarResultado(
            "$valorFuncion \n" +
                    " ___________ \n" +
                    "  s2 - $potencia"
        )
    }

    private fun septimoCaso() {
        obtenerPotenciaNumero(valorFuncion)
        accionesLaPlace.notificarResultado(
            "s \n" +
                    " ___________ \n" +
                    "  s2 - $potencia"
        )
    }

    private fun octavoCaso() {
        obtenerFactorial(valorFuncion)
        accionesLaPlace.notificarResultado(Transversal.obtenerHtmlFuncion("$factorial \n _____________ \n (s + )"))

    }

    private fun novenoCaso() {}
    private fun decimoCaso() {}


    private fun obtenerValorFuncion() {
        valorFuncion = Integer.parseInt(valorOperacion.replace("[^0-9]".toRegex(), ""))
    }

    private fun obtenerFactorial(factorial: Int) {
        var fact = 1
        for (i in 1..factorial) {
            fact *= i
        }
        this.factorial = fact.toString()
    }

    private fun obtenerPotenciaNumero(numero: Int) {
        val potencia = numero * numero
        this.potencia = potencia.toString()
    }

}