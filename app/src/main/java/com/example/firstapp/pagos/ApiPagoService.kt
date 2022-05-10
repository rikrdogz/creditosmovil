package com.example.firstapp.pagos

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiPagoService {

    @POST("/pago")
    suspend fun postGuardarPago(@Body pagoModel :  PagoModel): Response<Number>
}