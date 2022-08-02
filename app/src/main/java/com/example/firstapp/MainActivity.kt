package com.example.firstapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firstapp.clientes.ClienteComunicator
import com.example.firstapp.clientes.ClienteModel
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.databinding.ContentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ClienteComunicator, MainCommunicator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var BarraDeProgreso : ProgressBar? = null;

    var baseUrlApp : String = if (!BuildConfig.DEBUG) "http://creditosdev.azurewebsites.net/" else "https://creditosbellely.azurewebsites.net/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        BarraDeProgreso = findViewById(R.id.barraDeProgreso)

        if (BuildConfig.DEBUG)
        {
            Toast.makeText(this,"MODO DESARROLLO", Toast.LENGTH_LONG).show();
        }

        /*Revisar Permisos*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED )
        {
            Log.d("Permisos", "ACEPTADOS")
        }
        else
        {
            Log.d("Permisos", "DENEGAGOS X")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun showLoadingBar(isVisibleBar: Boolean) {

        if (isVisibleBar)
        {

            BarraDeProgreso?.visibility = View.VISIBLE
        }
        else
        {
            BarraDeProgreso?.visibility  = View.GONE
        }

    }

    override fun passDataCliente(cliente: ClienteModel) {
        var bundle = Bundle()

        bundle.putString("nombre_txt", cliente.nombre)

        bundle.putInt("idCliente", cliente.clienteId)



        findNavController(R.id.nav_host_fragment_content_main)
            .navigate(R.id.action_FirstFragment_to_ClientesInfoFragment, bundle)


        /*
        transaction.replace(R.id.layout_app_cliente, frag_Cliente)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()*/
    }
}