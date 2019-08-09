package net.esfun.tmdb.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV
import androidx.room.OnConflictStrategy

@Dao interface LocalDAO {

    @Query("Select * FROM movies")
    suspend fun getMovies(): List<TmdbMovie>?

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): TmdbMovie?

    @Query("Select * FROM tv")
    suspend fun getTVs(): List<TmdbTV>?

    @Query("SELECT * FROM tv WHERE id = :id")
    suspend fun getTVById(id: Int): TmdbTV?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<TmdbMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVs(tvs: List<TmdbTV>)

}