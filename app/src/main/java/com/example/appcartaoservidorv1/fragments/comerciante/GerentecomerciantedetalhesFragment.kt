package com.example.appcartaoservidorv1.fragments.comerciante

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.FragmentGerentecomerciantedetalhesBinding
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.example.appcartaoservidorv1.services.utilidades.BaseFragment
import com.example.appcartaoservidorv1.services.utilidades.fromEscolhasgerentecomercianteToDialog
import com.example.appcartaoservidorv1.services.utilidades.fromGerentecomercianteDetalhesToDialogDeletar
import com.example.appcartaoservidorv1.services.utilidades.fromGerentecomercianteDetalhesToDialogEditar
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentescomerciantedetalhesViewModel
import com.example.appcartaoservidorv1.viewmodels.comerciante.GerentescomerciantedetalhesViewModelFactory

class GerentecomerciantedetalhesFragment : BaseFragment() {
    lateinit var binding: FragmentGerentecomerciantedetalhesBinding
    lateinit var args: GerentecomerciantedetalhesFragmentArgs

    private lateinit var viewModel: GerentescomerciantedetalhesViewModel
    private lateinit var viewModelFactory: GerentescomerciantedetalhesViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_gerentecomerciantedetalhes,
            container,
            false
        )
        // Recupera as variaveis passada para a view
        args = GerentecomerciantedetalhesFragmentArgs.fromBundle(requireArguments())
        // Inicializa as variaveis do ViewModel
        viewModelFactory = GerentescomerciantedetalhesViewModelFactory(args.gerente, args.token)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[GerentescomerciantedetalhesViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Btn que traz o dialog para editar um perfil
        binding.btnEditar.setOnClickListener {
            fromGerentecomercianteDetalhesToDialogEditar(
                this,
                viewModel.gerente.Matricula,
                viewModel.token
            )
        }
        binding.btnDeletar.setOnClickListener {
            fromGerentecomercianteDetalhesToDialogDeletar(
                this,
                viewModel.gerente.Matricula,
                viewModel.token
            )
        }

        // Escuta se o dialog de editar foi fechado
        setFragmentResultListener("dialogEditarComerciante") { _, bundle ->
            val b = bundle.getBoolean("resultBollean")
            val s = bundle.getString("resultString")

            if (b) {
                binding.Status.text = s
            }
        }

        // Escuta se o dialog de deletar foi fechado
        setFragmentResultListener("dialogDeletarGerente") { _, bundle ->
            val usuarioDeletado = bundle.getBoolean("bundleDialogDeletarGerente")
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