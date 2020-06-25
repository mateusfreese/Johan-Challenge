package br.com.johan.johanapp.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.johan.johanapp.service.constants.Constants.FIREBASE.REALTIME
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import br.com.johan.johanapp.service.model.Note
import com.google.firebase.database.*

class FirebaseRealtime {

    // Firebase database -> root reference
    private val mRealtime: DatabaseReference = FirebaseDatabase.getInstance().reference

    private lateinit var mNotesReference: DatabaseReference

    val notesList: MutableList<Note> = mutableListOf()

    fun createNotesKey(userId: String) {
        mNotesReference = mRealtime.child("users").child(userId).child("notes")
        Log.i(REALTIME, "mNotes reference take key: $mNotesReference")

    }

    fun createNote(note: Note){
        // generate a note id
        val key = mNotesReference.push().key!!

         note.noteKey = key

        // create note
        mNotesReference.child(key).setValue(note).addOnCompleteListener {

        }

        Log.i(REALTIME, "Nota criada")
    }


    fun deleteNote(noteReference: String) {
        mNotesReference.child(noteReference).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(REALTIME, "Nota excluida -> $noteReference")
            }

        }

    }

    fun updateNote(note: Note, noteId: String) {
        mNotesReference.child(noteId).setValue(note).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(REALTIME, "Nota atualizada ")
            }
        }
    }


    fun callNotes(listener: Boolean, callback: FirebaseListener<ValidationListener>) {

        val notesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val notes = dataSnapshot.value
                Log.i(REALTIME, "Listener: onDataChange -> $notes")

                dataSnapshot.children.mapNotNullTo(notesList) {
                    it.getValue(Note::class.java)
                }

                Log.i(REALTIME, "NotesList add to mNotes")
                callback.callBack(ValidationListener())
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.i(REALTIME, "Listener: onCancelled")
            }
        }

        if (listener) {
            mNotesReference.addValueEventListener(notesListener)
        } else {
            mNotesReference.addListenerForSingleValueEvent(notesListener)
        }
    }

}



