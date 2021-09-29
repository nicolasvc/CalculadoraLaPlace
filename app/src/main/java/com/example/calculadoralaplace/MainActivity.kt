package com.example.calculadoralaplace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadoralaplace.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.buttons_layout.*
import kotlinx.android.synthetic.main.input_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        btn0.setOnClickListener { setProcess("0")}
        btn1.setOnClickListener { setProcess("1")}
        btn2.setOnClickListener { setProcess("2")}
        btn3.setOnClickListener { setProcess("3")}
        btn4.setOnClickListener { setProcess("4")}
        btn5.setOnClickListener { setProcess("5")}
        btn6.setOnClickListener { setProcess("6")}
        btn7.setOnClickListener { setProcess("7")}
        btn8.setOnClickListener { setProcess("8")}
        btn9.setOnClickListener { setProcess("9")}
        btnSum.setOnClickListener { setProcess("+")}
        btnRest.setOnClickListener { setProcess("-")}
        btnmultiply.setOnClickListener { setProcess("x")}
        btnDivision.setOnClickListener { setProcess("÷")}
        btnPercent.setOnClickListener { setProcess("%")}
        btnClear.setOnClickListener {cleanCalculator()}
        btnEquals.setOnClickListener { operateFunction() }

    }

    private fun operateFunction() {
        val listaOperadores :List<String> = tvInput.text.toString().split("(?<=[-+*/])|(?=[-+*/])")
        for (listaOperadore in listaOperadores) {
            
        }
    }

    private fun setProcess(value:String){
        val progress = tvInput.text
        tvInput.text = String.format("%s %s",progress,value)
    }

    private fun cleanCalculator(){
        tvInput.text = ""
        tvOutput.text = ""
    }


}