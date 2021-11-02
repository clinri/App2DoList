package ru.clinri.app2dolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ActivityNewNoteBinding
import ru.clinri.app2dolist.fragments.NoteFragment

class NewNoteActiviti : AppCompatActivity() {
    private lateinit var binding:ActivityNewNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.id_save){
            setMainResult()
        }else if(item.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult(){
        val i = Intent().apply {
            putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
            putExtra(NoteFragment.DISK_KEY, binding.edDiscription.text.toString())
        }
        setResult(RESULT_OK,i)
        finish()
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)

    }
}