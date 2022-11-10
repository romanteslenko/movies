package com.rteslenko.android.moviesgallery.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rteslenko.android.moviesgallery.data.model.Movie
import com.rteslenko.android.moviesgallery.R
import com.rteslenko.android.moviesgallery.utils.ImageLoader

class MoviesListAdapter(context: Context, private val onClick: (Movie, View) -> Unit)
    : ListAdapter<Movie, MoviesListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}) {

    private val imageLoader = ImageLoader(context)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            with(itemView) {
                findViewById<TextView>(R.id.title).text = movie.title
                findViewById<TextView>(R.id.release_date).text = movie.releaseDate
                val imageView = findViewById<ImageView>(R.id.image)
                imageLoader.loadPreview(movie.posterPath, imageView)
                setOnClickListener {
                    onClick(movie, imageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movies_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}