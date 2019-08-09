package net.esfun.tmdb.data.remote

import net.esfun.tmdb.data.TmdbDataSource
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbMovieResponse
import net.esfun.tmdb.data.model.TmdbTV
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ImplRemoteDAO: TmdbDataSource {


    private val SERVER_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "3ba9337bc3b8a1bb0202c51ad066f076"
    const val language = "ru-RU"

    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
//            .addHeader("AUTH_TOKEN", "test")
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(authInterceptor)
        .retryOnConnectionFailure(true)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    private val api = retrofit.create(API::class.java)

    override suspend fun getMovie(id: Int): TmdbMovie{
        return ImplRemoteDAO.api.getMovie(id,language )
    }

    override suspend fun getPopularMovies(page:Int): TmdbMovieResponse {
        return ImplRemoteDAO.api.getPopularMovie(language, page)
    }

    override suspend fun getTopRatedMovies(page: Int): TmdbMovieResponse {
        return ImplRemoteDAO.api.getTopRatedMovies(language, page)
    }

    override suspend fun getTV(id: Int): TmdbTV {
        return ImplRemoteDAO.api.getTV(id, language)
    }

    override suspend fun getPopularTV(): List<TmdbTV> {
        return ImplRemoteDAO.api.getPopularTV(language)
    }

}