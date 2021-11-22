package ru.clinri.app2dolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ActivityMainBinding
import ru.clinri.app2dolist.dialogs.NewListDialog
import ru.clinri.app2dolist.fragments.FragmentManager
import ru.clinri.app2dolist.fragments.NoteFragment
import ru.clinri.app2dolist.fragments.ToDoListNamesFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentManager.setFragment(ToDoListNamesFragment.newInstance(),this)
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
                    FragmentManager.setFragment(ToDoListNamesFragment.newInstance(),this)
                    Log.d("MyLog","List")
                }
                R.id.new_item ->{
                    FragmentManager.currentFrag?.onClickNew()
                    //NewListDialog.showDialog(this,this)
                    Log.d("MyLog","New")
                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog","List name: $name")
    }
}