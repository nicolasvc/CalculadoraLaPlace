package com.example.calculadoralaplace

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.calculadoralaplace.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


open class MainActivity : AppCompatActivity(), AccionesLaPlace {

    private lateinit var binding: ActivityMainBinding
    private var listaFuncionesPlace: MutableList<String> = arrayListOf()
    private var posicionFormula: Int = 0
    private lateinit var fabricaLaPlace: LaPlaceFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fabricaLaPlace = LaPlaceFactory(this)
        iniciarAutocompleteText()
        agregarListenerAutoComplete()
        agregarListenerSeleccionAutocomplete()
        agregarListenerBotton()
        setContentView(binding.root)
    }

    private fun iniciarAutocompleteText() {
        listaFuncionesPlace.add(0, "a")
        listaFuncionesPlace.add(1, "eat")
        listaFuncionesPlace.add(2, "tn")
        listaFuncionesPlace.add(3, "Sen(at)")
        listaFuncionesPlace.add(4, "Cos(at)")
        listaFuncionesPlace.add(5, "Senh(at)")
        listaFuncionesPlace.add(6, "Cosh(at)")
        listaFuncionesPlace.add(7, "tn*e-at")
        listaFuncionesPlace.add(8, "ebt*cosat")
        listaFuncionesPlace.add(9, "ebt*senat")
        listaFuncionesPlace.add(10, "ebt*seh(at)")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.select_dialog_item, listaFuncionesPlace)
        binding.autocompleteLaPlace.setAdapter(adapter)
        binding.autocompleteLaPlace.threshold = 0
    }

    private fun agregarListenerBotton() {
        binding.button.setOnClickListener {
            calcularRespuesta()
        }
    }

    private fun calcularRespuesta() {
        fabricaLaPlace.calcularOperacion(
            obtenerCasoLaPlace(),
            binding.editTextTextPersonName.text.toString()
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    fun agregarListenerAutoComplete() {
        binding.autocompleteLaPlace.setOnTouchListener { _, _ ->
            binding.autocompleteLaPlace.showDropDown()
            binding.autocompleteLaPlace.requestFocus()
            false
        }
    }

    fun agregarListenerSeleccionAutocomplete() {
        binding.autocompleteLaPlace.setOnItemClickListener { _, _, pos, _ ->
            posicionFormula = pos
            mostrarSnackBar()
            mostrarBotton()
            mostrarEditTextFormula()
            asignarFocoEditText()
        }
    }

    private fun mostrarSnackBar() {
        Toast.makeText(this,"Reemplaza la x por datos numericos",Toast.LENGTH_LONG).show()
    }

    private fun mostrarBotton() {
        binding.button.visibility = View.VISIBLE
    }

    private fun asignarFocoEditText() {
        binding.editTextTextPersonName.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editTextTextPersonName, InputMethodManager.SHOW_IMPLICIT)
    }

    fun mostrarEditTextFormula() {
        binding.editTextTextPersonName.setText(obtenerFormula())
        binding.editTextTextPersonName.setSelection(obtenerPosicion())
        binding.editTextTextPersonName.visibility = View.VISIBLE
        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("yolo")
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("yolo")
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun obtenerPosicion(): Int {
        return when (posicionFormula) {
            0 -> 1
            1 -> 2
            2 -> 2
            3 -> 5
            4 -> 5
            5 -> 6
            6 -> 6
            7 -> 2
            8 -> 2
            9 -> 2
            else -> 0
        }
    }

    private fun obtenerFormula(): Spanned {
        var valorFormula = ""
        when (posicionFormula) {
            0 -> valorFormula = "a"
            1 -> valorFormula = "e<sup>xt</sup>"
            2 -> valorFormula = "t<sup>x</sup>"
            3 -> valorFormula = "Sen(xt)"
            4 -> valorFormula = "Cos(xt)"
            5 -> valorFormula = "Senh(xt)"
            6 -> valorFormula = "Cosh(xt)"
            7 -> valorFormula = "t<sup>x</sup>  *  e<sup>-xt</sup>"
            8 -> valorFormula = "e<sup>xt</sup> * Cos(xt)"
            9 -> valorFormula = "e<sup>xt</sup> * Sen(xt)"

        }
        return Transversal.obtenerHtmlFuncion(valorFormula)
    }



    private fun obtenerCasoLaPlace(): OperacionLaPlace =
        when (posicionFormula) {
            0 -> OperacionLaPlace.PrimerCaso
            1 -> OperacionLaPlace.SegundoCaso
            2 -> OperacionLaPlace.TercerCaso
            3 -> OperacionLaPlace.CuartoCaso
            4 -> OperacionLaPlace.QuintoCaso
            5 -> OperacionLaPlace.SextoCaso
            6 -> OperacionLaPlace.SeptimoCaso
            7 -> OperacionLaPlace.OctavoCaso
            8 -> OperacionLaPlace.NovenoCaso
            9 -> OperacionLaPlace.DecimoCaso
            else -> OperacionLaPlace.PrimerCaso
        }


    override fun notificarResultado(resultado: String) {
        binding.editTextTextPersonName.setText(resultado)
    }

    override fun notificarResultado(resultado: Spanned) {
        binding.editTextTextPersonName.setText(resultado)
    }

    override fun notificarError(mensaje: String) {
        Snackbar.make(binding.constrainPadre.rootView, mensaje, Snackbar.LENGTH_LONG).show()
    }

}