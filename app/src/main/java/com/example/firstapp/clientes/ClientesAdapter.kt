package com.example.firstapp.clientes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R

class ClientesAdapter(val listaClientes:List<ClienteModel>, private val onItemClick: (position:Int) -> Unit ) : RecyclerView.Adapter<ClienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClienteViewHolder(layoutInflater.inflate(R.layout.item_cliente, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        /*Por cada elemento*/

        val item = listaClientes[position]

        holder.render(item)
    }

    override fun getItemCount(): Int = listaClientes.size

}