package ru.clinri.app2dolist.activities

import android.app.Application
import ru.clinri.app2dolist.db.MainDataBase

class MainApp : Application(){
    val database by lazy {MainDataBase.getDataBase(this)}
}