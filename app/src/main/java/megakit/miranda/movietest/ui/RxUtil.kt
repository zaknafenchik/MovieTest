package megakit.miranda.movietest.ui

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import megakit.miranda.movietest.exeption.NoNetExeption
import java.util.concurrent.TimeUnit

object RxUtil {


    fun <T> repeatFlowableIfIOException(milli: Long): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.retryWhen { errors ->
                errors.flatMap { error ->
                    if (error is NoNetExeption) {
                        Flowable.timer(milli, TimeUnit.SECONDS)
                    } else {
                        errors.last(error).toFlowable()
                    }
                }
            }

        }
    }
}