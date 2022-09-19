package com.example.movieapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.movieapp.placeholder.PlaceholderContent.PlaceholderItem
import com.example.movieapp.databinding.FragmentMovieItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

interface MovieItemListener{
    fun onItemSelected(position: Int)
}
class MyItemRecyclerViewAdapter(
    private val values: List<BodyCardMovies>,
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.movieName
        holder.contentView.text = item.synopsis
        holder.imgMovie.setImageResource(item.imgMovie)
        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val idView: TextView = binding.movieName
        val contentView: TextView = binding.content
        val imgMovie: ImageView = binding.imageView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}