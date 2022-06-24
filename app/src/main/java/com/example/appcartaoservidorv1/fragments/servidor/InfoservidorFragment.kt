package com.example.appcartaoservidorv1.fragments.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentInfoservidorBinding
import com.example.appcartaoservidorv1.viewmodels.servidor.InfoservidorViewModel
import com.example.appcartaoservidorv1.viewmodels.servidor.InfoservidorViewModelFactory

class InfoservidorFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInfoservidorBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: InfoservidorViewModel
    private lateinit var viewModelFactory: InfoservidorViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_infoservidor, container, false)
        // Recupera as variaveis passada para a view
        val args = InfoservidorFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InfoservidorViewModelFactory(
            args.matricula,
            args.nome,
            args.cpf,
            args.tipoUsuario,
            args.status,
            args.instituto,
            args.limiteMensal.toDouble()
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[InfoservidorViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}