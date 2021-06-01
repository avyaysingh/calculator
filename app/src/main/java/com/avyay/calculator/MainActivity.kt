package com.avyay.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.NumberFormatException

import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {

//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        //Data input Buttons
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.buttonDot)
//
//        //Operation Buttons
//
//        val buttonPlus: Button = findViewById(R.id.buttonPlus)
//        val buttonMinus: Button = findViewById(R.id.buttonMinus)
//        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
//        val buttonDivide: Button = findViewById(R.id.buttonDivide)
//        val buttonEquals: Button = findViewById(R.id.buttonEquals)


        val lisener = View.OnClickListener { v ->
//            val b = v as Button
            newNumber.append((v as Button).text)
        }

        button0.setOnClickListener(lisener)
        button1.setOnClickListener(lisener)
        button2.setOnClickListener(lisener)
        button3.setOnClickListener(lisener)
        button4.setOnClickListener(lisener)
        button5.setOnClickListener(lisener)
        button6.setOnClickListener(lisener)
        button7.setOnClickListener(lisener)
        button8.setOnClickListener(lisener)
        button9.setOnClickListener(lisener)
        buttonDot.setOnClickListener(lisener)

        val opListner = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }

            pendingOperation = op
//            displayOperation.text = pendingOperation
            operation.text =  pendingOperation
        }

        buttonEquals.setOnClickListener(opListner)
        buttonPlus.setOnClickListener(opListner)
        buttonMinus.setOnClickListener(opListner)
        buttonMultiply.setOnClickListener(opListner)
        buttonDivide.setOnClickListener(opListner)

        buttonNeg.setOnClickListener{ view ->
            val value = newNumber.text.toString()
            if (value.isNotEmpty()){
                newNumber.setText("-")
            }else{
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                }catch (e: NumberFormatException){
                    //newNumber was "-" or ".", so clear it
                    newNumber.setText("")
                }
            }
        }

    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN     //Handle attempt to divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }

        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)){
            savedInstanceState.getDouble(STATE_OPERAND1)
        }else{
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
//        displayOperation.text = pendingOperation
        operation.text = pendingOperation
    }
}