package com.example.firstapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.clientes.ApiCreditoService
import com.example.firstapp.clientes.ClienteModel
import com.example.firstapp.clientes.ClientesAdapter
import com.example.firstapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapterClientes: ClientesAdapter
    private var listaCLientesMuteable =  mutableListOf<ClienteModel>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun iniciar(view: View) {
        val rview = view.findViewById<RecyclerView>(R.id.rc_list)

        adapterClientes = ClientesAdapter(listaCLientesMuteable) { position -> onClienteClick(position)} //listaCLientesMuteable

        rview.layoutManager = LinearLayoutManager(context)
        rview.adapter = adapterClientes



        Log.d("INICIAR", "Iniciando el view")

    }

    fun getClientesRetroFit() : Retrofit {
        return Retrofit.Builder().baseUrl("http://creditosdev.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun onClienteClick(position: Int) {

        Toast.makeText(context, listaCLientesMuteable[position].nombre, Toast.LENGTH_SHORT).show()

    }

    fun ObtenerClientes() {
        CoroutineScope(Dispatchers.IO).launch {
            val call =getClientesRetroFit().create(ApiCreditoService::class.java).getClientes("clientes")
            Log.d("DATOS", "----------OTENIENDO------------")
            val clientes  = call.body()

            activity?.runOnUiThread() {
                if (call.isSuccessful){
                    //show
                        listaCLientesMuteable.clear()
                        listaCLientesMuteable.addAll(clientes.orEmpty())

                        adapterClientes.notifyDataSetChanged()

                    Toast.makeText(context, "Iniciado", Toast.LENGTH_SHORT)
                    Log.d("LISTA", "HAY DATOS ${listaCLientesMuteable.size}")
                    Log.d("LISTA", "Nombre ${listaCLientesMuteable[0].nombre}")
                }
                else {
                    showError()
                    //Toast.makeText(this,"Sin Clientes", Toast.LENGTH_SHORT)
                }


            }

        }
    }

    private fun showError() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Mandar el parametro de la vista*/
        this.iniciar(view)
        this.ObtenerClientes()
        Toast.makeText(context, "CREATED", Toast.LENGTH_SHORT).show()

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_ClientesFragment)
            Toast.makeText(getActivity(), "CREATED", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}