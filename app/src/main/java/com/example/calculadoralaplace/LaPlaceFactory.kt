package com.example.calculadoralaplace

open class LaPlaceFactory(private val accionesLaPlace: AccionesLaPlace) {

    private lateinit var factorial: String
    private lateinit var potencia: String
    private var valorx: Int = 0
    private var valory: Int = 0

    fun calcularOperacion(operacionLaPlace: OperacionLaPlace, valorx: String, valorY: String) {
        this.valorx = Integer.parseInt(valorx)
        if (valorY != "y")
            this.valory = Integer.parseInt(valorY)

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
        accionesLaPlace.notificarResultado("$valorx /  s")
    }

    private fun segundoCaso() {
        accionesLaPlace.notificarResultado(
            "1 / s - $valorx"
        )
    }

    private fun tercerCaso() {
        obtenerFactorial(valorx)
        val sumaPotencia  = valorx + 1
        accionesLaPlace.notificarResultado(
                Transversal.obtenerHtmlFuncion(
                    " $factorial / s<sup>$sumaPotencia </sup>"
                )
        )
    }

    private fun cuartoCaso() {
        obtenerPotenciaNumero(valorx)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$valorx / s<sup>2</sup>  + s<sup>$potencia</sup> ")
        )
    }

    private fun quintoCaso() {
        obtenerPotenciaNumero(valorx)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("s / s<sup>2</sup>  + $potencia ")
        )
    }

    private fun sextoCaso() {
        obtenerPotenciaNumero(valorx)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$valorx / s<sup>2</sup>  - $potencia ")
        )
    }

    private fun septimoCaso() {
        obtenerPotenciaNumero(valorx)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("s / s<sup>2</sup>  - $potencia ")
        )
    }

    private fun octavoCaso() {
        val potenciaN = valory + 1
        obtenerFactorial(valorx)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$factorial / (s - $valory)<sup>$potenciaN</sup> ")
        )

    }

    private fun novenoCaso() {
        val potenciaY = obtenerPotenciaNumero(valory)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("s - $valorx / (s - $valorx)<sup>2</sup> + $potenciaY")
        )
    }
    private fun decimoCaso() {
        val potenciaY = obtenerPotenciaNumero(valory)
        accionesLaPlace.notificarResultado(
            Transversal.obtenerHtmlFuncion("$valory / (s - $valorx)<sup>2</sup> + $potenciaY")
        )
    }


    private fun obtenerFactorial(factorial: Int):String {
        var fact = 1
        for (i in 1..factorial) {
            fact *= i
        }
        this.factorial = fact.toString()
        return fact.toString()
    }

    private fun obtenerPotenciaNumero(numero: Int) :String {
        val potencia = numero * numero
        this.potencia = potencia.toString()
        return potencia.toString()
    }

}