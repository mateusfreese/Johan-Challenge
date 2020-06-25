package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.johan.johanapp.R
import br.com.johan.johanapp.service.model.Note
import br.com.johan.johanapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note_body.*



class NoteBody: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_body)

        val note = intent.getSerializableExtra("note") as Note

        setNoteValues(note)

        body_note_exclude.setOnClickListener {
            delete(note.noteKey)
            startActivity(Intent(this, NoteActivity::class.java))
            finish()
            Toast.makeText(applicationContext, "Anotação Excluida!", Toast.LENGTH_SHORT).show()
        }

        body_note_save.setOnClickListener {
            updateNote(note.noteKey)
            startActivity(Intent(this, NoteActivity::class.java))
            finish()
        }

    }

    private fun delete(noteKey: String?) {
        NoteViewModel().doDeleteNote(noteKey)
    }

    private fun setNoteValues(note: Note){
        body_note_title.editText!!.setText(note.title)
        body_note_body.editText!!.setText(note.body)
        body_note_offline.isChecked = note.offline
    }

    private fun updateNote(noteKey: String){
        val body= body_note_body.editText!!.text.toString()
        val title = body_note_title.editText!!.text.toString()
        val offline = body_note_offline.isChecked

        val note = Note(title, body, offline, noteKey)
        note.noteKey = noteKey

        NoteViewModel().doUpdateNote(note)
    }
}
