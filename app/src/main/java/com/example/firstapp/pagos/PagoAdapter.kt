package com.example.firstapp.pagos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PagoAdapter (val listaPagos : List<PagoModel>) : RecyclerView.Adapter<PagoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PagoViewHolder, position: Int) {
        val item = listaPagos[position]

        holder.renderizar(item)
    }

    override fun getItemCount(): Int {
        return listaPagos.count()
    }
}