package com.example.appcartaoservidorv1.fragments.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentUsuarionaopermitidoBinding
import com.example.appcartaoservidorv1.services.utilidades.fromViewToLogin
import com.example.appcartaoservidorv1.viewmodels.login.UsuarionaopermitidoViewModel
import com.example.appcartaoservidorv1.viewmodels.login.UsuarionaopermitidoViewModelFactory


class UsuarionaopermitidoFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentUsuarionaopermitidoBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: UsuarionaopermitidoViewModel
    private lateinit var viewModelFactory: UsuarionaopermitidoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_usuarionaopermitido,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = UsuarionaopermitidoViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory)[UsuarionaopermitidoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Chama o site ao clickar no btn
        binding.btnSite.setOnClickListener { site() }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fromViewToLogin(binding.root)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun siteItent(): Intent {
        val shareIntent = Intent(Intent.ACTION_VIEW)
        shareIntent.data = Uri.parse(Constantes.Site)

        return shareIntent
    }

    private fun site() {
        startActivity(siteItent())
    }


}
