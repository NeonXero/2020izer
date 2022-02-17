package net.neonlotus.activities2022.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var lst = MutableLiveData<ArrayList<String>>()
    var newlist = arrayListOf<String>()


    fun add(blog: String) {
        newlist.add(blog)
        lst.value = newlist
    }

    fun remove(blog: String) {
        newlist.remove(blog)
        lst.value = newlist
    }


}