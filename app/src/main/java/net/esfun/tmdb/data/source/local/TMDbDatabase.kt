package net.esfun.tmdb.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV

@Database(
    entities = [TmdbMovie::class, TmdbTV::class],
    version = 1)
abstract class TMDbDatabase  : RoomDatabase(){

    abstract fun TmdbDAO() : LocalDAO

    companion object{
        private var INSTANCE: TMDbDatabase? = null
        private val lock = Any()
        fun getInstance(context: Context): TMDbDatabase {
            synchronized(lock){
                if (INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TMDbDatabase::class.java, "TMDb.db").build()
                }
                return INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
