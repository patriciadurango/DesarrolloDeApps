package com.example.a02_patriciadurango

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener { finish() }
    }
}
