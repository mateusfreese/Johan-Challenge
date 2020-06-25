package br.com.johan.johanapp.service.model

import com.google.gson.annotations.SerializedName

class PlanetsModel {

    @SerializedName("results")
    var results: ArrayList<PlanetModel> = arrayListOf()
}