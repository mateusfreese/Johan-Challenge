package br.com.johan.johanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.service.model.PlanetModel
import br.com.johan.johanapp.service.model.PlanetsModel
import br.com.johan.johanapp.service.repository.RetrofitClient
import br.com.johan.johanapp.service.repository.remote.PlanetsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwapiViewModel : ViewModel() {

    private val mMutableResults = MutableLiveData<ArrayList<PlanetModel>>()
    var mutableResults: LiveData<ArrayList<PlanetModel>> = mMutableResults

    private var remote: PlanetsService = RetrofitClient.createService(PlanetsService::class.java)

    private var temp: ArrayList<PlanetModel> = arrayListOf()

    private val call1: Call<PlanetsModel> = remote.planets()
    private val call2: Call<PlanetsModel> = remote.planetsPage2()
    private val call3: Call<PlanetsModel> = remote.planetsPage3()
    private val call4: Call<PlanetsModel> = remote.planetsPage4()
    private val call5: Call<PlanetsModel> = remote.planetsPage5()
    private val call6: Call<PlanetsModel> = remote.planetsPage6()

    private val callList: ArrayList<Call<PlanetsModel>> = arrayListOf(call1,call2, call3, call4, call5, call6)

    init {
        callNext()
    }

    private var x = 0
    private fun callNext() {
        callAPI(callList[x])
        x += 1
    }

    private fun callAPI(call: Call<PlanetsModel>) {
        call.enqueue(object : Callback<PlanetsModel> {
            override fun onFailure(call: Call<PlanetsModel>, t: Throwable) {
//                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<PlanetsModel>, response: Response<PlanetsModel>) {
                val results: ArrayList<PlanetModel> = response.body()!!.results

                if (callList.size > x) {
                    callNext()
                }
                temp.addAll(results)

                mMutableResults.apply { value = temp }
            }
        })
    }
}


