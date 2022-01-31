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
import net.neonlotus.activities2022.R
import net.neonlotus.activities2022.adapter.NoteRecyclerAdapter
import net.neonlotus.activities2022.factory.MainViewModelFactory
import net.neonlotus.activities2022.model.Blog
import net.neonlotus.activities2022.realm.Task
import net.neonlotus.activities2022.viewModel.MainViewModel

/*
Libraries
   Koin (dependency injection)
   Retrofit (Networking)
   Realm (On Device Storage)            https://docs.mongodb.com/realm/sdk/android/

Kotlin Topics
   Coroutines
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

    private val blogListViewModel by viewModels<MainViewModel> {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Realm WIP example stuff
        Realm.init(this) // context, usually an Activity or Application
        val realmName: String = "My Project"
        val config = RealmConfiguration.Builder().name(realmName).build()
        val backgroundThreadRealm: Realm = Realm.getInstance(config)

        val task: Task = Task()
        task.name = "New Task"



        backgroundThreadRealm.executeTransaction { transactionRealm ->
            transactionRealm.insert(task)
        }

        // all tasks in the realm
        val tasks: RealmResults<Task> = backgroundThreadRealm.where<Task>().findAll()

        Log.d("ryan", "Realm read size " + tasks.size)

        //END Realm WIP example stuff

        mainrecycler = findViewById(R.id.recycler_view)
        but = findViewById(R.id.button)

        but.setOnClickListener {
            addData()
        }

        blogList = arrayListOf()


        adapter = NoteRecyclerAdapter(blogListViewModel, blogList, this)
        initializeList() //todo with name and stuff, this is just messing around for now

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
