package com.example.tp1exo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val l: RelativeLayout
        l = findViewById(R.id.main3) as RelativeLayout
        val col = getSharedPreferences("MySharedPref", 0)
        l.setBackgroundColor(col.getInt("maparam",0))
    }
}
