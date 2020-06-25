package br.com.johan.johanapp.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import br.com.johan.johanapp.service.model.Note
import br.com.johan.johanapp.service.repository.FirebaseAuth
import br.com.johan.johanapp.service.repository.FirebaseRealtime
import br.com.johan.johanapp.view.NoteBody


class NoteViewModel : ViewModel() {

    private val mFirebaseRealtime = FirebaseRealtime()
    private val mUserID = FirebaseAuth().getUserID()

    private var mNotesOffline = MutableLiveData<ArrayList<Note>>()
    val notesOffline: LiveData<ArrayList<Note>> = mNotesOffline

    private var mNotes = MutableLiveData<ArrayList<Note>>()
    val notes: LiveData<ArrayList<Note>> = mNotes

    private var mIntent = MutableLiveData<Intent>()
    val intent: LiveData<Intent> = mIntent


    init {
        // create user key or inicialize
        mFirebaseRealtime.createNotesKey(mUserID)

        doCallNotes()
    }

    private fun doCallNotes() {
        mFirebaseRealtime.callNotes(true, object : FirebaseListener<ValidationListener> {
            override fun callBack(result: ValidationListener) {
                fillListNotes()
            }
        })
    }

    private fun fillListNotes(){
        val sNotesOff = arrayListOf<Note>()
        val sNotes = arrayListOf<Note>()

        mFirebaseRealtime.notesList.forEach {
            if(it.offline){
                sNotesOff.add(it)
            }else{
                sNotes.add(it)
            }
        }

        mNotesOffline.value = sNotesOff
        mNotes.value = sNotes
    }

    fun doCreateNote(noteTitle: String, noteBody: String, noteOffline: Boolean) {
        val note = Note(noteTitle, noteBody, noteOffline, "")
        mFirebaseRealtime.createNote(note)
    }

    fun doUpdateNote(note: Note) {
        if (note.noteKey != "") {
            mFirebaseRealtime.updateNote(note, note.noteKey)
        }
    }

    fun doDeleteNote(noteKey: String?){
        if (noteKey != null) {
            mFirebaseRealtime.deleteNote(noteKey)
        }
    }

    fun selectNote(position: Int, applicationContext: Context){
        val mNotesOfflineSize = mNotesOffline.value?.size!!

        val notePosition: Int
        val note: Note
        val intent: Intent

        if (position <= mNotesOfflineSize){
            notePosition = position - 1

            note = notesOffline.value!![notePosition]
            intent = Intent(applicationContext, NoteBody::class.java).putExtra("note", note)
            mIntent.value = intent

        }else{
            notePosition = position - mNotesOfflineSize - 2

            note = notes.value!![notePosition]
            intent = Intent(applicationContext, NoteBody::class.java).putExtra("note", note)
            mIntent.value = intent
        }

    }

}