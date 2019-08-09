package net.esfun.tmdb.data

import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbMovieResponse
import net.esfun.tmdb.data.model.TmdbTV

interface TmdbDataSource {

    suspend fun getMovie(id: Int): TmdbMovie
    suspend fun getPopularMovies(page: Int): TmdbMovieResponse
    suspend fun getTopRatedMovies(page:Int): TmdbMovieResponse
    suspend fun getTV(id:Int):TmdbTV
    suspend fun getPopularTV():List<TmdbTV>


}