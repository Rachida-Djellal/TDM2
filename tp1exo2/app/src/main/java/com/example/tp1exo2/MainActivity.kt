package com.example.tp1exo2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Message
import android.util.JsonReader
import android.util.JsonToken
import java.io.*


class MainActivity : AppCompatActivity() {

   public var IntervList: ArrayList<Interv> = ArrayList()
    var TypeIntervList: ArrayList<String> =ArrayList()
    var NomPlomList: ArrayList<String> =ArrayList()
    lateinit var adapter: TachAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val btn_ajouter =findViewById<Button>(R.id.ajouter)
        val fileName = cacheDir.absolutePath+"/data.json"
        val long=fileName.length
        val array=readJSONfromFile(fileName)

       val root :JSONObject  = JSONObject(array);
        val   booksArray :JSONArray = root.getJSONArray("the_array");

        Toast.makeText(this, long.toString(), Toast.LENGTH_SHORT).show()

        for (i in 0 until booksArray.length() ) {
            val firstBook :String = booksArray.getString(i);

            var gson = Gson()
            var post = gson.fromJson(firstBook.toString(), Interv::class.java)
            IntervList.add(post)
        }
        btn_ajouter.setOnClickListener {
            val intent = Intent(this, TraitInterv_Activity::class.java)
            startActivity(intent)
          //  val post=Interv("12","amir","menage","20-05-2020")
          //  IntervList.add(post)
         //   Toast.makeText(this, IntervList.size.toString(), Toast.LENGTH_SHORT).show()
        }


        adapter = TachAdapter(this)
        recyclerView.adapter = adapter
    }


    class TachAdapter(val activity: MainActivity) : RecyclerView.Adapter<TachAdapter.TachViewHolder>() {
        class TachViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val IntervNmr = v.findViewById<TextView>(R.id.intervNmrView)
            val IntervDate = v.findViewById<TextView>(R.id.intervDateView)
            val IntervType = v.findViewById<TextView>(R.id.IntervTypeView)
                val NamePlo =v.findViewById<TextView>(R.id.IntervNomView)
            val tacheLayout = v.findViewById<RelativeLayout>(R.id.tacheLayoutView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TachViewHolder {
            return TachViewHolder(
                LayoutInflater.from(activity).inflate(
                    R.layout.interv_view,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return activity.IntervList.size
        }

        override fun onBindViewHolder(holder: TachViewHolder, position: Int) {
            holder.IntervNmr.text = activity.IntervList[position].num
            holder.IntervDate.text = activity.IntervList[position].date
            holder.IntervType.text = activity.IntervList[position].Type
            holder.NamePlo.text = activity.IntervList[position].nom
            holder.tacheLayout.setOnClickListener {


            }
        }
    }

  private fun readJSONfromFile(f:String): String{
      //Creating a new Gson object to read data
      var gson = Gson()
      //Read the PostJSON.json file
      val bufferedReader: BufferedReader = File(f).bufferedReader()
      // Read the text from buffferReader and store in String variable
      val inputString = bufferedReader.use { it.readText()}
      //use { it.readText() }



         return inputString
  }
    @Throws(IOException::class)
    fun readJsonStream(`in`: InputStream): List<Interv> {
        val reader = JsonReader(InputStreamReader(`in`, "UTF-8"))
        try {
            return readMessagesArray(reader)
        } finally {
            reader.close()
        }
    }

    @Throws(IOException::class)
    fun readMessagesArray(reader: JsonReader): List<Interv> {
        val messages = ArrayList<Interv>()

        reader.beginArray()
        while (reader.hasNext()) {
            messages.add(readMessage(reader))
        }
        reader.endArray()
        return messages
    }

    @Throws(IOException::class)
    fun readMessage(reader: JsonReader): Interv{
        var num: String = ""
        var nom: String = ""
        var Type: String = ""
        var date: String = ""

        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "num") {
                num = reader.nextString()
            } else if (name == "nom") {
                nom = reader.nextString()
            } else if (name == "Type" ) {
                Type = reader.nextString()
            } else if (name == "user") {
                date = reader.nextString()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return Interv(num, nom, Type, date)
    }

  /*  @Throws(IOException::class)
    fun readDoublesArray(reader: JsonReader): List<Double> {
        val doubles = ArrayList<Double>()

        reader.beginArray()
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble())
        }
        reader.endArray()
        return doubles
    }

    @Throws(IOException::class)
    fun readUser(reader: JsonReader): Interv {
        var username: String? = null
        var followersCount = -1

        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "name") {
                username = reader.nextString()
            } else if (name == "followers_count") {
                followersCount = reader.nextInt()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return Interv(username, followersCount)
    }*/
}





