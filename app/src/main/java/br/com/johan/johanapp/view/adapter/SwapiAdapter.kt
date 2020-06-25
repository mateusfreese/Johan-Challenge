package br.com.johan.johanapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.johan.johanapp.R
import br.com.johan.johanapp.service.model.PlanetModel
import kotlinx.android.synthetic.main.adapter_swapi.view.*

class SwapiAdapter(list: List<PlanetModel>) : RecyclerView.Adapter<SwapiAdapter.MyViewHolder>(){

    private var listPlanets : List<PlanetModel> = list

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var planetText: AppCompatTextView = itemView.adapter_list_text_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemList: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_swapi, parent, false)

        return MyViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val planet = listPlanets[position]
        holder.planetText.text = planet.name
    }

    override fun getItemCount(): Int {
        return listPlanets.size
    }

}