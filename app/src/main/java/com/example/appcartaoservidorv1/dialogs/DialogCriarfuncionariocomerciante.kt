package com.example.appcartaoservidorv1.dialogs

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
import com.example.appcartaoservidorv1.databinding.DialogCriarfuncionariocomercianteBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogCriarfuncionariocomercianteViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogCriarfuncionariocomercianteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogCriarfuncionariocomerciante : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogCriarfuncionariocomercianteBinding
    lateinit var args: DialogCriarfuncionariocomercianteArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogCriarfuncionariocomercianteViewModel
    private lateinit var viewModelFactory: DialogCriarfuncionariocomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_criarfuncionariocomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogCriarfuncionariocomercianteArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogCriarfuncionariocomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogCriarfuncionariocomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------

        // Fecha o Dialog
        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------
        // Criar gerente
        binding.btnCriar.setOnClickListener {
            binding.NomeContainer.error = null
            if (!nomeOk()) {
                binding.NomeContainer.error = "Nome invalido"
            } else {
                viewModel.criarFuncionario(
                    viewModel.nome.value!!,
                    viewModel.matricula,
                    viewModel.token,
                )
            }
        }
        //------------------------------------------------------------------------------------------
        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogCriarfuncionariocomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogCriarfuncionariocomercianteViewModel.ApiStatus.DONE -> {
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
                setFragmentResult("DialogCriarFuncionario", bundleOf("_" to "_"))
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun nomeOk(): Boolean {
        if (viewModel.nome.value.isNullOrEmpty())
            return false
        return true
    }

    //----------------------------------------------------------------------------------------------
    private fun btnCriar() {
        binding.btnCriar.visibility = View.INVISIBLE
    }

    //----------------------------------------------------------------------------------------------
    private fun infoContainer() {
        binding.infoContainer.visibility = View.GONE
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
        btnCriar()
        infoContainer()
        cardContainer()
        bar(true)
        resultados(false)
    }

    private fun estadoResultados(usuarioInserido: Boolean, text: String) {
        infoContainer()
        bar(false)
        resultados(true)
        mensagem(text)
        btnCriar()
        if (usuarioInserido) {
            iconSucesso()
        } else {
            iconError()
        }
    }
}