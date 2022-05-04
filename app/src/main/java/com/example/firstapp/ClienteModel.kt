package com.example.firstapp

import com.google.gson.annotations.SerializedName

data class ClienteModel (@SerializedName("nombre") val nombre:String, @SerializedName("apellidoPaterno") val apellidoPaterno: String,@SerializedName("apellidoMaterno")  val apellidoMaterno:String)