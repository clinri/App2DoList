package ru.clinri.app2dolist.db

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.entities.ToDoListName

class MainViewModel(database: MainDataBase) : ViewModel() {
    val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allToDoListNames: LiveData<List<ToDoListName>> = dao.getAllToDoListNames().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertToDoListName(listName: ToDoListName) = viewModelScope.launch {
        dao.insertToDoListName(listName)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
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