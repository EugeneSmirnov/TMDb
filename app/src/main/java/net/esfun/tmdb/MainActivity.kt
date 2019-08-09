package net.esfun.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.esfun.tmdb.data.MainViewModel
import net.esfun.tmdb.data.model.TmdbMovie


class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var listMovies: ArrayList<TmdbMovie>
    private lateinit var model: MainViewModel
    private lateinit var data: LiveData<List<TmdbMovie>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        data = model.getMovies()

        swipeContainer.setOnRefreshListener {
            model.fetchMovie()
        }

        rv= findViewById(R.id.recycleView)
        // add in xml layout
        //rv.layoutManager = LinearLayoutManager(this)
        listMovies = ArrayList()
        rv.adapter = MovieAdapter(listMovies, this)

        data.observe(this,
            Observer<List<TmdbMovie>> { t ->
                var rText="Loaded ${t?.size}"
                if (t?.size==0)  rText="Loading..."
                Toast.makeText(this,rText, Toast.LENGTH_SHORT).show()
                listMovies.clear()
                listMovies.addAll(t)
                rv.adapter!!.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            })

        /*
        GlobalScope.launch {
            val list = TMDbApp.database?.TmdbDAO()?.getMovies()
            if (list != null) {
                listMovies.addAll(list)
                rv.adapter!!.notifyDataSetChanged()
            }
        }
*/

    }




}
