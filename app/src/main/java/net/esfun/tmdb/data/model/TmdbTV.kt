package net.esfun.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv")
data class TmdbTV(
    @PrimaryKey val id: Int,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val name: String,
    val overview: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("in_production")
    val inProduction: Boolean
)

data class TmdbTVResponse(
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    val results: List<TmdbTV>
)
