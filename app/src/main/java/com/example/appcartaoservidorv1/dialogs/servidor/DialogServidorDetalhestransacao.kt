package com.example.appcartaoservidorv1.dialogs.servidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogServidorDetalhestransacaoBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorDetalhestransacaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorDetalhestransacaoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogServidorDetalhestransacao : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogServidorDetalhestransacaoBinding
    lateinit var args: DialogServidorDetalhestransacaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogServidorDetalhestransacaoViewModel
    private lateinit var viewModelFactory: DialogServidorDetalhestransacaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_servidor_detalhestransacao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogServidorDetalhestransacaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogServidorDetalhestransacaoViewModelFactory(args.transacao)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogServidorDetalhestransacaoViewModel::class.java]
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