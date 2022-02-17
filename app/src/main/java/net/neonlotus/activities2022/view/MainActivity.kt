package net.neonlotus.activities2022.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.neonlotus.activities2022.R
import net.neonlotus.activities2022.databinding.ActivityMainBinding

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
   Live Data
   MVVM
 */

//https://github.com/CatalinStefan/mvvm-cities/blob/main/app/src/main/java/com/catalin/mvvm_cities/viewmodel/CityViewModel.kt
//https://developer.android.com/codelabs/kotlin-android-training-quality-and-states#5
//https://github.com/google-developer-training/android-kotlin-fundamentals-apps/blob/master/TrackMySleepQualityFinal/app/src/main/java/com/example/android/trackmysleepquality/sleepquality/SleepQualityViewModel.kt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "ryann"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        binding.btnAdd.setOnClickListener{
            //Toast.makeText(this,"FIRST", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
        binding.btnBooks.setOnClickListener{
            //Toast.makeText(this,"SECOND", Toast.LENGTH_SHORT).show()

        }
        binding.btnGames.setOnClickListener{
            //Toast.makeText(this,"THIRD", Toast.LENGTH_SHORT).show()

        }
        binding.btnMovies.setOnClickListener{
            //Toast.makeText(this,"FOURTH", Toast.LENGTH_SHORT).show()

        }
        binding.btnShows.setOnClickListener{
            //Toast.makeText(this,"FIFTH", Toast.LENGTH_SHORT).show()

        }
    }

}