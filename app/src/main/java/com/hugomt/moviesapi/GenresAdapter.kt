package com.hugomt.moviesapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class GenresAdapter(private val data: ArrayList<GenresResponse.Genre>, val onClick:(GenresResponse.Genre)->Unit) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_genero, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val genero = itemView.findViewById<TextView>(R.id.genero)
        val card = itemView.findViewById<CardView>(R.id.card)
        fun bind(item: GenresResponse.Genre) {
            genero.text = item.name
            card.setCardBackgroundColor(generateColor().toInt())
            card.setOnClickListener {
                Log.v("Pulso sobre", item.id.toString())
                onClick(item)
            }
        }
        fun generateColor(): Long {
            val colors = arrayListOf(0xff009688)
            return colors[0]
        }
    }
}
