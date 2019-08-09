package net.esfun.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class TmdbMovie(
    @PrimaryKey val id: Int,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releasedDate: String,

    val title: String,
    val overview: String,
    val adult: Boolean
)

data class TmdbMovieResponse(
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    val results: List<TmdbMovie>
)