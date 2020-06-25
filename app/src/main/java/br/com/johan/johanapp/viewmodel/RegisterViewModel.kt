package br.com.johan.johanapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.johan.johanapp.service.constants.Constants
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import br.com.johan.johanapp.service.repository.FirebaseAuth

class RegisterViewModel : ViewModel() {

    private val mRegisterValidator = MutableLiveData<ValidationListener>()
    val registerValidator: LiveData<ValidationListener> = mRegisterValidator

    fun doRegister(name: String, email: String, password: String, confirmPassword: String) {
        if (validateFields(name, email, password, confirmPassword)) {
            FirebaseAuth().register(email, password, object : FirebaseListener<ValidationListener> {
                override fun callBack(result: ValidationListener) {
                    if (result.success()) {
                        doAddUserName(name)
                    } else {
                        mRegisterValidator.value = result
                    }
                }
            })
        }
    }

    fun doAddUserName(name: String) {
        FirebaseAuth().addUserName(name, object : FirebaseListener<ValidationListener> {
            override fun callBack(result: ValidationListener) {
                mRegisterValidator.value = result
            }
        })
    }

    // Validate the fields and return true if all fields it is ok
    private fun validateFields(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            mRegisterValidator.value = ValidationListener("Preencha todos os campos!")
            return false

        } else if (password != confirmPassword) {
            mRegisterValidator.value = ValidationListener("As senhas não coincidem!")
            return false

        }
        return true
    }
}
