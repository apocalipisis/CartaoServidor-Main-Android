package com.example.appcartaoservidorv1.fragments.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentComerciantegerenteFuncionarioDetalhesBinding
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteFuncionarioDetalhesToDialogDeletar
import com.example.appcartaoservidorv1.services.utilidades.fromComerciantegerenteFuncionarioDetalhesToDialogEditar
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteFuncionarioDetalhesViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciantegerente.ComerciantegerenteFuncionarioDetalhesViewModelFactory

class ComerciantegerenteFuncionarioDetalhesFragment : BaseFragment() {
    lateinit var binding: FragmentComerciantegerenteFuncionarioDetalhesBinding
    lateinit var args: ComerciantegerenteFuncionarioDetalhesFragmentArgs

    private lateinit var viewModel: ComerciantegerenteFuncionarioDetalhesViewModel
    private lateinit var viewModelFactory: ComerciantegerenteFuncionarioDetalhesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_comerciantegerente_funcionario_detalhes,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = ComerciantegerenteFuncionarioDetalhesFragmentArgs.fromBundle(requireArguments())
        // Inicializa as variaveis do ViewModel
        viewModelFactory = ComerciantegerenteFuncionarioDetalhesViewModelFactory(args.funcionario, args.token)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ComerciantegerenteFuncionarioDetalhesViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para editar um perfil
        binding.btnEditar.setOnClickListener {
            fromComerciantegerenteFuncionarioDetalhesToDialogEditar(
                this,
                viewModel.funcionario.Matricula,
                args.token
            )
        }

        // Btn que traz o dialog para Deletar um perfil
        binding.btnDeletar.setOnClickListener {
            fromComerciantegerenteFuncionarioDetalhesToDialogDeletar(
                this,
                viewModel.funcionario.Matricula,
                args.token
            )
        }

        // Escuta se o dialog de editar foi fechado
        setFragmentResultListener("dialogEditarFuncionarioComerciantegerente") { _, bundle ->
            val b = bundle.getBoolean("resultBollean")
            val s = bundle.getString("resultString")

            if (b) {
                binding.Status.text = s
            }
        }

        // Escuta se o dialog de deletar foi fechado
        setFragmentResultListener("dialogDeletarFuncionarioComerciantegerente") { _, bundle ->
            val usuarioDeletado = bundle.getBoolean("bundleDialogDeletarFuncionarioComerciantegerente")
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