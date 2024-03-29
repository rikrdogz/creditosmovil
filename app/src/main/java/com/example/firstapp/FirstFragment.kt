package com.example.firstapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.clientes.*
import com.example.firstapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Comunicador
    private  lateinit var commCliente: ClienteComunicator
    private  lateinit var commMain: MainCommunicator

    //observables
    var repo = ClientesRepository()


    private lateinit var adapterClientes: ClientesAdapter
    private var listaCLientesMuteable =  mutableListOf<ClienteModel>()
    private var intentConection = 0

    private var creditoApp = MainActivity()

    @SuppressLint("CutPasteId")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        commCliente =requireActivity() as ClienteComunicator
        commMain = requireActivity() as MainCommunicator


        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        //commMain.showLoadingBar(true);

        repo.statusText.observe(viewLifecycleOwner)  { it -> view?.findViewById<Button>(R.id.btnRecargar)?.text = it}
        repo.enableValueBoton.observe(viewLifecycleOwner) {it -> view?.findViewById<Button>(R.id.btnRecargar)?.isEnabled = it}

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
        return Retrofit.Builder().baseUrl(creditoApp.baseUrlApp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun onClienteClick(position: Int) {
        commCliente.passDataCliente(listaCLientesMuteable[position])

    }

    private fun setLoadingInfo(isLoading : Boolean = true) {

        repo.setEstatus("Cargando...")

        if (!isLoading)
        {
           repo.setEstatus("Recargar")

            Log.d("data", "----------------CARGADO---------------")
        }

        repo.setEnableButton(!isLoading)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun obtenerClientes() {

        val respuesta = CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main)
            {
                commMain.showLoadingBar(true)
            }

            try {
                setLoadingInfo(true)
                val call =getClientesRetroFit().create(ApiCreditoService::class.java).getClientes("clientes")
                Log.d("DATOS", "----------OTENIENDO------------")

                val clientes  = call.body().orEmpty()

                //Thread.sleep(1000)

                if (call.isSuccessful){
                    //show
                    withContext(Dispatchers.Main)
                    {
                        listaCLientesMuteable.clear()

                        clientes.forEach { clienteModel -> listaCLientesMuteable.add(clienteModel)  }

                        adapterClientes.notifyDataSetChanged()
                    }



                    Log.d("LISTA", "HAY DATOS ${listaCLientesMuteable.size}")
                    Log.d("LISTA", "Nombre ${listaCLientesMuteable[0].nombre}")
                }
                else {
                    showError()
                    //Toast.makeText(this,"Sin Clientes", Toast.LENGTH_SHORT)
                }

                setLoadingInfo(false)


            }

            catch (e: Exception) {
                Log.d("Error Problema", e.message.toString())
                Log.d("INTENTO", "# $intentConection")
                intentConection++

                if (intentConection < 4)
                {
                    withContext(Dispatchers.Main)
                    {

                        //Toast.makeText(activity, "intento # ${intentConection}", Toast.LENGTH_SHORT).show();
                        Log.d("TOAST ENTRO", "SI")
                    }

                    obtenerClientes()
                }
                setLoadingInfo(false)

            }

            withContext(Dispatchers.Main)
            {
                commMain.showLoadingBar(false)
                //Toast.makeText(context, "intento ", Toast.LENGTH_SHORT).show();
            }

        }

        Log.d("Carga", "fin intento $intentConection")

    }

    private fun showError() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Mandar el parametro de la vista*/
        this.iniciar(view)
        this.intentConection = 1
        this.obtenerClientes()

        binding.btnRecargar.setOnClickListener { obtenerClientes() }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}