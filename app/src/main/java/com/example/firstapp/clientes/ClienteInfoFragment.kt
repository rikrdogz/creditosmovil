package com.example.firstapp.clientes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.firstapp.R
import com.example.firstapp.databinding.FragmentClienteInfoBinding

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


        super.onViewCreated(view, savedInstanceState)



    }

    private fun showModalFecha(){
        val datePicker = FechaDateTimePicker{day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")

    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        view?.findViewById<EditText>(R.id.txtFecha)?.setText("$day/$month/$year")

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