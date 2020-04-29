package com.example.tp1exo1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import yuku.ambilwarna.AmbilWarnaDialog
import android.content.SharedPreferences
class SettingsActivity : AppCompatActivity() {
    var PARAMS_NAME = 0
     lateinit var l: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val sharedPreferences = getSharedPreferences(
            "MySharedPref",
            Context.MODE_PRIVATE
        )
        l = findViewById(R.id.main2) as RelativeLayout
        val btn1= findViewById(R.id.button) as Button
        val btn2= findViewById(R.id.button2) as Button
        val col = getSharedPreferences("MySharedPref", 0)
        l.setBackgroundColor(col.getInt("maparam",0))
        btn1.setOnClickListener {
            openColorPicker(sharedPreferences);
              }
        btn2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun openColorPicker(sharedPreferences :SharedPreferences) {
        val colorPicker =
            AmbilWarnaDialog(this, PARAMS_NAME, object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {

                }

                @SuppressLint("WrongConstant")
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    PARAMS_NAME = color


                    val myEdit = sharedPreferences.edit()
                    myEdit.putInt("maparam", PARAMS_NAME)
                        //Integer.parseInt(
                       //     age.getText().toString()
                       // )
                    myEdit.apply()
                    myEdit.commit()
                    l.setBackgroundColor(PARAMS_NAME)
                }
            })
        colorPicker.show()
    }
}


