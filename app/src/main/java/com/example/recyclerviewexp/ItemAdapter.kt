package com.example.recyclerviewexp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val exampleList: List<ExampleItem>,
                    private val listener: OnItemClickListener
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val cardImage: ImageView = itemView.findViewById(R.id.cardimage)
        val cardTitle: TextView = itemView.findViewById(R.id.card_title)
        val cardDesc: TextView = itemView.findViewById(R.id.card_desc)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.cardImage.setImageResource(currentItem.imageResource)
        holder.cardTitle.text = currentItem.text1
        holder.cardDesc.text = currentItem.text2
    }

    override fun getItemCount(): Int {
       return exampleList.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}