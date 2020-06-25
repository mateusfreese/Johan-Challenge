package br.com.johan.johanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.service.repository.FirebaseAuth

class DashboardViewModel : ViewModel(){

    private val mUserLoggedName = MutableLiveData<String>()
    val userLoggedName: LiveData<String> = mUserLoggedName

    init {
        mUserLoggedName.value = FirebaseAuth().getUserName()
    }

    fun doLogout(){
        FirebaseAuth().logout()
    }
}