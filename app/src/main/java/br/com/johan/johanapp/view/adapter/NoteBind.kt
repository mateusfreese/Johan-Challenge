package br.com.johan.johanapp.view.adapter

import br.com.johan.johanapp.R
import br.com.johan.johanapp.service.model.Note
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_note.*

class NoteBind(private val note: Note) : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.item_note_text.text = note.title
    }

    override fun getLayout() = R.layout.item_note
}