package ru.clinri.app2dolist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.entities.ToDoListItem
import ru.clinri.app2dolist.entities.ToDoListName

@Dao
interface Dao {
    @Query("SELECT * FROM note_List") // считывание из БД
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM to_do_list_names") // считывание из БД
    fun getAllToDoListNames(): Flow<List<ToDoListName>>

    @Query("SELECT * FROM todo_List_item WHERE listId LIKE :listId") // считывание из БД
    fun getAllToDoListItems(listId: Int): Flow<List<ToDoListItem>>

    @Query("DELETE FROM note_List WHERE id IS :id") // удаление из БД
    suspend fun deleteNote(id: Int) // suspend позволяет делать операции из Coroutines

    @Query("DELETE FROM to_do_list_names WHERE id IS :id") // удаление из БД
    suspend fun deleteToDoListName(id: Int) // suspend позволяет делать операции из Coroutines

    @Insert // запись в БД
    suspend fun insertNote(note: NoteItem)

    @Insert // запись в БД
    suspend fun insertToDoListName(listName: ToDoListName)

    @Insert // запись в БД
    suspend fun insertToDoItem(toDoListItem: ToDoListItem)

    @Update // перезапись
    suspend fun updateNote(note: NoteItem)

    @Update // перезапись
    suspend fun updateToDoListName(toDoListName: ToDoListName)
}