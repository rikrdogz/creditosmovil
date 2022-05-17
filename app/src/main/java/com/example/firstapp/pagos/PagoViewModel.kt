package com.example.firstapp.pagos

import com.google.gson.annotations.SerializedName

data class PagoViewModel (
    @SerializedName("pagoId") var pagoId: Number,
    @SerializedName("creditoId") var creditoId: Number,
    @SerializedName("monto") var monto: Float,
    @SerializedName("descuento") var descuento: Float,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("numeroPago") var numeroPago: String,
    @SerializedName("faltaDePago") var faltaDePago: Float,
    @SerializedName("fechaCreacion") var fechaCreacion: String,
    @SerializedName("fechaPago") var fechaPago: String,
    @SerializedName("estatusId") var estatusId: Number,
    @SerializedName("idUsuario") var idUsuario: Number,
)