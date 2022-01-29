package net.neonlotus.activities2022.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.neonlotus.activities2022.viewModel.MainViewModel

class MainViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}