package ru.clinri.app2dolist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.clinri.app2dolist.activities.MainApp
import ru.clinri.app2dolist.databinding.FragmentToDoListNamesBinding
import ru.clinri.app2dolist.db.MainViewModel
import ru.clinri.app2dolist.db.ToDoListNamesAdapter
import ru.clinri.app2dolist.dialogs.DeleteDialog
import ru.clinri.app2dolist.dialogs.NewListDialog
import ru.clinri.app2dolist.entities.ToDoListName
import ru.clinri.app2dolist.utils.TimeManager


class ToDoListNamesFragment : BaseFragment(), ToDoListNamesAdapter.Listener {
    private lateinit var binding: FragmentToDoListNamesBinding
    private lateinit var adapter: ToDoListNamesAdapter

    private val mainVeiwModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)

    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity,
        object : NewListDialog.Listener{
            override fun onClick(name: String) {
                Log.d("MyLog", "Name: $name")
                val toDoListName = ToDoListName(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainVeiwModel.insertToDoListName(toDoListName)
            }
        },"")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ToDoListNamesAdapter(this@ToDoListNamesFragment)
        rcView.adapter = adapter

    }

    private fun observer() { //будет следить за изменениями в БД
        mainVeiwModel.allToDoListNames.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = ToDoListNamesFragment()
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener{
            override fun onClick() {
                mainVeiwModel.deleteToDoListName(id)
            }
        })
    }

    override fun editItem(toDoListName: ToDoListName) {
        NewListDialog.showDialog(activity as AppCompatActivity,
            object : NewListDialog.Listener{
                override fun onClick(name: String) {
                    mainVeiwModel.updateToDoListName(toDoListName.copy(name = name))
                }
            }, toDoListName.name)
    }

    override fun onClickItem(toDoListName: ToDoListName) {

    }

}