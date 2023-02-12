package com.hugomt.moviesapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hugomt.moviesapi.api.ApiRest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GenresList : Fragment() {


    private var swiperefresh: SwipeRefreshLayout? = null
    private lateinit var rvGeneros: RecyclerView
    private var adapter: GenresAdapter? = null
    val TAG = "GenresList"
    var data: ArrayList<GenresResponse.Genre> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvGeneros = view.findViewById(R.id.rvGeneros)
        swiperefresh = view.findViewById(R.id.swiperefresh)

        val mLayoutManager = GridLayoutManager(context, 2)
        rvGeneros.layoutManager = mLayoutManager


        adapter = GenresAdapter(data){ genre ->
            activity?.let{
                val fragment = MoviesList()

                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("genre", genre)

                Log.i("He pinchado sobre la celda", genre.name)
                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainContainer, fragment).commit()
            }

        }
        rvGeneros.adapter = adapter

        ApiRest.initService()
        getGenres()
        getMovies()

        swiperefresh?.setOnClickListener{
            getGenres()
            getMovies()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres_list, container, false)
    }



    private fun getMovies(){
        val call = ApiRest.service.getMovies("genero pulsado por el usuario")
    }

    private fun getGenres() {
        val call = ApiRest.service.getGenres()
        call.enqueue(object : Callback<GenresResponse> {
            override fun onResponse(
                call: Call<GenresResponse>,
                response: Response<GenresResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    data.clear()
                    data.addAll(body.genres)
                    adapter?.notifyDataSetChanged()
// Imprimir aqui el listado con logs
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }

                swiperefresh?.isRefreshing = false
                //loader?.isVisible = false
            }

            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                swiperefresh?.isRefreshing = false
                //loader?.isVisible = false
            }


        })
    }

}