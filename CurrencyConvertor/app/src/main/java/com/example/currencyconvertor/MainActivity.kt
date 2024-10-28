package com.example.currencyconvertor

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var tv_to: TextView
    lateinit var tv_from: TextView
    lateinit var tv_result: TextView
    lateinit var edt_amount: EditText
    lateinit var btnusd: Button
    lateinit var btngbp: Button
    lateinit var btnkrw: Button
    lateinit var btncny: Button
    lateinit var btneur: Button
    lateinit var btnjpy: Button

    var amount: String = ""
    var vnd: Double = 0.0
    var result: Double = 0.0

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

    public fun initialize(){
        tv_to=findViewById(R.id.tv_to)
        tv_from=findViewById(R.id.tv_from)
        tv_result=findViewById(R.id.tvresult)
        edt_amount=findViewById(R.id.edt_amount)
        btnusd=findViewById(R.id.btnusd)
        btngbp=findViewById(R.id.btngbp)
        btnkrw=findViewById(R.id.btnkrw)
        btncny=findViewById(R.id.btncny)
        btneur=findViewById(R.id.btneur)
        btnjpy=findViewById(R.id.btnjpy)
        btncny.setOnClickListener(this)
        btnjpy.setOnClickListener(this)
        btneur.setOnClickListener(this)
        btngbp.setOnClickListener(this)
        btnkrw.setOnClickListener(this)
        btnusd.setOnClickListener(this)
    }

    override fun onClick(p0: View?){
        val id = p0?.id
        when(id){
            R.id.btnusd -> convert("USD", 25355.0)
            R.id.btngbp -> convert("GBP", 32899.0)
            R.id.btneur -> convert("EURO", 27426.0)
            R.id.btncny -> convert("CNY", 3558.0)
            R.id.btnjpy -> convert("JPY", 166.2)
            R.id.btnkrw -> convert("KRW", 18.31)
        }
    }
    fun convert(currency: String, rate: Double){
        amount = edt_amount.text.toString()
        vnd = rate
        result = amount.toDouble() * (vnd)
        tv_result.text = result.toLong().toString()
        tv_result.append(" VND")
        tv_to.text = currency
        tv_from.text = rateSelector(currency)
    }
    fun rateSelector(currency: String): String {
        when(currency){
            "USD"  -> return getString(R.string.USD)
            "GBP"  -> return getString(R.string.GBP)
            "EURO" -> return getString(R.string.EURO)
            "CNY"  -> return getString(R.string.CNY)
            "JPY"  -> return getString(R.string.JPY)
            "KRW"  -> return getString(R.string.KRW)
        }
        return "???"
    }
}