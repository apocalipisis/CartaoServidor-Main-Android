package com.example.appcartaoservidorv1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.Constantes
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentSessaoexpiradaBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromSessaoexpiradaToLogin
import com.example.appcartaoservidorv1.viewmodels.SessaoexpiradaViewModel
import com.example.appcartaoservidorv1.viewmodels.SessaoexpiradaViewModelFactory

class SessaoexpiradaFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentSessaoexpiradaBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: SessaoexpiradaViewModel
    private lateinit var viewModelFactory: SessaoexpiradaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sessaoexpirada, container, false)
        // Recupera as variaveis passada para a view

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = SessaoexpiradaViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SessaoexpiradaViewModel::class.java]

        val fragment = this

        binding.mensagem.text =
            "Sua sessão atingiu o tempo limite de ${Constantes.TempoLimite} minutos"

        // Click listener para o btn Refresh
        binding.btnRefazerlogin.setOnClickListener {
            fromSessaoexpiradaToLogin(fragment)
        }

        binding.btnVoltar.setOnClickListener {
            fromSessaoexpiradaToLogin(fragment)
        }

        // Configura o botão de voltar para ao pressionar voltar para a página login
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fromSessaoexpiradaToLogin(fragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}