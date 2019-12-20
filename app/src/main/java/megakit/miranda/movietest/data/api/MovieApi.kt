package megakit.miranda.movietest.data.api

import io.reactivex.Single
import megakit.miranda.movietest.data.pojo.MovieRepsonse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val BASE_URL = "https://api.themoviedb.org/"
const val API_KEY = "3fa9058382669f72dcb18fb405b7a831"

interface MovieApi {

    @GET("/3/movie/popular")
    fun movie(
        @Query("page") page: Int,
        @Query("language")language:String = Locale.getDefault().language,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieRepsonse>
}