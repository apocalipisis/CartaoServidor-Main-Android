package com.example.appcartaoservidorv1.dialogs.servidor

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogServidorCartaoCancelarsolicitacaoBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorCartaoCancelarsolicitacaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorCartaoCancelarsolicitacaoViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogServidorCartaoCacelarsolicitacao : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogServidorCartaoCancelarsolicitacaoBinding
    lateinit var args: DialogServidorCartaoCacelarsolicitacaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogServidorCartaoCancelarsolicitacaoViewModel
    private lateinit var viewModelFactory: DialogServidorCartaoCancelarsolicitacaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_servidor_cartao_cancelarsolicitacao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogServidorCartaoCacelarsolicitacaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogServidorCartaoCancelarsolicitacaoViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogServidorCartaoCancelarsolicitacaoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Fecha o Dialog
        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        binding.btnNao.setOnClickListener { dialog?.dismiss() }

        // Btn que camceça a solicitação
        binding.btnSim.setOnClickListener {
            viewModel.cancelarSolicitacao()
        }

        // Monitora o estado da consulta a API para os dados do cartão
        viewModel.status.observe(
            viewLifecycleOwner
        ) { status ->
            when (status) {
                DialogServidorCartaoCancelarsolicitacaoViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogServidorCartaoCancelarsolicitacaoViewModel.ApiStatus.DONE -> {
                    estadoResultados(
                        viewModel.response.b,
                        viewModel.response.s
                    )
                }
                else -> {
                    estadoResultados(
                        viewModel.response.b,
                        viewModel.response.s
                    )
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val response = viewModel.response
        if (response.b){
            findNavController().navigateUp()
            findNavController().navigateUp()
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun btnContainer() {
        binding.btnContainer.visibility = View.GONE
    }

    //----------------------------------------------------------------------------------------------
    private fun cardContainer() {
        binding.CardContainer.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun bar(isVisible: Boolean) {
        if (isVisible) {
            binding.Bar.visibility = View.VISIBLE
        } else {
            binding.Bar.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun resultados(isVisible: Boolean) {
        if (isVisible) {
            binding.Resultados.visibility = View.VISIBLE
        } else {
            binding.Resultados.visibility = View.GONE
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun iconSucesso() {
        binding.iconSucesso.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun iconError() {
        binding.iconError.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun mensagem(text: String) {
        binding.mensagem.text = text
    }
    //----------------------------------------------------------------------------------------------

    private fun estadoCarregando() {
        btnContainer()
        cardContainer()
        bar(true)
        resultados(false)
    }

    private fun estadoResultados(usuarioInserido: Boolean, text: String) {
        bar(false)
        resultados(true)
        mensagem(text)
        if (usuarioInserido) {
            iconSucesso()
        } else {
            iconError()
        }
    }
}