package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.johan.johanapp.R
import br.com.johan.johanapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_create_note.*


class CreateNoteActivity : AppCompatActivity() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        create_note_cancel.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
            finish()
        }

        create_note_save.setOnClickListener {
            createNote()
            startActivity(Intent(this, NoteActivity::class.java))
            finish()
        }
    }

    private fun createNote(){
        val noteTitle = create_note_title.editText?.text.toString().trim()
        val noteBody = create_note_body.editText?.text.toString().trim()
        val noteOffline = create_note_offline.isChecked

        mNoteViewModel.doCreateNote(noteTitle, noteBody, noteOffline )
    }
}
