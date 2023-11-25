package com.example.appcartaoservidorv1.dialogs.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogServidorComerciantesdetalhesBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorComerciantesdetalhesViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorComerciantesdetalhesViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogServidorComerciantesdetalhes : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogServidorComerciantesdetalhesBinding
    lateinit var args: DialogServidorComerciantesdetalhesArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogServidorComerciantesdetalhesViewModel
    private lateinit var viewModelFactory: DialogServidorComerciantesdetalhesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_servidor_comerciantesdetalhes,
                container,
                false
            )
        // Recupera as variaveis passada para a view
        args = DialogServidorComerciantesdetalhesArgs.fromBundle(
            requireArguments()
        )
        // Inicializa as variaveis do ViewModel
        viewModelFactory = DialogServidorComerciantesdetalhesViewModelFactory(args.comerciante)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogServidorComerciantesdetalhesViewModel::class.java]
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