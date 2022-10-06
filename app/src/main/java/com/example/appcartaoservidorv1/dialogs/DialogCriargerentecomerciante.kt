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
import com.example.appcartaoservidorv1.databinding.DialogCriargerentecomercianteBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.CriargerentecomercianteViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.CriargerentecomercianteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogCriargerentecomerciante : BottomSheetDialogFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: DialogCriargerentecomercianteBinding
    lateinit var args: DialogCriargerentecomercianteArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: CriargerentecomercianteViewModel
    private lateinit var viewModelFactory: CriargerentecomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_criargerentecomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogCriargerentecomercianteArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = CriargerentecomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CriargerentecomercianteViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel
        //------------------------------------------------------------------------------------------
        // Fecha o Dialog
        binding.btnVoltar.setOnClickListener { dialog?.dismiss() }
        //------------------------------------------------------------------------------------------
        // Criar gerente
        binding.btnCriar.setOnClickListener {
            binding.NomeContainer.error = null
            binding.CpfContainer.error = null
            if (!nomeOk()) {
                binding.NomeContainer.error = "Nome invalido"
            } else {
                if (!cpfOk()) {
                    binding.CpfContainer.error = "CPF invalido"
                } else {
                    viewModel.criarGerente(
                        viewModel.nome.value!!,
                        viewModel.cpf.value!!,
                        viewModel.matricula,
                        viewModel.token,
                    )
                }
            }
        }
        //------------------------------------------------------------------------------------------
        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                CriargerentecomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                CriargerentecomercianteViewModel.ApiStatus.DONE -> {
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val response = viewModel.response.value
        if (response != null) {
            if (response.b)
                setFragmentResult("DialogCriarGerente", bundleOf("_" to "_"))
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun nomeOk(): Boolean {
        if (viewModel.nome.value.isNullOrEmpty())
            return false
        return true
    }

    private fun cpfOk(): Boolean {
        if (viewModel.cpf.value?.length != 11)
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