package com.example.movieapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FragmentMovieListBinding

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph){
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)
        val view = binding.root
        MockupMovies.populateCards()

        adapter = MyItemRecyclerViewAdapter(this)

        recyclerView = binding.list
        progressBar = binding.progressLoading

        recyclerView.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        viewModel.addState(DataState.Error)
        initObservers()
        return view
    }

    private fun initObservers(){

        viewModel.dataStateLiveData.observe(viewLifecycleOwner, Observer{

            when(it){
                DataState.Error -> showErrorDialog()
                DataState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                DataState.Success -> {
                    viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer { MovieList ->
                        adapter.updateData(MovieList)
                    })
                }
                else -> println("Ops...algum erro inesperado ocorreu, tente novamente mais tarde.")
            }
        })


        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment()
            findNavController().navigate(action)
        })
    }

    private fun showErrorDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Erro")
        builder.setMessage("Não foi possível carregar a lista de filmes, tente novamente mais tarde.")
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener{ _, _ ->
            activity?.finish()
        } )
        builder.create()
        builder.show()

    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }

}