package megakit.miranda.movietest.ui

import io.reactivex.Flowable
import io.reactivex.SingleTransformer
import java.io.IOException
import java.util.concurrent.TimeUnit

object RxUtil {

    fun <T> repeatSingleIfIOException(milli: Long): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.retryWhen { errors ->
                errors.flatMap { error ->
                    if (error is IOException) {
                        Flowable.timer(milli, TimeUnit.MILLISECONDS)
                    } else {
                        errors.last(error).toFlowable()
                    }
                }
            }
        }
    }
}