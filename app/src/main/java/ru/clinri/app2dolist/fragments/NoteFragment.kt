package ru.clinri.app2dolist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import ru.clinri.app2dolist.activities.MainApp
import ru.clinri.app2dolist.activities.NewNoteActiviti
import ru.clinri.app2dolist.databinding.FragmentNoteBinding
import ru.clinri.app2dolist.db.MainViewModel


class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    private val mainVeiwModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)

    }

    override fun onClickNew(){
        editLauncher.launch(Intent(activity, NewNoteActiviti:: class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onEditResult(){
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                Log.d("MyLog", "discription: ${it.data?.getStringExtra(DISK_KEY)}")
            }

        }
    }

    companion object {
        const val TITLE_KEY = "title_key"
        const val DISK_KEY = "disk_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}