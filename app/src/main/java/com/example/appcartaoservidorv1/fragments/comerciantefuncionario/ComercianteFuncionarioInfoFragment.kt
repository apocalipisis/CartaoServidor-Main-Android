package com.example.appcartaoservidorv1.fragments.comerciantefuncionario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantefuncionarioInfoBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario.ComercianteFuncionarioInfoViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantefuncionario.ComercianteFuncionarioInfoViewModelFactory

class ComercianteFuncionarioInfoFragment : BaseFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComerciantefuncionarioInfoBinding
    lateinit var args: ComercianteFuncionarioInfoFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: ComercianteFuncionarioInfoViewModel
    private lateinit var viewModelFactory: ComercianteFuncionarioInfoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comerciantefuncionario_info, container, false)
        // Recupera as variaveis passada para a view
        args = ComercianteFuncionarioInfoFragmentArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = ComercianteFuncionarioInfoViewModelFactory(
            args.nome,
            args.matricula,
            args.tipoUsuario,
            args.status,
            args.matriculaMae,
            args.cnpj,
            args.nomeComerciante,
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ComercianteFuncionarioInfoViewModel::class.java]
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