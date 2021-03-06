package ru.clinri.app2dolist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import ru.clinri.app2dolist.R
import ru.clinri.app2dolist.databinding.ActivityNewNoteBinding
import ru.clinri.app2dolist.entities.NoteItem
import ru.clinri.app2dolist.fragments.NoteFragment
import ru.clinri.app2dolist.utils.HtmlManager
import ru.clinri.app2dolist.utils.MyTouchListener
import ru.clinri.app2dolist.utils.TimeManager
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
        init()
        onClickColorPicker()
        actionMenuCallback()
    }

    private fun onClickColorPicker() = with(binding) {
        ibRed.setOnClickListener {
            setColorForSelectedText(R.color.picker_red)
        }
        ibBlack.setOnClickListener {
            setColorForSelectedText(R.color.picker_black)
        }
        ibBlue.setOnClickListener {
            setColorForSelectedText(R.color.picker_blue)
        }
        ibYellow.setOnClickListener {
            setColorForSelectedText(R.color.picker_yellow)
        }
        ibGreen.setOnClickListener {
            setColorForSelectedText(R.color.picker_green)
        }
        ibOrange.setOnClickListener {
            setColorForSelectedText(R.color.picker_orange)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        edTitle.setText(note?.title)
        edDiscription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save) {
            setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.id_bold) {
            setBoldForSelectedText()
        } else if (item.itemId == R.id.id_color) {
            if (binding.colorPicker.isShown) {
                closeColorPicker()
            } else {
                openColorPicker()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDiscription.selectionStart
        val endPos = edDiscription.selectionEnd
        // ?????? ???????????? ???????????? ?? ???????????? ??????????????
        val styles = edDiscription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        //???????? ???????????? ?????????? ??????, ???? ??????????????, ?????????? ????????????????
        if (styles.isNotEmpty()) {
            edDiscription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        edDiscription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDiscription.text.trim()
        edDiscription.setSelection(startPos)
    }

    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPos = edDiscription.selectionStart
        val endPos = edDiscription.selectionEnd
        //?????? ???????????? ???????????? ?? ????????????
        val styles = edDiscription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)

        // ???????? ???????? ?????? ?????? ???? ???????????? ?????? ??????????????
        if (styles.isNotEmpty()) edDiscription.text.removeSpan(styles[0])

        edDiscription.text.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(this@NewNoteActivity, colorId)
            ),
            startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        edDiscription.text.trim()
        edDiscription.setSelection(startPos)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem? = if (note == null) {
            createNewNote()
        } else {
            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
//            putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
//            putExtra(NoteFragment.DISK_KEY, binding.edDiscription.text.toString())
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote(): NoteItem? = with(binding) {
        return note?.copy(
            title = edTitle.text.toString(),
            content = HtmlManager.toHTML(edDiscription.text)
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHTML(binding.edDiscription.text),
            TimeManager.getCurrentTime(),
            ""
        )

    }

    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun actionMenuCallback() {
        val actionCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }
            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }
            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }
            override fun onDestroyActionMode(p0: ActionMode?) {

            }
        }
        binding.edDiscription.customSelectionActionModeCallback = actionCallback
    }

}