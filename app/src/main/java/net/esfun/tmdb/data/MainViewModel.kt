package net.esfun.tmdb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import net.esfun.tmdb.TMDbApp

import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.remote.ImplRemoteDAO

class MainViewModel : ViewModel() {

    private val serviceApi = ImplRemoteDAO

    lateinit var movies: MutableLiveData<List<TmdbMovie>>
    var movieList: List<TmdbMovie>? = null

    var sync = false


    fun getMovies(): LiveData<List<TmdbMovie>> {

        if (!sync) {
            movies = MutableLiveData()
            fetchMovie()
        }
        return movies
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
                    sync = false
                }
            }
        }
        sync = true
    }

}