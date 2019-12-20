package megakit.miranda.movietest.data.repo

import io.reactivex.Single
import megakit.miranda.movietest.data.pojo.Movie
import megakit.miranda.movietest.data.pojo.MovieRepsonse

interface Repo {
    fun loadMovie(page: Int): Single<MovieRepsonse>
}