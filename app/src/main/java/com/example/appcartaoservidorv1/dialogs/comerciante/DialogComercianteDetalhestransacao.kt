package com.example.appcartaoservidorv1.dialogs.comerciante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComercianteDetalhestransacaoBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciante.DialogComercianteDetalhestransacaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciante.DialogComercianteDetalhestransacaoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComercianteDetalhestransacao : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComercianteDetalhestransacaoBinding
    lateinit var args: DialogComercianteDetalhestransacaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComercianteDetalhestransacaoViewModel
    private lateinit var viewModelFactory: DialogComercianteDetalhestransacaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciante_detalhestransacao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComercianteDetalhestransacaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComercianteDetalhestransacaoViewModelFactory(args.transacao)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComercianteDetalhestransacaoViewModel::class.java]
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