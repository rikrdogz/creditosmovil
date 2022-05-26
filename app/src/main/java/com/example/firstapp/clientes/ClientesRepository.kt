package com.example.firstapp.clientes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData


class ClientesRepository : ViewModel() {

    private  val statusLiveData = MutableLiveData<String>("cargando...")
    val statusText: LiveData<String> = statusLiveData

    private  val enableBotonRecargarLiveData = MutableLiveData<Boolean>(false)
    val enableValueBoton : LiveData<Boolean> = enableBotonRecargarLiveData



    fun setEstatus( texto: String) {

        statusLiveData.postValue(texto)
    }

    fun setEnableButton(isEnable: Boolean) {

        enableBotonRecargarLiveData.postValue(isEnable)
    }

}