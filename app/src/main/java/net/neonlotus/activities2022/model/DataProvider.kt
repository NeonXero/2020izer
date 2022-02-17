package net.neonlotus.activities2022.model

class DataProvider {

    private val games = arrayListOf<Game>()
    private val books = arrayListOf<Book>()
    private val movies = arrayListOf<Movie>()
    private val shows = arrayListOf<Show>()

    fun getGames() = games
    fun getBooks() = books
    fun getMovies() = movies
    fun getShows() = shows
}