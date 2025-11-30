package com.example.a02_patriciadurango

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRelative = findViewById<Button>(R.id.btnRelativeLayout)
        val btnLinear = findViewById<Button>(R.id.btnLinearLayout)
        val btnTable = findViewById<Button>(R.id.btnTableLayout)
        val btnGrid = findViewById<Button>(R.id.btnGridLayout)

        btnRelative.setOnClickListener {
            startActivity(Intent(this, RelativeLayoutActivity::class.java))
        }

        btnLinear.setOnClickListener {
            startActivity(Intent(this, LinearLayoutActivity::class.java))
        }

        btnTable.setOnClickListener {
            startActivity(Intent(this, TableLayoutActivity::class.java))
        }

        btnGrid.setOnClickListener {
            startActivity(Intent(this, GridLayoutActivity::class.java))
        }
    }
}
