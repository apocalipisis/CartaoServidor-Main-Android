package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentFuncionarioscomercianteBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromFuncionariosToFuncionario
import com.example.appcartaoservidorv1.services.utilidades.fromFuncionariosToGerentes
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionarioscomercianteViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionarioscomercianteViewModelFactory

class FuncionarioscomercianteFragment : BaseFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: FragmentFuncionarioscomercianteBinding
    lateinit var args: HistoricovendasFragmentArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: FuncionarioscomercianteViewModel
    private lateinit var viewModelFactory: FuncionarioscomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_funcionarioscomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = HistoricovendasFragmentArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = FuncionarioscomercianteViewModelFactory(args.matricula, args.token)
        viewModel = ViewModelProvider(this, viewModelFactory)[FuncionarioscomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        binding.btnGerentes.setOnClickListener { fromFuncionariosToGerentes(this, viewModel.matricula, viewModel.token) }
        binding.btnFuncionarios.setOnClickListener { fromFuncionariosToFuncionario(this, viewModel.matricula, viewModel.token) }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}