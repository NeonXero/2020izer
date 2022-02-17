package net.neonlotus.activities2022.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.neonlotus.activities2022.realm.RealmQuote
import net.neonlotus.activities2022.retrofitexample.QuoteObject
import net.neonlotus.activities2022.retrofitexample.QuotesApi
import net.neonlotus.activities2022.retrofitexample.RetrofitHelper

class MainViewModel : ViewModel() {
    var lst = MutableLiveData<ArrayList<QuoteObject>>()
    var newlist = arrayListOf<QuoteObject>()

    val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)

    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun add(blog: QuoteObject) {
        newlist.add(blog)
        lst.value = newlist
    }

    fun remove(blog: QuoteObject) {
        newlist.remove(blog)
        lst.value = newlist
    }

    fun getQuotes(): ArrayList<QuoteObject>? {
        viewModelScope.launch {

            val result = quotesApi.getQuotes().body()
            if (result != null) {
                result.results.forEach {

                    newlist.add(it)
                }

                lst.value = newlist
            }


        }

        return lst.value ?: arrayListOf()
    }

//    fun getQuotesFromDB(db: RealmResults<RealmQuote>): ArrayList<QuoteObject>? {
//
//
//        viewModelScope.launch {
//            val result = db
//            if (result != null) {
//                result.forEach {
//                    var quotez = QuoteObject(it)
//                    newlist.add(quotez)
//                }
//
//                lst.value = newlist
//            }
//        }
//
//        return lst.value ?: arrayListOf()
//    }

//    fun getQuotesFromDB() {
//        return realm.dao().getKart()
//    }

}