package net.neonlotus.activities2022.retrofitexample

import net.neonlotus.activities2022.realm.RealmQuote

data class QuoteObject(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    constructor(realm: RealmQuote) : this(
        _id = realm.id,
        author = realm.author,
        authorSlug = "",
        content = realm.content,
        dateAdded = "",
        dateModified = "",
        length = realm.length,
        tags = listOf()
    )
}