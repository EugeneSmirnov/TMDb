package net.esfun.tmdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext

import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV
import net.esfun.tmdb.data.source.remote.ImplRemoteDAO

class MainViewModel : ViewModel() {

    private val serviceApi = ImplRemoteDAO

    lateinit var movies: MutableLiveData<List<TmdbMovie>>
    var movieList: List<TmdbMovie>? = null
    var syncMovie = false

    lateinit var tvs: MutableLiveData<List<TmdbTV>>
    var tvList: List<TmdbTV>? = null
    var syncTV = false


    fun getMovies(): LiveData<List<TmdbMovie>> {

        if (!syncMovie) {
            movies = MutableLiveData()
            fetchMovie()
        }
        return movies
    }

    fun getTVs(): LiveData<List<TmdbTV>> {

        if (!syncTV) {
            tvs = MutableLiveData()
            fetchTV()
        }
        return tvs
    }

    fun fetchMovie() {
        viewModelScope.launch(coroutineContext) {
            try {
                //load from database
                movieList = TMDbApp.database?.TmdbDAO()?.getMovies()!!
                //movies= MutableLiveData()
                movies.postValue(movieList)
            } catch (ex: Exception) {
                print("error: $ex")
            }
        }

        getFromServerAndLoadMoviesToDatabase(3)
    }

    private fun getFromServerAndLoadMoviesToDatabase(pages: Int) {
        for (page in 1..pages) {
            viewModelScope.launch(coroutineContext) {
                try {
                    // start refresh info and insert database and select
                    val response = serviceApi.getPopularMovies(page)
                    TMDbApp.database?.TmdbDAO()?.insertMovies(response.results)
                    movieList = TMDbApp.database?.TmdbDAO()?.getMovies()
                    movies.postValue(movieList)

                } catch (ex: Exception) {
                    print("error: $ex")
                    syncMovie = false
                }
            }
        }
        syncMovie = true
    }

    fun fetchTV() {
        viewModelScope.launch(coroutineContext) {
            try {
                //load from database
                tvList = TMDbApp.database?.TmdbDAO()?.getTVs()!!
                //movies= MutableLiveData()
                tvs.postValue(tvList)
            } catch (ex: Exception) {
                print("error: $ex")
            }
        }

        getFromServerAndLoadTVsToDatabase(3)
    }

    private fun getFromServerAndLoadTVsToDatabase(pages: Int) {
        for (page in 1..pages) {
            viewModelScope.launch(coroutineContext) {
                try {
                    // start refresh info and insert database and select
                    val response = serviceApi.getPopularTV(page)
                    TMDbApp.database?.TmdbDAO()?.insertTVs(response.results)
                    tvList = TMDbApp.database?.TmdbDAO()?.getTVs()
                    tvs.postValue(tvList)

                } catch (ex: Exception) {
                    print("error: $ex")
                    syncTV = false
                }
            }
        }
        syncTV = true
    }

}