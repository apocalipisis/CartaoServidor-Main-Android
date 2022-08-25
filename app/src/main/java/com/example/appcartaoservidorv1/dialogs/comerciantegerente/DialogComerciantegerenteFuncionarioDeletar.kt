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

        // Fecha o Dialog
        binding.btnSair.setOnClickListener { dialog?.dismiss() }

        // Deletar gerente
        binding.btnSim.setOnClickListener {
            viewModel.deletarFuncionario(
                viewModel.matricula,
                viewModel.token,
            )
        }

        binding.btnNao.setOnClickListener { dialog?.dismiss() }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogComerciantegerenteFuncionarioDeletarViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogComerciantegerenteFuncionarioDeletarViewModel.ApiStatus.DONE -> {
                    estadoResultados(viewModel.response.value?.b)
                }
                DialogComerciantegerenteFuncionarioDeletarViewModel.ApiStatus.ERROR -> {
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
        binding.pergunta.visibility = View.GONE
        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.GONE
    }

    private fun estadoResultados(usuarioDeletado: Boolean?) {
        binding.Bar.visibility = View.GONE

        binding.btnSim.visibility = View.GONE
        binding.btnNao.visibility = View.GONE

        binding.EstadoNormal.visibility = View.GONE
        binding.pergunta.visibility = View.GONE
        binding.resultados.visibility = View.VISIBLE
        binding.result.visibility = View.INVISIBLE

        if (usuarioDeletado!!) {
            binding.ImageStatusSucess.visibility = View.VISIBLE
            binding.ImageStatusSucess.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioDeletado.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
                viewModel.deletado = true
            }
        } else {
            binding.ImageStatusError.visibility = View.VISIBLE
            binding.ImageStatusError.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioNaoDeletado.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (viewModel.deletado) {
            setFragmentResult(
                "dialogDeletarFuncionarioComerciantegerente",
                bundleOf("bundleDialogDeletarFuncionarioComerciantegerente" to true)
            )
        }
    }


}