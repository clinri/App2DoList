package ru.clinri.app2dolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.clinri.app2dolist.activities.MainApp
import ru.clinri.app2dolist.activities.NewNoteActiviti
import ru.clinri.app2dolist.databinding.FragmentNoteBinding
import ru.clinri.app2dolist.db.MainViewModel


class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    private val mainVeiwModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)

    }

    override fun onClickNew(){
        startActivity(Intent(activity, NewNoteActiviti:: class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}