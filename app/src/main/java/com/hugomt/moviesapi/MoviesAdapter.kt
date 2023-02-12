package com.hugomt.moviesapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hugomt.moviesapi.api.ApiRest
import com.squareup.picasso.Picasso


class MoviesAdapter(private val data: ArrayList<MoviesResponse.Result>, val onClick:(MoviesResponse.Result) ->Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_movie, parent, false)
        return ViewHolder(view)


    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieTitle = itemView.findViewById<TextView>(R.id.movieTitle)
        val card = itemView.findViewById<ImageView>(R.id.moviePoster)

        fun bind(item: MoviesResponse.Result) {
            movieTitle.text = item.title
            val image = ApiRest.URL_IMAGES + item.posterPath
            Picasso.get().load(image).into(card)

            itemView.setOnClickListener {
                onClick(item)
            }
        }

    }
}
