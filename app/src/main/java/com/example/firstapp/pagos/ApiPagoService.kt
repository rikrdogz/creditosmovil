package com.example.firstapp.pagos

import com.example.firstapp.clientes.ClienteCreditoActivoViewModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiPagoService {

    @POST("/pago")
    suspend fun postGuardarPago(@Body pagoModel :  PagoModel): Response<Number>

    @GET("/Creditos/creditoActivo/{idCliente}")
    suspend fun creditoActivo(@Path("idCliente") idCliente : Int?): Response<ClienteCreditoActivoViewModel>

    @GET("/pago/porcliente/{idCliente}")
    suspend fun obtenerpagos(@Path ("idCliente")  idCliente: Int? ): Response<List<PagoViewModel>>
}