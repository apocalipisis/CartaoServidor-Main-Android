package com.example.appcartaoservidorv1.dialogs.servidor

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogServidorNovocartaoBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorNovocartaoViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.servidor.DialogServidorNovocartaoViewModelFactory
import com.example.appcartaoservidorv1.services.redirecionamento.fromNovocartaoDialogToNovocartaoFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogServidorNovoCartao : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogServidorNovocartaoBinding
    lateinit var args: DialogServidorNovoCartaoArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogServidorNovocartaoViewModel
    private lateinit var viewModelFactory: DialogServidorNovocartaoViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_servidor_novocartao,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogServidorNovoCartaoArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogServidorNovocartaoViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogServidorNovocartaoViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------
        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------

        // Deletar gerente
        binding.btnSim.setOnClickListener {
            viewModel.novoCartao(
                viewModel.matricula,
                viewModel.token,
            )
        }
        //------------------------------------------------------------------------------------------

        binding.btnNao.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogServidorNovocartaoViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogServidorNovocartaoViewModel.ApiStatus.DONE -> {
                    estadoResultados(
                        viewModel.response.value!!.b,
                        viewModel.response.value!!.s
                    )
                }
                DialogServidorNovocartaoViewModel.ApiStatus.ERROR -> {
                    estadoResultados(
                        viewModel.response.value!!.b,
                        viewModel.response.value!!.s
                    )
                }
            }
        }
        //------------------------------------------------------------------------------------------
        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    //----------------------------------------------------------------------------------------------

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val response = viewModel.response.value
        if (response != null) {
            if (response.b)
                fromNovocartaoDialogToNovocartaoFragment(this, viewModel.matricula, viewModel.token)
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