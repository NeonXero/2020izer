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
import net.neonlotus.activities2022.model.Blog
import net.neonlotus.activities2022.realm.Task
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
    private var blogList = arrayListOf<Blog>()
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

        val task = Task()
        task.name = "New Task"
        val task2 = Task()
        task2.name = "second task"

        //TODO figure out a better way to Realm.getInstance
        // couldn't access on different threads or blah blah when doing it other way
        GlobalScope.launch(Dispatchers.IO) {
            Realm.getInstance(config).executeTransaction { transactionRealm ->
                transactionRealm.insertOrUpdate(task)
                transactionRealm.insertOrUpdate(task2)
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            val tasks: RealmResults<Task> = Realm.getInstance(config).where<Task>().findAll()
            Log.d(
                "ryan",
                "Realm read size " + tasks.size
            ) //THINK IT WORKS - not quite .. runs after insert
            //TODO make sure it runs after insert. Not sure if this is even a real world example - but either way, figure it out
        }

        //END Realm WIP example stuff


        //Retrofit example stuff ; WORKS - let's try to update the list with quotes :D issues updating UI thread stuff
        //I made some changes to use view model stuff, works well!
        blogListViewModel.getQuotes()
        //END Retrofit example stuff

        mainrecycler = findViewById(R.id.recycler_view)
        but = findViewById(R.id.button)

        but.setOnClickListener {
            addData()
        }

        blogList = arrayListOf()


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
            Log.d("data", it.toString()) //always the full list
            // D/data: [Blog(title=zzz, author=temp), Blog(title=sadt, author=temp), Blog(title=trtrtrtr, author=temp)]
            mainrecycler.adapter = NoteRecyclerAdapter(blogListViewModel, it, this)
        })
    }

    private fun addData() {
        var txtplce = findViewById<EditText>(R.id.titletxt)
        var title = txtplce.text.toString()
        if (title.isNullOrBlank()) {
            Toast.makeText(this, "Enter value!", Toast.LENGTH_SHORT).show()
        } else {
            var blog = Blog(title, "temp")
            blogListViewModel.add(blog)
            txtplce.text.clear()
            mainrecycler.adapter?.notifyDataSetChanged()
        }
    }
}