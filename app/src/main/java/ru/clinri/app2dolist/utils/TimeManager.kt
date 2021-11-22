package ru.clinri.app2dolist.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}