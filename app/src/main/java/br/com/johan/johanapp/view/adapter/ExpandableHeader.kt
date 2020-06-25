package br.com.johan.johanapp.view.adapter

import br.com.johan.johanapp.R
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_note_header.*

class ExpandableHeader(private val title: String) : Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.item_note_Expandable_header_title.text = title

        // Setar a imagem
        viewHolder.item_note_Expandable_header_icon.setImageResource(getRotatedIconResId())

        // Setar o ouvinte de click do cabeçalho
        viewHolder.item_note_Expandable_header_root.setOnClickListener {
            // chamar o método para expandir ou recolher
            expandableGroup.onToggleExpanded()
            // atualizar a imagem
            viewHolder.item_note_Expandable_header_icon.setImageResource(getRotatedIconResId())
        }
    }

    override fun getLayout() = R.layout.item_note_header

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun getRotatedIconResId() =
        if (expandableGroup.isExpanded) {
            R.drawable.ic_keyboard_arrow_up_black_24dp
        } else {
            R.drawable.ic_keyboard_arrow_down_black_24dp
        }

}