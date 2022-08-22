package com.example.appcartaoservidorv1.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.example.appcartaoservidorv1.R
import com.example.appcartaoservidorv1.databinding.DialogEditarfuncionariocomercianteBinding
import com.example.appcartaoservidorv1.databinding.DialogEditargerentecomercianteBinding
import com.example.appcartaoservidorv1.dialogsviewmodels.*
import com.example.appcartaoservidorv1.models.auxiliares.ParBoolString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogEditarfucionariocomerciante :  BottomSheetDialogFragment() {
    // Variavel responsavel pelo binding
    lateinit var binding: DialogEditarfuncionariocomercianteBinding
    lateinit var args: DialogEditargerentecomercianteArgs

    // Variavel que representa o viewModel
    private lateinit var viewModel: DialogEditarfuncionariocomercianteViewModel
    private lateinit var viewModelFactory: DialogEditarfuncionariocomercianteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_editarfuncionariocomerciante,
                container,
                false
            )

        // Recupera as variaveis passada para a view
        args = DialogEditargerentecomercianteArgs.fromBundle(requireArguments())

        // Inicializa o ViewModel e passa as variaveis
        viewModelFactory = DialogEditarfuncionariocomercianteViewModelFactory(args.matricula, args.token)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogEditarfuncionariocomercianteViewModel::class.java]
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
                DialogEditarfuncionariocomercianteViewModel.ApiStatus.LOADING -> {
                    estadoCarregando()
                }
                DialogEditarfuncionariocomercianteViewModel.ApiStatus.DONE -> {
                    if (viewModel.response.value!!.b){
                        enviaDadosParaFragment(viewModel.response.value!!)
                    }
                    estadoResultados(viewModel.response.value!!.b)
                }
                DialogEditarfuncionariocomercianteViewModel.ApiStatus.ERROR -> {
                    estadoResultados(viewModel.response.value!!.b)
                }
            }
        }

        // Configura o ciclo de vida
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun enviaDadosParaFragment(result: ParBoolString) {
        if (viewModel.status.value == DialogEditarfuncionariocomercianteViewModel.ApiStatus.DONE){
            val bundle = bundleOf("resultBollean" to result.b)
            bundle.putString("resultString", result.s)
            setFragmentResult("dialogEditarFuncionario", bundle)
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