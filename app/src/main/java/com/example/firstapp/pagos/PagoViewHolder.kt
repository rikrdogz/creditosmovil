package com.example.firstapp.pagos

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.ModuloImpresora
import com.example.firstapp.R

class PagoViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    /*Elementos UI*/
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val fechaPago = view.findViewById<TextView>(R.id.txtFechaPago)
    val monto = view.findViewById<TextView>(R.id.txtMontoPago)
    val numero = view.findViewById<TextView>(R.id.txtNumero)
    val btnImprimir = view.findViewById<Button>(R.id.btnImprimir)

    fun renderizar(pagoModel: PagoViewModel) {
        nombre.text = "Nombre del cliente model"
        fechaPago.text =pagoModel.fechaPago
        monto.text = pagoModel.monto.toString()
        numero.text = pagoModel.fechaCreacion

        btnImprimir.setOnClickListener {

            var moduloImpresora: ModuloImpresora = ModuloImpresora()

            moduloImpresora.imprimirTicket(
                PagoViewModel(
                    1,
                    1,
                    pagoModel.monto,
                    pagoModel.descuento,
                    pagoModel.nombre,
                    pagoModel.numeroPago,
                    pagoModel.faltaDePago,
                   "",
                    pagoModel.fechaPago,
                    1,1,
                    ""
                )
            )


        }


    }
}
