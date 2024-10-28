package com.example.currencyconvertor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {
    lateinit var tvResult: TextView
    lateinit var tvRate: TextView
    lateinit var edtAmount: EditText
    lateinit var spFrom: Spinner
    lateinit var spTo: Spinner

    val currencyRates = mutableListOf(
        CurrencyRate("VND", "USD", 0.000041f),
        CurrencyRate("VND", "CNY", 0.00027f),
        CurrencyRate("VND", "JPY", 0.0067f),
        CurrencyRate("VND", "EUR", 0.000039f),
        CurrencyRate("VND", "VND", 1f),
        CurrencyRate("USD", "VND", 24390f),
        CurrencyRate("USD", "CNY", 7.30f),
        CurrencyRate("USD", "JPY", 149.30f),
        CurrencyRate("USD", "EUR", 0.95f),
        CurrencyRate("USD", "USD", 1f),
        CurrencyRate("CNY", "VND", 3340f),
        CurrencyRate("CNY", "USD", 0.137f),
        CurrencyRate("CNY", "JPY", 20.45f),
        CurrencyRate("CNY", "EUR", 0.13f),
        CurrencyRate("CNY", "CNY", 1f),
        CurrencyRate("JPY", "VND", 6.70f),
        CurrencyRate("JPY", "USD", 0.0067f),
        CurrencyRate("JPY", "CNY", 0.049f),
        CurrencyRate("JPY", "EUR", 0.0064f),
        CurrencyRate("JPY", "JPY", 1f),
        CurrencyRate("EUR", "VND", 25400f),
        CurrencyRate("EUR", "USD", 1.05f),
        CurrencyRate("EUR", "CNY", 7.80f),
        CurrencyRate("EUR", "JPY", 156.25f),
        CurrencyRate("EUR", "EUR", 1f)
    )

    var amount: String = ""
    var conversionResult: Int = 0

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

    private fun initialize() {
        tvResult = findViewById(R.id.tvResult)
        tvRate = findViewById(R.id.tvRate)
        edtAmount = findViewById(R.id.edtAmount)
        spTo = findViewById(R.id.spToCurrency)
        spFrom = findViewById(R.id.spFromCurrency)
        edtAmount.addTextChangedListener(this)
        spFrom.onItemSelectedListener = this
        spTo.onItemSelectedListener = this
    }

    private fun getExchangeRate(fromCurrency: String, toCurrency: String): Float? {
        return currencyRates.find { it.fromCurrency == fromCurrency && it.toCurrency == toCurrency }?.rate
    }

    private fun convert(fromCurrency: String, toCurrency: String, rate: Float) {
        val inputAmount = edtAmount.text.toString()
        if (inputAmount.isNotEmpty()) {
            val amountFloat = inputAmount.toFloatOrNull()
            if (amountFloat != null) {
                val conversionResult = amountFloat * rate
                tvResult.text = String.format("%.2f %s", conversionResult, toCurrency)
                tvRate.text = "1 $fromCurrency = $rate $toCurrency"
            } else {
                tvResult.text = ""
                tvRate.text = ""
            }
        } else {
            tvResult.text = ""
            tvRate.text = ""
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val fromCur = spFrom.selectedItem.toString()
        val toCur = spTo.selectedItem.toString()
        val curRate = getExchangeRate(fromCur, toCur)
        if (curRate != null) {
            convert(fromCur, toCur, curRate)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val fromCur = spFrom.selectedItem.toString()
        val toCur = spTo.selectedItem.toString()
        val curRate = getExchangeRate(fromCur, toCur)
        if (curRate != null) {
            convert(fromCur, toCur, curRate)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // No action needed here
    }

    override fun afterTextChanged(s: Editable?) {
        val fromCur = spFrom.selectedItem.toString()
        val toCur = spTo.selectedItem.toString()
        val curRate = getExchangeRate(fromCur, toCur)
        if (curRate != null) {
            convert(fromCur, toCur, curRate)
        }
    }
}

data class CurrencyRate(val fromCurrency: String, val toCurrency: String, val rate: Float)
