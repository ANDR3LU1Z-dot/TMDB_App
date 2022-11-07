package com.example.movieapp

import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    fun loadMovieDetails() : MovieDetails{
        return MovieDetails("Filme 1", "Texto do conteúdo da sinopse", "10/10")
    }
}