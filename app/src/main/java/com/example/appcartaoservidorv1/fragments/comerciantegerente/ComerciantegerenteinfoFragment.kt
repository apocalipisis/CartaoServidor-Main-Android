package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteInfoBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteinfoViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteinfoViewModelFactory

class ComerciantegerenteinfoFragment : BaseFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantegerenteInfoBinding
    lateinit var args: ComerciantegerenteinfoFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComerciantegerenteinfoViewModel
    private lateinit var viewModelFactory: ComerciantegerenteinfoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comerciantegerente_info, container, false)
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteinfoFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComerciantegerenteinfoViewModelFactory(
            args.nome,
            args.matricula,
            args.tipoUsuario,
            args.status,
            args.cpf,
            args.matriculaMae,
            args.cnpj,
            args.nomeComerciante,
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ComerciantegerenteinfoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}