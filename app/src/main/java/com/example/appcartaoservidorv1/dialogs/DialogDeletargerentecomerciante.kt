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
import com.example.appcartaoservidorv1.databinding.DialogDeletargerentecomercianteBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogDeletargerentecomercianteViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.DialogDeletargerentecomercianteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogDeletargerentecomerciante : BottomSheetDialogFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: DialogDeletargerentecomercianteBinding
    lateinit var args: DialogDeletargerentecomercianteArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogDeletargerentecomercianteViewModel
    private lateinit var viewModelFactory: DialogDeletargerentecomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_deletargerentecomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogDeletargerentecomercianteArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogDeletargerentecomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogDeletargerentecomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------

        // Fecha o Dialog
        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------

        // Deletar gerente
        binding.btnSim.setOnClickListener {
            viewModel.deletarGerente(
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
                DialogDeletargerentecomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogDeletargerentecomercianteViewModel.ApiStatus.DONE -> {
                    estadoResultados(viewModel.response.value!!.b, viewModel.response.value!!.s)
                }
                DialogDeletargerentecomercianteViewModel.ApiStatus.ERROR -> {
                    estadoResultados(viewModel.response.value!!.b, viewModel.response.value!!.s)
                }
            }
        }
        //------------------------------------------------------------------------------------------

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val response = viewModel.response.value
        if (response != null) {
            if (response.b)
                setFragmentResult("DialogDeletarGerente", bundleOf("_" to "_"))
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