package com.example.firstapp.clientes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClienteInfoRepository : ViewModel() {

    private val creditoIdLiveData = MutableLiveData<Int>(0);
    val creditoId: LiveData<Int> = creditoIdLiveData;

    private  val montoLiveData = MutableLiveData<Float>(0f);
    val monto : LiveData<Float> = montoLiveData;

    private  val guardadoLiveData = MutableLiveData<Boolean>(false);
    val guardado : LiveData<Boolean> = guardadoLiveData;


    fun setCreditoId( value:Int) {
        creditoIdLiveData.postValue(value)
    }

    fun setGuardado(value: Boolean) {
        guardadoLiveData.postValue(value);
    }

    fun setMonto(value: Float) {
        montoLiveData.postValue(value)
    }

    /*var monto: Float,
    var descuento: Float,

     var faltaDePago: Float,
     var fechaPago: String,
     var observacion: String*/
}