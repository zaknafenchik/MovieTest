package megakit.miranda.movietest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import megakit.miranda.movietest.R
import megakit.miranda.movietest.data.pojo.Movie

class MovieAdapter : ListAdapter<Movie ,MovieAdapter.MovieVH>(
    AsyncDifferConfig.Builder<Movie>(diffCallback).build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieVH(view)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val movie = getItem(position)
        with(holder.itemView) {
            tvTitle.text = movie.title
            Picasso.get()
                .load(movie.posterPath)
                .into(ivMovie)
        }
    }

    class MovieVH(view: View) : RecyclerView.ViewHolder(view)
}

val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}