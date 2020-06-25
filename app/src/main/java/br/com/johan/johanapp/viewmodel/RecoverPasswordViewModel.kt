package br.com.johan.johanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import br.com.johan.johanapp.service.repository.FirebaseAuth

class RecoverPasswordViewModel : ViewModel() {

    private val mRecoverPasswordValidator = MutableLiveData<ValidationListener>()
    val recoverPasswordValidator: LiveData<ValidationListener> = mRecoverPasswordValidator

    fun doRecoverPassword(email: String) {
        if (validateFields(email)) {
            FirebaseAuth().recoverPassword(email, object : FirebaseListener<ValidationListener> {
                override fun callBack(result: ValidationListener) {
                    mRecoverPasswordValidator.value = result
                }
            })
        }

    }

    private fun validateFields(email: String): Boolean {
        if (email.isEmpty()) {
            mRecoverPasswordValidator.value = ValidationListener("Insira um E-mail para recuperar a senha!")
            return false
        }
        return true
    }

}