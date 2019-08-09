package net.esfun.tmdb


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies_list.*
import net.esfun.tmdb.data.MainViewModel
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV

private const val ARG_TYPE = "typeTMDb"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MoviesListFragment : Fragment() {

    private var typeTMDb: String? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var listMovies: ArrayList<TmdbMovie>
    private lateinit var dataMovie: LiveData<List<TmdbMovie>>

    private lateinit var listTVs: ArrayList<TmdbTV>
    private lateinit var dataTV: LiveData<List<TmdbTV>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeTMDb = it.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //recycleView.layoutManager = LinearLayoutManager(this)

        viewModel = (activity as MainActivity).viewModel

        if (typeTMDb?.contains("movie")!!) {
            setMovieData()
        }
        if (typeTMDb?.contains("tv")!!) {
            setTVData()
        }


    }

    fun setMovieData(){
        dataMovie = viewModel.getMovies()

        swipeContainer.setOnRefreshListener {
            viewModel.fetchMovie()
        }

        listMovies = ArrayList()
        recycleView.adapter = MovieAdapter(listMovies)

        dataMovie.observe(this,
            Observer<List<TmdbMovie>> { t ->
                var rText="Loaded ${t?.size}"
                if (t?.size==0)  rText="Loading..."
                Toast.makeText(activity, rText, Toast.LENGTH_SHORT).show()
                listMovies.clear()
                listMovies.addAll(t)
                recycleView.adapter!!.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            })
    }

    fun setTVData(){
        dataTV = viewModel.getTVs()

        swipeContainer.setOnRefreshListener {
            viewModel.fetchTV()
        }

        listTVs= ArrayList()
        recycleView.adapter = TVAdapter(listTVs)

        dataTV.observe(this,
            Observer<List<TmdbTV>> { t ->
                var rText="Loaded ${t?.size}"
                if (t?.size==0)  rText="Loading..."
                Toast.makeText(activity, rText, Toast.LENGTH_SHORT).show()
                listTVs.clear()
                listTVs.addAll(t)
                recycleView.adapter!!.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            })
    }


    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type)
                }
            }
    }
}
