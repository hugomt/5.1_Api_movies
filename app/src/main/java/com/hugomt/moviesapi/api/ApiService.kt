package com.hugomt.moviesapi.api

import com.hugomt.moviesapi.GenresResponse
import com.hugomt.moviesapi.MovieDetailResponse
import com.hugomt.moviesapi.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language
    ): Call<GenresResponse>

    @GET ("discover/movie")
    fun getMovies(
        @Query("with_genres")with_genres: String,
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language
    ): Call<MoviesResponse>
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language): Call<MovieDetailResponse>

}