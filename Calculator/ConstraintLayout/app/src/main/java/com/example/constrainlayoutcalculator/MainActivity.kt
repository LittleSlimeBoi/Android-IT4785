package com.example.constrainlayoutcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textReuslt: TextView
    lateinit var textSolution: TextView

    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0
    var remain = 0
    var prevOp: String = ""
    var opRemain: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textReuslt = findViewById(R.id.IOEntry)
        textSolution = findViewById((R.id.PreviousEntry))
        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.C).setOnClickListener(this)
        findViewById<Button>(R.id.CE).setOnClickListener(this)
        findViewById<Button>(R.id.BS).setOnClickListener(this)
        findViewById<Button>(R.id.decimal).setOnClickListener(this)
        findViewById<Button>(R.id.equal).setOnClickListener(this)
        findViewById<Button>(R.id.divide).setOnClickListener(this)
        findViewById<Button>(R.id.plus).setOnClickListener(this)
        findViewById<Button>(R.id.minus).setOnClickListener(this)
        findViewById<Button>(R.id.multiply).setOnClickListener(this)
        findViewById<Button>(R.id.sign_swap).setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when(id){
            R.id.button0 -> addDigit(0)
            R.id.button1 -> addDigit(1)
            R.id.button2 -> addDigit(2)
            R.id.button3 -> addDigit(3)
            R.id.button0 -> addDigit(4)
            R.id.button5 -> addDigit(5)
            R.id.button6 -> addDigit(6)
            R.id.button7 -> addDigit(7)
            R.id.button8 -> addDigit(8)
            R.id.button9 -> addDigit(9)

            R.id.C -> allClear()
            R.id.CE -> singleClear()
            R.id.BS -> backspace()
            R.id.sign_swap -> signSwap()

            R.id.plus -> calculate("+")
            R.id.minus -> calculate("-")
            R.id.multiply -> calculate("*")
            R.id.divide -> calculate("/")
            R.id.equal -> calculate("=")


        }
    }

    fun allClear(){
        textSolution.text = ""
        textReuslt.text = "0"
        op1 = 0
        op2 = 0
        remain = 0
    }
    fun singleClear(){
        textReuslt.text = "0"
    }
    fun backspace(){
        if (state == 1) {
            op1 /= 10
            textReuslt.text = "$op1"
        } else {
            op2 /= 10
            textReuslt.text = "$op2"
        }
    }

    fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            textReuslt.text = "$op1"
        } else {
            state = 2
            op2 = op2 * 10 + c
            textReuslt.text = "$op2"
        }
    }

    fun signSwap(){
        if (state == 1) {
            op1 = 0 - op1
            textReuslt.text = "$op1"
        } else {
            op2 = 0 - op2
            textReuslt.text = "$op2"
        }
    }
    fun calculate(operator: String){
        if(state != 3){
            textSolution.append(textReuslt.text)
            textSolution.append(operator)
        }else{
            textSolution.text.dropLast(1)
            textSolution.append(operator)
        }
        if(state == 1 || state == 3){
            prevOp = operator
        }else{
            when(operator){
                "+" -> {
                    simpleOperator(prevOp)
                    calculateRemainOp(opRemain)
                    opRemain = "";
                    remain = 0
                }
                "-" -> {
                    simpleOperator(prevOp)
                    calculateRemainOp(opRemain)
                    opRemain = "";
                    remain = 0
                }
                "*" -> {
                    if(prevOp == "+"|| prevOp == "-"){
                        opRemain = prevOp
                        remain = op1
                        op1 = op2
                        op2 = 0
                    }else calculate(prevOp)
                }
                "/" -> {
                    if(prevOp == "+"|| prevOp == "-"){
                        opRemain = prevOp
                        remain = op1
                        op1 = op2
                        op2 = 0
                    }else calculate(prevOp)
                }
                "=" -> {
                    calculate(prevOp)
                    calculateRemainOp(opRemain)
                    opRemain = ""
                    remain = 0
                    op2 = 0
                    state = 1
                }
            }
            prevOp = operator
        }
        state = 3
    }

    fun simpleOperator(operator: String){
        when(operator){
            "+" -> { op1 = op1 + op2; }
            "-" -> { op1 = op1 - op2; }
            "*" -> { op1 = op1 * op2; }
            "/" -> {
                if (op2 == 0)  textReuslt.text = getString(R.string.cant_divide_by_0)
                else { op1 = op1 / op2; }
            }
        }
    }

    fun calculateRemainOp(operator: String){
        when(operator) {
            "+" -> { op1 = op1 + remain; }
            "-" -> { op1 = remain - op1 }
        }
    }
}