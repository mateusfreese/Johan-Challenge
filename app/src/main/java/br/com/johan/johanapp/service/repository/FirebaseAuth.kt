package br.com.johan.johanapp.service.repository

import android.util.Log
import br.com.johan.johanapp.service.constants.Constants
import br.com.johan.johanapp.service.constants.Constants.FIREBASE.AUTH
import br.com.johan.johanapp.service.listener.FirebaseListener
import br.com.johan.johanapp.service.listener.ValidationListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class FirebaseAuth {

    // Firebase Auth reference
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var mUser: FirebaseUser? = mAuth.currentUser

    fun register(email: String, password: String, response: FirebaseListener<ValidationListener>) {
        Log.d(AUTH, "cadastrar acionado")
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(AUTH, "creteUserWithEmail:Success")
                mUser = mAuth.currentUser
                response.callBack(ValidationListener())
            } else {
                Log.w(AUTH, "creteUserWithEmail:Fail", it.exception)
                mUser = null
                response.callBack(ValidationListener(it.exception.toString()))
            }
        }
    }

    fun login(
        email: String,
        password: String,
        response: FirebaseListener<ValidationListener>
    ) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(AUTH, "LoginWithEmail:Success")
                response.callBack(ValidationListener(""))
            } else {
                Log.w(AUTH, "LoginWithEmail:Fail", it.exception)
                response.callBack(ValidationListener(it.exception.toString()))
            }
        }
    }

    fun recoverPassword(
        email: String,
        response: FirebaseListener<ValidationListener>
    ) {
        mAuth.setLanguageCode("pt-BR")

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(AUTH, "RecoverEmail:Success")
                response.callBack(ValidationListener(""))
            } else {
                Log.w(AUTH, "RecoverEmail:Fail", it.exception)
                response.callBack(ValidationListener("E-mail Inválido!"))
            }
        }
    }

    fun addUserName(name: String, response: FirebaseListener<ValidationListener>) {
        if (name.isBlank() || mUser == null) {
            Log.i(AUTH, "nome veio vazio ou nulo")
        } else {
            var userPCR = UserProfileChangeRequest.Builder()
                .setDisplayName(name).build()

            mUser!!.updateProfile(userPCR).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(AUTH, "User profile updated")
                    response.callBack(ValidationListener(""))
                } else {
                    Log.d(AUTH, "User profile failed:", it.exception)
                    response.callBack(ValidationListener(it.exception.toString()))
                }
            }
        }
    }

    fun logout(){
        mAuth.signOut()
        mUser = null
    }

    fun getUserName() = mUser!!.displayName

    fun getUserID() = mUser!!.uid

}
