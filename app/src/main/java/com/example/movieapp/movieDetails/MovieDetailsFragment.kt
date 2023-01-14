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
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class MovieDetailsFragment : Fragment() {
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) {defaultViewModelProviderFactory}
    private lateinit var binding: FragmentMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false)

        binding.lifecycleOwner = this
        binding.movieViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carousel: ImageCarousel = binding.carousel

        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.tudo_em_todo_lugar
            )
        )

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.tudo_em_todo_lugar3
            )
        )

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.tudo_em_todo_lugar4
            )
        )

        carousel.setData(list)

    }

}