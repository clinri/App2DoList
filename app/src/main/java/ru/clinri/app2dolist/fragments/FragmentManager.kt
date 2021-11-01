package ru.clinri.app2dolist.fragments

import androidx.appcompat.app.AppCompatActivity
import ru.clinri.app2dolist.R

object FragmentManager { // object позволяет использовать функцию без инициализации класса.
    var currentFrag: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }

}