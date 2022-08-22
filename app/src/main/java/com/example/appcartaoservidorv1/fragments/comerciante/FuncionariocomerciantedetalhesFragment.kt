package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentFuncionariocomerciantedetalheBinding
import com.example.appcartaoservidorv1.databinding.FragmentGerentecomerciantedetalhesBinding
import com.example.appcartaoservidorv1.services.utilidades.*
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionariocomerciantedetalhesViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentescomerciantedetalhesViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentescomerciantedetalhesViewModelFactory
import com.example.appcartaoservidorv1.viewmodels.comerciante.FuncionariocomerciantedetalhesViewModel as FuncionariocomerciantedetalhesViewModel1

class FuncionariocomerciantedetalhesFragment : BaseFragment() {
    lateinit var binding: FragmentFuncionariocomerciantedetalheBinding
    lateinit var args: FuncionariocomerciantedetalhesFragmentArgs

    private lateinit var viewModel: FuncionariocomerciantedetalhesViewModel1
    private lateinit var viewModelFactory: FuncionariocomerciantedetalhesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_funcionariocomerciantedetalhe,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = FuncionariocomerciantedetalhesFragmentArgs.fromBundle(requireArguments())
        // Inicializa as variaveis do ViewModel
        viewModelFactory = FuncionariocomerciantedetalhesViewModelFactory(args.funcionario, args.token)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[FuncionariocomerciantedetalhesViewModel1::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para editar um perfil
        binding.btnEditar.setOnClickListener {
            fromFuncionariocomercianteDetalhesToDialogEditar(
                this,
                viewModel.funcionario.Matricula,
                args.token
            )
        }

        // Btn que traz o dialog para Deletar um perfil
        binding.btnDeletar.setOnClickListener {
            fromFuncionariocomercianteDetalhesToDialogDeletar(
                this,
                viewModel.funcionario.Matricula,
                args.token
            )
        }

        // Escuta se o dialog de editar foi fechado
        setFragmentResultListener("dialogEditarFuncionario") { _, bundle ->
            val b = bundle.getBoolean("resultBollean")
            val s = bundle.getString("resultString")

            if (b) {
                binding.Status.text = s
            }
        }

        // Escuta se o dialog de deletar foi fechado
        setFragmentResultListener("dialogDeletarFuncionario") { _, bundle ->
            val usuarioDeletado = bundle.getBoolean("bundleDialogDeletarFuncionario")
            if(usuarioDeletado){
                findNavController().navigateUp()
                findNavController().navigateUp()
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}