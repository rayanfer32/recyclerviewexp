package com.example.recyclerviewexp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ItemAdapter(private val exampleList: List<ExampleItem>,
                    private val listener: OnItemClickListener
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(),Filterable {
    var filteredList = exampleList
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val cardImage: ImageView = itemView.findViewById(R.id.cardimage)
        val cardTitle: TextView = itemView.findViewById(R.id.card_title)
        val cardDesc: TextView = itemView.findViewById(R.id.card_desc)


        init{
            itemView.setOnClickListener(this)
            var filteredList = exampleList
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position,filteredList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = filteredList[position]

        holder.cardImage.setImageResource(currentItem.imageResource)
        holder.cardTitle.text = currentItem.text1
        holder.cardDesc.text = currentItem.text2
    }

    override fun getItemCount(): Int {
       return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = exampleList
                } else {
                    var resultList: List<ExampleItem> = emptyList()
                    for (row in exampleList) {
                        if (row.text1.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList += row
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<ExampleItem>
                notifyDataSetChanged()
            }

        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,filteredList:List<ExampleItem>)
    }
}