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
import com.example.appcartaoservidorv1.databinding.DialogComerciantegerenteFuncionarioCriarBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioCriarViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioCriarViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComerciantegerenteFuncionarioCriar : BottomSheetDialogFragment() {

    // Variavel responsavel pelo binding
    lateinit var binding: DialogComerciantegerenteFuncionarioCriarBinding
    lateinit var args: DialogComerciantegerenteFuncionarioCriarArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComerciantegerenteFuncionarioCriarViewModel
    private lateinit var viewModelFactory: DialogComerciantegerenteFuncionarioCriarViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciantegerente_funcionario_criar,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComerciantegerenteFuncionarioCriarArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory =
            DialogComerciantegerenteFuncionarioCriarViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[DialogComerciantegerenteFuncionarioCriarViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Fecha o Dialog
        binding.btnSair.setOnClickListener { dialog?.dismiss() }

        // Criar gerente
        binding.btnCriar.setOnClickListener {
            viewModel.criarFuncionario(
                viewModel.nome.value!!,
                binding.radioAtivo.isChecked,
                viewModel.matricula,
                viewModel.token,
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogComerciantegerenteFuncionarioCriarViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogComerciantegerenteFuncionarioCriarViewModel.ApiStatus.DONE -> {
                    estadoResultados(viewModel.response.value?.b)
                }
                DialogComerciantegerenteFuncionarioCriarViewModel.ApiStatus.ERROR -> {
                    estadoResultados(viewModel.response.value?.b)
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun estadoCarregando() {
        binding.Bar.visibility = View.VISIBLE
        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.GONE
    }

    private fun estadoResultados(usuarioInserido: Boolean?) {
        binding.Bar.visibility = View.GONE

        binding.btnCriar.visibility = View.GONE

        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.VISIBLE
        binding.result.visibility = View.INVISIBLE

        if (usuarioInserido!!) {
            binding.ImageStatusSucess.visibility = View.VISIBLE
            binding.ImageStatusSucess.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioInserido.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
            }
        } else {
            binding.ImageStatusError.visibility = View.VISIBLE
            binding.ImageStatusError.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioNaoInserido.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (viewModel.status.value == DialogComerciantegerenteFuncionarioCriarViewModel.ApiStatus.DONE) {
            setFragmentResult(
                "DialogCriarfuncionarioComerciantegerente",
                bundleOf("bundleDialogCriarFuncionarioComerciantegerente" to "dialogCriarFuncionarioComerciantegerenteFechou")
            )
        }
    }


}