package com.example.firstapp

import com.google.gson.annotations.SerializedName


data class ClienteResponse (

    @SerializedName("nombre") var nombre : String,
    @SerializedName("apellidoPaterno") var apellidoPaterno :String,
    @SerializedName("apellidoMaterno") var apellidoMaterno : String

)