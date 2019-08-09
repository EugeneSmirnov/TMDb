package net.esfun.tmdb


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_item.view.*
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV

class TVAdapter(
    private var list: ArrayList<TmdbTV>
) : RecyclerView.Adapter<TVAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = list[position].title
        holder.txtOverview.text = list[position].overview
        holder.txtVote.text = "${list[position].voteAverage}"
        holder.imageView.loadUsualImage("https://image.tmdb.org/t/p/w500${list[position].backdropPath}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return list.size
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