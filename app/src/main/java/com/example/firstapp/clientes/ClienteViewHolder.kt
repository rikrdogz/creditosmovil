package com.example.firstapp.clientes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R

class ClienteViewHolder(view:View, private val onClickItem:(position:Int) -> Unit) : RecyclerView.ViewHolder(view) {

    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val paterno = view.findViewById<TextView>(R.id.txtPaterno)
    val materno = view.findViewById<TextView>(R.id.txtMaterno)
    val iniciales = view.findViewById<TextView>(R.id.txtIniciales)

    init {
        itemView.setOnClickListener{
            return@setOnClickListener onClickItem(adapterPosition)
        }
    }

    fun render(clienteModel: ClienteModel) {
        nombre.text = clienteModel.nombre
        paterno.text = clienteModel.apellidoPaterno
        materno.text = clienteModel.apellidoMaterno
        iniciales.text = clienteModel.nombre.substring(0,1) + clienteModel.apellidoPaterno.substring(0,1)

    }
}