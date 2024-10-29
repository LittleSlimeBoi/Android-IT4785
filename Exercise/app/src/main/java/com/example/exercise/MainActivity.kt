package com.example.exercise

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var edtNumber: EditText
    lateinit var radioEven: RadioButton
    lateinit var radioOdd: RadioButton
    lateinit var radioSquare: RadioButton
    lateinit var btnShow: Button
    lateinit var lvNumbers: ListView
    lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialize()
    }

    fun initialize(){
        edtNumber = findViewById(R.id.edtNumber)
        radioEven = findViewById(R.id.radioEven)
        radioOdd = findViewById(R.id.radioOdd)
        radioSquare = findViewById(R.id.radioSquare)
        btnShow = findViewById(R.id.btnShow)
        lvNumbers = findViewById(R.id.listViewNumbers)
        tvError = findViewById(R.id.tvError)
        btnShow.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        showNumbers()
    }

    private fun showNumbers() {
        tvError.text = "" // Clear any previous errors
        val input = edtNumber.text.toString().trim()

        // Validate input
        if (input.isEmpty()) {
            tvError.text = "Vui lòng nhập một số nguyên dương."
            return
        }

        val n = try {
            input.toInt().also {
                if (it <= 0) {
                    tvError.text = "Số phải lớn hơn 0."
                    return
                }
            }
        } catch (e: NumberFormatException) {
            tvError.text = "Dữ liệu không hợp lệ."
            return
        }

        val numbers = arrayListOf<String>()

        // Populate the numbers list based on the selected option
        when {
            radioEven.isChecked -> {
                for (i in 0..n step 2) {
                    numbers.add(i.toString())
                }
            }
            radioOdd.isChecked -> {
                for (i in 1..n step 2) {
                    numbers.add(i.toString())
                }
            }
            radioSquare.isChecked -> {
                var i = 0
                while (i * i <= n) {
                    numbers.add((i * i).toString())
                    i++
                }
            }
            else -> {
                tvError.text = "Vui lòng chọn một loại số."
                return
            }
        }

        // Set up the ListView with the results
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        lvNumbers.adapter = adapter
    }

}