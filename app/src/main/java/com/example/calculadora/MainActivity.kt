package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    var expresion = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt_resultado.text = "0"

        initButtons()
    }

    private fun initButtons()
    {
        btnFunciones()
        //btnOperaciones()
        capturar()
    }

    private fun capturar()
    {
        button1.setOnClickListener{
            numPantalla("1")
        }

        button2.setOnClickListener{
            numPantalla("2")
        }

        button3.setOnClickListener{
            numPantalla("3")
        }

        button4.setOnClickListener{
            numPantalla("4")
        }

        button5.setOnClickListener{
            numPantalla("5")
        }

        button6.setOnClickListener{
            numPantalla("6")
        }

        button7.setOnClickListener{
            numPantalla("7")
        }

        button8.setOnClickListener{
            numPantalla("8")
        }

        button9.setOnClickListener{
            numPantalla("9")
        }

        button0.setOnClickListener{
            numPantalla("0")
        }

        btn_dot.setOnClickListener{
            numPantalla(".")
        }

        btn_suma.setOnClickListener {
            numPantalla("+")
        }

        btn_resta.setOnClickListener {
            numPantalla("-")
        }

        btn_div.setOnClickListener {
            numPantalla("/")
        }

        btn_multi.setOnClickListener {
            numPantalla("*")
        }

        btn_log.setOnClickListener {
            numPantalla("Log")
        }
    }

    private fun numPantalla(digito : String)
    {
        if (expresion.length < 1 && (digito == "/" || digito == "*"))
        {
            txt_resultado.text = ""
            Toast.makeText(applicationContext, "Expresión inválida", Toast.LENGTH_SHORT).show()
        }
        else
        {
            expresion.append(digito)
            txt_resultado.text = expresion.toString()
        }
    }

    private fun btnFunciones()
    {
        btn_del.setOnClickListener {
            expresion.clear()
            txt_resultado.text = ""
        }

        btn_back.setOnClickListener {
            borrar()
        }

        btn_equal.setOnClickListener {
            OnAction()
        }
    }

    private fun OnAction()
    {
        if (expresion.contains('L'))
            loga()

        try {
            val expression = ExpressionBuilder(expresion.toString()).build()
            var result = expression.evaluate()

            val longRes = result.toLong()

            if (result == longRes.toDouble()) {
                txt_resultado.text = longRes.toString()
                expresion = StringBuilder().append(longRes.toString())
            }
            else {
                txt_resultado.text = result.toString()
                expresion = StringBuilder().append(result.toString())
            }

        } catch (e:Exception) {
            Log.d("Excepcion", "mensaje: " + e.message)
            Toast.makeText(applicationContext, "Expresión inválida", Toast.LENGTH_SHORT).show()
            expresion.clear()
            txt_resultado.text = "Syntax Error"
        }
    }

    private fun loga()
    {
        val value = StringBuilder()
        val resLoga = StringBuilder()
        var res = 0.0
        var i = 0
        val expr = expresion.toString().toCharArray()

        if (expr[0] != 'L')
        {
            for (c in expr.indices)
                if (expr[c] != 'L')
                    value.append(expr[c])
                else
                {
                    value.append('*')
                    break
                }
        }

        for (c in expresion.indices)
        {
            if (expresion[c] == 'g')
            {
                i = c + 1
                while (i < expresion.length && expresion[i] != 'L')
                    resLoga.append(expresion[i++])
            }
        }

        try {
            val expressionLoga = ExpressionBuilder(resLoga.toString()).build()
            val logResult = Math.log10(expressionLoga.evaluate())

            value.append(logResult)

            val expression = ExpressionBuilder(value.toString()).build()
            res = expression.evaluate()

            expresion.clear()
            expresion.append(res)

        } catch (e:Exception) {
            Log.d("Excepcion", "mensaje: " + e.message)
            Toast.makeText(applicationContext, "Expresión inválida", Toast.LENGTH_SHORT).show()
            expresion.clear()
            txt_resultado.text = "Syntax Error"
        }
    }

    private fun borrar()
    {
        val len = expresion.length

        if (txt_resultado.text == "Syntax Error")
            txt_resultado.text = ""

        if (len > 0) {
            expresion.deleteCharAt(expresion.length - 1)
            txt_resultado.text = expresion.toString()
        }
    }
}