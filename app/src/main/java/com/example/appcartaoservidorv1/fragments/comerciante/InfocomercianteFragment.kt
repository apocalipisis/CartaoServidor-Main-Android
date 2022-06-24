package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentInfocomercianteBinding
import com.example.appcartaoservidorv1.viewmodels.comerciante.InfocomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.InfocomercianteViewModelFactory

class InfocomercianteFragment : Fragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentInfocomercianteBinding

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_infocomerciante, container, false)
        // Recupera as variaveis passada para a view
        val args = InfocomercianteFragmentArgs.fromBundle(requireArguments())
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

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}