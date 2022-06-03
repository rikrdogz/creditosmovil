package com.example.firstapp.pagos

import com.google.gson.annotations.SerializedName

data class PagoViewModel (
    @SerializedName("idPago") var pagoId: Number,
    @SerializedName("creditoId") var creditoId: Number,
    @SerializedName("monto") var monto: Float,
    @SerializedName("descuento") var descuento: Float,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("numeroPago") var numeroPago: String,
    @SerializedName("faltaPago") var faltaDePago: Float,
    @SerializedName("fechaCreacionPago") var fechaCreacion: String,
    @SerializedName("fechaPago") var fechaPago: String,
    @SerializedName("estatusId") var estatusId: Number,
    @SerializedName("idUsuario") var idUsuario: Number,
    @SerializedName("observacion") var observacion: String
)