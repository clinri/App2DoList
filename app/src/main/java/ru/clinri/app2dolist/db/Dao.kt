package ru.clinri.app2dolist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.clinri.app2dolist.entities.NoteItem

@Dao
interface Dao {
    @Query("SELECT * FROM note_List") // считывание из БД
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("DELETE FROM note_List WHERE id IS :id") // считывание из БД
    suspend fun deleteNote(id: Int) // suspend позволяет делать операции из Corutines

    @Insert // запись в БД
    suspend fun insertNote(note: NoteItem)
}