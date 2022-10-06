package com.example.appcartaoservidorv1.dialogs.comerciantegerente

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComerciantegerenteFuncionarioDeletarBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioDeletarViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioDeletarViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComerciantegerenteFuncionarioDeletar : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComerciantegerenteFuncionarioDeletarBinding
    lateinit var args: DialogComerciantegerenteFuncionarioDeletarArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComerciantegerenteFuncionarioDeletarViewModel
    private lateinit var viewModelFactory: DialogComerciantegerenteFuncionarioDeletarViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciantegerente_funcionario_deletar,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComerciantegerenteFuncionarioDeletarArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComerciantegerenteFuncionarioDeletarViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComerciantegerenteFuncionarioDeletarViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------

        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------

        // Deletar gerente
        binding.btnSim.setOnClickListener {
            viewModel.deletarFuncionario(
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
                DialogComerciantegerenteFuncionarioDeletarViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogComerciantegerenteFuncionarioDeletarViewModel.ApiStatus.DONE -> {
                    estadoResultados(viewModel.response.value!!.b, viewModel.response.value!!.s)
                }
                else -> {
                    estadoResultados(viewModel.response.value!!.b, viewModel.response.value!!.s)
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
                setFragmentResult(
                    "DialogDeletarFuncionarioGerenteComerciante",
                    bundleOf("_" to "_")
                )
        }
    }
    //----------------------------------------------------------------------------------------------

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
//            binding.Bar.visibility = View.VISIBLE
        } else {
//            binding.Bar.visibility = View.GONE
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