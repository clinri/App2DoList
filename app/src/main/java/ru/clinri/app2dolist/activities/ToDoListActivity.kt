package ru.clinri.app2dolist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ActivityToDoListBinding
import ru.clinri.app2dolist.db.MainViewModel
import ru.clinri.app2dolist.db.ToDoListNameItemAdapter
import ru.clinri.app2dolist.entities.ToDoListItem
import ru.clinri.app2dolist.entities.ToDoListName

class ToDoListActivity : AppCompatActivity(), ToDoListNameItemAdapter.Listener {
    private lateinit var binding: ActivityToDoListBinding

    //берем элемент названия списка чтобы
    // получить оттуда идентификаторы элементов этого списка
    private var toDoListName: ToDoListName? = null

    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null

    private var adapter: ToDoListNameItemAdapter? = null


    private val mainVeiwModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initRcView()
        listItemObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.to_do_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        edItem = newItem.actionView.findViewById(R.id.edNewToDoItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item) {
            addNewToToItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewToToItem() {
        if (edItem?.text.toString().isEmpty()) return
        val item = ToDoListItem(
            null,
            edItem?.text.toString(),
            null,
            0,
            toDoListName?.id!!,
            0
        )
        edItem?.setText("")
        mainVeiwModel.insertToDoListItem(item)
    }

    private fun listItemObserver() {
        mainVeiwModel.getAllItemsFromList(toDoListName?.id!!).observe(this, {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun initRcView() = with(binding) {
        adapter = ToDoListNameItemAdapter(this@ToDoListActivity)
        rcViev.layoutManager = LinearLayoutManager(this@ToDoListActivity)
        rcViev.adapter = adapter
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu() //перерисовать меню
                return true
            }

        }

    }

    private fun init() {
        toDoListName = intent.getSerializableExtra(TO_DO_LIST_NAME) as ToDoListName

    }

    companion object {
        const val TO_DO_LIST_NAME = "to_do_list_names"
    }

    override fun deleteItem(id: Int) {

    }

    override fun editItem(toDoListName: ToDoListName) {

    }

    override fun onClickItem(toDoListName: ToDoListName) {

    }
}