package com.example.movieapp.movieFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.Results
import com.example.movieapp.databinding.FragmentMovieItemBinding

//interface MovieItemListener{
//    fun onItemSelected(position: Int)
//}

class MovieListAdapter() : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    var onClick : ((Int) -> Unit)? = null

    private var values: List<Results> = ArrayList()

    fun updateData(movieList: List<Results>){
        values = movieList
        notifyDataSetChanged()
    }

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
        holder.bindItem(item)
        holder.view.setOnClickListener {
            onClick?.let {
                it(item.id)
            }
//            listener.onItemSelected(position)
        }

    }

    override fun getItemCount(): Int = values.size

    fun onMovieClick(listener: (Int)-> Unit){
        onClick = listener
    }

    inner class ViewHolder(private val binding: FragmentMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val view: View = binding.root

        fun bindItem(item: Results){
            binding.movieItem = item
            binding.executePendingBindings()
        }

    }

}