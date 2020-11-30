package com.example.recyclerviewexp

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View


import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.File
import java.io.IOException
import kotlin.random.Random


class MainActivity : AppCompatActivity() ,ItemAdapter.OnItemClickListener{
     private lateinit var exampleList: ArrayList<ExampleItem>
     private lateinit var adapter: ItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exampleList = loadJsonItems()
        adapter = ItemAdapter(exampleList, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.setHasFixedSize(true)



        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }

        })


    }

    private fun loadJsonItems(): ArrayList<ExampleItem> {
        val arrlist = ArrayList<ExampleItem>()
//        var json: String = ""
        try {
//            val inputStream: InputStream = assets.open("products.json") // your json value here
            val json = application.assets.open("products.json").bufferedReader().use{
                it.readText()
            }
            //val topic = Gson().fromJson(json, Json4Kotlin_Base::class.java)
            var jsonarr = JSONArray(json)
            println(jsonarr.length())
            for (i in 0..jsonarr.length() - 1) {
                var jsonobj = jsonarr.getJSONObject(i)
                val itemName:String = jsonobj.getString("name")
                var drawable:Int = R.drawable.fries


//                drawable = getImageIdFromAsset(itemName)
//                Log.i("INFO", filePath(itemName))

                arrlist += ExampleItem(
                        drawable,//Pass only int id
                        itemName,
                        jsonobj.getString("brand")
                )
            }
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
            return arrlist
        }
        return arrlist
    }

//    fun filePath(image: String, _context: Context): String? {
//        val file = File(_context.cacheDir + File.separator.toString() + image)
//        return if (file.exists()) file.getPath() else null
//    }

    private fun getImageIdFromAsset(filename: String): Int {
        TODO("Find the icon asset from its itemname and return its id " +
                "Split the itemName into aliases using seperator for | and check if\" +\n" +
                   " the icon exists in the svg asset folder ")

//        val itemAliases = filename.split("|",
//                ignoreCase =false,
//                limit = 0)
//        for alias in itemAliases{
//
//        }
//                try {
//                    if(itemName.contains(itemName, ignoreCase = true)){
//                        drawable = resources.getIdentifier("svg/$itemName", "drawable","com.example.recyclerviewexp")
//                    }
//                }
//                catch(e:Exception){
//                    //fallback icon
//                    drawable = R.drawable.fries
//                }
    }

    private fun generateDummyList(size: Int): ArrayList<ExampleItem>{
        val list = ArrayList<ExampleItem>()

        for (i in 0 until size){
            val drawable = when(i % 3){
                0 -> R.mipmap.ic_launcher_round
                1 -> R.drawable.ic_launcher_foreground
                else -> R.drawable.ic_launcher_background
            }
            val item = ExampleItem(drawable, text1 = "Item $i", text2 = "This is an item $i")
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
        exampleList.add(index, newItem)
        adapter.notifyItemInserted(index)
    }

}

