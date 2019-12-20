package megakit.miranda.movietest.data.pojo

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Long
)