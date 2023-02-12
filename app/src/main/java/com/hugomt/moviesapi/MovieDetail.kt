package com.hugomt.moviesapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hugomt.moviesapi.api.ApiRest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetail: Fragment() {


    val TAG = "MovieDetail"
    var data: ArrayList<MovieDetailResponse.Genre> = ArrayList()
    private var adapter: MoviesAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable("id", MoviesResponse.Result::class.java)

            } else {
                arguments?.getSerializable("id") as? MoviesResponse.Result
            }

        ApiRest.initService()
        getMovieDetailFunc(movie)


    }

    private fun getMovieDetailFunc(movies: MoviesResponse.Result?) {
        print(movies?.id)
        val call = ApiRest.service.getMovieDetail(movieId = movies?.id.toString())
        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                val body = response.body()

                if (body != null) {
                    setText(body)
                }

                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    data.clear()
                    data.addAll(body.genres)

// Imprimir aqui el listado con logs
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }

            }
            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())

            }
        })
    }

    fun setText(datosPeli: MovieDetailResponse){



        val imageC = ApiRest.URL_IMAGES + datosPeli.backdropPath
        val imageP = ApiRest.URL_IMAGES + datosPeli.posterPath
        var ivCabecera = view?.findViewById<ImageView>(R.id.photo)
        Picasso.get().load(imageC).into(ivCabecera)
        var ivPortada = view?.findViewById<ImageView>(R.id.tittle)
        Picasso.get().load(imageP).into(ivPortada)

    }





}


