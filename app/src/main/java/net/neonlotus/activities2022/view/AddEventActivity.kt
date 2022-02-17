package net.neonlotus.activities2022.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import net.neonlotus.activities2022.databinding.ActivityAddSomethingBinding

class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSomethingBinding
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSomethingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        binding.etName.visibility = GONE
        binding.etPlatform.visibility = GONE
        binding.btnAddEntry.isEnabled = false

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: View = radioGroup.findViewById(i)
            val idx: Int = radioGroup.indexOfChild(radioButton)
            //could have just referenced the R.id stuff I guess, oh well
            Log.d(TAG,"radio changed " + idx)
            //0 game 1 show 2 movie 3 book

            when (idx) {
                0 -> { //game
                    binding.etName.visibility = VISIBLE
                    binding.etPlatform.visibility = VISIBLE
                    binding.etPlatform.hint = "Platform"
                }
                1 -> { //show
                    binding.etName.visibility = VISIBLE
                    binding.etPlatform.visibility = GONE
                }
                2 -> { //movie
                    binding.etName.visibility = VISIBLE
                    binding.etPlatform.visibility = GONE
                }
                3 -> { //book
                    binding.etName.visibility = VISIBLE
                    binding.etPlatform.visibility = VISIBLE
                    binding.etPlatform.hint = "Author"
                }
            }
        }


        binding.btnAddEntry.setOnClickListener {
            Log.d(TAG, "add it")
        }

    }
}