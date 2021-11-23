package ru.clinri.app2dolist.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.clinri.app2dolist.databinding.ActivityToDoListBinding
import ru.clinri.app2dolist.db.MainViewModel
import ru.clinri.app2dolist.entities.ToDoListName

class ToDoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToDoListBinding

    //берем элемент названия списка чтобы
    // получить оттуда идентификаторы элементов этого списка
    private var toDoListName: ToDoListName? = null

    private val mainVeiwModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        toDoListName = intent.getSerializableExtra(TO_DO_LIST_NAME) as ToDoListName
        binding.tvTest.text = toDoListName?.name
    }

    companion object {
        const val TO_DO_LIST_NAME = "to_do_list_names"
    }
}