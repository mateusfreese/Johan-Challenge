package br.com.johan.johanapp.service.model

import java.io.Serializable

class Note(
    var title: String,
    var body: String,
    var offline: Boolean,
    var noteKey: String
): Serializable{
    constructor(): this("","", false ,"")
}
