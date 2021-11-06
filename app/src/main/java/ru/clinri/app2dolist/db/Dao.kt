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
    @Insert // запись в БД
    suspend fun insertNote(note: NoteItem)
}