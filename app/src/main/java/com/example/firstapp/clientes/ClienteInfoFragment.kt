package com.example.firstapp.clientes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firstapp.HeaderInterceptor
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentClienteInfoBinding
import com.example.firstapp.pagos.ApiPagoService
import com.example.firstapp.pagos.PagoModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  var fechaPago: String = ""

    private var inputNombre: String? = ""
    private var fragmentClienteBinding: FragmentClienteInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

        inputNombre =arguments?.getString("nombre_txt")

        Log.d("info OnCreate", arguments?.getString("nombre_txt").orEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var txtName = view.findViewById<TextView>(R.id.txtNombre)
        Log.d("Created!!", inputNombre.orEmpty())
        txtName.text = inputNombre.orEmpty()

        view.findViewById<TextView>(R.id.txtNombre).text = inputNombre.orEmpty()

        view.findViewById<EditText>(R.id.txtFecha).setOnClickListener { showModalFecha() }

        view.findViewById<Button>(R.id.btnPagar).setOnClickListener{ GuardarPago()}

        view.findViewById<Button>(R.id.btnVerPagos).setOnClickListener {
            findNavController().navigate(R.id.action_ClienteInfoFragment_to_pagosFragment)
        }


        super.onViewCreated(view, savedInstanceState)



    }

    fun pagoRetroFit() : Retrofit {

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return Retrofit.Builder().baseUrl("http://creditosdev.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(pagoClient())
            .build()
    }

    private fun pagoClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).addInterceptor(loggingInterceptor).build()
    }

    fun GuardarPago() {

        var newPagoDate = this.fechaPago

        CoroutineScope(Dispatchers.IO).launch {

            var pago: PagoModel = PagoModel(0, 33, monto = 150f, descuento = 0f,
                    faltaDePago = 0f, fechaCreacion = newPagoDate , fechaPago = newPagoDate, idUsuario = 1, estatusId = 1 )

            val call =pagoRetroFit().create(ApiPagoService::class.java).postGuardarPago(pago)
            Log.d("DATOS", "----------ENVIANDO------------")

            val responsePago  = call.body()

            activity?.runOnUiThread() {
                if (call.isSuccessful){
                    //show
                    Log.d("DATOS", responsePago.toString())


                    Toast.makeText(context, "Obtenido", Toast.LENGTH_SHORT).show()

                }
                else {
                    //showError()
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                    Log.d("PROBLEMA", responsePago.toString())
                }


            }

        }
    }


    private fun showModalFecha(){
        val datePicker = FechaDateTimePicker{day, month, year -> onDateSelected(day, month, year) }

        datePicker.show(parentFragmentManager, "datePicker")

    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        view?.findViewById<EditText>(R.id.txtFecha)?.setText("$day/$month/$year")
        this.fechaPago = "$year/$month/$day"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //para el binding de la data


        return inflater.inflate(R.layout.fragment_cliente_info, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClienteInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClienteInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}