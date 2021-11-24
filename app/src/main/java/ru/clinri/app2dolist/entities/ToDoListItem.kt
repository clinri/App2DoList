package ru.clinri.app2dolist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_List_item")
data class ToDoListItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "itemInfo")
    val itemInfo: String?,
    @ColumnInfo(name = "itemChecked")
    val itemChecked: Int = 0,
    @ColumnInfo(name = "listId") // ид списка которому пренадлежит элемент
    val listId: Int,
    @ColumnInfo(name = "itemType")
    val itemType: Int = 0
)
