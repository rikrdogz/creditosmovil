package com.example.firstapp.pagos

import com.google.gson.annotations.SerializedName
import java.util.*

data class PagoModel(
    @SerializedName("pagoId") var pagoId: Number,
    @SerializedName("creditoId") var creditoId: Number,
    @SerializedName("monto") var monto: Float,
    @SerializedName("descuento") var descuento: Float,
    @SerializedName("faltaDePago") var faltaDePago: Float,
    @SerializedName("fechaCreacion") var fechaCreacion: Date,
    @SerializedName("fechaPago") var fechaPago: Date,
    @SerializedName("estatusId") var estatusId: Number,
    @SerializedName("idUsuario") var idUsuario: Number,
)