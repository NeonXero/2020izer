package net.neonlotus.activities2022.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.neonlotus.activities2022.R
import net.neonlotus.activities2022.adapter.NoteRecyclerAdapter
import net.neonlotus.activities2022.factory.MainViewModelFactory
import net.neonlotus.activities2022.realm.RealmQuote
import net.neonlotus.activities2022.retrofitexample.QuoteObject
import net.neonlotus.activities2022.viewModel.MainViewModel

/*
Libraries
   Koin (dependency injection)
   Retrofit (Networking) ; mostly the same, viewmodel, gg etc ; working on it
   Realm (On Device Storage)            https://docs.mongodb.com/realm/sdk/android/

Kotlin Topics
   Coroutines ; working on it
   Extension Functions
   Sealed Classes

Android
   Live Data ; working on it
   MVVM ; working on it
 */

class MainActivity : AppCompatActivity() {

    private var viewManager = LinearLayoutManager(this)
    private var adapter: NoteRecyclerAdapter? = null
    private var blogList = arrayListOf<QuoteObject>()
    private lateinit var mainrecycler: RecyclerView
    private lateinit var but: Button

    lateinit var realmName: String
    lateinit var config: RealmConfiguration
    // just doing Realm.getInstance(config) for whenever I need to reference it?
    // lateinit var backgroundThreadRealm: Realm

    private val blogListViewModel by viewModels<MainViewModel> {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Realm WIP example stuff
        initRealm()


//        GlobalScope.launch(Dispatchers.IO) {
//            Realm.getInstance(config).executeTransaction { transactionRealm ->
//                transactionRealm.deleteAll()
//            }
//        }


        //TODO figure out a better way to Realm.getInstance
        // couldn't access on different threads or blah blah when doing it other way
//        GlobalScope.launch(Dispatchers.IO) {
//            Realm.getInstance(config).executeTransaction { transactionRealm ->
//                transactionRealm.insertOrUpdate(task)
//                transactionRealm.insertOrUpdate(task2)
//            }
//        }

        GlobalScope.launch(Dispatchers.IO) {
            val tasks: RealmResults<RealmQuote> =
                Realm.getInstance(config).where<RealmQuote>().findAll()
            //Log.d("ryan", "Realm read size " + tasks.size) //THINK IT WORKS - not quite .. runs after insert

            if (tasks.size > 0) {
                Log.d("ryan", "use db items")
                //blogListViewModel.getQuotesFromDB(tasks)
            } else {
                Log.d("ryan", "use api items")
                blogListViewModel.getQuotes()
            }

            //TODO make sure it runs after insert. Not sure if this is even a real world example - but either way, figure it out
        }

        //END Realm WIP example stuff


        //Retrofit example stuff ; WORKS - let's try to update the list with quotes :D issues updating UI thread stuff
        //I made some changes to use view model stuff, works well!
        //blogListViewModel.getQuotes()
        //END Retrofit example stuff

        mainrecycler = findViewById(R.id.recycler_view)
        but = findViewById(R.id.button)

        but.setOnClickListener {
            addData()
        }



        Log.d("ryan", "passing in size ${blogList.size}")
        adapter = NoteRecyclerAdapter(blogListViewModel, blogList, this)
        initializeList() //todo with name and stuff, this is just messing around for now

    }

    fun initRealm() {
        Realm.init(this) // context, usually an Activity or Application

        realmName = "My Project"
        config = RealmConfiguration.Builder().name(realmName).build()
//        backgroundThreadRealm = Realm.getInstance(config)
    }

    private fun initializeList() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager
        observeData()

    }

    private fun observeData() {
        blogListViewModel.lst.observe(this, Observer {
            //Log.d("data", it.toString()) //always the full list
            Log.d("ryan", "observe data size ${blogListViewModel.lst.value?.size}")
            // D/data: [Blog(title=zzz, author=temp), Blog(title=sadt, author=temp), Blog(title=trtrtrtr, author=temp)]
            mainrecycler.adapter = NoteRecyclerAdapter(blogListViewModel, it, this)

            saveToRealm(blogListViewModel.lst.value)
        })
    }

    private fun saveToRealm(data: ArrayList<QuoteObject>?) {
        GlobalScope.launch(Dispatchers.IO) {
            Realm.getInstance(config).executeTransaction { transactionRealm ->
                data?.forEach {
                    val realmQuote = RealmQuote()
                    realmQuote.author = it.author
                    realmQuote.content = it.content
                    realmQuote.id = it._id
                    realmQuote.length = it.length
                    Log.d("ryan", "donger ${it.author}")

                    transactionRealm.insertOrUpdate(realmQuote)
                }
            }
        }
    }

    private fun addData() {
        var txtplce = findViewById<EditText>(R.id.titletxt)
        var title = txtplce.text.toString()
        if (title.isNullOrBlank()) {
            Toast.makeText(this, "Enter value!", Toast.LENGTH_SHORT).show()
        } else {
            var quotez = QuoteObject(
                "id",
                "author",
                "slug",
                "content",
                "dateadded",
                "datemod",
                1,
                listOf("tag1", "tag2")
            )
            blogListViewModel.add(quotez)
            txtplce.text.clear()
            mainrecycler.adapter?.notifyDataSetChanged()
        }
    }
}