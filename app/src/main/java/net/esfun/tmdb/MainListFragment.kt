package net.esfun.tmdb


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_movies_list.*
import net.esfun.tmdb.data.model.TmdbMovie
import net.esfun.tmdb.data.model.TmdbTV

private const val ARG_TYPE = "typeTMDb"

/**
 * A simple [Fragment] subclass.
 * Use the [MainListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainListFragment : Fragment() {

    private var typeTMDb: String? = null

    private lateinit var viewModel: MainViewModel

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

            var arrayList = ArrayList<TmdbMovie>()
            recycleView.adapter = MovieAdapter(arrayList)
            setData(arrayList, viewModel.getMovies())
            swipeContainer.setOnRefreshListener {
                viewModel.fetchMovie()
            }
        }
        if (typeTMDb?.contains("tv")!!) {
            var arrayList = ArrayList<TmdbTV>()
            recycleView.adapter = TVAdapter(arrayList)
            setData(arrayList, viewModel.getTVs())

            swipeContainer.setOnRefreshListener {
                viewModel.fetchTV()
            }
        }
    }

    private fun <T> setData(arrayList: ArrayList<T>, liveData: LiveData<List<T>>) {

        liveData.observe(this,
            Observer {
                if (it?.size == 0) Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
                arrayList.clear()
                arrayList.addAll(it)
                recycleView.adapter?.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            })
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            MainListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type)
                }
            }
    }
}