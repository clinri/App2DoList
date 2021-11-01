package ru.clinri.app2dolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ActivityMainBinding
import ru.clinri.app2dolist.fragments.FragmentManager
import ru.clinri.app2dolist.fragments.NoteFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings ->{
                    Log.d("MyLog","Settings")
                }
                R.id.notes->{
                    FragmentManager.setFragment(NoteFragment.newInstance(),this)
                    Log.d("MyLog","Notes")
                }
                R.id.to_do_list ->{
                    Log.d("MyLog","List")
                }
                R.id.new_item ->{
                    Log.d("MyLog","New")
                }
            }
            true
        }
    }
}