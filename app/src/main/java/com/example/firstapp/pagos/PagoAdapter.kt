package com.example.firstapp.pagos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.clientes.ClienteViewHolder

class PagoAdapter (val listaPagos : List<PagoViewModel>) : RecyclerView.Adapter<PagoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagoViewHolder(layoutInflater.inflate(R.layout.item_pago, parent, false))
    }

    override fun onBindViewHolder(holder: PagoViewHolder, position: Int) {
        val item = listaPagos[position]

        holder.renderizar(item)
    }

    override fun getItemCount(): Int {
        return listaPagos.count()
    }
}