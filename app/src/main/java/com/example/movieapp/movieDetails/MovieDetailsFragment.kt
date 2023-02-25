package com.example.movieapp.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.movieapp.ApiCredentials
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carouselList: ImageCarousel = binding.carousel

        setCarouselView(carouselList)

    }

    private fun setCarouselView(carousel: ImageCarousel) {
        val list: MutableList<CarouselItem> = mutableListOf()
        viewModel.moviePostersLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty() || it.size < 3) {
                binding.carousel.visibility = View.GONE
                Toast.makeText(
                    requireActivity(),
                    "Não foi possível carregar os posters do filme.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                list.clear()
                list.add(
                    CarouselItem(
                        ApiCredentials.imageUrl + it[0].file_path
                    )
                )

                list.add(
                    CarouselItem(
                        ApiCredentials.imageUrl + it[1].file_path
                    )
                )

                list.add(
                    CarouselItem(
                        ApiCredentials.imageUrl + it[2].file_path
                    )
                )
                carousel.setData(list)
            }
        })

    }

}