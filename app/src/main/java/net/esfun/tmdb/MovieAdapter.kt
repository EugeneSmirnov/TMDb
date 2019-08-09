package net.esfun.tmdb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import net.esfun.tmdb.data.model.TmdbMovie

class MovieAdapter(
    private var movieList: ArrayList<TmdbMovie>,
    private var context: Context
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = movieList[position].title
        holder.txtOverview.text = movieList[position].overview
        holder.txtVote.text = "${movieList[position].voteAverage}"
        holder.imageView.loadUsualImage("https://image.tmdb.org/t/p/w500${movieList[position].backdropPath}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.txtName
        val txtOverview = itemView.txtOverview
        val txtVote = itemView.txtVote
        val imageView = itemView.imageView

        init {
            itemView.setOnClickListener {

            }
        }
    }
}