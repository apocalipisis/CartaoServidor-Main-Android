package com.example.appcartaoservidorv1.dialogs.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComerciantegerenteDetalhestransacaoBinding
import com.example.appcartaoservidorv1.dialogs.comerciante.DialogComercianteDetalhestransacaoArgs
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteDetalhestransacaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteDetalhestransacaoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComerciantegerenteDetalhestransacao: BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComerciantegerenteDetalhestransacaoBinding
    lateinit var args: DialogComerciantegerenteDetalhestransacaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComerciantegerenteDetalhestransacaoViewModel
    private lateinit var viewModelFactory: DialogComerciantegerenteDetalhestransacaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciantegerente_detalhestransacao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComerciantegerenteDetalhestransacaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComerciantegerenteDetalhestransacaoViewModelFactory(args.transacao)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComerciantegerenteDetalhestransacaoViewModel::class.java]
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