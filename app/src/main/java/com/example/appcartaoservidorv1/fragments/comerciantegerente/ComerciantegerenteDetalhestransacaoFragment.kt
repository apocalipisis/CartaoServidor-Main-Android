package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteDetalhestransacaoBinding
import com.example.appcartaoservidorv1.databinding.FragmentTransacaodetalhescomercianteBinding
import com.example.appcartaoservidorv1.fragments.comerciante.TransacaodetalhescomercianteFragmentArgs
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciante.TransacaodetalhescomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.TransacaodetalhescomercianteViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComeciantegerenteDetalhestransacaoViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComeciantegerenteDetalhestransacaoViewModelFactory

class ComerciantegerenteDetalhestransacaoFragment : BaseFragment() {

    lateinit var binding: FragmentComerciantegerenteDetalhestransacaoBinding
    lateinit var args: ComerciantegerenteDetalhestransacaoFragmentArgs

    private lateinit var viewModel: ComeciantegerenteDetalhestransacaoViewModel
    private lateinit var viewModelFactory: ComeciantegerenteDetalhestransacaoViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_comerciantegerente_detalhestransacao,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteDetalhestransacaoFragmentArgs.fromBundle(requireArguments())
        // Inicializa as variaveis do ViewModel
        viewModelFactory = ComeciantegerenteDetalhestransacaoViewModelFactory(args.transacao)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ComeciantegerenteDetalhestransacaoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}