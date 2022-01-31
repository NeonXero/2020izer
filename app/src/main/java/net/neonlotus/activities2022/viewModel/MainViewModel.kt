package net.neonlotus.activities2022.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.neonlotus.activities2022.model.Blog

class MainViewModel : ViewModel() {
    var lst = MutableLiveData<ArrayList<Blog>>()
    var newlist = arrayListOf<Blog>()

    fun add(blog: Blog) {
        newlist.add(blog)
        lst.value = newlist
    }

    fun remove(blog: Blog) {
        newlist.remove(blog)
        lst.value = newlist
    }

    fun dupeFirst() {
        val firstItem = lst.value?.get(0)
        newlist.add(firstItem!!)
        lst.value = newlist

    }

}