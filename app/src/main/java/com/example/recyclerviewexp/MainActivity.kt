package com.example.recyclerviewexp

import Json4Kotlin_Base
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.security.AccessController.getContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() ,ItemAdapter.OnItemClickListener{
    private var exampleList = loadJsonItems(applicationContext)
    private val adapter = ItemAdapter(exampleList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(this,3)
        recycler_view.setHasFixedSize(true)
    }

    private fun loadJsonItems(context:Context): ArrayList<ExampleItem> {
        val arrlist = ArrayList<ExampleItem>()
        var json: String = ""
        try {
            val inputStream: InputStream = context.assets.open("products.json") // your json value here
            json = inputStream.bufferedReader().use{ it.readText() }
//        val topic = Gson().fromJson(json, Json4Kotlin_Base::class.java)

            var jsonarr = JSONArray(json)
            println(jsonarr.length())
            for (i in 0..jsonarr.length() - 1) {
                var jsonobj = jsonarr.getJSONObject(i)
                arrlist += ExampleItem(
                    R.drawable.ic_launcher_foreground,
                    jsonobj.getString("brand"),
                    jsonobj.getString("name")
                )
            }
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
            return arrlist
        }
        return arrlist
    }

    private fun generateDummyList(size: Int): ArrayList<ExampleItem>{
        val list = ArrayList<ExampleItem>()

        for (i in 0 until size){
            val drawable = when(i % 3){
                0 -> R.mipmap.ic_launcher_round
                1 -> R.drawable.ic_launcher_foreground
                else -> R.drawable.ic_launcher_background
            }
            val item = ExampleItem(drawable,text1 = "Item $i",text2 = "This is an item $i")
            list += item
        }

        return  list
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position Clicked!", Toast.LENGTH_SHORT).show()
        val clickedItem: ExampleItem = exampleList[position]
        clickedItem.text1 = "Clicked"
        adapter.notifyItemChanged(position)
    }

    fun removeItem(view: View) {
        val index: Int = Random.nextInt(8)
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }
    fun insertItem(view: View) {
        val index:Int = Random.nextInt(8)
        val newItem = ExampleItem(
            R.drawable.ic_launcher_foreground,
            text1 = "NewItem $index",
            text2 = "Newitem $index was added!"
        )
        exampleList.add(index,newItem)
        adapter.notifyItemInserted(index)
    }

}

