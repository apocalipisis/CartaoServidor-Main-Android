package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentTransacaodetalhesBinding
import com.example.appcartaoservidorv1.viewmodels.servidor.TransacaodetalhesViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.TransacaodetalhesViewModelFactory

class TransacaodetalhesFragment: Fragment() {

    lateinit var binding: FragmentTransacaodetalhesBinding
    lateinit var args : TransacaodetalhesFragmentArgs

    private lateinit var viewModel: TransacaodetalhesViewModel
    private lateinit var viewModelFactory: TransacaodetalhesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transacaodetalhes, container, false)
        // Recupera as variaveis passada para a view
        args = TransacaodetalhesFragmentArgs.fromBundle(
            requireArguments()
        )
        // Inicializa as variaveis do ViewModel
        viewModelFactory = TransacaodetalhesViewModelFactory(args.transacao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TransacaodetalhesViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}