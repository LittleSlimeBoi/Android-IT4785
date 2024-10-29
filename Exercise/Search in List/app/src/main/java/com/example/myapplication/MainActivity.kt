package com.example.myapplication

import SinhVienAdapter
import android.os.Bundle
import android.widget.Adapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var adapterSV: Adapter
    lateinit var listSV: List<SinhVien>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun initialize(){
        listSV = listOf(
            SinhVien("Nguyen Van A", "20211234"),
            SinhVien("Tran Thi B", "20215566"),
            SinhVien("Le Van C", "20213256"),
            SinhVien("Pham Thi D", "20214567"),
            SinhVien("Nguyen Van E", "20221234")
        )

        adapterSV = SinhVienAdapter(listSV)
    }

}

