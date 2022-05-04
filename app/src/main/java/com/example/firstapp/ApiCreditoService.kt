package com.example.firstapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiCreditoService {
    @GET
    suspend fun getClientes(@Url url:String): Response<List<ClienteModel>>
}