package net.neonlotus.activities2022.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    private val radioCategory = MutableLiveData<Int>()
    private var selectedCategory: Int = 0

    init {
        selectedCategory = 0
    }


}