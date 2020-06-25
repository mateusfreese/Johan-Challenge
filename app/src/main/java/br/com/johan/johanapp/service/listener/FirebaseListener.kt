package br.com.johan.johanapp.service.listener

interface FirebaseListener<S> {
    fun callBack(result: S)
}