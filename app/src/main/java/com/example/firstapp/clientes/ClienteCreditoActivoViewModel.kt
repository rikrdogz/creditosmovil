package com.example.firstapp.clientes

import com.google.gson.annotations.SerializedName

data class ClienteCreditoActivoViewModel (
    @SerializedName("idCredito") val idCredito: Int,
    @SerializedName("pendientePago") val pendientePago: Float,
    @SerializedName("numeroPago") val numeroPago: Int,
    @SerializedName("montoPrestado") val montoPrestado: Float,
    @SerializedName("cliente") val cliente: String,
    @SerializedName("fechaUltimoPago") val fechaUltimoPago: String
)