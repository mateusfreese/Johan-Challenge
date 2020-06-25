package br.com.johan.johanapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.johan.johanapp.R
import br.com.johan.johanapp.service.constants.Constants.FIREBASE.REALTIME
import br.com.johan.johanapp.view.adapter.ExpandableHeader
import br.com.johan.johanapp.view.adapter.NoteBind
import br.com.johan.johanapp.viewmodel.NoteViewModel
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    private lateinit var mNotesOffline: MutableList<NoteBind>
    private lateinit var mNotes: MutableList<NoteBind>

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        mNotes = mutableListOf()
        mNotesOffline = mutableListOf()

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        fab.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
            finish()
        }

        createObservers()
    }

    private fun groupieAdapter(){
        Log.i(REALTIME, "groupieAdapte Started")
        val groupieAdapter = GroupAdapter<GroupieViewHolder>()

        note_recycler_view.apply {
            layoutManager = GridLayoutManager(this@NoteActivity, groupieAdapter.spanCount).apply {
                spanSizeLookup = groupieAdapter.spanSizeLookup
            }

            adapter = groupieAdapter
        }

        ExpandableGroup(ExpandableHeader("Notas Favoritas"), true).apply {
            add(Section(mNotesOffline))
            groupieAdapter.add(this)

        }

        ExpandableGroup(ExpandableHeader("Notas"), true).apply{
            add(Section(mNotes))
            notifyChanged()

            groupieAdapter.add(this)
        }

        groupieAdapter.setOnItemClickListener { item, _ ->
            val position = groupieAdapter.getAdapterPosition(item)
            mNoteViewModel.selectNote(position, applicationContext)
        }

    }

    private fun createObservers() {
        mNoteViewModel.notesOffline.observe(this, Observer {
            it.forEach {note ->
                mNotesOffline.add(NoteBind(note))
            }

            groupieAdapter()
        })

        mNoteViewModel.notes.observe(this, Observer {
            it.forEach {note ->
                mNotes.add(NoteBind(note))
            }

            groupieAdapter()
        })

        mNoteViewModel.intent.observe(this, Observer {
            startActivity(it)
            finish()
        })
    }
}
