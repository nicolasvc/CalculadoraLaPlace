package com.example.calculadoralaplace

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadoralaplace.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


open class MainActivity : AppCompatActivity(), AccionesLaPlace {

    private lateinit var binding: ActivityMainBinding
    private var listaFuncionesPlace: MutableList<String> = arrayListOf()
    private var posicionFormula: Int = 0
    private lateinit var fabricaLaPlace: LaPlaceFactory
    private var valorx: String = ""
    private var valory: String = ""
    private var operacionTerminada = false


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
        listaFuncionesPlace.add(1, "e^at")
        listaFuncionesPlace.add(2, "t^n")
        listaFuncionesPlace.add(3, "Sen(at)")
        listaFuncionesPlace.add(4, "Cos(at)")
        listaFuncionesPlace.add(5, "Senh(at)")
        listaFuncionesPlace.add(6, "Cosh(at)")
        listaFuncionesPlace.add(7, "t^n*e-at")
        listaFuncionesPlace.add(8, "e^bt*cos(at)")
        listaFuncionesPlace.add(9, "e^bt*sen(at)")
        //listaFuncionesPlace.add(10, "e^bt*seh(at)")
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


    private fun validarCampos(): Boolean {
        if (valorx == "x") {
            Snackbar.make(
                binding.constrainPadre.rootView,
                "Por favor ingrese un valor para la variable x",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }
        if ((posicionFormula == 7 || posicionFormula == 8
                    || posicionFormula == 9 || posicionFormula == 10
                    ) && valory == "y"
        ) {
            Snackbar.make(
                binding.constrainPadre.rootView,
                "Por favor ingrese un valor para la variable y",
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }


    private fun calcularRespuesta() {
        if (!validarCampos()) return
        ajustaVisibilidadPrimerCampo(View.GONE)
        ajustaVisibilidadSegundoCampo(View.GONE)
        fabricaLaPlace.calcularOperacion(
            obtenerCasoLaPlace(),
            valorx,valory
        )
        valorx = ""
        valory = ""
        operacionTerminada = true
        binding.editPrimeraVariable.setText(valorx)
        binding.editSegundaVariable.setText(valory)
        binding.button.visibility = View.GONE
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
            operacionTerminada = false
            posicionFormula = pos
            ajustaVisibilidadPrimerCampo(View.VISIBLE)
            habilitarCapturaDatosY()
            mostrarToast()
            mostrarBotton()
            mostrarEditTextFormula()
            asignarFocoEditText()
        }
    }


    private fun habilitarCapturaDatosY() {
        when (posicionFormula) {
            7 -> ajustaVisibilidadSegundoCampo(View.VISIBLE)
            8 -> ajustaVisibilidadSegundoCampo(View.VISIBLE)
            9 -> ajustaVisibilidadSegundoCampo(View.VISIBLE)
        }
    }


    private fun ajustaVisibilidadPrimerCampo(visible: Int) {
        binding.textInputLayout2.visibility = visible
        binding.editPrimeraVariable.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!operacionTerminada){
                    valorx = if (s.toString().contains("x") ) s.toString().replace("x","") else s.toString()
                    binding.editTextTextPersonName.setText(obtenerFormula())
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun ajustaVisibilidadSegundoCampo(visible: Int) {
        binding.textInputLayout3.visibility = visible
        binding.editSegundaVariable.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!operacionTerminada){
                    valory =  if (s.toString().contains("y") ) s.toString().replace("x","") else s.toString()
                    binding.editTextTextPersonName.setText(obtenerFormula())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun mostrarToast() {
        Toast.makeText(this, "Reemplaza la x por datos numericos", Toast.LENGTH_LONG).show()
    }

    private fun mostrarBotton() {
        binding.button.visibility = View.VISIBLE
    }

    private fun asignarFocoEditText() {
        binding.editPrimeraVariable.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editPrimeraVariable, InputMethodManager.SHOW_IMPLICIT)
        binding.editPrimeraVariable.setSelection(binding.editPrimeraVariable.text!!.length)
    }

    fun mostrarEditTextFormula() {
        binding.editTextTextPersonName.setText(obtenerFormula())
        binding.editTextTextPersonName.visibility = View.VISIBLE
    }


    private fun obtenerFormula(): Spanned {
        var valorFormula = ""
        when (posicionFormula) {
            0 -> valorFormula = valorx
            1 -> valorFormula = "e<sup>$valorx t</sup>"
            2 -> valorFormula = "t<sup>$valorx</sup>"
            3 -> valorFormula = "Sen($valorx t)"
            4 -> valorFormula = "Cos($valorx t)"
            5 -> valorFormula = "Senh($valorx t)"
            6 -> valorFormula = "Cosh($valorx t)"
            7 -> valorFormula = "t<sup>$valorx</sup>  *  e<sup>-$valory t</sup>"
            8 -> valorFormula = "e<sup>$valorx t</sup> * Cos($valory t)"
            9 -> valorFormula = "e<sup>$valorx t</sup> * Sen($valory t)"

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

    private fun mostrarSnackBar(){
        Snackbar.make(binding.constrainPadre.rootView,"¡Ya puedes seleccionar otra formula si gustas!",Snackbar.LENGTH_SHORT).show()
    }

    override fun notificarResultado(resultado: String) {
        mostrarSnackBar()
        binding.editTextTextPersonName.setText(resultado)
    }

    override fun notificarResultado(resultado: Spanned) {
        mostrarSnackBar()
        binding.editTextTextPersonName.setText(resultado)
    }

    override fun notificarError(mensaje: String) {
        Snackbar.make(binding.constrainPadre.rootView, mensaje, Snackbar.LENGTH_LONG).show()
    }

}