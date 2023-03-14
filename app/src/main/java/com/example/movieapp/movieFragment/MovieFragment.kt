package com.example.movieapp.movieFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.*
import com.example.movieapp.databinding.FragmentMovieListBinding

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MovieListAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph){
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)
        val view = binding.root
        recyclerView = binding.list

        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        adapter = MovieListAdapter(this)

        recyclerView.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()
        return view
    }

    private fun initObservers(){
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
           it?.let {
               adapter.updateData(it)
           }
        })

//        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
//            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment()
//            findNavController().navigate(action)
//        })
    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment()
        findNavController().navigate(action)
    }

}