package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentTransacaodetalhescomercianteBinding
import com.example.appcartaoservidorv1.viewmodels.comerciante.TransacaodetalhescomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.TransacaodetalhescomercianteViewModelFactory

class TransacaodetalhescomercianteFragment : Fragment() {

    lateinit var binding: FragmentTransacaodetalhescomercianteBinding
    lateinit var args: TransacaodetalhescomercianteFragmentArgs

    private lateinit var viewModel: TransacaodetalhescomercianteViewModel
    private lateinit var viewModelFactory: TransacaodetalhescomercianteViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transacaodetalhescomerciante,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = TransacaodetalhescomercianteFragmentArgs.fromBundle(requireArguments())
        // Inicializa as variaveis do ViewModel
        viewModelFactory = TransacaodetalhescomercianteViewModelFactory(args.transacao)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[TransacaodetalhescomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}