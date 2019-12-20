package megakit.miranda.movietest.ui

import android.content.Context
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import megakit.miranda.movietest.data.pojo.Movie
import megakit.miranda.movietest.data.pojo.MovieRepsonse
import megakit.miranda.movietest.data.pojo.Resource
import megakit.miranda.movietest.data.pojo.State
import megakit.miranda.movietest.data.repo.Repo
import megakit.miranda.movietest.di.qualifier.ApplicationContext
import javax.inject.Inject

const val TAG = "MainViewModel"

@Suppress("DEPRECATION")
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: Repo
) : ViewModel() {
    private val cd = CompositeDisposable()

    private var loading = false
    private var page = 0
    private var totalPages = 0
    private val items = mutableListOf<Movie>()

    val showNoNetLD = MutableLiveData<Boolean>()
    var dataLD: MutableLiveData<Resource<List<Movie>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                loadItems()
            }
            return field
        }

    init {
        cd.add(ReactiveNetwork.observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val connected = it.state() == NetworkInfo.State.CONNECTED
                showNoNetLD.postValue(!connected)
            })
    }

    private fun loadItems() {
        cd.add(repo.loadMovie(++page)
            .compose(RxUtil.repeatSingleIfIOException(1000))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading = true
                dataLD?.value = Resource(State.LOADING)
            }
            .doAfterTerminate { loading = false }
            .subscribe(
                { handleResponse(it) },
                { handleError(it) })
        )
    }

    private fun handleResponse(response: MovieRepsonse) {
        totalPages = response.totalPages
        items.addAll(response.results)
        dataLD?.value = Resource(State.SUCCESS, items)

    }

    private fun handleError(exception: Throwable) {
        dataLD?.value = Resource(State.FAILED, null, exception)
    }

    override fun onCleared() {
        super.onCleared()
        cd.dispose()
    }

    fun loadNextPage() {
        if (!loading && totalPages > page) {
            loadItems()
        }
    }
}