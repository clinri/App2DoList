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
import androidx.recyclerview.widget.LinearLayoutManager
import ru.clinri.app2dolist.activities.MainApp
import ru.clinri.app2dolist.activities.NewNoteActiviti
import ru.clinri.app2dolist.databinding.FragmentNoteBinding
import ru.clinri.app2dolist.db.MainViewModel
import ru.clinri.app2dolist.db.NoteAdapter
import ru.clinri.app2dolist.entities.NoteItem


class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter()
        rcViewNote.adapter = adapter
    }

    private fun observer(){ //будет следить за изменениями в БД
        mainVeiwModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onEditResult(){
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                //Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                //Log.d("MyLog", "discription: ${it.data?.getStringExtra(DISK_KEY)}")
                mainVeiwModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
            }

        }
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}