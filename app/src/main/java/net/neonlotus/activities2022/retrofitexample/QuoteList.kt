package net.neonlotus.activities2022.retrofitexample

// data class QuoteList
// according to JSON response

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<QuoteObject>,
    val totalCount: Int,
    val totalPages: Int
)