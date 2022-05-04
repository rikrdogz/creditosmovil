package com.example.firstapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewbinding.ViewBindings
import com.example.firstapp.databinding.ClientesFragmentBinding

class ClientesFragment : Fragment() {

    companion object {
        fun newInstance() = ClientesFragment()
    }

    private lateinit var viewModel: ClientesViewModel

    private var _binding: ClientesFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = ClientesFragmentBinding.inflate(inflater)
        //val inf = inflater.inflate(R.layout.clientes_fragment, container, false);
        //val web1 = inf.findViewById<WebView>(R.id.webview1);


        //web1.loadUrl("https://www.google.com/");
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)

        val web1 = view.findViewById<WebView>(R.id.webview1)

        //navegar dentro del webcliente local
        web1.webViewClient = WebViewClient()
        web1.settings.javaScriptEnabled = true
        web1.loadUrl("https://www.google.com/")
        // TODO: Use the ViewModel
        //val myWeb: WebView = ViewBindings.findChildViewById(R.id.webview1)
        //myWeb.loadUrl("https://www.google.com")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}