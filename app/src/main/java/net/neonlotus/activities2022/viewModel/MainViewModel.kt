package net.neonlotus.activities2022.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.neonlotus.activities2022.model.Blog
import net.neonlotus.activities2022.retrofitexample.QuotesApi
import net.neonlotus.activities2022.retrofitexample.RetrofitHelper

class MainViewModel : ViewModel() {
    var lst = MutableLiveData<ArrayList<Blog>>()
    var newlist = arrayListOf<Blog>()

    val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)

    fun add(blog: Blog) {
        newlist.add(blog)
        lst.value = newlist
    }

    fun remove(blog: Blog) {
        newlist.remove(blog)
        lst.value = newlist
    }

    fun getQuotes() {
        viewModelScope.launch {

            val result = quotesApi.getQuotes().body()
            if (result != null) {
                result!!.results.forEach {
                    val blog = Blog(it.content, it.author)
                    newlist.add(blog)
                }

                lst.value = newlist
            }


        }
    }

}