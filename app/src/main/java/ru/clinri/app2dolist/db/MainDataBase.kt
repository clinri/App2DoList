package ru.clinri.app2dolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.clinri.app2dolist.entities.LibraryItem
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.entities.ToDoListItem
import ru.clinri.app2dolist.entities.ToDoListName

@Database(
    entities = [LibraryItem::class,
        NoteItem::class,
        ToDoListItem::class,
        ToDoListName::class
    ], version = 1
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun getDao():Dao
    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "to_do_list.db"
                ).build()
                instance
            }
        }
    }
}