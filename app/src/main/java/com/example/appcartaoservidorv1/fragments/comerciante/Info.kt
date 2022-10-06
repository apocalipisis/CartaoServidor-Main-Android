package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComercianteInfoBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.viewmodels.comerciante.InfocomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InfocomercianteViewModelFactory

class Info : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentComercianteInfoBinding

    // Variavel que representa o viewModel
    private lateinit var viewModel: InfocomercianteViewModel
    private lateinit var viewModelFactory: InfocomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comerciante_info, container, false)
        // Recupera as variaveis passada para a view
        val args = InfoArgs.fromBundle(requireArguments())
        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = InfocomercianteViewModelFactory(
            args.matricula,
            args.nome,
            args.cnpj,
            args.tipoUsuario,
            args.status,
            args.pagamentoUsoSistema
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[InfocomercianteViewModel::class.java]
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