package ru.clinri.app2dolist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "to_do_list_names")
data class ToDoListNames(
    @PrimaryKey(autoGenerate = true) //идентификатор
    val id: Int?,

    @ColumnInfo(name = "name") //данные
    val name: String,

    @ColumnInfo(name = "time") //данные
    val time: String,

    @ColumnInfo(name = "allItemCount") //данные
    val allItemCounter: Int,

    @ColumnInfo(name = "checkedItemsCounter") //данные
    val checkedItemsCounter: Int,

    @ColumnInfo(name = "itemsIds") //данные
    val itemsId: String
) : Serializable // для передачи в друге Активити
