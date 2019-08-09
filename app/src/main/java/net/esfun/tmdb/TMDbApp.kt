package net.esfun.tmdb

import android.app.Application
import net.esfun.tmdb.data.local.TMDbDatabase

class TMDbApp : Application() {

    companion object {
        var database: TMDbDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = TMDbDatabase.getInstance(this)
    }
}


