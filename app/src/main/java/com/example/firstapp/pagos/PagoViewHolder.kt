package com.example.firstapp.pagos


import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    val fechaCreacion = view.findViewById<TextView>(R.id.lblFechaCreacion)

    fun renderizar(pagoModel: PagoViewModel, idPagoRealizado: Int) {
        nombre.text = pagoModel.nombre
        fechaPago.text =pagoModel.fechaPago
        monto.text = pagoModel.monto.toString()
        numero.text = pagoModel.pagoId.toString()
        fechaCreacion.text = pagoModel.fechaCreacion

        if (pagoModel.pagoId.toInt() == idPagoRealizado) {

            monto.setTextColor(Color.BLUE)
            monto.setTextSize(1,28f)
        }



        btnImprimir.setOnClickListener {

            val moduloImpresora = ModuloImpresora()

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
