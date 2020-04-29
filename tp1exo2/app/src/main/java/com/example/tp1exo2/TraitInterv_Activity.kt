package com.example.tp1exo2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import java.util.*
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import org.json.JSONArray

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.json.JSONException

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
class TraitInterv_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trait_interv_)

        val c = Calendar.getInstance()
        val annee = c.get(Calendar.YEAR)
        val mois = c.get(Calendar.MONTH)
        val jour = c.get(Calendar.DAY_OF_MONTH)

        val addDate = findViewById<Button>(R.id.addDate)
        val spinner_type  = findViewById<Spinner>(R.id.spinner_type)
        val spinner_nom  = findViewById<Spinner>(R.id.spinner_nom)
        val InputDate=findViewById<TextView>(R.id.InputDate)
        val spinner_nom_text = findViewById<TextView>(R.id.spinner_nom_text)
        val spinner_type_text = findViewById<TextView>(R.id.spinner_type_text)
        val NumInter =findViewById<TextView>(R.id.editNom)
        val fileName = cacheDir.absolutePath+"/data.json"
        val btn_ajouter = findViewById<Button>(R.id.ajouter)
        val  file=File(fileName)

        btn_ajouter.setOnClickListener {
            val   num:String = NumInter.text.toString()
            val type:String = spinner_type.selectedItem.toString()
            val nomp:String = spinner_nom.selectedItem.toString()
            val date:String =InputDate.text.toString()

               val post = Interv(num,nomp,type,date)


            var gson = Gson()
            var array:JSONArray=JSONArray()
            var jsonString:String = gson.toJson(post)
            array.put(jsonString)

            writeJSONtoFile(file,array)


        }

        addDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, annee, mois, jour ->
                InputDate.text="$jour-${mois+1}-$annee"

            }, annee, mois, jour)

            datePickerDialog.show()


        }

        val NomP= arrayOf("Djellal","Ahmim","Mazrou","fodil","fedala")
        val Type_Interv= arrayOf("Nurse","Menage","Baby-setting","Garde malade","transport de personne")
        val adapterSpinner_N = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, NomP
        )
        adapterSpinner_N.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_nom.setAdapter(adapterSpinner_N)


        val adapterSpinner_T = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, Type_Interv
        )
        adapterSpinner_T.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_type.setAdapter(adapterSpinner_T)

        spinner_nom_text.text=spinner_nom.selectedItem.toString()
        spinner_type_text.text=spinner_type.selectedItem.toString()

    }
    private fun writeJSONtoFile(file:File,array:JSONArray) {
        //Create a Object of Gson
        var gson = Gson()
        val obj = JSONObject()
        try {
            // Add the JSONArray to the JSONObject
            obj.put("the_array", array)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //Convert the Json object to JsonString
        //Initialize the File Writer and write into file
        val json = obj.toString()
        file.writeText(json)
    }
    private fun readJSONfromFile(f:String) {

        //Creating a new Gson object to read data
        var gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }
/*
        //Convert the Json File to Gson Object
        var post = gson.fromJson(inputString, Interv::class.java)
        //Initialize the String Builder
        stringBuilder = StringBuilder("Post Details\n---------------------")
        +Log.d("Kotlin",post.nom)
        stringBuilder?.append("\nPost Heading: " + post.num)
        stringBuilder?.append("\nPost URL: " + post.Type)
        stringBuilder?.append("\nPost Author: " + post.date)
        stringBuilder?.append("\nTags:")
        //get the all Tags

        post.nom?.forEach { tag -> stringBuilder?.append(tag + ",") }
        //Display the all Json object in text View
        textView?.setText(stringBuilder.toString())*/

    }


}

