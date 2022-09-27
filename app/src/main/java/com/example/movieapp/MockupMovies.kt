package com.example.movieapp

object MockupMovies {

    val cardMoviesList = mutableListOf<BodyCardMovies>()
    fun populateCards(){

        cardMoviesList.clear()

        val movie1 = BodyCardMovies(
            "Tudo em Todo Lugar ao mesmo Tempo",
            "Sinopse: Uma ruptura interdimensional bagunça a realidade e uma inesperada heroína precisa usar seus novos poderes para lutar contra os perigos bizarros do multiverso.",
            R.drawable.tudo_em_todo_lugar
        )

        val movie2 = BodyCardMovies(
            "Não! Não Olhe!",
            "Sinopse: Em um vale isolado no interior da Califórnia, os moradores de um vilarejo são surpreendidos com uma descoberta apavorante que surgiu no céu.",
            R.drawable.nope_poster2
        )

        val movie3 = BodyCardMovies(
            "O Predador: A Caçada",
            "Sinopse: Em 1719, uma habilidosa guerreira Comanche tenta proteger seu povo de um predador alienígena altamente evoluído que caça humanos por esporte. Ela luta contra a natureza, colonizadores perigosos e essa criatura misteriosa para manter sua tribo segura.",
            R.drawable.prey_poster
        )
        val movie4 = BodyCardMovies(
            "Telefone Preto",
            "Sinopse: Finney Shaw, de 13 anos, é sequestrado por um sádico assassino mascarado e mantido em um porão à prova de som. Até que um telefone desconectado na parede começa a tocar, e ele logo descobre que pode ouvir as vozes das vítimas anteriores do maníaco.",
            R.drawable.o_telefone_preto2
        )

        val movie5 = BodyCardMovies(
            "Minions 2: A Origem de Gru",
            "Sinopse: Nos anos 1970, o jovem Gru tenta entrar para um time de supervilões, mas a entrevista é desastrosa e ele e seus minions acabam fugindo do grupo de mal-feitores.",
            R.drawable.minions2

        )

        val movie6 = BodyCardMovies(
            "The Batman",
            "Sinopse: Após dois anos espreitando as ruas como Batman, Bruce Wayne se encontra nas profundezas mais sombrias de Gotham City. Com poucos aliados confiáveis, o vigilante solitário se estabelece como a personificação da vingança para a população.",
            R.drawable.the_batman
        )

        val movie7 = BodyCardMovies(
            "The Northman",
            "Sinopse: O Príncipe Amleth está prestes a se tornar um homem quando seu tio assassina seu pai e sequestra sua mãe. Duas décadas depois, o jovem é agora um viking com a missão de salvar a mãe, matar o tio e vingar seu pai.",
            R.drawable.the_northman
        )

        val movie8 = BodyCardMovies(
            "Não Se Preocupe, Querida",
            "Sinopse: Uma dona de casa que vive em uma comunidade experimental começa a suspeitar que a empresa de seu marido está escondendo segredos perturbadores.",
            R.drawable.dont_worry_darling
        )

        val movie9 = BodyCardMovies(
            "Homem Aranha Sem volta pra Casa",
        "Sinopse: O Homem-Aranha precisa lidar com as consequências da sua verdadeira identidade ter sido descoberta.",
            R.drawable.homem_aranha_poster
        )

        val movie10 = BodyCardMovies(
            "O Lendário Cão Guerreiro",
            "Sinopse: Um cachorro desajeitado sonha um dia em ser um autêntico e legítimo samurai.",
            R.drawable.o_lendario_cao_guerreiro
        )

        cardMoviesList.add(movie1)
        cardMoviesList.add(movie2)
        cardMoviesList.add(movie3)
        cardMoviesList.add(movie4)
        cardMoviesList.add(movie5)
        cardMoviesList.add(movie6)
        cardMoviesList.add(movie7)
        cardMoviesList.add(movie8)
        cardMoviesList.add(movie9)
        cardMoviesList.add(movie10)
    }


}