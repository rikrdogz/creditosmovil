package com.example.firstapp.pagos

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R

class PagoViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    /*Elementos UI*/
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val fechaPago = view.findViewById<TextView>(R.id.txtFechaPago)
    val monto = view.findViewById<TextView>(R.id.txtMontoPago)
    val numero = view.findViewById<TextView>(R.id.txtNumero)


    fun renderizar(pagoModel: PagoModel) {
        nombre.text = "Nombre del cliente model"
        fechaPago.text =pagoModel.fechaPago
        monto.text = pagoModel.monto.toString()
        numero.text = "#4"
    }
}
