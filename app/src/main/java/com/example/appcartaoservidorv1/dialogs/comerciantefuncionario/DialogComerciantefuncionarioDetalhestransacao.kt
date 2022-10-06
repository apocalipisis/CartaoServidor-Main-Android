package com.example.appcartaoservidorv1.dialogs.comerciantefuncionario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComerciantefuncionarioDetalhestransacaoBinding
import com.example.appcartaoservidorv1.dialogs.comerciantegerente.DialogComerciantegerenteDetalhestransacaoArgs
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantefuncionario.DialogComerciantefuncionarioDetalhestransacaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantefuncionario.DialogComerciantefuncionarioDetalhestransacaoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComerciantefuncionarioDetalhestransacao: BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComerciantefuncionarioDetalhestransacaoBinding
    lateinit var args: DialogComerciantegerenteDetalhestransacaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComerciantefuncionarioDetalhestransacaoViewModel
    private lateinit var viewModelFactory: DialogComerciantefuncionarioDetalhestransacaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciantefuncionario_detalhestransacao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComerciantegerenteDetalhestransacaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComerciantefuncionarioDetalhestransacaoViewModelFactory(args.transacao)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComerciantefuncionarioDetalhestransacaoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            dialog?.dismiss()
        }
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}