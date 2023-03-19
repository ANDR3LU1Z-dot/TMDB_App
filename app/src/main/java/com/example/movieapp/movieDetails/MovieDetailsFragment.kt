package com.example.movieapp.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.movieViewModel = viewModel

        return binding.root
    }

}