package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentCompraservidorBinding
import com.example.appcartaoservidorv1.databinding.FragmentInserirsenhaBinding
import com.example.appcartaoservidorv1.fragments.servidor.CompraservidorFragmentArgs
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InserirsenhaViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.CompraservidorViewModelFactory

class InserirsenhaFragment  : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInserirsenhaBinding
    lateinit var args: InserirsenhaFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: InserirsenhaViewModel
    private lateinit var viewModelFactory: InserirsenhaViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_inserirsenha, container, false)
        // Recupera as variaveis passada para a view
        args = InserirsenhaFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InserirsenhaViewModelFactory(args.matricula, args.nome)
        viewModel = ViewModelProvider(this, viewModelFactory).get(InserirsenhaViewModel::class.java)
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}