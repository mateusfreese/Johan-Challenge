package br.com.johan.johanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.BuildConfig
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import br.com.johan.johanapp.service.repository.FirebaseAuth

class LoginViewModel : ViewModel() {

    private var mClick = 7

    private val mAppVersion = MutableLiveData<String>()
    val appVersion: LiveData<String> = mAppVersion

    private val mLoginValidator = MutableLiveData<ValidationListener>()
    val loginValidator: LiveData<ValidationListener> = mLoginValidator

    private val mSwapiValidator = MutableLiveData<ValidationListener>()
    val swapiValidator: LiveData<ValidationListener> = mSwapiValidator

    init {
        mAppVersion.value = BuildConfig.VERSION_NAME
    }

    fun doLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            mLoginValidator.value = ValidationListener("Preencha todos os campos")
        } else {
            FirebaseAuth().login(email, password, object : FirebaseListener<ValidationListener> {
                override fun callBack(result: ValidationListener) {
                    mLoginValidator.value = result
                }
            })
        }
    }

    fun appVersionClick() {
        mClick -= 1

        if (mClick < 1) {
            mClick = 7
            mSwapiValidator.value = ValidationListener()
        } else {
            mSwapiValidator.value = ValidationListener("Clique mais $mClick vezes!")
        }
    }

}