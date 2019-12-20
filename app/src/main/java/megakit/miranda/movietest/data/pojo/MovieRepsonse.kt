package megakit.miranda.movietest.data.pojo

import com.google.gson.annotations.SerializedName

data class MovieRepsonse (
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val totalResults : Int,
    @SerializedName("total_pages") val totalPages : Int,
    @SerializedName("results") val results : List<Movie>
    )