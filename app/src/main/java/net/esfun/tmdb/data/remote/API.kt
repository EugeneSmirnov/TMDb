package net.esfun.tmdb.data.remote

import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbMovieResponse
import net.esfun.tmdb.data.model.TmdbTV
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("/movie/{id}")
    suspend fun getMovie(@Path("id") id: Int, @Query("language") language: String): TmdbMovie

    @GET("movie/popular")
    suspend fun getPopularMovie( @Query("language") language: String, @Query("page") page: Int ): TmdbMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies( @Query("language") language: String, @Query("page") page: Int =1): TmdbMovieResponse

    @GET("tv/{id}")
    suspend fun getTV(@Path("id") id: Int, @Query("language") language: String): TmdbTV

    @GET("tv/popular")
    suspend fun getPopularTV( @Query("language") language: String, @Query("page") page: Int = 1):List<TmdbTV>



}