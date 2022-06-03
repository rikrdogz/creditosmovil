package com.example.firstapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.pagos.ApiPagoService
import com.example.firstapp.pagos.PagoAdapter
import com.example.firstapp.pagos.PagoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PagosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PagosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private  lateinit var adaptarPagos : PagoAdapter
    private  var listaPagosMuteable = mutableListOf<PagoViewModel>()
    private  var inputIdCredito: Int? = 0
    private  var inputIdPagoRealizado: Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        inputIdCredito = arguments?.getInt("idCredito")
        inputIdPagoRealizado = arguments?.getInt("idPagoRealizado")!!
        Log.d("idPago", inputIdPagoRealizado.toString())
        this.obtenerPagos()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_pagos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.iniciarRecycler()

    }

    fun iniciarRecycler() {
        val rview = view?.findViewById<RecyclerView>(R.id.listPagosRecycler)

        adaptarPagos = PagoAdapter(listaPagosMuteable, inputIdPagoRealizado)

        rview?.layoutManager = LinearLayoutManager(context)
        rview?.adapter = adaptarPagos

        Log.d("RECYCLER", "Iniciando el view")

    }

    fun getPagosRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl("http://creditosdev.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun obtenerPagos() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                Log.d("API", inputIdCredito.toString())

                val call =getPagosRetrofit().create(ApiPagoService::class.java).obtenerpagos(inputIdCredito)
                Log.d("DATOS", "----------OTENIENDO------------")


                activity?.runOnUiThread() {
                    val pagos  = call.body().orEmpty()
                    if (call.isSuccessful){
                        //show
                        listaPagosMuteable.clear()

                        pagos.forEach { pagoModel -> listaPagosMuteable.add(pagoModel)  }

                        adaptarPagos.notifyDataSetChanged()

                        Log.d("LISTA", "HAY DATOS ${pagos.size}")
                        //Log.d("LISTA", "Nombre ${pagos[0].nombre}")
                    }
                    else {
                        Log.d("LISTA", "NOO DATOS ${pagos.size}")
                        //Toast.makeText(this,"Sin Clientes", Toast.LENGTH_SHORT)
                    }


                }
            }

            catch (e: Exception) {
                Log.d("Error Problema", e.message.toString())
            }


        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PagosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}