package com.example.firstapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClienteViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val paterno = view.findViewById<TextView>(R.id.txtPaterno)
    val materno = view.findViewById<TextView>(R.id.txtMaterno)

    fun render(clienteModel: ClienteModel) {
        nombre.text = clienteModel.nombre
        paterno.text = clienteModel.apellidoPaterno
        materno.text = clienteModel.apellidoMaterno
    }
}