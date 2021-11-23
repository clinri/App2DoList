package ru.clinri.app2dolist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.entities.ToDoListName

@Dao
interface Dao {
    @Query("SELECT * FROM note_List") // считывание из БД
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM to_do_list_names") // считывание из БД
    fun getAllToDoListNames(): Flow<List<ToDoListName>>

    @Query("DELETE FROM note_List WHERE id IS :id") // удаление из БД
    suspend fun deleteNote(id: Int) // suspend позволяет делать операции из Corutines

    @Query("DELETE FROM to_do_list_names WHERE id IS :id") // удаление из БД
    suspend fun deleteToDoListName(id: Int) // suspend позволяет делать операции из Corutines


    @Insert // запись в БД
    suspend fun insertNote(note: NoteItem)

    @Insert // запись в БД
    suspend fun insertToDoListName(listName: ToDoListName)

    @Update // перезапись
    suspend fun updateNote(note: NoteItem)
}