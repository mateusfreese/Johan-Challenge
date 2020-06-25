package br.com.johan.johanapp.service.repository.remote

import br.com.johan.johanapp.service.model.PlanetsModel
import retrofit2.Call
import retrofit2.http.GET

interface PlanetsService {

    @GET("planets/")
    fun planets(): Call<PlanetsModel>

    @GET("planets/?page=2")
    fun planetsPage2(): Call<PlanetsModel>

    @GET("planets/?page=3")
    fun planetsPage3(): Call<PlanetsModel>

    @GET("planets/?page=4")
    fun planetsPage4(): Call<PlanetsModel>

    @GET("planets/?page=5")
    fun planetsPage5(): Call<PlanetsModel>

    @GET("planets/?page=6")
    fun planetsPage6(): Call<PlanetsModel>



}