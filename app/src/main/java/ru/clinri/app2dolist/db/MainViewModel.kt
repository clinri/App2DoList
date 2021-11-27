package ru.clinri.app2dolist.db

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.entities.ToDoListItem
import ru.clinri.app2dolist.entities.ToDoListName

class MainViewModel(database: MainDataBase) : ViewModel() {
    val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allToDoListNames: LiveData<List<ToDoListName>> = dao.getAllToDoListNames().asLiveData()

    fun getAllItemsFromList(listId: Int): LiveData<List<ToDoListItem>>{
        return dao.getAllToDoListItems(listId).asLiveData()
    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertToDoListName(listName: ToDoListName) = viewModelScope.launch {
        dao.insertToDoListName(listName)
    }

    fun insertToDoListItem(listItem: ToDoListItem) = viewModelScope.launch {
        dao.insertToDoItem(listItem)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateToDoListName (toDoListName: ToDoListName) = viewModelScope.launch {
        dao.updateToDoListName(toDoListName)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteToDoListName(id: Int) = viewModelScope.launch {
        dao.deleteToDoListName(id)
    }

    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}