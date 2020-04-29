package com.example.myapplication
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


class MainActivity : AppCompatActivity() ,IntervAdapter.OnIntervListener{
    var array = arrayOf<Interv>()
    var intervList : MutableList<Interv> ?= null
    private var db: DataBase? = null
    private var dao: IntervDAO? = null
    var adapter: IntervAdapter? = null
     var layoutManager: LinearLayoutManager ?=  null

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(com.example.myapplication.R.layout.activity_main)


       /* val db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java, "intervention"
        ).build()*/
        initDB()

         intervList?.addAll(array)
        Log.i("main",array.size.toString())
        val recyclerView =findViewById(com.example.myapplication.R.id.recyclerView) as RecyclerView
        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        val adapter =IntervAdapter(this, this)

        recyclerView.adapter = adapter


        val activity = this

            val  fab =findViewById(com.example.myapplication.R.id.fab) as View
            fab.setOnClickListener {
                val intent = Intent(activity, TraitInterv_Activity::class.java)
                intent.putExtra("mode","ajout")
                startActivity(intent)
                Log.i("mainn",intervList?.size.toString())
            }





    }

    override fun OnItemClick(item: Interv, position: Int) {
        Toast.makeText(this, item.nom , Toast.LENGTH_SHORT).show()
        val intent = Intent(this,TraitInterv_Activity::class.java)
        intent.putExtra("mode","modif")
        intent.putExtra("pos", position)

        startActivity(intent)
    }
    fun initDB() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = DataBase.invoke(act)
                act.dao = db?.intervDAO()
                intervList= act.dao!!.getIntervs()
                Log.i("kotlin",intervList!!.size.toString())
                return null
            }
            override fun onPostExecute(result: Void?) {
                if(intervList != null) {
                } }
        }.execute()
    }


    }








