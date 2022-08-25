package com.example.appcartaoservidorv1.dialogs.comerciantegerente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogComerciantegerenteFuncionarioEditarBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioEditarViewModel
import com.example.appcartaoservidorv1.dialogsviewmodels.comerciantegerente.DialogComerciantegerenteFuncionarioEditarViewModelFactory
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogComerciantegerenteFuncionarioEditar:  BottomSheetDialogFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: DialogComerciantegerenteFuncionarioEditarBinding
    lateinit var args: DialogComerciantegerenteFuncionarioEditarArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogComerciantegerenteFuncionarioEditarViewModel
    private lateinit var viewModelFactory: DialogComerciantegerenteFuncionarioEditarViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_comerciantegerente_funcionario_editar,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogComerciantegerenteFuncionarioEditarArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = DialogComerciantegerenteFuncionarioEditarViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogComerciantegerenteFuncionarioEditarViewModel::class.java]
        // Faz o binding com o viewModel
        binding.viewModel = viewModel

        // Fecha o Dialog
        binding.btnSair.setOnClickListener { dialog?.dismiss() }

        // Criar gerente
        binding.btnSalvar.setOnClickListener {
            viewModel.editarFuncionario(
                viewModel.matricula,
                binding.radioAtivo.isChecked,
                viewModel.token,
            )
        }

        // Coloca a barra de atualização como visivel
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DialogComerciantegerenteFuncionarioEditarViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogComerciantegerenteFuncionarioEditarViewModel.ApiStatus.DONE -> {
                    if (viewModel.response.value!!.b){
                        enviaDadosParaFragment(viewModel.response.value!!)
                    }
                    estadoResultados(viewModel.response.value!!.b)
                }
                DialogComerciantegerenteFuncionarioEditarViewModel.ApiStatus.ERROR -> {
                    estadoResultados(viewModel.response.value!!.b)
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun enviaDadosParaFragment(result: ParBoolString) {
        if (viewModel.status.value == DialogComerciantegerenteFuncionarioEditarViewModel.ApiStatus.DONE){
            val bundle = bundleOf("resultBollean" to result.b)
            bundle.putString("resultString", result.s)
            setFragmentResult("dialogEditarFuncionarioComerciantegerente", bundle)
        }
    }

    private fun estadoCarregando() {
        binding.Bar.visibility = View.VISIBLE
        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.GONE
    }

    private fun estadoResultados(usuarioEditado: Boolean?) {
        binding.Bar.visibility = View.GONE

        binding.btnSalvar.visibility = View.GONE

        binding.EstadoNormal.visibility = View.GONE
        binding.resultados.visibility = View.VISIBLE
        binding.result.visibility = View.INVISIBLE

        if (usuarioEditado!!) {
            binding.ImageStatusSucess.visibility = View.VISIBLE
            binding.ImageStatusSucess.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                binding.usuarioInserido.visibility = View.VISIBLE
                binding.result.visibility = View.VISIBLE
                binding.result.text = "Gerente editado"
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

}