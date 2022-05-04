package com.example.firstapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ClientesAdapter(val listaClientes:List<ClienteModel>) : RecyclerView.Adapter<ClienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClienteViewHolder(layoutInflater.inflate(R.layout.item_cliente, parent, false))
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        /*Por cada elemento*/

        val item = listaClientes[position]

        holder.render(item)
    }

    override fun getItemCount(): Int = listaClientes.size

}