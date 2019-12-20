package megakit.miranda.movietest.data.repo

import io.reactivex.Single
import megakit.miranda.movietest.data.api.MovieApi
import megakit.miranda.movietest.data.pojo.MovieRepsonse

const val IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w185/"
class RepoImpl(
    private val movieApi: MovieApi
) : Repo {

    override fun loadMovie(page: Int): Single<MovieRepsonse> {
        return movieApi.movie(page).map {
            it.results.forEach {movie->
                movie.posterPath = IMAGE_BASE_PATH + movie.posterPath
            }
            it
        }
    }
}