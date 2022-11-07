package com.example.movieapp

object MockupMovies {

    val cardMoviesList = mutableListOf<BodyCardMovies>()
    fun populateCards(){

        cardMoviesList.clear()

        val movie1 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )

        val movie2 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )

        val movie3 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )
        val movie4 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )

        val movie5 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",

        )

        val movie6 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )

        val movie7 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
        )

        cardMoviesList.add(movie1)
        cardMoviesList.add(movie2)
        cardMoviesList.add(movie3)
        cardMoviesList.add(movie4)
        cardMoviesList.add(movie5)
        cardMoviesList.add(movie6)
        cardMoviesList.add(movie7)

    }


}