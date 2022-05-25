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
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.firstapp.ErrorResponse
import com.example.firstapp.HeaderInterceptor
import com.example.firstapp.ModuloImpresora
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentClienteInfoBinding
import com.example.firstapp.pagos.ApiPagoService
import com.example.firstapp.pagos.PagoModel
import com.example.firstapp.pagos.PagoViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


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
    private  var inputIdCliente: Int? = 0
    private  var inputIdCredito: Int = 0

    private var fragmentClienteBinding: FragmentClienteInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

        inputNombre =arguments?.getString("nombre_txt")
        inputIdCliente = arguments?.getInt("idCliente")

        Log.d("info OnCreate", arguments?.getString("nombre_txt").orEmpty())
        Log.d("info CLIENTE", inputIdCliente.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var txtName = view.findViewById<TextView>(R.id.txtNombre)
        Log.d("Created!!", inputNombre.orEmpty())
        txtName.text = inputNombre.orEmpty()

        view.findViewById<TextView>(R.id.txtNombre).text = inputNombre.orEmpty()

        this.ObtenerInfoCliente()

        view.findViewById<EditText>(R.id.txtFecha).setOnClickListener { showModalFecha() }

        view.findViewById<Button>(R.id.btnPagar).setOnClickListener{
            GuardarPago()
            //this.imprimirTicket()
            //var moduloImpresora : ModuloImpresora = ModuloImpresora()
            //moduloImpresora.imprimirTicket(PagoViewModel(1,1,125f,0f,"luis ricardo","1", 20f, "hoy", "hoy",1,1, ""))
        }

        view.findViewById<Button>(R.id.btnVerPagos).setOnClickListener {
            findNavController().navigate(R.id.action_ClienteInfoFragment_to_pagosFragment, arguments)
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

    fun ObtenerInfoCliente(){
        CoroutineScope(Dispatchers.IO).launch {



            val call = pagoRetroFit().create(ApiPagoService::class.java).creditoActivo(inputIdCliente)
            Log.d("DATOS", "----------OBTENIENDO------------")

            var responseInfo = call.body()

            activity?.runOnUiThread() {
                if (call.isSuccessful)
                {
                    Log.d("DATOS", responseInfo.toString())

                    var pendienteTexto : String = "";
                    var ultimoPago : String = "";

                    if (responseInfo?.idCredito != null)
                    {

                        pendienteTexto = responseInfo?.pendientePago.toString().orEmpty()
                        ultimoPago = responseInfo?.fechaUltimoPago.toString().orEmpty()

                        inputIdCredito = responseInfo?.idCredito

                        view?.findViewById<Button>(R.id.btnPagar)?.visibility = View.VISIBLE

                    }
                    else
                    {
                        pendienteTexto = "NO HAY CREDITO ACTIVO"

                        inputIdCliente = 0;

                        view?.findViewById<Button>(R.id.btnPagar)?.visibility = View.INVISIBLE
                    }

                    view?.findViewById<TextView>(R.id.lblDebe)?.text = pendienteTexto
                    view?.findViewById<TextView>(R.id.txtUltimoPago)?.text = ultimoPago

                    Toast.makeText(context, "Obtenido", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    view?.findViewById<TextView>(R.id.lblDebe)?.text = "NO CREDITO ACTIVO"
                    Log.d("DATOS", "NO OBTENIDOS")
                }
            }

        }
    }

    fun imprimirTicket() {
        var connection = BluetoothPrintersConnections.selectFirstPaired()

        if (connection != null)
        {
            var locale: Locale = Locale("id", "MX")
            var df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", locale)
            var nf = NumberFormat.getCurrencyInstance(locale)


            var impresora = EscPosPrinter(connection,203, 30f, 30);
            var textoImpresora : String = """
                [C] <b>BELLELY CREDITOS</b>
                [L]
                [L] ${inputNombre.toString()} ${System.lineSeparator()}
                [L]${df.format(Date())}
                [C]<b>===== RECIBO DE PAGO ======<b> ${System.lineSeparator()}
                [C]================================
                [L]
                [L]1 pago[R]${nf.format(150)}
                [C]<b>del credito</b>
                [C]--------------------------------
                [L]SUBTOTAL[R]${nf.format(150)} ${System.lineSeparator()}
                [L]MULTA [R]${nf.format(20)}  ${System.lineSeparator()}              
                [L]<b>TOTAL[R]${nf.format(130)}</b>
             
                """.trimIndent()

            impresora.printFormattedText(textoImpresora, 8f)

        }

        else
        {
            Log.d("IMPRESORA", "NO ESTA CONECTADA")
        }

    }

    fun GuardarPago() {

        var newPagoDate = this.fechaPago

        CoroutineScope(Dispatchers.IO).launch {
            var monto = view?.findViewById<EditText>(R.id.txtMontoPago).toString().toFloat();

            var pago: PagoModel = PagoModel(0, inputIdCredito, monto = monto, descuento = 0f,
                    faltaDePago = 0f, fechaCreacion = newPagoDate , fechaPago = newPagoDate, idUsuario = 1, estatusId = 1, observacion = ""  )

            val call =pagoRetroFit().create(ApiPagoService::class.java).postGuardarPago(pago)
            Log.d("DATOS", "----------ENVIANDO------------")

            val responsePago  = call.body()

            activity?.runOnUiThread() {
                if (call.isSuccessful){
                    //show
                    Log.d("DATOS", responsePago.toString())


                }
                else {

                    Toast.makeText(context, "NO SE PUDO GUARDAR EL PAGO", Toast.LENGTH_SHORT).show()

                    var jsonObject = JSONObject()
                    var textReponse : String = call.errorBody()!!.charStream().readText()
                    textReponse = textReponse.replace("$", "")
                    Log.d("RESPONSE", textReponse )

                    var problemaGeneral : String = ""

                    try {


                        try {
                            /*Intenerar convertir a json si tiene formato*/
                            JSONObject(textReponse)


                        } catch (e: JSONException) {
                            Log.d("PROBLEMA", "No es JSON" )

                            /*Se mostrara el erros que venga*/
                            problemaGeneral = textReponse;
                            throw Exception("No es FORMATO JSON")

                        }

                        /*Convertir a tipo ErrorResponse*/
                        if (JSONObject(textReponse).has("errors"))
                        {
                            var myObjet = Gson().fromJson(textReponse, ErrorResponse::class.java)

                            var errorData = JSONObject(myObjet.errors.toString())

                            problemaGeneral = errorData.toString()

                            Log.d("PROBLEMA", errorData.toString() )
                        }
                        else
                        {
                            problemaGeneral = textReponse;
                        }

                    }
                    catch (e: Exception)
                    {
                        Log.d("EXEP", e.message.toString())
                    }


                    view?.findViewById<TextView>(R.id.lblProblema)?.text = problemaGeneral


                }


            }

        }
    }




    private fun showModalFecha(){
        val datePicker = FechaDateTimePicker{day, month, year -> onDateSelected(day, month, year) }

        datePicker.show(parentFragmentManager, "datePicker")

    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        var fechaData = "$year-${ (month + 1).toString().padStart(2,'0')}-${day.toString().padStart(2,'0')}"
        view?.findViewById<EditText>(R.id.txtFecha)?.setText(fechaData)
        this.fechaPago = fechaData
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