package megakit.miranda.movietest.ui

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Test {
    private fun test(context: Context) {
        ReactiveNetwork
            .observeNetworkConnectivity(context)
            .flatMap<Any> { connectivity: Connectivity ->
                if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                    Observable.error<Any> { RuntimeException("not connected") }
                }
                Observable.error { RuntimeException("not connected") }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response: Any? -> } /* handle response here */
                /* handle error here */
            ) { throwable: Throwable? -> }
    }
}