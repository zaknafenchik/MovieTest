package megakit.miranda.movietest.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import megakit.miranda.movietest.R
import megakit.miranda.movietest.data.pojo.State
import megakit.miranda.movietest.di.Injectable
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by lazy(::obtainViewModel)
    lateinit var movieAdapter: MovieAdapter

    private fun obtainViewModel() = ViewModelProvider(this, viewModelFactory)
        .get(MainViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter()
        with(rvMovie) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == adapter!!.itemCount - 1) {
                        viewModel.loadNextPage()
                    }
                }
            })
        }
    }

    private fun observeViewModel() {
        viewModel.dataLD?.observe(this, Observer {
            when (it.state) {
                State.SUCCESS -> {
                    pb.visibility = View.GONE
                    movieAdapter.submitList(it.data)
                    movieAdapter.notifyDataSetChanged()
                }
                State.LOADING -> pb.visibility = View.VISIBLE
                State.FAILED -> {
                    pb.visibility = View.GONE
                }
            }
        })

        viewModel.showNoNetLD.observe(this, Observer { show ->
            when (show) {
                true -> View.VISIBLE
                false -> View.GONE
            }.let {
                tvNoNet.visibility = it
            }
        })
    }
}
